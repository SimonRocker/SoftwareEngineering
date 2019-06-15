package com.company;

public class GameMain {
    public static void main(String[] args) {
        IModel model = new Fassade();
        IObserver view = new ObserverView(model);
        Controller controller = new Controller(model, view);
        controller.run();
    }
}
