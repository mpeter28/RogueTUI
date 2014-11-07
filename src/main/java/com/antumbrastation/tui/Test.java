package com.antumbrastation.tui;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Test {

    public static void main(String args[]) {
        final JFrame frame = new JFrame("Test");
        final TextPanel panel = new TextPanel(new ColorPalette(), new Font(Font.MONOSPACED, Font.BOLD, 18), 25, 80, 20, 13);
        frame.setLayout(new GridLayout(1, 1));
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBackground(Color.black);
        frame.setResizable(false);

        final char[][] text = new char[25][80];
        final int[][] color = new int[25][80];
        final int[][] highlight = new int[25][80];

        for (int x = 0; x < 25; x++)
            for (int y = 0; y < 80; y++) {
                text[x][y] = (char) (Math.random() * 90 + 33);
                color[x][y] = (int) (Math.random() * 15 + 1);
                highlight[x][y] = 0;//(color[x][y] + (int) (Math.random() * 15) + 1) % 16;
            }

        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    panel.update(text, color, highlight);
                    frame.setVisible(true);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
