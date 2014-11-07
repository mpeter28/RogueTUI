package com.antumbrastation.tui;

public class DisplayBuffer {
    private int[][] textColor;
    private int[][] highlightColor;
    private char[][] text;

    private DisplayBounds bufferBounds;
    private DisplayBounds writingBounds;

    public DisplayBuffer(DisplayBounds bounds) {
        bufferBounds = bounds;
        writingBounds = new DisplayBounds(bounds.getHeight(), bounds.getWidth());

        int rows = bounds.getHeight();
        int columns = bounds.getWidth();

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

    public void setWritingBounds(DisplayBounds writingBounds) {
        this.writingBounds = writingBounds;
    }

    public void writeChar(char character, int row, int column, int color, int highlight) {
        if (!writingBounds.inBounds(row, column))
            return;

        row += writingBounds.getCornerRow();
        column += writingBounds.getCornerColumn();

        if (!bufferBounds.inBounds(row, column))
            return;

        text[row][column] = character;
        textColor[row][column] = color;
        highlightColor[row][column] = highlight;
    }

    public void writeLine(String line, int row, int column, int color, int highlight) {
        for (char character: line.toCharArray()) {
            writeChar(character, row, column, color, highlight);
            column++;
        }
    }

    public void writeFill(char fill, int color, int highlight) {
        for (int i = 0; i < writingBounds.getHeight(); i++)
            for (int j = 0; j < writingBounds.getWidth(); j++)
                writeChar(fill, i, j, color, highlight);
    }

    public void copyToBuffer(DisplayBuffer copyTo) {
        copyTo.setWritingBounds(bufferBounds);

        int rows = bufferBounds.getHeight();
        int columns = bufferBounds.getWidth();

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                copyTo.writeChar(text[i][j], i, j, textColor[i][j], highlightColor[i][j]);
    }

    public DisplayBounds getDisplayBounds() {
        return bufferBounds;
    }

    public void moveDisplayBounds(int cornerRow, int cornerColumn) {
        bufferBounds = new DisplayBounds(cornerRow, cornerColumn,
                bufferBounds.getHeight(), bufferBounds.getWidth());
    }
}
