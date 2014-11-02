package com.antumbrastation.tui;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class InputJournal implements KeyListener, MouseListener, MouseMotionListener {
    private int rowHeight;
    private int columnWidth;
    private int currentMouseRow;
    private int currentMouseColumn;
    private BlockingQueue<JournalUpdate> updates;

    private String inputEventType;
    private char typedCharacter;
    private String keyName;
    private int buttonNumber;

    private Set<String> keysDown;
    private Set<Integer> mouseButtonsDown;
    private int mouseRow;
    private int mouseColumn;

    public InputJournal(int rowHeight, int columnWidth) {
        this.rowHeight = rowHeight;
        this.columnWidth = columnWidth;
        currentMouseRow = -1;
        currentMouseColumn = -1;
        updates = new LinkedBlockingQueue<>();

        cleanJournal();

        keysDown = new HashSet<>();
        mouseButtonsDown = new HashSet<>();
        mouseRow = -1;
        mouseColumn = -1;
    }

    public boolean updateInput(int millisecondWait) {
        cleanJournal();
        try {
            JournalUpdate update = updates.poll(millisecondWait, TimeUnit.MILLISECONDS);

            if (update == null)
                return false;

            update.makeJournalChanges();
            return true;
        } catch (InterruptedException ignored) {
            return false;
        }
    }

    private void cleanJournal() {
        inputEventType = "None";
        typedCharacter = 0;
        keyName = null;
        buttonNumber = MouseEvent.NOBUTTON;
    }

    public String getInputEventType() {
        return inputEventType;
    }

    public char getTypedCharacter() {
        return typedCharacter;
    }

    public String getKeyName() {
        return keyName;
    }

    public int getButtonNumber() {
        return buttonNumber;
    }

    public boolean isKeyDown(String key) {
        return keysDown.contains(key);
    }

    public boolean isMouseButtonDown(int button) {
        return mouseButtonsDown.contains(button);
    }

    public int getMouseRow() {
        return mouseRow;
    }

    public int getMouseColumn() {
        return mouseColumn;
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

    private interface JournalUpdate {
        public void makeJournalChanges();
    }

    private class KeyUpdate implements JournalUpdate {
        private char typedCharacter;
        private String keyName;
        private boolean pressNotRelease;

        private KeyUpdate(char typedCharacter, String keyName, boolean pressNotRelease) {
            this.typedCharacter = typedCharacter;
            this.keyName = keyName;
            this.pressNotRelease = pressNotRelease;
        }

        public void makeJournalChanges() {
            InputJournal.this.typedCharacter = typedCharacter;
            InputJournal.this.keyName = keyName;
            if (pressNotRelease) {
                InputJournal.this.keysDown.add(keyName);
                InputJournal.this.inputEventType = "KeyDown";
            } else {
                InputJournal.this.keysDown.remove(keyName);
                InputJournal.this.inputEventType = "KeyHit";
            }
        }
    }

    private class MouseMoveUpdate implements JournalUpdate {
        private int row, column;

        private MouseMoveUpdate(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public void makeJournalChanges() {
            InputJournal.this.mouseRow = row;
            InputJournal.this.mouseColumn = column;
            InputJournal.this.inputEventType = "MouseMove";
        }
    }

    private class MouseButtonUpdate implements JournalUpdate {
        private int button;
        private boolean pressNotRelease;

        private MouseButtonUpdate(int button, boolean pressNotRelease) {
            this.button = button;
            this.pressNotRelease = pressNotRelease;
        }

        public void makeJournalChanges() {
            InputJournal.this.buttonNumber = button;
            if (pressNotRelease) {
                InputJournal.this.mouseButtonsDown.add(button);

                InputJournal.this.inputEventType = "MouseDown";
            } else {
                InputJournal.this.mouseButtonsDown.remove(button);
                InputJournal.this.inputEventType = "MouseClick";
            }
        }
    }
}
