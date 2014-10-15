package com.antumbrastation.tui.controller;

import com.antumbrastation.tui.elements.DisplayElement;

import java.util.Set;

public interface InputTask {
    public void initialize();
    public void processKeyHit(char key, Set<String> modifiers);
    public void processKeyHit(String key, Set<String> modifiers);
    public void processMouseClick(int row, int column, int button, DisplayElement element);
    public void processMouseMove(int row, int column, DisplayElement element);
    public boolean isComplete();
}
