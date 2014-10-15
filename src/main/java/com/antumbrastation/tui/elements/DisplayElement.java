package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayView;

public interface DisplayElement {
    public void display(DisplayView view);
    public DisplayBounds getDisplayBounds();
}
