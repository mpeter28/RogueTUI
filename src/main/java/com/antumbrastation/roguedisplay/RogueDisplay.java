package com.antumbrastation.roguedisplay;

import com.antumbrastation.roguedisplay.components.DisplayComponent;
import com.antumbrastation.roguedisplay.controller.*;
import com.antumbrastation.roguedisplay.view.RogueFrame;
import com.antumbrastation.roguedisplay.view.ColorPalette;

import java.awt.*;

public class RogueDisplay {
    Controller control;
    RogueFrame view;

    public RogueDisplay(String title, DisplayComponent root, int rows, int columns, Font font, ColorPalette colors) {
        view = new RogueFrame(title, root, rows, columns, font, colors);
        control = new Controller(view);

        view.addKeyListener(control);
        view.addMouseListener(control);
        view.addMouseMotionListener(control);
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
    }
}
