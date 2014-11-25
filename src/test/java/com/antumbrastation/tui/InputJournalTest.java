package com.antumbrastation.tui;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputJournalTest {

    @Test
    public void initialStateTest() {
        InputJournal journal = new InputJournal(10,10);

        Assert.assertEquals(InputJournal.NO_INPUT, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());
    }

    @Test
    public void noInputTest() {
        InputJournal journal = new InputJournal(10,10);

        journal.updateInput(0);

        Assert.assertEquals(InputJournal.NO_INPUT, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());
    }

    @Test
    public void mouseMoveTest() {
        InputJournal journal = new InputJournal(10,10);

        journal.mouseMoved(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.NOBUTTON));
        journal.mouseMoved(new MouseEvent(new JButton(), 0, 0, 0, 23, 33, 0, false, MouseEvent.NOBUTTON));
        journal.mouseMoved(new MouseEvent(new JButton(), 0, 0, 0, 25, 37, 0, false, MouseEvent.NOBUTTON));
        journal.mouseDragged(new MouseEvent(new JButton(), 0, 0, 0, 21, 31, 0, false, MouseEvent.NOBUTTON));
        journal.mouseDragged(new MouseEvent(new JButton(), 0, 0, 0, 15, 47, 0, false, MouseEvent.NOBUTTON));

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.MOUSE_MOVED, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(2, journal.getMouseRow());
        Assert.assertEquals(1, journal.getMouseColumn());

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.MOUSE_MOVED, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(3, journal.getMouseRow());
        Assert.assertEquals(2, journal.getMouseColumn());

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.MOUSE_MOVED, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(4, journal.getMouseRow());
        Assert.assertEquals(1, journal.getMouseColumn());

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.NO_INPUT, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(4, journal.getMouseRow());
        Assert.assertEquals(1, journal.getMouseColumn());
    }

    @Test
    public void mouseTest() {
        InputJournal journal = new InputJournal(10, 10);

        Assert.assertEquals(false, journal.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(false, journal.isMouseButtonDown(MouseEvent.BUTTON2));

        journal.mousePressed(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.mousePressed(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON2));
        journal.mouseReleased(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.mouseReleased(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON2));

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.MOUSE_DOWN, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.BUTTON1, journal.getButtonNumber());
        Assert.assertEquals(true, journal.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(false, journal.isMouseButtonDown(MouseEvent.BUTTON2));
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.MOUSE_DOWN, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.BUTTON2, journal.getButtonNumber());
        Assert.assertEquals(true, journal.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(true, journal.isMouseButtonDown(MouseEvent.BUTTON2));
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.MOUSE_CLICK, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.BUTTON1, journal.getButtonNumber());
        Assert.assertEquals(false, journal.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(true, journal.isMouseButtonDown(MouseEvent.BUTTON2));
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.MOUSE_CLICK, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.BUTTON2, journal.getButtonNumber());
        Assert.assertEquals(false, journal.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(false, journal.isMouseButtonDown(MouseEvent.BUTTON2));
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());
    }

    @Test
    public void keyDownTest() {
        InputJournal journal = new InputJournal(10, 10);

        Assert.assertEquals(false, journal.isKeyDown("Ctrl"));
        Assert.assertEquals(false, journal.isKeyDown("5"));

        journal.keyPressed(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.keyPressed(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_5, (char) 0));
        journal.keyReleased(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.keyReleased(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_5, (char) 0));

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.KEY_DOWN, journal.getInputEventType());
        Assert.assertEquals("Ctrl", journal.getKeyName());
        Assert.assertEquals(true, journal.isKeyDown("Ctrl"));
        Assert.assertEquals(false, journal.isKeyDown("5"));
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.KEY_DOWN, journal.getInputEventType());
        Assert.assertEquals("5", journal.getKeyName());
        Assert.assertEquals(true, journal.isKeyDown("Ctrl"));
        Assert.assertEquals(true, journal.isKeyDown("5"));
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.KEY_RELEASE, journal.getInputEventType());
        Assert.assertEquals("Ctrl", journal.getKeyName());
        Assert.assertEquals(false, journal.isKeyDown("Ctrl"));
        Assert.assertEquals(true, journal.isKeyDown("5"));
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.KEY_RELEASE, journal.getInputEventType());
        Assert.assertEquals("5", journal.getKeyName());
        Assert.assertEquals(false, journal.isKeyDown("Ctrl"));
        Assert.assertEquals(false, journal.isKeyDown("5"));
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());
    }

    @Test
    public void keyHitTest() {
        InputJournal journal = new InputJournal(10, 10);

        journal.keyReleased(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_5, '5'));

        journal.updateInput(0);
        Assert.assertEquals(InputJournal.KEY_RELEASE, journal.getInputEventType());
        Assert.assertEquals("5", journal.getKeyName());
        Assert.assertEquals('5', journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());
    }

    @Test
    public void ignoredInputTypesTest() {
        InputJournal journal = new InputJournal(10, 10);

        journal.mouseEntered(null);
        journal.mouseExited(null);
        journal.mouseClicked(null);
        journal.keyTyped(null);

        journal.updateInput(0);

        Assert.assertEquals(InputJournal.NO_INPUT, journal.getInputEventType());
        Assert.assertEquals("", journal.getKeyName());
        Assert.assertEquals(0, journal.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, journal.getButtonNumber());
        Assert.assertEquals(-1, journal.getMouseRow());
        Assert.assertEquals(-1, journal.getMouseColumn());
    }

}
