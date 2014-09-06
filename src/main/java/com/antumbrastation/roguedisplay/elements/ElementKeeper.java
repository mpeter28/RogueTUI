package com.antumbrastation.roguedisplay.elements;

import com.antumbrastation.roguedisplay.view.DisplayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElementKeeper {

    private ArrayList<DisplayElement> visible;
    private Map<String, DisplayElement> names;

    public ElementKeeper() {
        visible = new ArrayList<DisplayElement>();
        names = new HashMap<String, DisplayElement>();
    }

    public void displayComponents(DisplayView view) {
        for (DisplayElement c : visible) {
            c.display(view);
        }
    }

    public void makeFirstInZOrder(String name) {
        DisplayElement component = names.get(name);
        visible.remove(component);
        visible.add(component);
    }

    public void makeLastInZOrder(String name) {
        DisplayElement component = names.get(name);
        visible.remove(component);
        visible.add(0, component);
    }

    public void add(DisplayElement component, String name) {
        names.put(name, component);
    }

    public void remove(String name) {
        DisplayElement component = names.remove(name);
        visible.remove(component);
    }

    public DisplayElement fetchByName(String name) {
        return names.get(name);
    }

    public void show(String name) {
        DisplayElement component = names.get(name);
        visible.add(component);
    }

    public void hide(String name) {
        DisplayElement component = names.get(name);
        visible.remove(component);
    }
}
