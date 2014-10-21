package com.antumbrastation.tui.elements;

import java.util.ArrayList;

public class FrameElement implements DisplayElement {

    private DisplayBounds bounds;
    private ArrayList<Line> vertical;
    private ArrayList<Line> horizontal;
    private int lineColor;

    private char horizontalChar;
    private char verticalChar;

    private class Line {
        int x, y, length;

        public Line(int y, int x, int length) {
            this.y = y;
            this.x = x;
            this.length = length;
        }
    }

    public FrameElement(DisplayBounds bounds) {
        this.bounds = bounds;
        this.lineColor = -1;

        vertical = new ArrayList<Line>();
        horizontal = new ArrayList<Line>();
        horizontalChar = '-';
        verticalChar = '|';
    }

    public void addVerticalLine(int row, int column, int length) {
        vertical.add(new Line(row, column, length));
    }

    public void addHorizontalLine(int row, int column, int length) {
        horizontal.add(new Line(row, column, length));
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public void setHorizontalChar(char horizontalChar) {
        this.horizontalChar = horizontalChar;
    }

    public void setVerticalChar(char verticalChar) {
        this.verticalChar = verticalChar;
    }

    public void pushToBuffer(DisplayBuffer view) {
        view.setWritingBounds(bounds);

        for (int i = 0; i < horizontal.size(); i++) {
            Line line = horizontal.get(i);
            for (int j = 0; j < line.length; j++)
                view.writeChar(horizontalChar, line.y, line.x + j, lineColor, 0);
        }

        for (int i = 0; i < vertical.size(); i++) {
            Line line = vertical.get(i);
            for (int j = 0; j < line.length; j++)
                view.writeChar(verticalChar, line.y + j, line.x, lineColor, 0);
        }
    }

    public DisplayBounds getDisplayBounds() {
        return bounds;
    }

}
