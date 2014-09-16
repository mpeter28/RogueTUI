package com.antumbrastation.tui;

import com.antumbrastation.tui.elements.ElementKeeper;
import com.antumbrastation.tui.controller.*;
import com.antumbrastation.tui.view.ColorPalette;
import com.antumbrastation.tui.view.TextPanel;

import javax.swing.*;
import java.awt.*;

public class TextInterface {
    private Controller control;
    private JFrame frame;

    private String title;
    private int rows, columns;

    private ElementKeeper elements;
    private Font font;
    private ColorPalette colorPalette;
    private Image icon;

    public TextInterface() {
        font = new Font(Font.MONOSPACED, Font.BOLD, 18);
        colorPalette = new ColorPalette();
    }

    public void makeControllerAndView() {
        TextPanel view = new TextPanel(colorPalette, font, rows, columns);
        control = new Controller(view, elements);

        frame = new JFrame(title);
        frame.setLayout(new GridLayout(1, 1));
        frame.add(view);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        if (icon != null) {
            frame.setIconImage(icon);
        }

        frame.addKeyListener(control);
        view.addMouseListener(control);
        view.addMouseMotionListener(control);
    }

    public void runTask(InputTask task) {
        frame.setVisible(true);
        control.runTask(task);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setElementKeeper(ElementKeeper elements) {
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
