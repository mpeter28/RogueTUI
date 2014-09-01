package com.antumbrastation.roguedisplay.controller;

import com.antumbrastation.roguedisplay.view.RogueFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyPressLogger implements KeyListener {

    Controller control;

    public KeyPressLogger(Controller control) {
        this.control = control;
    }

    public void keyTyped(KeyEvent e) {
        control.processKeyHit(e.getKeyChar());
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
