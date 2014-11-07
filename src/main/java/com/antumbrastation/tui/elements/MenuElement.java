package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;

public class MenuElement extends DisplayElement {

    private int dividerColor;
    private char dividerChar;

    private String[] keys;
    private String[] options;
    private int[] keyColors, optionColors;

    private int currentOption, topOption, bottomOption;

    public MenuElement(DisplayBounds bounds) {
        setDisplayBounds(bounds);
    }

    public void setMeunOptions(String[] keys, String[] options, int[] keyColors, int[] optionColors) {
        this.keys = keys;
        this.options = options;
        this.keyColors = keyColors;
        this.optionColors = optionColors;

        currentOption = 0;
        topOption = 0;
        bottomOption = Math.min(keys.length, getDisplayBounds().getHeight());
    }

    public void setDivider(char dividerChar, int dividerColor) {
        this.dividerChar = dividerChar;
        this.dividerColor = dividerColor;
    }

    public void pushToBuffer(DisplayBuffer view) {
        view.setWritingBounds(getDisplayBounds());
        view.writeFill(' ', -1, -1);

        int keySize = 0;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].length() > keySize)
                keySize = keys[i].length();
        }

        int index = 0;
        for (int i = topOption; i < bottomOption; i++) {
            if (currentOption == i)
                view.writeLine(keys[i], index, 0, -1, keyColors[i]);
            else
                view.writeLine(keys[i], index, 0, keyColors[i], -1);
            view.writeChar(dividerChar, index, keySize, dividerColor, -1);
            view.writeLine(options[i], index, keySize + 2, optionColors[i], -1);
            index++;
        }
    }

    public String[] currentSelection() {
        String[] result = new String[2];

        result[0] = keys[currentOption];
        result[1] = options[currentOption];

        return result;
    }

    public void jumpToOption(int row) {
        row += topOption;
        if (row < bottomOption) {
            currentOption = row;
        }
    }

    public void scrollUp() {
        currentOption--;

        if (currentOption < 0)
            currentOption = 0;

        if (currentOption < topOption) {
            topOption = currentOption;
            bottomOption = topOption + getDisplayBounds().getHeight();
        }

        if (bottomOption > keys.length) {
            bottomOption = keys.length;
        }
    }

    public void scrollDown() {
        currentOption++;

        if (currentOption >= keys.length)
            currentOption = keys.length - 1;

        if (currentOption >= bottomOption) {
            bottomOption = currentOption + 1;
            topOption = bottomOption - getDisplayBounds().getHeight();
        }

        if (topOption < 0) {
            topOption = 0;
        }
    }
}
