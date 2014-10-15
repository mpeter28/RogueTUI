package com.antumbrastation.tui;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;

public class TextPanel extends JPanel{

    private ColorPalette colors;
    private Font font;

    private int rows, columns;
    private int gridWidth, gridHeight;
    private DisplayView displayView;

    public TextPanel(ColorPalette colors, Font font, int rows, int columns) {
        this.gridHeight = font.getSize() + 2;
        this.gridWidth = gridHeight * 2 / 3;
        this.colors = colors;
        this.font = font;
        this.rows = rows;
        this.columns = columns;

        displayView = new DisplayView(rows, columns);

        this.setBackground(colors.defaultHighlightColor());
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
        char[] glyph = new char[1];

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
                    glyph[0] = text[row][col];

                    GlyphVector v = font.createGlyphVector(g.getFontRenderContext(), glyph);

                    float descent = (float) v.getVisualBounds().getMaxY();
                    if (descent < 0) {
                        descent = 0;
                    }

                    float offset = (float) v.getVisualBounds().getMinX();
                    float width = (float) v.getVisualBounds().getWidth();
                    width = gridWidth - width;
                    width /= 2;

                    float squareX = col * gridWidth;
                    float squareY = (row + 1) * gridHeight;
                    g.drawGlyphVector(v, squareX - offset + width, squareY - descent - 1);
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
