package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;

import java.util.ArrayList;


public class TextInputElement extends DisplayElement {

    private int cursorPosition;
    private int startIndex;

    private int cursorTextColor;
    private int cursorHighlightColor;
    private int textColor;
    private int highlightColor;

    private ArrayList<Character> inputCharacters;

    public TextInputElement(int cornerRow, int cornerColumn, int width) {
        setDisplayBounds(new DisplayBounds(cornerRow, cornerColumn, 1, width));
        inputCharacters = new ArrayList<>();
    }

    public void pushToBuffer(DisplayBuffer view) {
        view.setWritingBounds(getDisplayBounds());
        view.writeFill((char) 0, textColor, highlightColor);

        for (int i = startIndex; i < inputCharacters.size(); i++) {
            view.writeChar(inputCharacters.get(i), 0, i - startIndex, textColor, highlightColor);
        }

        if (cursorPosition >= inputCharacters.size()) {
            view.writeChar((char) 0, 0, cursorPosition - startIndex, cursorTextColor, cursorHighlightColor);
        } else {
            view.writeChar(inputCharacters.get(cursorPosition),
                    0, cursorPosition - startIndex, cursorTextColor, cursorHighlightColor);
        }
    }

    public void insertCharacter(char character) {
        inputCharacters.add(cursorPosition, character);
        moveCursorRight();
    }

    public void setText(String text) {
        inputCharacters.clear();

        for (char c : text.toCharArray()) {
            inputCharacters.add(c);
        }

        moveCursorEnd();
    }

    public String getText() {
        StringBuilder sumOfInput = new StringBuilder();

        for (char c : inputCharacters) {
            sumOfInput.append(c);
        }

        return sumOfInput.toString();
    }

    public void setTextColors(int textColor, int highlightColor) {
        this.textColor = textColor;
        this.highlightColor = highlightColor;
    }

    public void setCursorColors(int textColor, int highlightColor) {
        this.cursorTextColor = textColor;
        this.cursorHighlightColor = highlightColor;
    }

    public void backspace() {
        if (cursorPosition > 0) {
            moveCursorLeft();
            inputCharacters.remove(cursorPosition);
        }
    }

    public void delete() {
        if (cursorPosition < inputCharacters.size()) {
            inputCharacters.remove(cursorPosition);
        }
    }

    public void moveCursorHome() {
        cursorPosition = 0;
        startIndex = 0;
    }

    public void moveCursorEnd() {
        cursorPosition = inputCharacters.size();

        startIndex = cursorPosition - getDisplayBounds().getWidth() + 1;
        if (startIndex < 0)
            startIndex = 0;
    }

    public void moveCursorLeft() {
        cursorPosition--;

        if (cursorPosition < 0)
            cursorPosition = 0;

        if (cursorPosition < startIndex)
            startIndex = cursorPosition;
    }

    public void moveCursorRight() {
        cursorPosition++;

        if (cursorPosition > inputCharacters.size())
            cursorPosition = inputCharacters.size();

        if (cursorPosition - startIndex >= getDisplayBounds().getWidth())
            startIndex = cursorPosition - getDisplayBounds().getWidth() + 1;
    }
}
