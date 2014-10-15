package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayView;

public class ScrollTextElement implements DisplayElement {

    private DisplayBounds window;
    private String[] lines;
    private int[] colors;
    private int[] highlights;

    private boolean bottomToTop;

    public ScrollTextElement(boolean bottomToTop, DisplayBounds window) {
        this.window = window;
        this.bottomToTop = bottomToTop;

        int height = window.getHeight();
        lines = new String[height];
        colors = new int[height];
        highlights = new int[height];
    }

    public void writeLine(String line, int color, int highlight) {
        for (int i = lines.length - 1; i > 0; i++) {
            lines[i] = lines[i-1];
            colors[i] = colors[i-1];
            highlights[i] = highlights[i-1];
        }

        lines[0] = line;
        colors[0] = color;
        highlights[0] = highlight;
    }

    public void clearLines() {
        for (int i = 0; i < lines.length; i++) {
            lines[i] = "";
            colors[i] = -1;
            highlights[i] = -1;
        }
    }

    public void display(DisplayView view) {
        view.setBounds(window);
        view.writeFill(' ', -1, -1);

        if (bottomToTop) {
            for (int i = 0; i < lines.length; i++) {
                view.writeLine(lines[i], lines.length - i - 1, 0, colors[i], highlights[i]);
            }
        } else {
            for (int i = 0; i < lines.length; i++) {
                view.writeLine(lines[i], i, 0, colors[i], highlights[i]);
            }
        }
    }

    public DisplayBounds getDisplayBounds() {
        return window;
    }

}
