package com.antumbrastation.tui;

public class DisplayBuffer {

    private int[][] textColor;
    private int[][] highlightColor;
    private char[][] text;

    private DisplayBounds bounds;

    private int columns, rows;

    public DisplayBuffer(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        textColor = new int[rows][columns];
        highlightColor = new int[rows][columns];
        text = new char[rows][columns];
    }

    public int[][] getTextColor() {
        return textColor;
    }

    public int[][] getHighlightColor() {
        return highlightColor;
    }

    public char[][] getText() {
        return text;
    }

    public void setBounds(DisplayBounds bounds) {
        this.bounds = bounds;
    }

    public boolean writeChar(char character, int row, int column, int color, int highlight) {
        if (!bounds.inBounds(row, column))
            return false;

        row += bounds.getCornerRow();
        column += bounds.getCornerColumn();

        if (!inBounds(row, column))
            return false;

        text[row][column] = character;
        textColor[row][column] = color;
        highlightColor[row][column] = highlight;

        return true;
    }

    public boolean writeLine(String line, int row, int column, int color, int highlight) {
        boolean tooLong;
        for (char character: line.toCharArray()) {
            tooLong = !writeChar(character, row, column, color, highlight);
            if (tooLong)
                return false;
            column++;
        }

        return true;
    }

    public boolean writeFill(char fill, int color, int highlight) {
        for (int i = 0; i < bounds.getHeight(); i++)
            for (int j = 0; j < bounds.getWidth(); j++)
                writeChar(fill, i, j, color, highlight);

        return true;
    }

    public boolean inBounds(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

}
