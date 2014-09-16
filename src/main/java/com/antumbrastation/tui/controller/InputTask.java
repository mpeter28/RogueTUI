package com.antumbrastation.tui.controller;

import com.antumbrastation.tui.elements.DisplayElement;

public interface InputTask {
    public boolean initialize();
    public boolean processKeyHit(char key);
    public boolean processMouseClick(int row, int column, int button, DisplayElement element);
    public boolean processMouseMove(int row, int column, DisplayElement element);
    public boolean isComplete();
}
