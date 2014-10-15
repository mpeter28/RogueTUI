package com.antumbrastation.tui;

public class DisplayView {

    private int[][] textColor;
    private int[][] highlightColor;
    private char[][] text;

    private DisplayBounds window;

    private int columns, rows;

    public DisplayView(int rows, int columns) {
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

    public void setBounds(DisplayBounds window) {
        this.window = window;
    }

    public boolean writeChar(char character, int row, int column, int color, int highlight) {
        if (window.outOfWindow(row, column))
            return false;

        row += window.getCornerRow();
        column += window.getCornerColumn();

        if (outOfBounds(row, column))
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
        for (int i = 0; i < window.getHeight(); i++)
            for (int j = 0; j < window.getWidth(); j++)
                writeChar(fill, i, j, color, highlight);

        return true;
    }

    public boolean outOfBounds(int row, int column) {
        return !inBounds(row, column);
    }

    public boolean inBounds(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

}
