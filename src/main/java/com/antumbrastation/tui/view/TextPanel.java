package com.antumbrastation.tui.view;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel{

    private ColorPalette colors;
    private Font font;

    private int rows, columns;
    private int gridWidth, gridHeight;
    private DisplayView view;

    public TextPanel(ColorPalette colors, Font font, int rows, int columns) {
        this.gridHeight = font.getSize();
        this.gridWidth = gridHeight * 2 / 3;
        this.colors = colors;
        this.font = font;
        this.rows = rows;
        this.columns = columns;

        view = new DisplayView(rows, columns);

        this.setPreferredSize(new Dimension(columns * gridWidth, rows * gridHeight));
    }

    public DisplayView view() {
        return view;
    }

    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setBackground(Color.BLACK);
        g.setFont(font);

        char[][] text = view.getText();
        int[][] textColor = view.getTextColor();
        int[][] highlight = view.getHighlightColor();

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

                if (text[row][col] != 0)
                    g.drawChars(text[row], col, 1, col * gridWidth,
                            (row + 1) * gridHeight - (int) (gridHeight / 4));
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
