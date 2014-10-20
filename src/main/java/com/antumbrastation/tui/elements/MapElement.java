package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;

public class MapElement implements DisplayElement {

    private DisplayBounds bounds;

    private char[][] text;
    private int[][] highlight;
    private int[][] textColor;

    public MapElement(DisplayBounds bounds) {
        this.bounds = bounds;

        int rows = bounds.getHeight();
        int columns = bounds.getWidth();

        text = new char[rows][columns];
        highlight = new int[rows][columns];
        textColor = new int[rows][columns];
    }

    public void set(int y, int x, char c, int textColor, int highlight) {
        text[y][x] = c;
        this.highlight[y][x] = highlight;
        this.textColor[y][x] = textColor;
    }

    public void display(DisplayBuffer view) {
        view.setBounds(bounds);

        int width = bounds.getWidth();
        int height = bounds.getHeight();

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                view.writeChar(text[i][j], i, j, textColor[i][j], highlight[i][j]);
    }

    public DisplayBounds getDisplayBounds() {
        return bounds;
    }

}
