package com.antumbrastation.roguedisplay.view;

import com.antumbrastation.roguedisplay.components.DisplayComponent;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;

public class RogueFrame extends JFrame {

    private TextPanel panel;
    private int columnWidth, rowHeight;

    private DisplayComponent root;
    private Semaphore taskLock;

    public RogueFrame(String title, DisplayComponent root, Semaphore taskLock,
                      int rows, int columns, Font font, ColorPalette colors) {
        super(title);

        this.root = root;
        this.taskLock = taskLock;

        rowHeight = font.getSize();
        columnWidth = rowHeight * 2 / 3;

        panel = new TextPanel(colors, font, rows, columns, rowHeight, columnWidth);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new GridLayout(1, 1));
        this.add(panel);

        this.setSize(columns * columnWidth + 6, rows * rowHeight + 25);
    }

    public void paint(Graphics g) {
        taskLock.acquireUninterruptibly();
        super.paint(g);
        taskLock.release();
    }

    public void reDraw() {
        root.display(panel.view(), true);
    }

    public int getRowWidth() {
        return columnWidth;
    }

    public int getRowHeight() {
        return rowHeight;
    }
}
