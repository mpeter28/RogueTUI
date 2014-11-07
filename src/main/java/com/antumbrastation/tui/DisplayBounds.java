package com.antumbrastation.tui;

public class DisplayBounds {
    private int cornerRow, cornerColumn;
    private int height, width;

    public DisplayBounds(int height, int width) {
        this(0, 0, height, width);
    }

    public DisplayBounds(int cornerRow, int cornerColumn, int height, int width) {
        this.cornerRow = cornerRow;
        this.cornerColumn = cornerColumn;
        this.height = height;
        this.width = width;
    }

    public boolean inBoundsAbsolute(int row, int column) {
        return row >= cornerRow && row < cornerRow + height && column >= cornerColumn && column < cornerColumn + width;
    }

    public boolean inBounds(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    public int getCornerRow() {
        return cornerRow;
    }

    public int getCornerColumn() {
        return cornerColumn;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
