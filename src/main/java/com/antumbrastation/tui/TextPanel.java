package com.antumbrastation.tui;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;

public class TextPanel extends JPanel{
    private ColorPalette colors;
    private Font font;

    private int rows, columns;
    private int gridWidth, gridHeight;
    private float baseline;

    private char[][] text;
    private int[][] textColor;
    private int[][] highlight;

    public TextPanel(ColorPalette colors, Font font, int rows, int columns,
                     int gridHeight, int gridWidth, float baseline) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.baseline = baseline;

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

        setRenderingHints(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                setTextColor(g, textColor[row][col]);
                setHighlightColor(g, highlight[row][col]);

                g.clearRect(col * gridWidth, row * gridHeight, gridWidth,
                        gridHeight);

                if (text[row][col] != 0) {
                    GlyphVector v = getGlyphVector(g, text[row][col]);
                    renderGlyphVector(g, v, row, col);
                }
            }
        }
    }

    public void setRenderingHints(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }

    public void setTextColor(Graphics2D graphics2D, int colorIndex) {
        if (colorIndex != -1)
            graphics2D.setColor(colors.indexToColor(colorIndex));
        else
            graphics2D.setColor(colors.defaultTextColor());
    }

    public void setHighlightColor(Graphics2D graphics2D, int colorIndex) {
        if (colorIndex != -1)
            graphics2D.setBackground(colors.indexToColor(colorIndex));
        else
            graphics2D.setBackground(colors.defaultHighlightColor());
    }

    public GlyphVector getGlyphVector(Graphics2D graphics2D, char character) {
        char[] glyphText = new char[] {character};
        return font.createGlyphVector(graphics2D.getFontRenderContext(), glyphText);
    }

    public void renderGlyphVector(Graphics2D graphics2D, GlyphVector glyphVector, int row, int column) {
        float offset = (float) glyphVector.getVisualBounds().getMinX();
        float width = (float) glyphVector.getVisualBounds().getWidth();
        width = gridWidth - width;
        width /= 2;

        float squareX = column * gridWidth;
        float squareY = (row + 1) * gridHeight;
        graphics2D.drawGlyphVector(glyphVector, squareX - offset + width, squareY - baseline);
    }
}
