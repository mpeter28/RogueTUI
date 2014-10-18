package com.antumbrastation.tui;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputTaskRunner implements KeyListener, MouseListener, MouseMotionListener {
    private InputTask task;

    private CountDownLatch finishedBarrier;
    private AtomicBoolean startBarrier;

    private int height;
    private int width;

    private int mouseRow;
    private int mouseColumn;

    private Set<String> specialKeys;

    public InputTaskRunner(int height, int width) {
        mouseRow = -1;
        mouseColumn = -1;

        this.height = height;
        this.width = width;

        startBarrier = new AtomicBoolean(false);

        specialKeys = new HashSet<String>();
    }

    public boolean runInputTask(InputTask task) {
        this.task = task;
        finishedBarrier = new CountDownLatch(1);
        startBarrier.set(true);

        try {
            finishedBarrier.await();
        } catch (InterruptedException e) {
            return false;
        }

        return true;
    }

    private void finishedTaskCheck() {
        if (task.isComplete()) {
            task = null;
            startBarrier.set(false);
            finishedBarrier.countDown();
        }
    }

    public void keyTyped(KeyEvent e) {
        if (startBarrier.get()) {
            task.processKeyHit(e.getKeyChar(), specialKeys);
            finishedTaskCheck();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (!Character.isDefined(e.getKeyChar())) {
            specialKeys.add(KeyEvent.getKeyText(e.getKeyCode()));
        }
    }

    public void keyReleased(KeyEvent e) {
        if (!Character.isDefined(e.getKeyChar())) {
            String keyText = KeyEvent.getKeyText(e.getKeyCode());
            specialKeys.remove(keyText);
            if (startBarrier.get()) {
                task.processKeyHit(keyText, specialKeys);
                finishedTaskCheck();
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (startBarrier.get()) {
            int x = e.getX();
            int y = e.getY();

            x /= width;
            y /= height;

            task.processMouseClick(y, x, e.getButton());
            finishedTaskCheck();
        }
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
        if (startBarrier.get()) {
            int x = e.getX();
            int y = e.getY();

            x /= width;
            y /= height;

            if (mouseRow != y || mouseColumn != x) {
                mouseRow = y;
                mouseColumn = x;

                task.processMouseMove(y, x);
                finishedTaskCheck();
            }
        }
    }

}
