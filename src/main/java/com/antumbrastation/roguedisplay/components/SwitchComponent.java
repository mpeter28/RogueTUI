package com.antumbrastation.roguedisplay.components;

import com.antumbrastation.roguedisplay.view.DisplayView;

import java.util.ArrayList;

public class SwitchComponent implements DisplayComponent {

    private ArrayList<DisplayComponent> components;
    private DisplayComponent focusComponent;

    public SwitchComponent() {
        components = new ArrayList<DisplayComponent>();
    }

    public void display(DisplayView view, boolean focus) {
        focusComponent.display(view, focus);
    }

    public boolean giveFocus(DisplayComponent focus) {
        if (focus == this)
            return true;

        for (DisplayComponent component: components) {
            if (component.giveFocus(focus)) {
                focusComponent = focus;
                return true;
            }
        }

        return false;
    }

    public void addComponent(DisplayComponent component) {
        components.add(component);
    }
}
