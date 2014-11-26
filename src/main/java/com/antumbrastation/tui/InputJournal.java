package com.antumbrastation.tui;

import java.awt.event.*;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class InputJournal implements KeyListener, MouseListener, MouseMotionListener {

    private int rowHeight;
    private int columnWidth;
    private int currentMouseRow;
    private int currentMouseColumn;

    private BlockingQueue<SnapshotUpdate> updates;

    private InputSnapshot currentSnapshot;

    public InputJournal(int rowHeight, int columnWidth) {
        this.rowHeight = rowHeight;
        this.columnWidth = columnWidth;
        currentMouseRow = -1;
        currentMouseColumn = -1;
        updates = new LinkedBlockingQueue<>();

        currentSnapshot = new InputSnapshot();
    }

    public InputSnapshot updateInput(int millisecondWait) {
        try {
            SnapshotUpdate update = updates.poll(millisecondWait, TimeUnit.MILLISECONDS);

            if (update != null) {
                currentSnapshot = update.makeSnapshotChanges(currentSnapshot);
            } else {
                currentSnapshot = noInputSnapshot(currentSnapshot);
            }
        } catch (InterruptedException ignored) {
            currentSnapshot = noInputSnapshot(currentSnapshot);
        }

        return currentSnapshot;
    }

    private InputSnapshot noInputSnapshot(InputSnapshot snapshot) {
        return new InputSnapshot(InputSnapshot.NO_INPUT, (char) 0, "",
                snapshot.getKeysDown(), MouseEvent.NOBUTTON, snapshot.getMouseButtonsDown(),
                snapshot.getMouseRow(), snapshot.getMouseColumn());
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        String keyText = KeyEvent.getKeyText(e.getKeyCode());
        updates.add(new KeyUpdate(e.getKeyChar(), keyText, true));
    }

    public void keyReleased(KeyEvent e) {
        String keyText = KeyEvent.getKeyText(e.getKeyCode());
        updates.add(new KeyUpdate(e.getKeyChar(), keyText, false));
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        updates.add(new MouseButtonUpdate(e.getButton(), true));
    }

    public void mouseReleased(MouseEvent e) {
        updates.add(new MouseButtonUpdate(e.getButton(), false));
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        x /= columnWidth;
        y /= rowHeight;

        if (currentMouseRow != y || currentMouseColumn != x) {
            currentMouseRow = y;
            currentMouseColumn = x;

            updates.add(new MouseMoveUpdate(currentMouseRow, currentMouseColumn));
        }
    }

    private interface SnapshotUpdate {
        public InputSnapshot makeSnapshotChanges(InputSnapshot previousSnapshot);
    }

    private class KeyUpdate implements SnapshotUpdate {
        private char typedCharacter;
        private String keyName;
        private boolean pressNotRelease;

        private KeyUpdate(char typedCharacter, String keyName, boolean pressNotRelease) {
            this.typedCharacter = typedCharacter;
            this.keyName = keyName;
            this.pressNotRelease = pressNotRelease;
        }

        public InputSnapshot makeSnapshotChanges(InputSnapshot previousSnapshot) {
            Set<String> keysDown = previousSnapshot.getKeysDown();
            int inputType;

            if (pressNotRelease) {
                keysDown.add(keyName);
                inputType = InputSnapshot.KEY_DOWN;
            } else {
                keysDown.remove(keyName);
                inputType = InputSnapshot.KEY_RELEASE;
            }

            return new InputSnapshot(inputType, typedCharacter, keyName, keysDown,
                    MouseEvent.NOBUTTON, previousSnapshot.getMouseButtonsDown(),
                    previousSnapshot.getMouseRow(), previousSnapshot.getMouseColumn());
        }
    }

    private class MouseMoveUpdate implements SnapshotUpdate {
        private int row, column;

        private MouseMoveUpdate(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public InputSnapshot makeSnapshotChanges(InputSnapshot previousSnapshot) {
            return new InputSnapshot(InputSnapshot.MOUSE_MOVED, (char) 0, "", previousSnapshot.getKeysDown(),
                    MouseEvent.NOBUTTON, previousSnapshot.getMouseButtonsDown(), row, column);
        }
    }

    private class MouseButtonUpdate implements SnapshotUpdate {
        private int button;
        private boolean pressNotRelease;

        private MouseButtonUpdate(int button, boolean pressNotRelease) {
            this.button = button;
            this.pressNotRelease = pressNotRelease;
        }

        public InputSnapshot makeSnapshotChanges(InputSnapshot previousSnapshot) {
            Set<Integer> mouseButtonsDown = previousSnapshot.getMouseButtonsDown();
            int inputType;

            if (pressNotRelease) {
                mouseButtonsDown.add(button);
                inputType = InputSnapshot.MOUSE_DOWN;
            } else {
                mouseButtonsDown.remove(button);
                inputType = InputSnapshot.MOUSE_CLICK;
            }

            return new InputSnapshot(inputType, (char) 0, "", previousSnapshot.getKeysDown(), button,
                    mouseButtonsDown, previousSnapshot.getMouseRow(), previousSnapshot.getMouseColumn());
        }
    }
}
