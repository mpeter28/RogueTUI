package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayView;

public class LabelElement implements DisplayElement {

    private DisplayBounds window;
    private String text;
    private int color;
    private int highlight;

    public LabelElement(DisplayBounds window) {
        this.window = window;
    }

    public void display(DisplayView view) {
        view.setBounds(window);

        view.writeLine(text, 0, 0, color, highlight);
    }

    public DisplayBounds getDisplayBounds() {
        return window;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setHighlight(int highlight) {
        this.highlight = highlight;
    }
}
