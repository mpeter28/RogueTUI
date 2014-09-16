package com.antumbrastation.tui.controller;

import com.antumbrastation.tui.elements.DisplayElement;
import com.antumbrastation.tui.elements.ElementKeeper;
import com.antumbrastation.tui.view.TextPanel;

import java.awt.event.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Controller implements KeyListener, MouseListener, MouseMotionListener {
    private TextPanel view;
    private BlockingQueue queue;

    private ElementKeeper elements;

    private int height;
    private int width;

    private int mouseRow;
    private int mouseColumn;

    public Controller(TextPanel view, ElementKeeper elements) {
        this.view = view;
        this.elements = elements;

        mouseRow = -1;
        mouseColumn = -1;

        height = view.getGridHeight();
        width = view.getGridWidth();

        queue = new LinkedBlockingQueue();
    }

    public void runTask(InputTask task) {
        task.initialize();

        boolean done = false;
        while (!done) {
            try {
                Object input = queue.poll(1000, TimeUnit.MILLISECONDS);

                boolean redraw = false;
                if (input instanceof Character) {
                    redraw = task.processKeyHit((Character) input);
                } else if (input instanceof MouseClick) {
                    MouseClick m = (MouseClick) input;

                    int row = m.row;
                    int column = m.column;
                    DisplayElement element = elements.mouseOnElement(row, column);
                    if (element != null) {
                        row -= element.getWindow().getCornerRow();
                        column -= element.getWindow().getCornerColumn();
                        redraw = task.processMouseClick(row, column, m.button, element);
                    }
                } else if (input instanceof MouseMove) {
                    MouseMove m = (MouseMove) input;

                    int row = m.row;
                    int column = m.column;
                    DisplayElement element = elements.mouseOnElement(row, column);
                    if (element != null) {
                        row -= element.getWindow().getCornerRow();
                        column -= element.getWindow().getCornerColumn();
                        redraw = task.processMouseMove(row, column, element);
                    }
                }

                if (redraw) {
                    elements.displayComponents(view.getDisplayView());
                    view.repaint();
                }

                if (task.isComplete()) {
                    done = true;
                }
            } catch (InterruptedException e) {
                //Not an issue
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        queue.add(e.getKeyChar());
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        x /= width;
        y /= height;

        queue.add(new MouseClick(y, x, e.getButton()));
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
            queue.add(new MouseMove(y, x));
        }
    }

    private class MouseClick {
        int row;
        int column;
        int button;

        public MouseClick(int row, int column, int button) {
            this.row = row;
            this.column = column;
            this.button = button;
        }
    }

    private class MouseMove {
        int row;
        int column;

        public MouseMove(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
