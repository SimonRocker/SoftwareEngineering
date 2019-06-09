package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    private IModel model;
    private ObserverView view;

    public Controller(IModel model, IObserver view) {

        this.model = model;
        this.view = (ObserverView) view;

        this.initialise();
    }

    private void initialise() {
        readEnterPressed();
    }

    private void readEnterPressed() {
        view.askForDiceRoll();
        String input = new String();
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            input = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (input.length() == 0) {
            this.model.throwDice();
        }

    }

}
