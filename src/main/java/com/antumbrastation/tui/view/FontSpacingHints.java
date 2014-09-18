package com.antumbrastation.tui.view;

import java.util.HashMap;
import java.util.Map;

public class FontSpacingHints {

    private Map<Character, Integer> descentMargins;
    private Map<Character, Integer> leftMargins;

    private int defaultDescentMargin;
    private int defaultLeftMargin;

    private int gridWidth;
    private int gridHeight;

    public FontSpacingHints(int gridHeight, int gridWidth, int defaultDescentMargin, int defaultLeftMargin) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.defaultLeftMargin = defaultLeftMargin;
        this.defaultDescentMargin = defaultDescentMargin;

        descentMargins = new HashMap<>();
        leftMargins = new HashMap<>();
    }

    public int getCharDescentMargin(char c) {
        Integer margin = descentMargins.get(c);
        if (margin == null) {
            return defaultDescentMargin;
        } else {
            return margin;
        }
    }

    public int getCharLeftMargin(char c) {
        Integer margin = leftMargins.get(c);
        if (margin == null) {
            return defaultLeftMargin;
        } else {
            return margin;
        }
    }

    public void setCharDescentMargin(char c, int margin) {
        descentMargins.put(c, margin);
    }

    public void setCharLeftMargin(char c, int margin) {
        leftMargins.put(c, margin);
    }

    public void setCharDescentMargin(char[] ca, int margin) {
        for (char c: ca) {
            descentMargins.put(c, margin);
        }
    }

    public void setCharLeftMargin(char[] ca, int margin) {
        for (char c: ca) {
            leftMargins.put(c, margin);
        }
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

}
