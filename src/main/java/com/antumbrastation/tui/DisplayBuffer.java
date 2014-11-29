package com.antumbrastation.tui;

public class DisplayBuffer {
    private int[][] textColor;
    private int[][] highlightColor;
    private char[][] text;

    private DisplayBounds bufferBounds;
    private DisplayBounds writingBounds;

    public DisplayBuffer(int rows, int columns) {
        bufferBounds = new DisplayBounds(rows, columns);
        writingBounds = new DisplayBounds(rows, columns);

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

    public DisplayBounds getWritingBounds() {
        return writingBounds;
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

    public void copyToBuffer(DisplayBuffer copyTo, int cornerRow, int cornerColumn) {
        int rows = bufferBounds.getHeight();
        int columns = bufferBounds.getWidth();

        copyTo.setWritingBounds(new DisplayBounds(cornerRow, cornerColumn, rows, columns));

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                copyTo.writeChar(text[i][j], i, j, textColor[i][j], highlightColor[i][j]);
    }

}
