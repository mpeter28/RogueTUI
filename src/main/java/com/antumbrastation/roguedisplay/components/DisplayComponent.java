package com.antumbrastation.roguedisplay.components;

import com.antumbrastation.roguedisplay.view.DisplayView;

public interface DisplayComponent {
    public void display(DisplayView view, boolean focus);
    public boolean giveFocus(DisplayComponent focus);
}
