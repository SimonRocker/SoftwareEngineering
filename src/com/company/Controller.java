package com.company;

import com.company.model.IModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    private IModel model;
    private ObserverView view;

    public Controller(IModel model, IObserver view) {

        this.model = model;
        this.view = (ObserverView) view;
    }

    public void run() {

        this.view.askForDiceRoll();
        this.readInput();


    }

    private void readInput() {
        String input = new String();
        while (true) {

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            try {
                input = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (input) {

                case "A":
                case "B":
                case "C":
                    this.model.turnValid(input);
                    break;
                case "":
                    this.model.rollDice();
                    break;
                default:
                    this.view.wrongInput();
                    break;
            }

        }
    }
}



