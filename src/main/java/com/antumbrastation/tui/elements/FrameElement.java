package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.view.DisplayView;
import com.antumbrastation.tui.view.Window;

import java.util.ArrayList;

public class FrameElement implements DisplayElement {

    private Window window;
    private ArrayList<Line> vertical;
    private ArrayList<Line> horizontal;
    private int lineColor;

    private class Line {
        int x, y, length;

        public Line(int y, int x, int length) {
            this.y = y;
            this.x = x;
            this.length = length;
        }
    }

    public FrameElement(Window window, int lineColor) {
        this.window = window;
        this.lineColor = lineColor;

        vertical = new ArrayList<Line>();
        horizontal = new ArrayList<Line>();
    }

    public void addVerticalLine(int row, int column, int length) {
        vertical.add(new Line(row, column, length));
    }

    public void addHorizontalLine(int row, int column, int length) {
        horizontal.add(new Line(row, column, length));
    }

    public void display(DisplayView view) {
        view.setWindow(window);

        for (int i = 0; i < horizontal.size(); i++) {
            Line line = horizontal.get(i);
            for (int j = 0; j < line.length; j++)
                view.writeChar('-', line.y, line.x + j, lineColor, 0);
        }

        for (int i = 0; i < vertical.size(); i++) {
            Line line = vertical.get(i);
            for (int j = 0; j < line.length; j++)
                view.writeChar('|', line.y + j, line.x, lineColor, 0);
        }
    }
}
