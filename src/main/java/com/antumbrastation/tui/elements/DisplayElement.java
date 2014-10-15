package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayView;
import com.antumbrastation.tui.Window;

public interface DisplayElement {
    public void display(DisplayView view);
    public Window getWindow();
}
