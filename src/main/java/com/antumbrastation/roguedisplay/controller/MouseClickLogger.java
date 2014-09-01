package com.antumbrastation.roguedisplay.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickLogger implements MouseListener {

    Controller control;

    int height;
    int width;

    int padY;
    int padX;

    public MouseClickLogger(Controller control, int rowHeight, int columnWidth) {
        this.control = control;

        height = rowHeight;
        width = columnWidth;

        padY = 25;
        padX = 6;
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        x -= padX;
        y -= padY;

        x /= width;
        y /= height;

        control.processMouseClick(y, x, e.getButton());
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
