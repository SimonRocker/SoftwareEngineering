package com.company;

public class Controller {

    private Model model = new Model();
    private int activePlayerId = 0;
    public void startGame() {
        do {
            activePlayerId = activePlayerId % 4;
            wuerfeln();
            activePlayerId++;
        }while(true);
    }

    public void wuerfeln(){
        try {
            model.wuerfeln(activePlayerId);
        } catch(Exception e) {
            //Entfällt bei Observable
        }
    }

    public void initialize(){
        model.initialize();
    }
}
