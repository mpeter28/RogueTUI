package com.antumbrastation.roguedisplay.elements;

import com.antumbrastation.roguedisplay.view.DisplayView;
import com.antumbrastation.roguedisplay.view.Window;

public class MenuElement implements DisplayElement {

    private Window window;

    private int color;
    private String[] keys;
    private String[] options;
    private int[] keyColors, optionColors;
    private int currentOption, topOption, bottomOption;

    public MenuElement(Window window) {
        this.window = window;
    }

    public void setPage(int color, String[] keys, String[] options, int[] keyColors, int[] optionColors) {
        this.color = color;
        this.keys = keys;
        this.options = options;
        this.keyColors = keyColors;
        this.optionColors = optionColors;

        currentOption = 0;
        topOption = 0;
        bottomOption = Math.min(keys.length, window.getHeight());
    }

    public void display(DisplayView view) {
        view.setWindow(window);
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
            view.writeChar('|', index, keySize, color, -1);
            view.writeLine(options[i], index, keySize + 2, optionColors[i], -1);
            index++;
        }
    }

    public String[] select() {
        String[] result = new String[2];

        result[0] = keys[currentOption];
        result[1] = options[currentOption];

        return result;
    }

    public void scrollUp() {
        currentOption--;

        if (currentOption < 0)
            currentOption = 0;

        if (currentOption < topOption) {
            topOption = currentOption;
            bottomOption = topOption + window.getHeight();
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
            topOption = bottomOption - window.getHeight();
        }

        if (topOption < 0) {
            topOption = 0;
        }
    }
}
