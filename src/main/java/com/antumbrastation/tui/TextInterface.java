package com.antumbrastation.tui;

import com.antumbrastation.tui.elements.ElementKeeper;
import com.antumbrastation.tui.controller.*;
import com.antumbrastation.tui.view.TextFrame;
import com.antumbrastation.tui.view.ColorPalette;

import java.awt.*;

public class TextInterface {
    Controller control;
    TextFrame view;

    public TextInterface(String title, ElementKeeper root, int rows, int columns, Font font, ColorPalette colors) {
        view = new TextFrame(title, root, rows, columns, font, colors);
        control = new Controller(view, root);

        view.addKeyListener(control);
        view.addMouseListener(control);
        view.addMouseMotionListener(control);
    }

    public void runTask(InputTask task) {
        view.setVisible(true);
        control.runTask(task);
    }

    public TextInterface(String title, ElementKeeper root, int rows, int columns, Font font) {
        this(title, root, rows, columns, font, new ColorPalette());
    }

    public TextInterface(String title, ElementKeeper root, int rows, int columns, ColorPalette colors) {
        this(title, root, rows, columns, new Font(Font.MONOSPACED, Font.BOLD, 18), colors);
    }

    public TextInterface(String title, ElementKeeper root, int rows, int columns) {
        this(title, root, rows, columns, new Font(Font.MONOSPACED, Font.BOLD, 18), new ColorPalette());
    }
}
