package com.antumbrastation.roguedisplay.controller;

import com.antumbrastation.roguedisplay.view.RogueFrame;

import java.util.concurrent.Semaphore;

public class Controller {
    Semaphore taskLock;

    InputTask task;
    RogueFrame frame;

    public Controller(RogueFrame frame, Semaphore taskLock) {
        this.taskLock = taskLock;
        this.frame = frame;
    }

    public void runTask(InputTask task) {
        taskLock.acquireUninterruptibly();
        this.task = task;
        if (task.initialize()) {
            frame.reDraw();
        }
        taskLock.release();

        boolean done = false;
        while (!done) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                //Not an issue, keep going
            }

            taskLock.acquireUninterruptibly();
            if (task.isComplete()) {
                done = true;
            }
            taskLock.release();
        }
    }

    public void processKeyHit(char key) {
        taskLock.acquireUninterruptibly();
        if (task.processKeyHit(key)) {
            frame.reDraw();
        }
        taskLock.release();
    }

    public void processMouseClick(int row, int column, int button) {
        taskLock.acquireUninterruptibly();
        if (task.processMouseClick(row, column, button)) {
            frame.reDraw();
        }
        taskLock.release();
    }

    public void processMouseMove(int row, int column) {
        taskLock.acquireUninterruptibly();
        if (task.processMouseMove(row, column)) {
            frame.reDraw();
        }
        taskLock.release();
    }
}
