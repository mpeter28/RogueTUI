package com.antumbrastation.roguedisplay;

import com.antumbrastation.roguedisplay.components.DisplayComponent;
import com.antumbrastation.roguedisplay.controller.*;
import com.antumbrastation.roguedisplay.view.RogueFrame;
import com.antumbrastation.roguedisplay.view.ColorPalette;

import java.awt.*;
import java.util.concurrent.Semaphore;

public class RogueDisplay {
    Semaphore taskLock;
    Controller control;
    RogueFrame view;

    public RogueDisplay(String title, DisplayComponent root, int rows, int columns, Font font, ColorPalette colors) {
        taskLock = new Semaphore(1, true);

        control = new Controller(view, taskLock);
        view = new RogueFrame(title, root, taskLock, rows, columns, font, colors);

        KeyPressLogger keyPress = new KeyPressLogger(control);
        //MouseClickLogger mouseClick = new MouseClickLogger(control, view.getRowHeight(), view.getRowWidth());
        //MouseMoveLogger mouseMove = new MouseMoveLogger(control, view.getRowHeight(), view.getRowWidth());

        view.addKeyListener(keyPress);
        //view.addMouseListener(mouseClick);
        //view.addMouseMotionListener(mouseMove);
    }

    public void runTask(InputTask task) {
        view.setVisible(true);
        control.runTask(task);
    }

    public RogueDisplay(String title, DisplayComponent root, int rows, int columns, Font font) {
        this(title, root, rows, columns, font, new ColorPalette());
    }

    public RogueDisplay(String title, DisplayComponent root, int rows, int columns, ColorPalette colors) {
        this(title, root, rows, columns, new Font(Font.MONOSPACED, Font.BOLD, 18), colors);
    }

    public RogueDisplay(String title, DisplayComponent root, int rows, int columns) {
        this(title, root, rows, columns, new Font(Font.MONOSPACED, Font.BOLD, 18), new ColorPalette());
        taskLock = new Semaphore(1, true);
    }
}
