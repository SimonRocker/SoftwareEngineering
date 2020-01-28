package com.company.gui;

import com.company.logic.IGameModel;

public interface IObserver  {

    public void update(int state);

    void startEventLoop(IGameModel model);
}
