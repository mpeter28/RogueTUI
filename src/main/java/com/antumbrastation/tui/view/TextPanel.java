package com.antumbrastation.tui.view;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel{

    private ColorPalette colors;
    private Font font;

    private int rows, columns;
    private int gridWidth, gridHeight;
    private DisplayView displayView;

    private FontSpacingHints fontHints;

    public TextPanel(ColorPalette colors, Font font, FontSpacingHints fontHints, int rows, int columns) {
        this.fontHints = fontHints;

        this.gridHeight = fontHints.getGridHeight();
        this.gridWidth = fontHints.getGridWidth();
        this.colors = colors;
        this.font = font;
        this.rows = rows;
        this.columns = columns;

        displayView = new DisplayView(rows, columns);

        this.setPreferredSize(new Dimension(columns * gridWidth, rows * gridHeight));
    }

    public DisplayView getDisplayView() {
        return displayView;
    }

    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setFont(font);

        char[][] text = displayView.getText();
        int[][] textColor = displayView.getTextColor();
        int[][] highlight = displayView.getHighlightColor();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (textColor[row][col] != -1)
                    g.setColor(colors.indexToColor(textColor[row][col]));
                else
                    g.setColor(colors.defaultTextColor());

                if (highlight[row][col] != -1)
                    g.setBackground(colors.indexToColor(highlight[row][col]));
                else
                    g.setBackground(colors.defaultHighlightColor());

                g.clearRect(col * gridWidth, row * gridHeight, gridWidth,
                        gridHeight);

                if (text[row][col] != 0) {
                    char c = text[row][col];

                    g.drawChars(text[row], col, 1, col * gridWidth + fontHints.getCharLeftMargin(c),
                            (row + 1) * gridHeight - fontHints.getCharDescentMargin(c));
                }
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }
}
