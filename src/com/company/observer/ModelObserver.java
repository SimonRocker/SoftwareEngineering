package com.company.observer;

import com.company.Model;

public class ModelObserver extends Observer {
    protected Model gameModel;
    public ModelObserver(Model gameModel) {
        this.gameModel = gameModel;
        this.gameModel.attach(this);
    }

    @Override
    public void update() {
        System.out.println("update called");
    }
}
