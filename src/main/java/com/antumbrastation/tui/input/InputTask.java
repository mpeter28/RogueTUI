package com.antumbrastation.tui.input;

public abstract class InputTask {

    public abstract boolean isTaskDone();
    public abstract void setUp();
    public abstract void processKeyDown(InputSnapshot inputSnapshot);
    public abstract void processKeyRelease(InputSnapshot inputSnapshot);
    public abstract void processMouseMove(InputSnapshot inputSnapshot);
    public abstract void processMouseDown(InputSnapshot inputSnapshot);
    public abstract void processMouseClick(InputSnapshot inputSnapshot);

    public void runTask(InputJournal inputJournal) {
        setUp();

        while (!isTaskDone()) {
            InputSnapshot inputSnapshot;
            do {
                inputSnapshot = inputJournal.updateInput(1000);
            } while (inputSnapshot.getInputEventType() == InputSnapshot.NO_INPUT);

            if (inputSnapshot.getInputEventType() == InputSnapshot.KEY_DOWN) {
                processKeyDown(inputSnapshot);
            } else if (inputSnapshot.getInputEventType() == InputSnapshot.KEY_RELEASE) {
                processKeyRelease(inputSnapshot);
            } else if (inputSnapshot.getInputEventType() == InputSnapshot.MOUSE_DOWN) {
                processMouseDown(inputSnapshot);
            } else if (inputSnapshot.getInputEventType() == InputSnapshot.MOUSE_CLICK) {
                processMouseClick(inputSnapshot);
            } else if (inputSnapshot.getInputEventType() == InputSnapshot.MOUSE_MOVED) {
                processMouseMove(inputSnapshot);
            }
        }
    }

}
