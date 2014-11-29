package com.antumbrastation.tui;

import org.junit.Assert;
import org.junit.Test;

public class DisplayBufferTest {

    @Test
    public void properArraySizesTest() {
        DisplayBuffer buffer = new DisplayBuffer(5, 4);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        Assert.assertEquals(5, text.length);
        Assert.assertEquals(5, textColor.length);
        Assert.assertEquals(5, highlightColor.length);

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(4, text[i].length);
            Assert.assertEquals(4, textColor[i].length);
            Assert.assertEquals(4, highlightColor[i].length);
        }
    }

    @Test
    public void properBoundsFromConstructorTest() {
        DisplayBuffer buffer = new DisplayBuffer(3, 4);

        Assert.assertEquals(0, buffer.getWritingBounds().getCornerRow());
        Assert.assertEquals(0, buffer.getWritingBounds().getCornerColumn());
        Assert.assertEquals(3, buffer.getWritingBounds().getHeight());
        Assert.assertEquals(4, buffer.getWritingBounds().getWidth());
    }

    @Test
    public void setWritingBoundsTest() {
        DisplayBuffer buffer = new DisplayBuffer(3, 4);
        buffer.setWritingBounds(new DisplayBounds(2, 2, 2, 2));

        Assert.assertEquals(2, buffer.getWritingBounds().getCornerRow());
        Assert.assertEquals(2, buffer.getWritingBounds().getCornerColumn());
        Assert.assertEquals(2, buffer.getWritingBounds().getHeight());
        Assert.assertEquals(2, buffer.getWritingBounds().getWidth());
    }

    @Test
    public void writeCharTest() {
        DisplayBuffer buffer = new DisplayBuffer(7, 7);
        buffer.setWritingBounds(new DisplayBounds(2, 2, 2, 2));

        buffer.writeChar('a', -1, 1, 1, 1);
        buffer.writeChar('a', 1, -1, 1, 1);
        buffer.writeChar('a', 1, 1, 1, 1);
        buffer.writeChar('a', 4, 4, 2, 2);
        buffer.writeChar('a', 3, 2, 3, 3);

        char[][] text = buffer.getText();
        int[][] textColor = buffer.getTextColor();
        int[][] highlightColor = buffer.getHighlightColor();

        Assert.assertEquals(0, text[1][2]);
        Assert.assertEquals(0, text[2][1]);
        Assert.assertEquals('a', text[3][3]);
        Assert.assertEquals(0, text[6][6]);
        Assert.assertEquals(0, text[5][4]);

        Assert.assertEquals(0, textColor[1][2]);
        Assert.assertEquals(0, textColor[2][1]);
        Assert.assertEquals(1, textColor[3][3]);
        Assert.assertEquals(0, textColor[6][6]);
        Assert.assertEquals(0, textColor[5][4]);

        Assert.assertEquals(0, highlightColor[1][2]);
        Assert.assertEquals(0, highlightColor[2][1]);
        Assert.assertEquals(1, highlightColor[3][3]);
        Assert.assertEquals(0, highlightColor[6][6]);
        Assert.assertEquals(0, highlightColor[5][4]);

        buffer.setWritingBounds(new DisplayBounds(-1, -1, 12, 12));

        buffer.writeChar('a', 0, 0, 1, 1);
        buffer.writeChar('a', 10, 10, 2, 2);
        buffer.writeChar('a', 1, 1, 3, 3);

        Assert.assertEquals('a', text[0][0]);
        Assert.assertEquals(3, textColor[0][0]);
        Assert.assertEquals(3, highlightColor[0][0]);
    }

    @Test
    public void writeLineTest() {
        DisplayBuffer buffer = new DisplayBuffer(5, 5);
        buffer.writeLine("A B C", 0, 0, 1, 1);
        buffer.writeLine("ABCDE", 1, 2, 2, 2);
        buffer.writeLine("ABCDE", 5, 0, 2, 2);
        buffer.writeLine("ABCDE", 2, -2, 2, 2);

        for (int j = 0; j < 5; j++) {
            Assert.assertEquals(1, buffer.getTextColor()[0][j]);
            Assert.assertEquals(1, buffer.getHighlightColor()[0][j]);
        }

        Assert.assertEquals('A', buffer.getText()[0][0]);
        Assert.assertEquals(' ', buffer.getText()[0][1]);
        Assert.assertEquals('B', buffer.getText()[0][2]);
        Assert.assertEquals(' ', buffer.getText()[0][3]);
        Assert.assertEquals('C', buffer.getText()[0][4]);

        Assert.assertEquals(0, buffer.getText()[1][0]);
        Assert.assertEquals(0, buffer.getText()[1][1]);
        Assert.assertEquals('A', buffer.getText()[1][2]);
        Assert.assertEquals('B', buffer.getText()[1][3]);
        Assert.assertEquals('C', buffer.getText()[1][4]);

        Assert.assertEquals('C', buffer.getText()[2][0]);
        Assert.assertEquals('D', buffer.getText()[2][1]);
        Assert.assertEquals('E', buffer.getText()[2][2]);
        Assert.assertEquals(0, buffer.getText()[2][3]);
        Assert.assertEquals(0, buffer.getText()[2][4]);
    }

    @Test
    public void writeFillTest() {
        DisplayBuffer buffer = new DisplayBuffer(10, 10);
        buffer.writeFill('a', 2, 3);

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                Assert.assertEquals('a', buffer.getText()[i][j]);
                Assert.assertEquals(2, buffer.getTextColor()[i][j]);
                Assert.assertEquals(3, buffer.getHighlightColor()[i][j]);
            }

        buffer.setWritingBounds(new DisplayBounds(5, 0, 5, 10));
        buffer.writeFill('b', 4, 5);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 10; j++) {
                Assert.assertEquals('a', buffer.getText()[i][j]);
                Assert.assertEquals(2, buffer.getTextColor()[i][j]);
                Assert.assertEquals(3, buffer.getHighlightColor()[i][j]);
            }

        for (int i = 5; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                Assert.assertEquals('b', buffer.getText()[i][j]);
                Assert.assertEquals(4, buffer.getTextColor()[i][j]);
                Assert.assertEquals(5, buffer.getHighlightColor()[i][j]);
            }

        buffer.setWritingBounds(new DisplayBounds(5, 0, 10, 10));
        buffer.writeFill('c', 6, 7);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 10; j++) {
                Assert.assertEquals('a', buffer.getText()[i][j]);
                Assert.assertEquals(2, buffer.getTextColor()[i][j]);
                Assert.assertEquals(3, buffer.getHighlightColor()[i][j]);
            }

        for (int i = 5; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                Assert.assertEquals('c', buffer.getText()[i][j]);
                Assert.assertEquals(6, buffer.getTextColor()[i][j]);
                Assert.assertEquals(7, buffer.getHighlightColor()[i][j]);
            }
    }

    @Test
    public void copyToBufferTest() {
        DisplayBuffer copyTo = new DisplayBuffer(10, 10);
        DisplayBuffer copyFrom = new DisplayBuffer(5, 5);

        copyFrom.writeFill('a', 1, 1);
        copyFrom.copyToBuffer(copyTo, 2, 2);

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (i >= 2 && j >= 2 && i < 7 && j < 7) {
                    Assert.assertEquals('a', copyTo.getText()[i][j]);
                    Assert.assertEquals(1, copyTo.getTextColor()[i][j]);
                    Assert.assertEquals(1, copyTo.getHighlightColor()[i][j]);
                } else {
                    Assert.assertEquals(0, copyTo.getText()[i][j]);
                    Assert.assertEquals(0, copyTo.getTextColor()[i][j]);
                    Assert.assertEquals(0, copyTo.getHighlightColor()[i][j]);
                }
            }

        Assert.assertEquals(2, copyTo.getWritingBounds().getCornerRow());
        Assert.assertEquals(2, copyTo.getWritingBounds().getCornerColumn());
        Assert.assertEquals(5, copyTo.getWritingBounds().getHeight());
        Assert.assertEquals(5, copyTo.getWritingBounds().getWidth());
    }

}
