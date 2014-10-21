package com.antumbrastation.tui.elements;

public interface DisplayElement {
    public void pushToBuffer(DisplayBuffer view);
    public DisplayBounds getDisplayBounds();
}
