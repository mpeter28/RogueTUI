package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;

public class LabelElement extends DisplayElement {

    private String text;
    private int color;
    private int highlight;

    public LabelElement(DisplayBounds bounds) {
        setDisplayBounds(bounds);
    }

    public void pushToBuffer(DisplayBuffer view) {
        view.setWritingBounds(getDisplayBounds());

        view.writeLine(text, 0, 0, color, highlight);
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
