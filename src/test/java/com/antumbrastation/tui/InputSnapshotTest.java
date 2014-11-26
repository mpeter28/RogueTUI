package com.antumbrastation.tui;

import org.junit.Assert;
import org.junit.Test;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class InputSnapshotTest {

    @Test
    public void blankConstructorTest() {
        InputSnapshot snapshot = new InputSnapshot();

        Assert.assertEquals(InputSnapshot.NO_INPUT, snapshot.getInputEventType());
        Assert.assertEquals(0, snapshot.getTypedCharacter());
        Assert.assertEquals("", snapshot.getKeyName());
        Assert.assertEquals(0, snapshot.getKeysDown().size());
        Assert.assertEquals(MouseEvent.NOBUTTON, snapshot.getButtonNumber());
        Assert.assertEquals(0, snapshot.getMouseButtonsDown().size());
        Assert.assertEquals(-1, snapshot.getMouseRow());
        Assert.assertEquals(-1, snapshot.getMouseColumn());
    }

    @Test
    public void fullConstructorTest() {
        Set<String> keysDown = new HashSet<>();
        keysDown.add("A");
        keysDown.add("B");
        keysDown.add("C");

        Set<Integer> mouseButtonsDown = new HashSet<>();
        mouseButtonsDown.add(1);
        mouseButtonsDown.add(2);

        InputSnapshot snapshot = new InputSnapshot(InputSnapshot.KEY_DOWN, 'a', "A", keysDown,
                MouseEvent.BUTTON1, mouseButtonsDown, 5, 6);

        Assert.assertEquals(InputSnapshot.KEY_DOWN, snapshot.getInputEventType());
        Assert.assertEquals('a', snapshot.getTypedCharacter());
        Assert.assertEquals("A", snapshot.getKeyName());
        Assert.assertEquals(keysDown, snapshot.getKeysDown());
        Assert.assertEquals(MouseEvent.BUTTON1, snapshot.getButtonNumber());
        Assert.assertEquals(mouseButtonsDown, snapshot.getMouseButtonsDown());
        Assert.assertEquals(5, snapshot.getMouseRow());
        Assert.assertEquals(6, snapshot.getMouseColumn());
    }

    @Test
    public void staticConstantsTest() {
        Assert.assertEquals(0, InputSnapshot.NO_INPUT);
        Assert.assertEquals(1, InputSnapshot.MOUSE_MOVED);
        Assert.assertEquals(2, InputSnapshot.KEY_DOWN);
        Assert.assertEquals(3, InputSnapshot.KEY_RELEASE);
        Assert.assertEquals(4, InputSnapshot.MOUSE_DOWN);
        Assert.assertEquals(5, InputSnapshot.MOUSE_CLICK);
    }

    @Test
    public void gettersCopyTest() {
        Set<String> keysDown = new HashSet<>();
        keysDown.add("A");
        keysDown.add("B");
        keysDown.add("C");

        Set<Integer> mouseButtonsDown = new HashSet<>();
        mouseButtonsDown.add(1);
        mouseButtonsDown.add(2);

        InputSnapshot snapshot = new InputSnapshot(InputSnapshot.KEY_DOWN, 'a', "A", keysDown,
                MouseEvent.BUTTON1, mouseButtonsDown, 5, 6);

        Assert.assertFalse(keysDown == snapshot.getKeysDown());
        Assert.assertFalse(mouseButtonsDown == snapshot.getMouseButtonsDown());
    }

    @Test
    public void isDownTest() {
        Set<String> keysDown = new HashSet<>();
        keysDown.add("A");
        keysDown.add("B");
        keysDown.add("C");

        Set<Integer> mouseButtonsDown = new HashSet<>();
        mouseButtonsDown.add(1);
        mouseButtonsDown.add(2);

        InputSnapshot snapshot = new InputSnapshot(InputSnapshot.KEY_DOWN, 'a', "A", keysDown,
                MouseEvent.BUTTON1, mouseButtonsDown, 5, 6);

        Assert.assertTrue(snapshot.isKeyDown("A"));
        Assert.assertTrue(snapshot.isKeyDown("B"));
        Assert.assertTrue(snapshot.isKeyDown("C"));

        Assert.assertTrue(snapshot.isMouseButtonDown(1));
        Assert.assertTrue(snapshot.isMouseButtonDown(2));

        Assert.assertFalse(snapshot.isKeyDown("D"));

        Assert.assertFalse(snapshot.isMouseButtonDown(3));
    }
}
