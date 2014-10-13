package com.antumbrastation.tui;

import com.antumbrastation.tui.elements.DisplayElement;
import com.antumbrastation.tui.controller.*;
import com.antumbrastation.tui.view.ColorPalette;
import com.antumbrastation.tui.view.TextPanel;

import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class TextInterface {
    private Controller control;

    private String title;
    private int rows, columns;

    private List<DisplayElement> elements;
    private Font font;
    private ColorPalette colorPalette;
    private Image icon;

    public TextInterface() {
        font = new Font(Font.MONOSPACED, Font.BOLD, 18);
        colorPalette = new ColorPalette();
        elements = new LinkedList<>();
    }

    public void makeControllerAndView(boolean keyListen, boolean mouseListen, boolean moveListen) {
        TextPanel view = new TextPanel(colorPalette, font, rows, columns);
        control = new Controller(view, elements);

        JFrame frame = new JFrame(title);
        frame.setLayout(new GridLayout(1, 1));
        frame.add(view);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        if (icon != null) {
            frame.setIconImage(icon);
        }

        if (keyListen) {
            frame.addKeyListener(control);
        }

        if (mouseListen) {
            view.addMouseListener(control);
        }

        if (moveListen) {
            view.addMouseMotionListener(control);
        }
    }

    public void runTask(InputTask task) {
        control.runTask(task);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setElementKeeper(List<DisplayElement> elements) {
        this.elements = elements;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setSize(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public void setColorPalette(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
}
