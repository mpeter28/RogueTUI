package com.antumbrastation.tui.view;

import com.antumbrastation.tui.elements.ElementKeeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TextFrame extends JFrame {

    private TextPanel panel;
    private ElementKeeper root;

    public TextFrame(String title, ElementKeeper root, int rows, int columns, Font font, ColorPalette colors) {
        super(title);

        this.root = root;

        panel = new TextPanel(colors, font, rows, columns);
        this.setLayout(new GridLayout(1, 1));
        this.add(panel);
        this.pack();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public synchronized void addMouseListener(MouseListener l) {
        panel.addMouseListener(l);
    }

    public synchronized void addMouseMotionListener(MouseMotionListener l) {
        panel.addMouseMotionListener(l);
    }

    public void reDraw() {
        root.displayComponents(panel.view());
        repaint();
    }

    public int getGridWidth() {
        return panel.getGridWidth();
    }

    public int getGridHeight() {
        return panel.getGridHeight();
    }
}
