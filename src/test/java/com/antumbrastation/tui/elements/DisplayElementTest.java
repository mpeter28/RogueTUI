package com.antumbrastation.tui.elements;

import com.antumbrastation.tui.DisplayBounds;
import com.antumbrastation.tui.DisplayBuffer;
import org.junit.Assert;
import org.junit.Test;

public class DisplayElementTest {

    @Test
    public void boundsTest() {
        DisplayElement element = new DisplayElement() {
            public void pushToBuffer(DisplayBuffer view) {
                //Do nothing
            }
        };

        Assert.assertNull(element.getDisplayBounds());

        element.setDisplayBounds(new DisplayBounds(3, 2, 5, 11));
        Assert.assertNotNull(element.getDisplayBounds());
        Assert.assertEquals(3, element.getDisplayBounds().getCornerRow());
        Assert.assertEquals(2, element.getDisplayBounds().getCornerColumn());
        Assert.assertEquals(5, element.getDisplayBounds().getHeight());
        Assert.assertEquals(11, element.getDisplayBounds().getWidth());
    }

}
