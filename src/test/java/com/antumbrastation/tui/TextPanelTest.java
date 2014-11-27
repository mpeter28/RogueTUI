package com.antumbrastation.tui;

import org.junit.Assert;
import org.junit.Test;

public class TextPanelTest {

    @Test
    public void sizeTest() {
        TextPanel textPanel = new TextPanel(null, null, 12, 13, 20, 15, 0.1f);

        Assert.assertEquals(240, textPanel.getPreferredSize().getHeight(), 0);
        Assert.assertEquals(195, textPanel.getPreferredSize().getWidth(), 0);
    }

}
