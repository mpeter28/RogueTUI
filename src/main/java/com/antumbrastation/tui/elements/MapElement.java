package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayView;

public class MapElement implements DisplayElement {

    private DisplayBounds window;

    private char[][] text;
    private int[][] highlight;
    private int[][] textColor;

    public MapElement(DisplayBounds window) {
        this.window = window;

        int rows = window.getHeight();
        int columns = window.getWidth();

        text = new char[rows][columns];
        highlight = new int[rows][columns];
        textColor = new int[rows][columns];
    }

    public void set(int y, int x, char c, int textColor, int highlight) {
        text[y][x] = c;
        this.highlight[y][x] = highlight;
        this.textColor[y][x] = textColor;
    }

    public void display(DisplayView view) {
        view.setBounds(window);

        int width = window.getWidth();
        int height = window.getHeight();

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                view.writeChar(text[i][j], i, j, textColor[i][j], highlight[i][j]);
    }

    public DisplayBounds getDisplayBounds() {
        return window;
    }

}
