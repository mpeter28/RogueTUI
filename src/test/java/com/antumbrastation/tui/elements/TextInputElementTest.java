package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBuffer;
import org.junit.Assert;
import org.junit.Test;

public class TextInputElementTest {

    @Test
    public void constructorTest() {
        TextInputElement textInput = new TextInputElement(1, 2, 6);

        Assert.assertEquals(1, textInput.getDisplayBounds().getCornerRow());
        Assert.assertEquals(2, textInput.getDisplayBounds().getCornerColumn());
        Assert.assertEquals(1,textInput.getDisplayBounds().getHeight());
        Assert.assertEquals(6, textInput.getDisplayBounds().getWidth());

        Assert.assertEquals("", textInput.getText());
    }

    @Test
    public void getAndSetTextTest() {
        TextInputElement textInput = new TextInputElement(0, 0, 6);

        textInput.setText("Foo foo foo");
        Assert.assertEquals("Foo foo foo", textInput.getText());
    }

    @Test
    public void setColorsTest() {
        TextInputElement textInput = new TextInputElement(0, 0, 30);

        textInput.setText("Foo foo foo");
        textInput.setCursorColors(1, 2);
        textInput.setTextColors(3, 4);

        DisplayBuffer buffer = new DisplayBuffer(1, 30);
        textInput.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        for (int col = 0; col < 30; col++) {
            if (col != 11) {
                Assert.assertEquals(3, textColor[0][col]);
                Assert.assertEquals(4, highlightColor[0][col]);
            }
        }

        Assert.assertEquals(1, textColor[0][11]);
        Assert.assertEquals(2, highlightColor[0][11]);

        Assert.assertEquals('F', text[0][0]);
        Assert.assertEquals(' ', text[0][3]);
        Assert.assertEquals('f', text[0][4]);
        Assert.assertEquals(' ', text[0][7]);
        Assert.assertEquals(0, text[0][11]);
        Assert.assertEquals(0, text[0][29]);
    }

    @Test
    public void backspaceTest() {
        TextInputElement textInput = new TextInputElement(0, 0, 30);

        textInput.setText("TestTestTest");

        textInput.backspace();
        Assert.assertEquals("TestTestTes", textInput.getText());

        textInput.moveCursorLeft();
        textInput.backspace();
        Assert.assertEquals("TestTestTs", textInput.getText());

        textInput.moveCursorHome();
        textInput.backspace();
        Assert.assertEquals("TestTestTs", textInput.getText());
    }

    @Test
    public void deleteTest() {
        TextInputElement textInput = new TextInputElement(0, 0, 30);

        textInput.setText("TestTestTest");

        textInput.delete();
        Assert.assertEquals("TestTestTest", textInput.getText());

        textInput.moveCursorLeft();
        textInput.delete();
        Assert.assertEquals("TestTestTes", textInput.getText());

        textInput.moveCursorHome();
        textInput.delete();
        Assert.assertEquals("estTestTes", textInput.getText());
    }

    @Test
    public void moveCursorHomeTest() {
        TextInputElement textInput = new TextInputElement(0, 0, 10);

        textInput.setText("Foo foo foo");
        textInput.setCursorColors(1, 2);
        textInput.setTextColors(3, 4);
        textInput.moveCursorHome();

        DisplayBuffer buffer = new DisplayBuffer(1, 10);
        textInput.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        Assert.assertEquals(1, textColor[0][0]);
        Assert.assertEquals(2, highlightColor[0][0]);

        Assert.assertEquals('F', text[0][0]);
        Assert.assertEquals('o', text[0][9]);
    }

    @Test
    public void moveCursorEndTest() {
        TextInputElement textInput = new TextInputElement(0, 0, 10);

        textInput.setText("Foo foo foo");
        textInput.setCursorColors(1, 2);
        textInput.setTextColors(3, 4);

        textInput.moveCursorHome();
        textInput.moveCursorEnd();

        DisplayBuffer buffer = new DisplayBuffer(1, 10);
        textInput.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        Assert.assertEquals(0, text[0][9]);
        Assert.assertEquals(1, textColor[0][9]);
        Assert.assertEquals(2, highlightColor[0][9]);
    }

    @Test
    public void moveCursorLeftTest() {
        TextInputElement textInput = new TextInputElement(0, 0, 10);

        textInput.setText("Foo foo aoo bbcbbb");
        textInput.setCursorColors(1, 2);
        textInput.setTextColors(3, 4);

        DisplayBuffer buffer = new DisplayBuffer(1, 10);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        textInput.moveCursorLeft();
        textInput.moveCursorLeft();
        textInput.moveCursorLeft();
        textInput.moveCursorLeft();

        textInput.pushToBuffer(buffer);
        Assert.assertEquals('c', text[0][5]);
        Assert.assertEquals(1, textColor[0][5]);
        Assert.assertEquals(2, highlightColor[0][5]);

        textInput.moveCursorLeft();
        textInput.moveCursorLeft();
        textInput.moveCursorLeft();
        textInput.moveCursorLeft();
        textInput.moveCursorLeft();
        textInput.moveCursorLeft();

        textInput.pushToBuffer(buffer);
        Assert.assertEquals('a', text[0][0]);
        Assert.assertEquals(1, textColor[0][0]);
        Assert.assertEquals(2, highlightColor[0][0]);

        textInput.moveCursorHome();
        textInput.moveCursorLeft();

        textInput.pushToBuffer(buffer);
        Assert.assertEquals('F', text[0][0]);
        Assert.assertEquals(1, textColor[0][0]);
        Assert.assertEquals(2, highlightColor[0][0]);
    }

    @Test
    public void moveCursorRightTest() {
        TextInputElement textInput = new TextInputElement(0, 0, 10);

        textInput.setText("Foo foo foa bbbbbc");
        textInput.setCursorColors(1, 2);
        textInput.setTextColors(3, 4);

        DisplayBuffer buffer = new DisplayBuffer(1, 10);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        textInput.moveCursorHome();
        textInput.moveCursorRight();
        textInput.moveCursorRight();
        textInput.moveCursorRight();
        textInput.moveCursorRight();

        textInput.pushToBuffer(buffer);
        Assert.assertEquals(1, textColor[0][4]);
        Assert.assertEquals(2, highlightColor[0][4]);

        textInput.moveCursorRight();
        textInput.moveCursorRight();
        textInput.moveCursorRight();
        textInput.moveCursorRight();
        textInput.moveCursorRight();
        textInput.moveCursorRight();

        textInput.pushToBuffer(buffer);
        Assert.assertEquals('a', text[0][9]);
        Assert.assertEquals(1, textColor[0][9]);
        Assert.assertEquals(2, highlightColor[0][9]);

        textInput.moveCursorEnd();
        textInput.moveCursorRight();

        textInput.pushToBuffer(buffer);
        Assert.assertEquals(0, text[0][9]);
        Assert.assertEquals(1, textColor[0][9]);
        Assert.assertEquals(2, highlightColor[0][9]);
    }

    @Test
    public void typeCharacterTest() {
        TextInputElement textInput = new TextInputElement(0, 0, 6);

        textInput.setText("Foo foo foo");
        textInput.insertCharacter('a');
        textInput.insertCharacter('c');
        textInput.moveCursorHome();
        textInput.insertCharacter('b');
        textInput.insertCharacter('d');
        Assert.assertEquals("bdFoo foo fooac", textInput.getText());
    }
}