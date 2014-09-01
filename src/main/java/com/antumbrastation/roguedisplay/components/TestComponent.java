package com.antumbrastation.roguedisplay.components;

import com.antumbrastation.roguedisplay.view.DisplayView;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 8/25/14
 * Time: 10:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestComponent implements DisplayComponent {
    @Override
    public void display(DisplayView view, boolean focus) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean giveFocus(DisplayComponent focus) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
