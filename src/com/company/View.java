package com.company;

import com.company.model.Figur;
import com.company.observer.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.company.Model.State.wrongFigure;

public class View extends Observer {
    public View(Model gameModel) {
        this.gameModel = gameModel;
        this.gameModel.attach(this);
    }

    public View() {
    }


    public void counterAusgeben(int counter) {
        System.out.println(counter + "er Versuch! Sie haben 3 Versuche!");
    }

    @Override
    public void update() {

        switch (this.gameModel.state) {
            case wuerfeln:
                wuerfeln();
                break;
            case waitForDiceThrow:
                BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
                System.out.println(this.gameModel.getActivePlayer() + " ist dran. \n" +
                        "Bitte drücken sie Enter zum würfeln!");
                //Readline in Controller
                try {
                    c.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case zugNichtMoeglich:
                System.out.println("Zug nicht möglich, bitte erneut auswählen!");
                break;
            case ziehen:
                ziehen();
                break;
            case wrongFigure:
                System.out.println("Bitte wählen Sie eine Figur korrekt aus!");
                break;
            case figureNotInHouse:
                System.out.println("Bitte wählen Sie eine Figur im Haus aus!");
                break;
            case sixrequired:
                System.out.println("Sie müssen eine 6 würfeln um aus dem Haus zu kommen.");
                break;
            case startfieldOccupied:
                System.out.println("Ihr Startfeld ist belegt, bitte wählen Sie eine andere Figur aus!");
                break;
            case collision:
                System.out.println("Es gibt eine Kollision! Bitte wählen Sie eine andere Figur aus!");
                break;

        }

    }

    private void wuerfeln() {
        for (Figur figur : this.gameModel.getFigurs()) {
            System.out.println(figur.getId() + "  " + figur.getField().getId());
        }
        System.out.println("active player" +
                this.gameModel.getActivePlayer());

        this.counterAusgeben(this.gameModel.getCounter());
        System.out.println("Das Würfelergebnis ist " + this.gameModel.getWuerfelErgebnis());
    }

    private void ziehen() {

        for (int u = (this.gameModel.getActivePlayerId() * 3); u < (this.gameModel.getActivePlayerId() * 3) + 3; u++) {
            //Syso in View
            System.out.println(this.gameModel.getFigurs().get(u).getId());
        }
        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        try {
            gameModel.setInput(String.valueOf(this.gameModel.getActivePlayerId() + 1) + c.readLine().toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.gameModel.state != wrongFigure) {
            System.out.println("Welche Figur möchten Sie ziehen? Geben sie hierfür den Buchstaben der Figur an und drücken Sie enter!");
        }
        return;
    }


}
