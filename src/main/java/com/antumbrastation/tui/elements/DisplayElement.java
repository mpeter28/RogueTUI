package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;

public interface DisplayElement {
    public void display(DisplayBuffer view);
    public DisplayBounds getDisplayBounds();
}
