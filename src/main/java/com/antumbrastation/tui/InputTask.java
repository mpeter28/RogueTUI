package com.antumbrastation.tui;

import java.util.Set;

public interface InputTask {
    public void processKeyHit(char key, Set<String> modifiers);
    public void processKeyHit(String key, Set<String> modifiers);
    public void processMouseClick(int row, int column, int button);
    public void processMouseMove(int row, int column);
    public boolean isComplete();
}
