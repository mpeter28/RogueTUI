package com.antumbrastation.tui;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class InputSnapshot {

    public static int NO_INPUT = 0;
    public static int MOUSE_MOVED = 1;
    public static int KEY_DOWN = 2;
    public static int KEY_RELEASE = 3;
    public static int MOUSE_DOWN = 4;
    public static int MOUSE_CLICK = 5;

    private int inputEventType;

    private char typedCharacter;
    private String keyName;
    private Set<String> keysDown;

    private int buttonNumber;
    private Set<Integer> mouseButtonsDown;
    private int mouseRow;
    private int mouseColumn;

    public InputSnapshot() {
        inputEventType = InputSnapshot.NO_INPUT;

        typedCharacter = 0;
        keyName = "";
        keysDown = new HashSet<>();

        buttonNumber = MouseEvent.NOBUTTON;
        mouseButtonsDown = new HashSet<>();
        mouseRow = -1;
        mouseColumn = -1;
    }

    public InputSnapshot(int inputEventType, char typedCharacter, String keyName, Set<String> keysDown,
                         int buttonNumber, Set<Integer> mouseButtonsDown, int mouseRow, int mouseColumn) {
        this.inputEventType = inputEventType;
        this.typedCharacter = typedCharacter;
        this.keyName = keyName;
        this.keysDown = keysDown;
        this.buttonNumber = buttonNumber;
        this.mouseButtonsDown = mouseButtonsDown;
        this.mouseRow = mouseRow;
        this.mouseColumn = mouseColumn;
    }

    public int getInputEventType() {
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

    public int getMouseRow() {
        return mouseRow;
    }

    public int getMouseColumn() {
        return mouseColumn;
    }

    public Set<String> getKeysDown() {
        return new HashSet<>(keysDown);
    }

    public boolean isKeyDown(String key) {
        return keysDown.contains(key);
    }

    public Set<Integer> getMouseButtonsDown() {
        return new HashSet<>(mouseButtonsDown);
    }

    public boolean isMouseButtonDown(int buttonNumber) {
        return mouseButtonsDown.contains(buttonNumber);
    }

}
