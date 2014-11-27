package com.antumbrastation.tui.input;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputJournalTest {

    @Test
    public void initialStateTest() {
        InputJournal journal = new InputJournal(10,10);

        InputSnapshot snapshot = journal.updateInput(0);

        Assert.assertEquals(InputSnapshot.NO_INPUT, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());
    }

    @Test
    public void noInputTest() {
        InputJournal journal = new InputJournal(10,10);

        InputSnapshot snapshot = journal.updateInput(0);

        Assert.assertEquals(InputSnapshot.NO_INPUT, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());
    }

    @Test
    public void mouseMoveTest() {
        InputJournal journal = new InputJournal(10,10);

        journal.mouseMoved(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.NOBUTTON));
        journal.mouseMoved(new MouseEvent(new JButton(), 0, 0, 0, 23, 33, 0, false, MouseEvent.NOBUTTON));
        journal.mouseMoved(new MouseEvent(new JButton(), 0, 0, 0, 25, 37, 0, false, MouseEvent.NOBUTTON));
        journal.mouseDragged(new MouseEvent(new JButton(), 0, 0, 0, 21, 31, 0, false, MouseEvent.NOBUTTON));
        journal.mouseDragged(new MouseEvent(new JButton(), 0, 0, 0, 15, 47, 0, false, MouseEvent.NOBUTTON));

        InputSnapshot snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.MOUSE_MOVED, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(2, snapshot.getMouseRow());
        Assert.assertEquals(1, snapshot.getMouseColumn());

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.MOUSE_MOVED, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(3, snapshot.getMouseRow());
        Assert.assertEquals(2, snapshot.getMouseColumn());

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.MOUSE_MOVED, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(4, snapshot.getMouseRow());
        Assert.assertEquals(1, snapshot.getMouseColumn());

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.NO_INPUT, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(4, snapshot.getMouseRow());
        Assert.assertEquals(1, snapshot.getMouseColumn());
    }

    @Test
    public void mouseButtonsTest() {
        InputJournal journal = new InputJournal(10, 10);

        InputSnapshot snapshot = journal.updateInput(0);

        Assert.assertEquals(false, snapshot.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(false, snapshot.isMouseButtonDown(MouseEvent.BUTTON2));

        journal.mousePressed(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.mousePressed(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON2));
        journal.mouseReleased(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.mouseReleased(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON2));

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.MOUSE_DOWN, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.BUTTON1, snapshot.getButtonNumber());
        Assert.assertEquals(true, snapshot.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(false, snapshot.isMouseButtonDown(MouseEvent.BUTTON2));
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.MOUSE_DOWN, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.BUTTON2, snapshot.getButtonNumber());
        Assert.assertEquals(true, snapshot.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(true, snapshot.isMouseButtonDown(MouseEvent.BUTTON2));
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.MOUSE_CLICK, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.BUTTON1, snapshot.getButtonNumber());
        Assert.assertEquals(false, snapshot.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(true, snapshot.isMouseButtonDown(MouseEvent.BUTTON2));
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.MOUSE_CLICK, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.BUTTON2, snapshot.getButtonNumber());
        Assert.assertEquals(false, snapshot.isMouseButtonDown(MouseEvent.BUTTON1));
        Assert.assertEquals(false, snapshot.isMouseButtonDown(MouseEvent.BUTTON2));
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());
    }

    @Test
    public void keyDownTest() {
        InputJournal journal = new InputJournal(10, 10);

        InputSnapshot snapshot = journal.updateInput(0);

        Assert.assertEquals(false, snapshot.isKeyDown("Ctrl"));
        Assert.assertEquals(false, snapshot.isKeyDown("5"));

        journal.keyPressed(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.keyPressed(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_5, (char) 0));
        journal.keyReleased(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.keyReleased(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_5, (char) 0));

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.KEY_DOWN, snapshot.getInputEventType());
        Assert.assertEquals("Ctrl", snapshot.getKeyName());
        Assert.assertEquals(true, snapshot.isKeyDown("Ctrl"));
        Assert.assertEquals(false, snapshot.isKeyDown("5"));
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.KEY_DOWN, snapshot.getInputEventType());
        Assert.assertEquals("5", snapshot.getKeyName());
        Assert.assertEquals(true, snapshot.isKeyDown("Ctrl"));
        Assert.assertEquals(true, snapshot.isKeyDown("5"));
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.KEY_RELEASE, snapshot.getInputEventType());
        Assert.assertEquals("Ctrl", snapshot.getKeyName());
        Assert.assertEquals(false, snapshot.isKeyDown("Ctrl"));
        Assert.assertEquals(true, snapshot.isKeyDown("5"));
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());

        snapshot = journal.updateInput(0);
        Assert.assertEquals(InputSnapshot.KEY_RELEASE, snapshot.getInputEventType());
        Assert.assertEquals("5", snapshot.getKeyName());
        Assert.assertEquals(false, snapshot.isKeyDown("Ctrl"));
        Assert.assertEquals(false, snapshot.isKeyDown("5"));
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());
    }

    @Test
    public void keyHitTest() {
        InputJournal journal = new InputJournal(10, 10);

        journal.keyReleased(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_5, '5'));

        InputSnapshot snapshot = journal.updateInput(0);

        Assert.assertEquals(InputSnapshot.KEY_RELEASE, snapshot.getInputEventType());
        Assert.assertEquals("5", snapshot.getKeyName());
        Assert.assertEquals('5', snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());
    }

    @Test
    public void ignoredInputTypesTest() {
        InputJournal journal = new InputJournal(10, 10);

        journal.mouseEntered(null);
        journal.mouseExited(null);
        journal.mouseClicked(null);
        journal.keyTyped(null);

        InputSnapshot snapshot = journal.updateInput(0);

        Assert.assertEquals(InputSnapshot.NO_INPUT, snapshot.getInputEventType());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());
    }

}
