package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;

public class LabelElement implements DisplayElement {

    private DisplayBounds bounds;
    private String text;
    private int color;
    private int highlight;

    public LabelElement(DisplayBounds bounds) {
        this.bounds = bounds;
    }

    public void display(DisplayBuffer view) {
        view.setBounds(bounds);

        view.writeLine(text, 0, 0, color, highlight);
    }

    public DisplayBounds getDisplayBounds() {
        return bounds;
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
