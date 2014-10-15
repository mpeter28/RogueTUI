package com.antumbrastation.tui;

public class DisplayBounds {
    private int cornerRow, cornerColumn;
    private int height, width;

    public DisplayBounds(int height, int width) {
        this.cornerRow = 0;
        this.cornerColumn = 0;
        this.height = height;
        this.width = width;
    }

    public DisplayBounds(int cornerRow, int cornerColumn, int height, int width) {
        this.cornerRow = cornerRow;
        this.cornerColumn = cornerColumn;
        this.height = height;
        this.width = width;
    }

    public boolean outOfWindowAbsolute(int row, int column) {
        return !inWindowAbsolute(row, column);
    }

    public boolean inWindowAbsolute(int row, int column) {
        return row >= cornerRow && row < cornerRow + height && column >= cornerColumn && column < cornerColumn + width;
    }

    public boolean outOfWindow(int row, int column) {
        return !inWindow(row, column);
    }

    public boolean inWindow(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    public int getCornerRow() {
        return cornerRow;
    }

    public void setCornerRow(int cornerRow) {
        this.cornerRow = cornerRow;
    }

    public int getCornerColumn() {
        return cornerColumn;
    }

    public void setCornerColumn(int cornerColumn) {
        this.cornerColumn = cornerColumn;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
