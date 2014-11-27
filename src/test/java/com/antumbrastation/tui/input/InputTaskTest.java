package com.antumbrastation.tui.input;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputTaskTest {

    @Test
    public void callsSetUpFirstTest() {
        InputTask callsSetUpFirstTask = new InputTask() {
            private boolean setUpCalled = false;

            public boolean isTaskDone() {
                Assert.assertTrue(setUpCalled);
                return true;
            }

            public void setUp() {
                setUpCalled = true;
            }

            public void processKeyDown(InputSnapshot inputSnapshot) {
                Assert.assertTrue(setUpCalled);
            }

            public void processKeyRelease(InputSnapshot inputSnapshot) {
                Assert.assertTrue(setUpCalled);
            }

            public void processMouseMove(InputSnapshot inputSnapshot) {
                Assert.assertTrue(setUpCalled);
            }

            public void processMouseDown(InputSnapshot inputSnapshot) {
                Assert.assertTrue(setUpCalled);
            }

            public void processMouseClick(InputSnapshot inputSnapshot) {
                Assert.assertTrue(setUpCalled);
            }
        };

        InputJournal journal = new InputJournal(10,10);
        callsSetUpFirstTask.runTask(journal);
        callsSetUpFirstTask.isTaskDone();
    }

    @Test
    public void doneAfterSetUpTest() {
        InputTask doneAfterSetUpTask = new InputTask() {
            public boolean isTaskDone() {
                return true;
            }

            public void setUp() {
            }

            public void processKeyDown(InputSnapshot inputSnapshot) {
                Assert.fail();
            }

            public void processKeyRelease(InputSnapshot inputSnapshot) {
                Assert.fail();
            }

            public void processMouseMove(InputSnapshot inputSnapshot) {
                Assert.fail();
            }

            public void processMouseDown(InputSnapshot inputSnapshot) {
                Assert.fail();
            }

            public void processMouseClick(InputSnapshot inputSnapshot) {
                Assert.fail();
            }
        };

        InputJournal journal = new InputJournal(10,10);
        journal.mousePressed(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.mouseReleased(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.keyPressed(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.keyReleased(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.mouseMoved(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.NOBUTTON));

        doneAfterSetUpTask.runTask(journal);
    }

    @Test
    public void inputPassedInCorrectOrderTest() {
        InputTask orderCheckingTask = new InputTask() {
            private int processingNumber = 0;

            public boolean isTaskDone() {
                return processingNumber == 6;
            }

            public void setUp() {
                Assert.assertEquals(0, processingNumber);
                processingNumber = 1;
            }

            public void processKeyDown(InputSnapshot inputSnapshot) {
                Assert.assertEquals(3, processingNumber);
                processingNumber = 4;
            }

            public void processKeyRelease(InputSnapshot inputSnapshot) {
                Assert.assertEquals(4, processingNumber);
                processingNumber = 5;
            }

            public void processMouseMove(InputSnapshot inputSnapshot) {
                Assert.assertEquals(5, processingNumber);
                processingNumber = 6;
            }

            public void processMouseDown(InputSnapshot inputSnapshot) {
                Assert.assertEquals(1, processingNumber);
                processingNumber = 2;
            }

            public void processMouseClick(InputSnapshot inputSnapshot) {
                Assert.assertEquals(2, processingNumber);
                processingNumber = 3;
            }
        };

        InputJournal journal = new InputJournal(10,10);
        journal.mousePressed(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.mouseReleased(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.keyPressed(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.keyReleased(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.mouseMoved(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.NOBUTTON));

        orderCheckingTask.runTask(journal);

        Assert.assertTrue(orderCheckingTask.isTaskDone());
    }

    @Test
    public void checksDoneOnceAfterEachProcessingTest() {
        InputTask checksDoneTask = new InputTask() {
            private int isTaskDoneCalledAmount = 0;
            private int processingCalledAmount = 0;

            public boolean isTaskDone() {
                isTaskDoneCalledAmount++;
                Assert.assertEquals(isTaskDoneCalledAmount, processingCalledAmount);

                return isTaskDoneCalledAmount == 6;
            }

            public void setUp() {
                processingCalledAmount++;
                Assert.assertEquals(isTaskDoneCalledAmount + 1, processingCalledAmount);
            }

            public void processKeyDown(InputSnapshot inputSnapshot) {
                processingCalledAmount++;
                Assert.assertEquals(isTaskDoneCalledAmount + 1, processingCalledAmount);
            }

            public void processKeyRelease(InputSnapshot inputSnapshot) {
                processingCalledAmount++;
                Assert.assertEquals(isTaskDoneCalledAmount + 1, processingCalledAmount);
            }

            public void processMouseMove(InputSnapshot inputSnapshot) {
                processingCalledAmount++;
                Assert.assertEquals(isTaskDoneCalledAmount + 1, processingCalledAmount);
            }

            public void processMouseDown(InputSnapshot inputSnapshot) {
                processingCalledAmount++;
                Assert.assertEquals(isTaskDoneCalledAmount + 1, processingCalledAmount);
            }

            public void processMouseClick(InputSnapshot inputSnapshot) {
                processingCalledAmount++;
                Assert.assertEquals(isTaskDoneCalledAmount + 1, processingCalledAmount);
            }
        };

        InputJournal journal = new InputJournal(10,10);
        journal.mousePressed(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.mouseReleased(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.BUTTON1));
        journal.keyPressed(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.keyReleased(new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_CONTROL, (char) 0));
        journal.mouseMoved(new MouseEvent(new JButton(), 0, 0, 0, 12, 22, 0, false, MouseEvent.NOBUTTON));

        checksDoneTask.runTask(journal);

        checksDoneTask.setUp();
    }
}
