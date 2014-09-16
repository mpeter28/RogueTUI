package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.view.DisplayView;
import com.antumbrastation.tui.view.Window;

public interface DisplayElement {
    public void display(DisplayView view);
    public Window getWindow();
}
