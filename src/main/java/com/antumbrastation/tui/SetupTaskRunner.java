package com.antumbrastation.tui;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class SetupTaskRunner {

    public boolean runSetupTask(final SetupTask task) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    task.initialize();
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            return false;
        }

        return true;
    }
}
