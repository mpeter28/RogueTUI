package com.antumbrastation.roguedisplay.components;

import com.antumbrastation.roguedisplay.view.DisplayView;
import com.antumbrastation.roguedisplay.view.Window;

public class LabelComponent implements DisplayComponent {

    private Window window;
    private String text;
    private int color;
    private int highlight;

    public LabelComponent(Window window) {
        this.window = window;
    }

    public void display(DisplayView view, boolean focus) {
        view.setWindow(window);

        view.writeLine(text, 0, 0, color, highlight);
    }

    public boolean giveFocus(DisplayComponent focus) {
        return focus == this;
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
