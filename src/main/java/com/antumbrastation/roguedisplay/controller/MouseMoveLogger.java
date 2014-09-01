package com.antumbrastation.roguedisplay.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMoveLogger implements MouseMotionListener {

    private Controller control;

    private int height;
    private int width;

    private int padY;
    private int padX;

    public MouseMoveLogger(Controller control, int rowHeight, int columnWidth) {
        this.control = control;

        height = rowHeight;
        width = columnWidth;

        padY = 25;
        padX = 6;
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        x -= padX;
        y -= padY;

        x /= width;
        y /= height;

        control.processMouseMove(y, x);
    }
}
