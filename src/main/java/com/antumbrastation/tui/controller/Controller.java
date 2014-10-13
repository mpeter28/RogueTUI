package com.antumbrastation.tui.controller;

import com.antumbrastation.tui.elements.DisplayElement;
import com.antumbrastation.tui.view.TextPanel;

import java.awt.event.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Controller implements KeyListener, MouseListener, MouseMotionListener {
    private TextPanel view;
    private BlockingQueue<QueuedInput> queue;

    private List<DisplayElement> elements;

    private int height;
    private int width;

    private int mouseRow;
    private int mouseColumn;

    private Set<String> specialKeys;

    public Controller(TextPanel view, List<DisplayElement> elements) {
        this.view = view;
        this.elements = elements;

        mouseRow = -1;
        mouseColumn = -1;

        height = view.getGridHeight();
        width = view.getGridWidth();

        queue = new LinkedBlockingQueue<QueuedInput>();

        specialKeys = new HashSet<String>();
    }

    public void runTask(InputTask task) {
        if (task.initialize()) {
            for (DisplayElement element: elements) {
                element.display(view.getDisplayView());
            }
            view.repaint();
        }

        boolean done = false;
        while (!done) {
            try {
                QueuedInput queuedInput = queue.poll(1000, TimeUnit.MILLISECONDS);

                if (queuedInput != null) {
                    boolean redraw = queuedInput.process(task);
                    if (redraw) {
                        for (DisplayElement element: elements) {
                            element.display(view.getDisplayView());
                        }
                        view.repaint();
                    }

                    if (task.isComplete()) {
                        done = true;
                    }
                }
            } catch (InterruptedException e) {
                //Not an issue
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        queue.add(new TypedCharacter(e.getKeyChar()));
    }

    public void keyPressed(KeyEvent e) {
        if (!Character.isDefined(e.getKeyChar())) {
            queue.add(new SpecialKey(KeyEvent.getKeyText(e.getKeyCode()), true));
        }
    }

    public void keyReleased(KeyEvent e) {
        if (!Character.isDefined(e.getKeyChar())) {
            queue.add(new SpecialKey(KeyEvent.getKeyText(e.getKeyCode()), false));
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        x /= width;
        y /= height;

        queue.add(new MouseInput(y, x, e.getButton()));
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        x /= width;
        y /= height;

        if (mouseRow != y || mouseColumn != x) {
            mouseRow = y;
            mouseColumn = x;
            queue.add(new MouseInput(y, x, MouseEvent.NOBUTTON));
        }
    }

    private interface QueuedInput {
        public boolean process(InputTask task);
    }

    private class TypedCharacter implements QueuedInput {
        private char c;

        private TypedCharacter(char c) {
            this.c = c;
        }

        public boolean process(InputTask task) {
            return task.processKeyHit(c, specialKeys);
        }
    }

    private class MouseInput implements QueuedInput {
        private int row;
        private int column;
        private int button;

        private MouseInput(int row, int column, int button) {
            this.row = row;
            this.column = column;
            this.button = button;
        }

        public boolean process(InputTask task) {
            DisplayElement element = null;

            for (DisplayElement e: elements) {
                if (e.getWindow().inWindowAbsolute(row, column)) {
                    element = e;
                    break;
                }
            }

            if (element != null) {
                row -= element.getWindow().getCornerRow();
                column -= element.getWindow().getCornerColumn();
                if (button != MouseEvent.NOBUTTON) {
                    return task.processMouseClick(row, column, button, element);
                } else {
                    return task.processMouseMove(row, column, element);
                }
            }
            return false;
        }
    }

    private class SpecialKey implements QueuedInput {
        private String specialKey;
        private boolean pressNotRelease;

        private SpecialKey(String specialKey, boolean pressNotRelease) {
            this.specialKey = specialKey;
            this.pressNotRelease = pressNotRelease;
        }

        public boolean process(InputTask task) {
            if (pressNotRelease) {
                specialKeys.add(specialKey);
                return false;
            } else {
                specialKeys.remove(specialKey);
                return task.processKeyHit(specialKey, specialKeys);
            }
        }
    }

}
