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

    private char typedCharacter;
    private String nonCharacterKey;
    private Set<String> keysDown;
    private int buttonClicked;
    private boolean mouseMoved;
    private int mouseRow;
    private int mouseColumn;

    public InputJournal(int rowHeight, int columnWidth) {
        this.rowHeight = rowHeight;
        this.columnWidth = columnWidth;

        currentMouseRow = -1;
        currentMouseColumn = -1;
        updates = new LinkedBlockingQueue<>();

        typedCharacter = 0;
        keysDown = new HashSet<>();
        buttonClicked = MouseEvent.NOBUTTON;
        mouseRow = -1;
        mouseColumn = -1;
    }

    public boolean updateInput(int millisecondWait) {
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

    public char getTypedCharacter() {
        return typedCharacter;
    }

    public String getNonCharacterKey() {
        return nonCharacterKey;
    }

    public boolean isKeyDown(String key) {
        return keysDown.contains(key);
    }

    public int getButtonClicked() {
        return buttonClicked;
    }

    public boolean wasMouseMoved() {
        return mouseMoved;
    }

    public int getMouseRow() {
        return mouseRow;
    }

    public int getMouseColumn() {
        return mouseColumn;
    }

    public void keyTyped(KeyEvent e) {
        updates.add(new CharacterTypedUpdate(e.getKeyChar()));
    }

    public void keyPressed(KeyEvent e) {
        if (!Character.isDefined(e.getKeyChar())) {
            String keyText = KeyEvent.getKeyText(e.getKeyCode());
            updates.add(new NonCharacterKeyUpdate(keyText, true));
        }
    }

    public void keyReleased(KeyEvent e) {
        if (!Character.isDefined(e.getKeyChar())) {
            String keyText = KeyEvent.getKeyText(e.getKeyCode());
            updates.add(new NonCharacterKeyUpdate(keyText, false));
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        x /= columnWidth;
        y /= rowHeight;

        updates.add(new MouseUpdate(e.getButton(), false, y, x));
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

        x /= columnWidth;
        y /= rowHeight;

        if (currentMouseRow != y || currentMouseColumn != x) {
            currentMouseRow = y;
            currentMouseColumn = x;

            updates.add(new MouseUpdate(MouseEvent.NOBUTTON, true, y, x));
        }
    }

    private interface JournalUpdate {
        public void makeJournalChanges();
    }

    private class CharacterTypedUpdate implements JournalUpdate{
        private char typedCharacter;

        private CharacterTypedUpdate(char typedCharacter) {
            this.typedCharacter = typedCharacter;
        }

        public void makeJournalChanges() {
            InputJournal.this.typedCharacter = typedCharacter;
            InputJournal.this.nonCharacterKey = null;
            InputJournal.this.buttonClicked = MouseEvent.NOBUTTON;
            InputJournal.this.mouseMoved = false;
        }
    }

    private class NonCharacterKeyUpdate implements JournalUpdate {
        private String specialKey;
        private boolean pressNotRelease;

        private NonCharacterKeyUpdate(String specialKey, boolean pressNotRelease) {
            this.specialKey = specialKey;
            this.pressNotRelease = pressNotRelease;
        }

        public void makeJournalChanges() {
            InputJournal.this.typedCharacter = 0;
            InputJournal.this.nonCharacterKey = specialKey;
            InputJournal.this.buttonClicked = MouseEvent.NOBUTTON;
            InputJournal.this.mouseMoved = false;

            if (pressNotRelease) {
                keysDown.add(this.specialKey);
            } else {
                keysDown.remove(this.specialKey);
            }
        }
    }

    private class MouseUpdate implements JournalUpdate{
        private int buttonClicked;
        private boolean mouseMoved;
        private int mouseRow, mouseColumn;

        private MouseUpdate(int buttonClicked, boolean mouseMoved, int mouseRow, int mouseColumn) {
            this.buttonClicked = buttonClicked;
            this.mouseMoved = mouseMoved;
            this.mouseRow = mouseRow;
            this.mouseColumn = mouseColumn;
        }

        public void makeJournalChanges() {
            InputJournal.this.typedCharacter = 0;
            InputJournal.this.nonCharacterKey = null;
            InputJournal.this.buttonClicked = buttonClicked;
            InputJournal.this.mouseMoved = mouseMoved;
            InputJournal.this.mouseRow = mouseRow;
            InputJournal.this.mouseColumn = mouseColumn;
        }
    }
}
