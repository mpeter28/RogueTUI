package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.view.DisplayView;
import com.antumbrastation.tui.view.Window;

import java.util.ArrayList;

public class CommandLineElement implements DisplayElement {

    private Window window;

    private ArrayList<Character> command;
    private int textColor;

    private int leftPosition;
    private int cursorPosition;
    private int cursorColor;

    public CommandLineElement(Window window) {
        this.window = window;

        command = new ArrayList<>();
        textColor = -1;

        leftPosition = 0;
        cursorPosition = 0;
        cursorColor = -1;
    }

    public String read() {
        StringBuilder b = new StringBuilder();
        for (char c: command) {
            b.append(c);
        }
        return b.toString();
    }

    public void clear() {
        command = new ArrayList<>();
    }

    public void typeCharacter(char c) {
        if (cursorPosition < command.size()) {
            command.add(cursorPosition, c);
        } else {
            command.add(c);
        }
        cursorRight();
    }

    public void delete() {
        if (cursorPosition < command.size()) {
            command.remove(cursorPosition);
        }
    }

    public void backspace() {
        cursorLeft();
        delete();
    }

    public void cursorHome() {
        cursorPosition = 0;
        leftPosition = 0;
    }

    public void cursorLeft() {
        cursorPosition--;

        if (cursorPosition < 0) {
            cursorPosition = 0;
        }

        if (cursorPosition < leftPosition) {
            leftPosition = cursorPosition;
        }
    }

    public void cursorRight() {
        cursorPosition++;

        if (cursorPosition > command.size()) {
            cursorPosition = command.size();
        }

        if (cursorPosition - leftPosition >= window.getWidth()) {
            leftPosition = cursorPosition - window.getWidth() + 1;
        }
    }

    public void display(DisplayView view) {
        view.setWindow(window);
        view.writeFill(' ', -1, -1);

        for (int i = leftPosition; i < command.size(); i++) {
            view.writeChar(command.get(i), 0, i - leftPosition, textColor, -1);
        }

        if (cursorPosition >= command.size()) {
            view.writeChar(' ', 0, cursorPosition - leftPosition, -1, cursorColor);
        } else {
            view.writeChar(command.get(cursorPosition), 0, cursorPosition - leftPosition,
                    -1, cursorColor);
        }
    }

    public Window getWindow() {
        return window;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
    }
}
