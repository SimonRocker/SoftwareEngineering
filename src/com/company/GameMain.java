package com.company;

import com.company.gui.Controller;
import com.company.gui.IObserver;
import com.company.gui.ObserverView;
import com.company.logic.IGameModel;

public class GameMain {
    public static void main(String[] args) {
        IGameModel model = new Fassade();
        IObserver view = new ObserverView(model);
        Controller controller = new Controller(model, view);
        controller.run();
    }
}
