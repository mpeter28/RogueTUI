package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;

import java.util.ArrayList;
import java.util.List;

public class WrappedTextElement extends DisplayElement {

    private List<Word> wordList;

    public WrappedTextElement(DisplayBounds bounds) {
        setDisplayBounds(bounds);

        wordList = new ArrayList<>();
    }

    private void walkThroughWordList(WordWalkProcessor wordWalkProcessor) {
        DisplayBounds bounds = getDisplayBounds();

        int row = 0;
        int column = 0;

        for (Word word : wordList) {
            int remainingWidth = bounds.getWidth() - column;

            if (word.text.length() > remainingWidth) {
                row++;
                column = 0;

                if (row >= bounds.getHeight())
                    return;
            }

            wordWalkProcessor.processWord(word, row, column);

            column += word.text.length();
            column++;
        }
    }

    public void pushToBuffer(DisplayBuffer view) {
        view.setWritingBounds(getDisplayBounds());

        view.writeFill((char) 0, -1, -1);
        walkThroughWordList(new WorkWalkBufferWriter(view));
    }

    public void addText(String text, int textColor, int highlightColor) {
        String[] words = text.split(" ");

        for (String wordToAdd : words) {
            Word word = new Word(wordToAdd, textColor, highlightColor);
            wordList.add(word);
        }
    }

    public boolean hasExcessText() {
        WordWalkCounter walkCounter = new WordWalkCounter();
        walkThroughWordList(walkCounter);
        return walkCounter.counter < wordList.size();
    }

    public void advanceText() {
        WordWalkCounter walkCounter = new WordWalkCounter();
        walkThroughWordList(walkCounter);

        for (int i = 0; i < walkCounter.counter; i++)
            wordList.remove(0);
    }

    public void clearAllText() {
        wordList.clear();
    }

    private static class Word {
        public String text;
        public int textColor;
        public int highlightColor;

        private Word(String text, int textColor, int highlightColor) {
            this.text = text;
            this.textColor = textColor;
            this.highlightColor = highlightColor;
        }
    }

    private static interface WordWalkProcessor {
        public void processWord(Word word, int row, int column);
    }

    private static class WordWalkCounter implements WordWalkProcessor {
        public int counter;

        public void processWord(Word word, int row, int column) {
            counter++;
        }
    }

    private static class WorkWalkBufferWriter implements WordWalkProcessor {
        private DisplayBuffer buffer;

        private WorkWalkBufferWriter(DisplayBuffer buffer) {
            this.buffer = buffer;
        }

        public void processWord(Word word, int row, int column) {
            buffer.writeLine(word.text, row, column, word.textColor, word.highlightColor);
        }
    }
}
