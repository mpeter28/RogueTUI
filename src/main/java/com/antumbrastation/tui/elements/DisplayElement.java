package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;

public abstract class DisplayElement {
    private DisplayBounds bounds;

    public abstract void pushToBuffer(DisplayBuffer view);

    public DisplayBounds getDisplayBounds() {
        return bounds;
    }

    public void setDisplayBounds(DisplayBounds newBounds) {
        bounds = newBounds;
    }
}
