package com.antumbrastation.tui;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class ColorPaletteTest {

    @Test
    public void defaultColorPaletteTest() {
        ColorPalette palette = new ColorPalette();
        Assert.assertEquals(palette.defaultHighlightColor(), Color.black);
        Assert.assertEquals(palette.defaultTextColor(), new Color(128, 128, 128));

        Assert.assertEquals(palette.indexToColor(0), Color.black);
        Assert.assertEquals(palette.indexToColor(1), new Color(128, 0, 0));
        Assert.assertEquals(palette.indexToColor(2), new Color(0, 128, 0));
        Assert.assertEquals(palette.indexToColor(3), new Color(128, 128, 0));
        Assert.assertEquals(palette.indexToColor(4), new Color(0, 0, 128));
        Assert.assertEquals(palette.indexToColor(5), new Color(128, 0, 128));
        Assert.assertEquals(palette.indexToColor(6), new Color(0, 128, 128));
        Assert.assertEquals(palette.indexToColor(7), new Color(128, 128, 128));
        Assert.assertEquals(palette.indexToColor(8), new Color(64, 64, 64));
        Assert.assertEquals(palette.indexToColor(9), Color.red);
        Assert.assertEquals(palette.indexToColor(10), Color.green);
        Assert.assertEquals(palette.indexToColor(11), Color.yellow);
        Assert.assertEquals(palette.indexToColor(12), Color.blue);
        Assert.assertEquals(palette.indexToColor(13), Color.magenta);
        Assert.assertEquals(palette.indexToColor(14), Color.cyan);
        Assert.assertEquals(palette.indexToColor(15), Color.white);

        Assert.assertEquals(palette.indexToColor(17), Color.black);
    }

    @Test
    public void customColorPaletteTest() {
        ColorPalette palette = new ColorPalette(new Color[] {Color.red,  Color.green, Color.blue},
                Color.black, Color.white);

        Assert.assertEquals(palette.defaultTextColor(), Color.black);
        Assert.assertEquals(palette.defaultHighlightColor(), Color.white);
        Assert.assertEquals(palette.indexToColor(0), Color.red);
        Assert.assertEquals(palette.indexToColor(1), Color.green);
        Assert.assertEquals(palette.indexToColor(2), Color.blue);
        Assert.assertEquals(palette.indexToColor(3), palette.defaultHighlightColor());
    }

    @Test
    public void indexToColorOutOfBoundsTest() {
        ColorPalette palette = new ColorPalette();
        Assert.assertEquals(palette.defaultHighlightColor(), palette.indexToColor(-1));
        Assert.assertEquals(palette.defaultHighlightColor(), palette.indexToColor(100));
    }
}
