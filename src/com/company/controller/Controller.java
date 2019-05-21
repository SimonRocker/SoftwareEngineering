package com.company.controller;

import com.company.Model;
import com.company.observer.ModelObserver;
import com.company.View;

public class Controller {

    private Model model;

    public Controller() {
        this.model = new Model();
        new ModelObserver(model);
        new View(model);
    }


    public void startGame() {
        do {
            wuerfeln();
        }while(true);
    }

    public void wuerfeln(){
        try {
            model.wuerfeln();
        } catch(Exception e) {
            //Entfällt bei Observable
        }
    }

    public void initialize(){
        model.initialize();
    }
}
