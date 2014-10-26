package com.antumbrastation.tui;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;

public class TextPanel extends JPanel{
    private ColorPalette colors;
    private Font font;

    private int rows, columns;
    private int gridWidth, gridHeight;

    private char[][] text;
    private int[][] textColor;
    private int[][] highlight;

    public TextPanel(ColorPalette colors, Font font, int rows, int columns, int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.colors = colors;
        this.font = font;
        this.rows = rows;
        this.columns = columns;

        this.setPreferredSize(new Dimension(columns * gridWidth, rows * gridHeight));
    }

    public void update(char[][] text, int[][] textColor, int[][] highlight) {
        this.text = text;
        this.textColor = textColor;
        this.highlight = highlight;
    }

    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        if (text == null || textColor == null || highlight == null) {
            g.setBackground(colors.defaultHighlightColor());
            g.clearRect(0, 0, gridWidth * columns, gridHeight * rows);
            return;
        }

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setFont(font);

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
}
