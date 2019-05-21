package com.company.observer;

import com.company.Model;

/**
 * @author Fabii
 */
public abstract class Observer {
    protected Model gameModel;

    public abstract void update();
}
