package com.antumbrastation.tui;

import org.junit.Assert;
import org.junit.Test;

public class DisplayBoundsTest {

    @Test
    public void fullConstructorTest() {
        DisplayBounds bounds = new DisplayBounds(4, 7, 16, 9);
        Assert.assertEquals(bounds.getCornerRow(), 4);
        Assert.assertEquals(bounds.getCornerColumn(), 7);
        Assert.assertEquals(bounds.getHeight(), 16);
        Assert.assertEquals(bounds.getWidth(), 9);
    }

    @Test
    public void zeroedConstructorTest() {
        DisplayBounds bounds = new DisplayBounds(16, 9);
        Assert.assertEquals(bounds.getCornerRow(), 0);
        Assert.assertEquals(bounds.getCornerColumn(), 0);
        Assert.assertEquals(bounds.getHeight(), 16);
        Assert.assertEquals(bounds.getWidth(), 9);
    }

    @Test
    public void inBoundsTest() {
        DisplayBounds bounds = new DisplayBounds(4, 7, 16, 9);
        Assert.assertTrue(bounds.inBounds(0,0));
        Assert.assertTrue(bounds.inBounds(15,8));
        Assert.assertTrue(bounds.inBounds(0,8));
        Assert.assertTrue(bounds.inBounds(15,0));
        Assert.assertTrue(bounds.inBounds(3,7));

        Assert.assertFalse(bounds.inBounds(-1, 3));
        Assert.assertFalse(bounds.inBounds(3, -1));
        Assert.assertFalse(bounds.inBounds(16, 5));
        Assert.assertFalse(bounds.inBounds(5, 16));
        Assert.assertFalse(bounds.inBounds(-1, -1));
        Assert.assertFalse(bounds.inBounds(16, 9));
    }

    @Test
    public void inBoundsAbsoluteTest() {
        DisplayBounds bounds = new DisplayBounds(4, 7, 16, 9);
        Assert.assertTrue(bounds.inBoundsAbsolute(4,7));
        Assert.assertTrue(bounds.inBoundsAbsolute(10,12));
        Assert.assertTrue(bounds.inBoundsAbsolute(19,7));
        Assert.assertTrue(bounds.inBoundsAbsolute(4,15));
        Assert.assertTrue(bounds.inBoundsAbsolute(19,15));

        Assert.assertFalse(bounds.inBoundsAbsolute(0,0));
        Assert.assertFalse(bounds.inBoundsAbsolute(20,16));
        Assert.assertFalse(bounds.inBoundsAbsolute(6,16));
        Assert.assertFalse(bounds.inBoundsAbsolute(20,8));
        Assert.assertFalse(bounds.inBoundsAbsolute(3,11));
        Assert.assertFalse(bounds.inBoundsAbsolute(11,6));
    }

}
