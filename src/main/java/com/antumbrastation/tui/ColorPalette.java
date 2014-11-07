package com.antumbrastation.tui;

import java.awt.*;

public class ColorPalette {

    private Color[] colors;
    private Color textColor;
    private Color highlightColor;

    public ColorPalette() {
        colors = new Color[]{ Color.black, new Color(128, 0, 0), new Color(0, 128, 0),
                new Color(128, 128, 0), new Color(0, 0, 128),
                new Color(128, 0, 128), new Color(0, 128, 128),
                new Color(128, 128, 128), new Color(64, 64, 64), Color.red,
                Color.green, Color.yellow, Color.blue, Color.magenta, Color.cyan,
                Color.white };

        textColor = new Color(128, 128, 128);
        highlightColor = Color.black;
    }

    public ColorPalette(Color[] colors, Color textColor, Color highlightColor) {
        this.colors = colors;
        this.textColor = textColor;
        this.highlightColor = highlightColor;
    }

    public Color indexToColor(int index) {
        if (index < 0 || index >= colors.length)
            return highlightColor;

        return colors[index];
    }

    public Color defaultTextColor() {
        return textColor;
    }

    public Color defaultHighlightColor() {
        return highlightColor;
    }

}
