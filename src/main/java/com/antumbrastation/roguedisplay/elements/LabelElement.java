package com.antumbrastation.roguedisplay.elements;

import com.antumbrastation.roguedisplay.view.DisplayView;
import com.antumbrastation.roguedisplay.view.Window;

public class LabelElement implements DisplayElement {

    private Window window;
    private String text;
    private int color;
    private int highlight;

    public LabelElement(Window window) {
        this.window = window;
    }

    public void display(DisplayView view) {
        view.setWindow(window);

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
