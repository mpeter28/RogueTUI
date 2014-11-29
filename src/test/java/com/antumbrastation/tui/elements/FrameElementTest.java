package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;
import org.junit.Assert;
import org.junit.Test;

public class FrameElementTest {

    @Test
    public void ConstructorTest() {
        FrameElement frame = new FrameElement(new DisplayBounds(1,2,5,6));

        Assert.assertEquals(1,frame.getDisplayBounds().getCornerRow());
        Assert.assertEquals(2,frame.getDisplayBounds().getCornerColumn());
        Assert.assertEquals(5,frame.getDisplayBounds().getHeight());
        Assert.assertEquals(6,frame.getDisplayBounds().getWidth());
    }

    @Test
    public void AddHorizontalLineTest() {
        FrameElement frame = new FrameElement(new DisplayBounds(1,1,6,6));
        frame.addHorizontalLine(0,1,5,'=',1,1);

        DisplayBuffer buffer = new DisplayBuffer(10, 10);
        frame.pushToBuffer(buffer);

        Assert.assertEquals('=', buffer.getText()[1][2]);
        Assert.assertEquals('=', buffer.getText()[1][3]);
        Assert.assertEquals('=', buffer.getText()[1][4]);
        Assert.assertEquals('=', buffer.getText()[1][5]);
        Assert.assertEquals('=', buffer.getText()[1][6]);

        Assert.assertEquals(1, buffer.getTextColor()[1][2]);
        Assert.assertEquals(1, buffer.getTextColor()[1][3]);
        Assert.assertEquals(1, buffer.getTextColor()[1][4]);
        Assert.assertEquals(1, buffer.getTextColor()[1][5]);
        Assert.assertEquals(1, buffer.getTextColor()[1][6]);

        Assert.assertEquals(1, buffer.getHighlightColor()[1][2]);
        Assert.assertEquals(1, buffer.getHighlightColor()[1][3]);
        Assert.assertEquals(1, buffer.getHighlightColor()[1][4]);
        Assert.assertEquals(1, buffer.getHighlightColor()[1][5]);
        Assert.assertEquals(1, buffer.getHighlightColor()[1][6]);
    }

    @Test
    public void AddVerticalLineTest() {
        FrameElement frame = new FrameElement(new DisplayBounds(1,1,6,6));
        frame.addVerticalLine(1,0,5,'=',1,1);

        DisplayBuffer buffer = new DisplayBuffer(10, 10);
        frame.pushToBuffer(buffer);

        Assert.assertEquals('=', buffer.getText()[2][1]);
        Assert.assertEquals('=', buffer.getText()[3][1]);
        Assert.assertEquals('=', buffer.getText()[4][1]);
        Assert.assertEquals('=', buffer.getText()[5][1]);
        Assert.assertEquals('=', buffer.getText()[6][1]);

        Assert.assertEquals(1, buffer.getTextColor()[2][1]);
        Assert.assertEquals(1, buffer.getTextColor()[3][1]);
        Assert.assertEquals(1, buffer.getTextColor()[4][1]);
        Assert.assertEquals(1, buffer.getTextColor()[5][1]);
        Assert.assertEquals(1, buffer.getTextColor()[6][1]);

        Assert.assertEquals(1, buffer.getHighlightColor()[2][1]);
        Assert.assertEquals(1, buffer.getHighlightColor()[3][1]);
        Assert.assertEquals(1, buffer.getHighlightColor()[4][1]);
        Assert.assertEquals(1, buffer.getHighlightColor()[5][1]);
        Assert.assertEquals(1, buffer.getHighlightColor()[6][1]);
    }

    @Test
    public void OverlappingLineTest() {
        FrameElement frame = new FrameElement(new DisplayBounds(1,1,5,6));
        frame.addHorizontalLine(0,0,3,'(',3,3);
        frame.addVerticalLine(0,0,5,'=',-1,-1);
        frame.addHorizontalLine(0,0,3,'-',2,2);

        DisplayBuffer buffer = new DisplayBuffer(10, 10);
        frame.pushToBuffer(buffer);

        Assert.assertEquals('-', buffer.getText()[1][1]);
        Assert.assertEquals(2, buffer.getTextColor()[1][1]);
        Assert.assertEquals(2, buffer.getHighlightColor()[1][1]);
    }

}
