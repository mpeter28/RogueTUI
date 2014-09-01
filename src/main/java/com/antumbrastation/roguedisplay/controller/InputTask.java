package com.antumbrastation.roguedisplay.controller;

public interface InputTask {
    public boolean initialize();
    public boolean processKeyHit(char key);
    public boolean processMouseClick(int row, int column, int button);
    public boolean processMouseMove(int row, int column);
    public boolean isComplete();
}
