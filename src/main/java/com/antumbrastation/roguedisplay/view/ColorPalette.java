package com.antumbrastation.roguedisplay.view;

import java.awt.*;

public class ColorPalette {

    private Color[] colors;
    private String[] colorNames;

    public ColorPalette() {
        colors = new Color[]{Color.black, Color.darkGray, Color.gray, Color.white, new Color(255, 0, 0), new Color(100, 0, 0), new Color(0, 255, 0), new Color(0, 100, 0), new Color(0, 0, 255), new Color(0, 0, 100), new Color(255, 255, 0), new Color(100, 100, 0), new Color(0, 255, 255), new Color(0, 100, 100), new Color(255, 0, 255), new Color(100, 0, 100)};
        colorNames = new String[]{"Black", "Dark Gray", "Light Gray", "White", "Red", "Dark Red", "Green", "Dark Green", "Blue", "Dark Blue", "Yellow", "Brown", "Turquoise", "Teal", "Magenta", "Purple"};
    }

    public ColorPalette(Color[] colors, String[] colorNames) {
        this.colors = colors;
        this.colorNames = colorNames;
    }

    public int nameToIndex(String color) {
        for (int i = 0; i < color.length(); i++) {
            if (color.equals(colorNames[i]))
                return i;
        }

        return -1;
    }

    public Color indexToColor(int index) {
        if (index < 0 || index > colors.length)
            return Color.black;

        return colors[index];
    }

}
