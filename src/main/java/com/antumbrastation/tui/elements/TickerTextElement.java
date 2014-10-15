package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayView;

import java.util.ArrayList;

public class TickerTextElement implements DisplayElement {

    private ArrayList<String> pageWords;
    private ArrayList<Integer> pageColors;

    private DisplayBounds window;
    private int nextWord;

    private String moreMessage;
    private int moreMessageColor;
    private int moreMessageHighlight;

    public TickerTextElement(DisplayBounds window) {
        this.window = window;

        pageWords = new ArrayList<String>();
        pageColors = new ArrayList<Integer>();

        moreMessage = "-- More --";
        moreMessageColor = -1;
        moreMessageHighlight = -1;
    }

    public void display(DisplayView view) {
        view.setBounds(window);
        view.writeFill(' ', -1, -1);

        int row = 0;
        int column = 0;

        nextWord = 0;

        for (int i = 0; i < pageWords.size(); i++) {
            String word = pageWords.get(i);
            int color = pageColors.get(i);

            if (window.getWidth() - column < word.length()) {
                row++;
                column = 0;
                if (row >= window.getHeight() - 1)
                    break;
            }

            view.writeLine(word, row, column, color, -1);

            column += word.length();
            column++;

            nextWord++;
        }

        if (hasMore()) {
            view.writeLine(moreMessage, window.getHeight() - 1,
                    window.getWidth() - moreMessage.length(), moreMessageColor, moreMessageHighlight);
        }
    }

    public DisplayBounds getDisplayBounds() {
        return window;
    }

    public void setMoreMessage(String message, int color, int highlight) {
        moreMessage = message;
        moreMessageColor = color;
        moreMessageHighlight = highlight;
    }

    public void addMessage(String message, int color) {
        String[] split = message.split(" ");

        for (String word: split) {
            pageWords.add(word);
            pageColors.add(color);
        }
    }

    public void clear() {
        nextWord = 0;
        pageColors.clear();
        pageWords.clear();
    }

    public boolean hasMore() {
        return (nextWord < pageWords.size());
    }

    public boolean nextPage() {
        if (pageWords.size() <= nextWord)
            return false;

        for (int i = 0; i < nextWord; i++) {
            pageWords.remove(0);
            pageColors.remove(0);
        }

        nextWord = 0;
        return true;
    }
}
