package com.antumbrastation.tui;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphJustificationInfo;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class TextPanelTest {

    @Test
    public void sizeTest() {
        TextPanel textPanel = new TextPanel(null, null, 12, 13, 20, 15, 0.1f);

        Assert.assertEquals(240, textPanel.getPreferredSize().getHeight(), 0);
        Assert.assertEquals(195, textPanel.getPreferredSize().getWidth(), 0);
    }

    @Test
    public void nullUpdateClearsTest() {
        ColorPalette colorPalette = new ColorPalette();
        TextPanel textPanel = new TextPanel(colorPalette, null, 1, 1, 20, 15, 0);

        char[][] text = new char[1][1];
        int[][] color = new int[1][1];

        MockUpGraphics2D clearPanelGraphics = new MockUpGraphics2D() {
            private Color backgroundColor;
            private boolean clearRectCalled;

            public void setBackground(Color color) {
                backgroundColor = color;
            }

            public Color getBackground() {
                return backgroundColor;
            }

            public void clearRect(int i, int i2, int i3, int i4) {
                Assert.assertEquals(new ColorPalette().defaultHighlightColor(), backgroundColor);

                Assert.assertEquals(0, i);
                Assert.assertEquals(0, i2);
                Assert.assertEquals(15, i3);
                Assert.assertEquals(20, i4);

                clearRectCalled = true;
            }

            public void dispose() {
                Assert.assertTrue(clearRectCalled);
                backgroundColor = null;
                clearRectCalled = false;
            }
        };

        textPanel.update(null, null, null);
        textPanel.paint(clearPanelGraphics);
        clearPanelGraphics.dispose();

        textPanel.update(text, null, null);
        textPanel.paint(clearPanelGraphics);
        clearPanelGraphics.dispose();

        textPanel.update(null, color, null);
        textPanel.paint(clearPanelGraphics);
        clearPanelGraphics.dispose();

        textPanel.update(null, null, color);
        textPanel.paint(clearPanelGraphics);
        clearPanelGraphics.dispose();

        textPanel.update(text, color, null);
        textPanel.paint(clearPanelGraphics);
        clearPanelGraphics.dispose();

        textPanel.update(text, null, color);
        textPanel.paint(clearPanelGraphics);
        clearPanelGraphics.dispose();

        textPanel.update(null, color, color);
        textPanel.paint(clearPanelGraphics);
        clearPanelGraphics.dispose();
    }

    @Test
    public void renderGlyphTest() {
        TextPanel textPanel = new TextPanel(null, null, 12, 13, 20, 15, 0.1f);

        MockUpGlyphVector dummyGlyph = new MockUpGlyphVector() {
            public Rectangle2D getVisualBounds() {
                Rectangle2D.Float bounds = new Rectangle2D.Float();
                bounds.setRect(2.1f, -1.2f, 10f, 10f);
                return bounds;
            }
        };

        MockUpGraphics2D glyphDrawGraphics = new MockUpGraphics2D() {
            private boolean drawGlyphVectorCalled;

            public void drawGlyphVector(GlyphVector glyphVector, float x, float y) {
                Assert.assertEquals(120.4f, x, 0f);
                Assert.assertEquals(179.9f, y, 0f);

                drawGlyphVectorCalled = true;
            }

            public void dispose() {
                Assert.assertTrue(drawGlyphVectorCalled);
            }
        };

        textPanel.renderGlyphVector(glyphDrawGraphics, dummyGlyph, 8, 8);
        glyphDrawGraphics.dispose();
    }

    @Test
    public void setRenderingHintsTest() {
        TextPanel textPanel = new TextPanel(null, null, 12, 13, 20, 15, 0.1f);

        MockUpGraphics2D setRenderingHintsGraphics = new MockUpGraphics2D() {
            private Object renderingHintValue;

            public void setRenderingHint(RenderingHints.Key key, Object o) {
                Assert.assertEquals(RenderingHints.KEY_TEXT_ANTIALIASING, key);
                renderingHintValue = o;
            }

            public Object getRenderingHint(RenderingHints.Key key) {
                return renderingHintValue;
            }
        };

        textPanel.setRenderingHints(setRenderingHintsGraphics);
        Assert.assertEquals(RenderingHints.VALUE_TEXT_ANTIALIAS_GASP,
                setRenderingHintsGraphics.getRenderingHint(null));
    }

    @Test
    public void setTextColorTest() {
        ColorPalette colorPalette = new ColorPalette();
        TextPanel textPanel = new TextPanel(colorPalette, null, 12, 13, 20, 15, 0.1f);

        MockUpGraphics2D setColorGraphics = new MockUpGraphics2D() {
            private Color backgroundColor;

            public void setColor(Color color) {
                backgroundColor = color;
            }

            public Color getColor() {
                return backgroundColor;
            }
        };

        textPanel.setTextColor(setColorGraphics, -1);
        Assert.assertEquals(colorPalette.defaultTextColor(), setColorGraphics.getColor());

        textPanel.setTextColor(setColorGraphics, 1);
        Assert.assertEquals(colorPalette.indexToColor(1), setColorGraphics.getColor());
    }

    @Test
    public void setHighlightColorTest() {
        ColorPalette colorPalette = new ColorPalette();
        TextPanel textPanel = new TextPanel(colorPalette, null, 12, 13, 20, 15, 0.1f);

        MockUpGraphics2D setBackgroundGraphics = new MockUpGraphics2D() {
            private Color backgroundColor;

            public void setBackground(Color color) {
                backgroundColor = color;
            }

            public Color getBackground() {
                return backgroundColor;
            }
        };

        textPanel.setHighlightColor(setBackgroundGraphics, -1);
        Assert.assertEquals(colorPalette.defaultHighlightColor(), setBackgroundGraphics.getBackground());

        textPanel.setHighlightColor(setBackgroundGraphics, 1);
        Assert.assertEquals(colorPalette.indexToColor(1), setBackgroundGraphics.getBackground());
    }

    @Test
    public void getGlyphVectorTest() {
        final MockUpGlyphVector dummyGlyph = new MockUpGlyphVector() {
            public Rectangle2D getVisualBounds() {
                Rectangle2D.Float bounds = new Rectangle2D.Float();
                bounds.setRect(2.1f, -1.2f, 10f, 10f);
                return bounds;
            }
        };

        Font dummyFont = new Font(Font.MONOSPACED, Font.PLAIN, 18) {
            public GlyphVector createGlyphVector(FontRenderContext frc, char[] chars) {
                Assert.assertEquals(1, chars.length);
                Assert.assertEquals('a', chars[0]);

                return dummyGlyph;
            }
        };

        TextPanel textPanel = new TextPanel(null, dummyFont, 12, 13, 20, 15, 0.1f);
        GlyphVector resultGlyph = textPanel.getGlyphVector(new MockUpGraphics2D(), 'a');
        Assert.assertSame(dummyGlyph, resultGlyph);
    }

    @Test
    public void paintSetRenderingTipsFirstTest() {
        TextPanel textPanel = new TextPanel(null, null, 1, 1, 20, 15, 0.1f) {
            private boolean renderingHintsSet;

            public void setRenderingHints(Graphics2D graphics2D) {
                Assert.assertTrue(graphics2D instanceof MockUpGraphics2D);
                renderingHintsSet = true;
            }

            public void setTextColor(Graphics2D graphics2D, int colorIndex) {
                Assert.assertTrue(renderingHintsSet);
            }

            public void setHighlightColor(Graphics2D graphics2D, int colorIndex) {
                Assert.assertTrue(renderingHintsSet);
            }

            public GlyphVector getGlyphVector(Graphics2D graphics2D, char character) {
                Assert.assertTrue(renderingHintsSet);
                return null;
            }

            public void renderGlyphVector(Graphics2D graphics2D, GlyphVector glyphVector, int row, int column) {
                Assert.assertTrue(renderingHintsSet);
            }
        };

        char[][] text = new char[1][1];
        int[][] color = new int[1][1];

        textPanel.update(text, color, color);
        textPanel.paint(new MockUpGraphics2D());
        textPanel.setTextColor(null, 0);
    }

    @Test
    public void paintStructureTest() {
        final boolean[][] writtenTo = new boolean[2][2];
        final int[][] textColorAsWrit = new int[2][2];
        final Color[][] highlightAsWrit = new Color[2][2];

        final MockUpGlyphVector dummyGlyph = new MockUpGlyphVector() {
            public Rectangle2D getVisualBounds() {
                Rectangle2D.Float bounds = new Rectangle2D.Float();
                bounds.setRect(2.1f, -1.2f, 10f, 10f);
                return bounds;
            }
        };

        final ColorPalette colorPalette = new ColorPalette();
        TextPanel textPanel = new TextPanel(colorPalette, null, 2, 2, 20, 15, 0.1f) {
            private int textColor;

            public void setRenderingHints(Graphics2D graphics2D) {

            }

            public void setTextColor(Graphics2D graphics2D, int colorIndex) {
                textColor = colorIndex;
            }

            public void setHighlightColor(Graphics2D graphics2D, int colorIndex) {
                graphics2D.setBackground(colorPalette.indexToColor(colorIndex));
            }

            public GlyphVector getGlyphVector(Graphics2D graphics2D, char character) {
                Assert.assertEquals('a', character);
                return dummyGlyph;
            }

            public void renderGlyphVector(Graphics2D graphics2D, GlyphVector glyphVector, int row, int column) {
                Assert.assertSame(dummyGlyph, glyphVector);
                writtenTo[row][column] = true;
                textColorAsWrit[row][column] = textColor;
            }
        };

        char[][] text = new char[][] {{'a', 0}, {0, 'a'}};
        int[][] color = new int[][] {{1, 2}, {3, 4}};

        textPanel.update(text, color, color);

        MockUpGraphics2D highlightTracker = new MockUpGraphics2D() {
            private Color highlight;

            public void setBackground(Color color) {
                highlight = color;
            }

            public void clearRect(int x, int y, int width, int height) {
                Assert.assertEquals(0, x % 15);
                Assert.assertEquals(0, y % 20);
                Assert.assertEquals(15, width);
                Assert.assertEquals(20, height);

                x = x / 15;
                y = y / 20;
                highlightAsWrit[y][x] = highlight;
            }
        };
        textPanel.paint(highlightTracker);

        Assert.assertTrue(writtenTo[0][0]);
        Assert.assertTrue(writtenTo[1][1]);
        Assert.assertFalse(writtenTo[1][0]);
        Assert.assertFalse(writtenTo[0][1]);

        Assert.assertEquals(1, textColorAsWrit[0][0]);
        Assert.assertEquals(4, textColorAsWrit[1][1]);
        Assert.assertEquals(0, textColorAsWrit[1][0]);
        Assert.assertEquals(0, textColorAsWrit[0][1]);

        Assert.assertEquals(colorPalette.indexToColor(1), highlightAsWrit[0][0]);
        Assert.assertEquals(colorPalette.indexToColor(3), highlightAsWrit[1][0]);
        Assert.assertEquals(colorPalette.indexToColor(2), highlightAsWrit[0][1]);
        Assert.assertEquals(colorPalette.indexToColor(4), highlightAsWrit[1][1]);
    }

    private static class MockUpGlyphVector extends GlyphVector {
        public Font getFont() {
            return null;
        }

        public FontRenderContext getFontRenderContext() {
            return null;
        }

        public void performDefaultLayout() {

        }

        public int getNumGlyphs() {
            return 0;
        }

        public int getGlyphCode(int i) {
            return 0;
        }

        public int[] getGlyphCodes(int i, int i2, int[] ints) {
            return new int[0];
        }

        public Rectangle2D getLogicalBounds() {
            return null;
        }

        public Rectangle2D getVisualBounds() {
            return null;
        }

        public Shape getOutline() {
            return null;
        }

        public Shape getOutline(float v, float v2) {
            return null;
        }

        public Shape getGlyphOutline(int i) {
            return null;
        }

        public Point2D getGlyphPosition(int i) {
            return null;
        }

        public void setGlyphPosition(int i, Point2D point2D) {

        }

        public AffineTransform getGlyphTransform(int i) {
            return null;
        }

        public void setGlyphTransform(int i, AffineTransform affineTransform) {

        }

        public float[] getGlyphPositions(int i, int i2, float[] floats) {
            return new float[0];
        }

        public Shape getGlyphLogicalBounds(int i) {
            return null;
        }

        public Shape getGlyphVisualBounds(int i) {
            return null;
        }

        public GlyphMetrics getGlyphMetrics(int i) {
            return null;
        }

        public GlyphJustificationInfo getGlyphJustificationInfo(int i) {
            return null;
        }

        public boolean equals(GlyphVector glyphVector) {
            return false;
        }
    }
    
    private static class MockUpGraphics2D extends Graphics2D {
        
        public void draw(Shape shape) {
            
        }
        
        public boolean drawImage(Image image, AffineTransform affineTransform, ImageObserver imageObserver) {
            return false;
        }
        
        public void drawImage(BufferedImage bufferedImage, BufferedImageOp bufferedImageOp, int i, int i2) {

        }
        
        public void drawRenderedImage(RenderedImage renderedImage, AffineTransform affineTransform) {

        }
        
        public void drawRenderableImage(RenderableImage renderableImage, AffineTransform affineTransform) {

        }

        public void drawString(String s, int i, int i2) {

        }
        
        public void drawString(String s, float v, float v2) {

        }
        
        public void drawString(AttributedCharacterIterator attributedCharacterIterator, int i, int i2) {

        }
        
        public void drawString(AttributedCharacterIterator attributedCharacterIterator, float v, float v2) {

        }
        
        public void drawGlyphVector(GlyphVector glyphVector, float v, float v2) {

        }
        
        public void fill(Shape shape) {

        }
        
        public boolean hit(Rectangle rectangle, Shape shape, boolean b) {
            return false;
        }
        
        public GraphicsConfiguration getDeviceConfiguration() {
            return null;
        }
        
        public void setComposite(Composite composite) {

        }
        
        public void setPaint(Paint paint) {

        }
        
        public void setStroke(Stroke stroke) {

        }
        
        public void setRenderingHint(RenderingHints.Key key, Object o) {

        }
        
        public Object getRenderingHint(RenderingHints.Key key) {
            return null;
        }
        
        public void setRenderingHints(Map<?, ?> map) {

        }
        
        public void addRenderingHints(Map<?, ?> map) {

        }
        
        public RenderingHints getRenderingHints() {
            return null;
        }
        
        public void translate(int i, int i2) {

        }
        
        public void translate(double v, double v2) {

        }
        
        public void rotate(double v) {

        }
        
        public void rotate(double v, double v2, double v3) {

        }
        
        public void scale(double v, double v2) {

        }
        
        public void shear(double v, double v2) {

        }
        
        public void transform(AffineTransform affineTransform) {

        }
        
        public void setTransform(AffineTransform affineTransform) {

        }
        
        public AffineTransform getTransform() {
            return null;
        }
        
        public Paint getPaint() {
            return null;
        }
        
        public Composite getComposite() {
            return null;
        }
        
        public void setBackground(Color color) {

        }

        public Color getBackground() {
            return null;
        }
        
        public Stroke getStroke() {
            return null;
        }
        
        public void clip(Shape shape) {

        }
        
        public FontRenderContext getFontRenderContext() {
            return null;
        }
        
        public Graphics create() {
            return null;
        }
        
        public Color getColor() {
            return null;
        }
        
        public void setColor(Color color) {

        }
        
        public void setPaintMode() {

        }
        
        public void setXORMode(Color color) {

        }
        
        public Font getFont() {
            return null;
        }
        
        public void setFont(Font font) {

        }
        
        public FontMetrics getFontMetrics(Font font) {
            return null;
        }
        
        public Rectangle getClipBounds() {
            return null;
        }
        
        public void clipRect(int i, int i2, int i3, int i4) {

        }
        
        public void setClip(int i, int i2, int i3, int i4) {

        }
        
        public Shape getClip() {
            return null;
        }
        
        public void setClip(Shape shape) {

        }
        
        public void copyArea(int i, int i2, int i3, int i4, int i5, int i6) {

        }
        
        public void drawLine(int i, int i2, int i3, int i4) {

        }
        
        public void fillRect(int i, int i2, int i3, int i4) {

        }
        
        public void clearRect(int i, int i2, int i3, int i4) {

        }
        
        public void drawRoundRect(int i, int i2, int i3, int i4, int i5, int i6) {

        }
        
        public void fillRoundRect(int i, int i2, int i3, int i4, int i5, int i6) {

        }
        
        public void drawOval(int i, int i2, int i3, int i4) {

        }
        
        public void fillOval(int i, int i2, int i3, int i4) {

        }
        
        public void drawArc(int i, int i2, int i3, int i4, int i5, int i6) {

        }
        
        public void fillArc(int i, int i2, int i3, int i4, int i5, int i6) {

        }
        
        public void drawPolyline(int[] ints, int[] ints2, int i) {

        }
        
        public void drawPolygon(int[] ints, int[] ints2, int i) {

        }
        
        public void fillPolygon(int[] ints, int[] ints2, int i) {

        }
        
        public boolean drawImage(Image image, int i, int i2, ImageObserver imageObserver) {
            return false;
        }
        
        public boolean drawImage(Image image, int i, int i2, int i3, int i4, ImageObserver imageObserver) {
            return false;
        }
        
        public boolean drawImage(Image image, int i, int i2, Color color, ImageObserver imageObserver) {
            return false;
        }
        
        public boolean drawImage(Image image, int i, int i2, int i3, int i4, Color color, ImageObserver imageObserver) {
            return false;
        }
        
        public boolean drawImage(Image image, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, ImageObserver imageObserver) {
            return false;
        }
        
        public boolean drawImage(Image image, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Color color, ImageObserver imageObserver) {
            return false;
        }
        
        public void dispose() {

        }
    }

}
