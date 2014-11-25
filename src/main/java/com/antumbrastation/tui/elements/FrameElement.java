package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;

import java.util.ArrayList;

public class FrameElement extends DisplayElement {

    private ArrayList<Line> lines;

    private class Line {
        int row, column, length;
        boolean horizontal;

        int textColor, highlightColor;
        char symbol;

        private Line(int row, int column, int length, boolean horizontal,
                     int textColor, int highlightColor, char symbol) {
            this.row = row;
            this.column = column;
            this.length = length;
            this.horizontal = horizontal;
            this.textColor = textColor;
            this.highlightColor = highlightColor;
            this.symbol = symbol;
        }
    }

    public FrameElement(DisplayBounds bounds) {
        setDisplayBounds(bounds);

        lines = new ArrayList<>();
    }

    public void addVerticalLine(int row, int column, int length,
                                char symbol, int textColor, int highlightColor) {
        lines.add(new Line(row, column, length, false, textColor, highlightColor, symbol));
    }

    public void addHorizontalLine(int row, int column, int length,
                                  char symbol, int textColor, int highlightColor) {
        lines.add(new Line(row, column, length, true, textColor, highlightColor, symbol));
    }

    public void pushToBuffer(DisplayBuffer view) {
        view.setWritingBounds(getDisplayBounds());

        for (Line line : lines) {
            if (line.horizontal) {
                for (int j = 0; j < line.length; j++)
                    view.writeChar(line.symbol, line.row, line.column + j, line.textColor, line.highlightColor);
            } else {
                for (int j = 0; j < line.length; j++)
                    view.writeChar(line.symbol, line.row + j, line.column, line.textColor, line.highlightColor);
            }
        }
    }

}