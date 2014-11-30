package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;
import org.junit.Assert;
import org.junit.Test;

public class WrappedTextElementTest {

    @Test
    public void constructorTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(1,2,3,4));

        Assert.assertEquals(1, wrapped.getDisplayBounds().getCornerRow());
        Assert.assertEquals(2, wrapped.getDisplayBounds().getCornerColumn());
        Assert.assertEquals(3, wrapped.getDisplayBounds().getHeight());
        Assert.assertEquals(4, wrapped.getDisplayBounds().getWidth());
    }

    @Test
    public void noTextToDisplayTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(1,1,4,4));
        DisplayBuffer buffer = new DisplayBuffer(6,6);
        wrapped.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        for (int row = 1; row < 5; row++) {
            for (int column = 1; column < 5; column++) {
                Assert.assertEquals(0, text[row][column]);
                Assert.assertEquals(-1, textColor[row][column]);
                Assert.assertEquals(-1, highlightColor[row][column]);
            }
        }
    }

    @Test
    public void addTextTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(2,8));
        DisplayBuffer buffer = new DisplayBuffer(2,8);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        wrapped.addText("Lorem", 2, 3);
        wrapped.pushToBuffer(buffer);

        Assert.assertEquals('L', text[0][0]);
        Assert.assertEquals(2, textColor[0][0]);
        Assert.assertEquals(3, highlightColor[0][0]);

        Assert.assertEquals(0, text[0][5]);
        Assert.assertEquals(-1, textColor[0][5]);
        Assert.assertEquals(-1, highlightColor[0][5]);

        Assert.assertEquals(0, text[1][0]);
        Assert.assertEquals(-1, textColor[1][0]);
        Assert.assertEquals(-1, highlightColor[1][0]);

        wrapped.addText("ipsum", 2, 3);
        wrapped.pushToBuffer(buffer);

        Assert.assertEquals('L', text[0][0]);
        Assert.assertEquals(2, textColor[0][0]);
        Assert.assertEquals(3, highlightColor[0][0]);

        Assert.assertEquals(0, text[0][5]);
        Assert.assertEquals(-1, textColor[0][5]);
        Assert.assertEquals(-1, highlightColor[0][5]);

        Assert.assertEquals('i', text[1][0]);
        Assert.assertEquals(2, textColor[1][0]);
        Assert.assertEquals(3, highlightColor[1][0]);

        Assert.assertEquals(0, text[1][5]);
        Assert.assertEquals(-1, textColor[1][5]);
        Assert.assertEquals(-1, highlightColor[1][5]);
    }

    @Test
    public void wrappedTextDisplayTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(2,8));
        DisplayBuffer buffer = new DisplayBuffer(2,8);

        wrapped.addText("Lorem ipsum", 2, 3);
        wrapped.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        Assert.assertEquals('L', text[0][0]);
        Assert.assertEquals(2, textColor[0][0]);
        Assert.assertEquals(3, highlightColor[0][0]);

        Assert.assertEquals(0, text[0][5]);
        Assert.assertEquals(-1, textColor[0][5]);
        Assert.assertEquals(-1, highlightColor[0][5]);

        Assert.assertEquals('i', text[1][0]);
        Assert.assertEquals(2, textColor[1][0]);
        Assert.assertEquals(3, highlightColor[1][0]);

        Assert.assertEquals(0, text[1][5]);
        Assert.assertEquals(-1, textColor[1][5]);
        Assert.assertEquals(-1, highlightColor[1][5]);
    }

    @Test
    public void excessTextDisplayTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(2,8));
        DisplayBuffer buffer = new DisplayBuffer(2,8);

        wrapped.addText("Lorem ipsum doler sit amet", 2, 3);
        wrapped.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        Assert.assertEquals('L', text[0][0]);
        Assert.assertEquals(2, textColor[0][0]);
        Assert.assertEquals(3, highlightColor[0][0]);

        Assert.assertEquals(0, text[0][5]);
        Assert.assertEquals(-1, textColor[0][5]);
        Assert.assertEquals(-1, highlightColor[0][5]);

        Assert.assertEquals('i', text[1][0]);
        Assert.assertEquals(2, textColor[1][0]);
        Assert.assertEquals(3, highlightColor[1][0]);

        Assert.assertEquals(0, text[1][5]);
        Assert.assertEquals(-1, textColor[1][5]);
        Assert.assertEquals(-1, highlightColor[1][5]);
    }

    @Test
    public void hasExcessTextTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(2,8));

        wrapped.addText("Lorem", 2, 3);
        Assert.assertFalse(wrapped.hasExcessText());

        wrapped.addText("ipsum", 2, 3);
        Assert.assertFalse(wrapped.hasExcessText());

        wrapped.addText("doler", 2, 3);
        Assert.assertTrue(wrapped.hasExcessText());
    }

    @Test
    public void advanceTextWithExcessTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(2,8));
        DisplayBuffer buffer = new DisplayBuffer(2,8);

        wrapped.addText("Lorem ipsum doler sit amet", 2, 3);
        wrapped.advanceText();
        wrapped.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        Assert.assertEquals('d', text[0][0]);
        Assert.assertEquals(2, textColor[0][0]);
        Assert.assertEquals(3, highlightColor[0][0]);

        Assert.assertEquals(0, text[0][5]);
        Assert.assertEquals(-1, textColor[0][5]);
        Assert.assertEquals(-1, highlightColor[0][5]);

        Assert.assertEquals('s', text[1][0]);
        Assert.assertEquals(2, textColor[1][0]);
        Assert.assertEquals(3, highlightColor[1][0]);

        Assert.assertEquals(0, text[1][3]);
        Assert.assertEquals(-1, textColor[1][3]);
        Assert.assertEquals(-1, highlightColor[1][3]);

        Assert.assertEquals('a', text[1][4]);
        Assert.assertEquals(2, textColor[1][4]);
        Assert.assertEquals(3, highlightColor[1][4]);
    }

    @Test
    public void advanceTextWithoutExcessTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(2,8));
        DisplayBuffer buffer = new DisplayBuffer(2,8);

        wrapped.addText("Lorem ipsum", 2, 3);
        wrapped.advanceText();
        wrapped.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 8; column++) {
                Assert.assertEquals(0, text[row][column]);
                Assert.assertEquals(-1, textColor[row][column]);
                Assert.assertEquals(-1, highlightColor[row][column]);
            }
        }
    }

    @Test
    public void clearAllTextTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(2,8));
        wrapped.addText("Lorem ipsum doler sit amet", 2, 3);
        wrapped.clearAllText();

        DisplayBuffer buffer = new DisplayBuffer(2,8);
        wrapped.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 8; column++) {
                Assert.assertEquals(0, text[row][column]);
                Assert.assertEquals(-1, textColor[row][column]);
                Assert.assertEquals(-1, highlightColor[row][column]);
            }
        }
    }

    @Test
    public void tooLargeWordTest() {
        WrappedTextElement wrapped = new WrappedTextElement(new DisplayBounds(2,8));
        wrapped.addText("Its Gargantuan", 2, 3);

        DisplayBuffer buffer = new DisplayBuffer(2,8);
        wrapped.pushToBuffer(buffer);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        Assert.assertEquals('I', text[0][0]);
        Assert.assertEquals(2, textColor[0][0]);
        Assert.assertEquals(3, highlightColor[0][0]);

        Assert.assertEquals('G', text[1][0]);
        Assert.assertEquals(2, textColor[1][0]);
        Assert.assertEquals(3, highlightColor[1][0]);

        Assert.assertFalse(wrapped.hasExcessText());
    }
}
