package com.company.observer;

import com.company.view.Model;

public class ModelObserver extends Observer {
    protected Model gameModel;
    public ModelObserver(Model gameModel) {
        this.gameModel = gameModel;
        this.gameModel.attach(this);
    }

    @Override
    public void update() {
    }
}
