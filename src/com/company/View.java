package com.company;

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

    public String wuerfel(String activePlayer) throws IOException {
        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(activePlayer + " ist dran. \n" +
                "Bitte drücken sie Enter zum würfeln!");
        //Readline in Controller
        return c.readLine();
    }

    public void counterAusgeben(int counter) {
        System.out.println(counter + "er Versuch! Sie haben 3 Versuche!");
    }

    @Override
    public void update() {
        if (this.gameModel.state == Model.State.wuerfeln) {


            for (Figur figur : this.gameModel.getFigurs()) {
                System.out.println(figur.getId() + "  " + figur.getField().getId());
            }
            System.out.println("active player" +
                    this.gameModel.getActivePlayer());

            this.counterAusgeben(this.gameModel.getCounter());
            System.out.println("Das Würfelergebnis ist " + this.gameModel.getWuerfelErgebnis());

        }

        switch (this.gameModel.state) {
            case zugNichtMoeglich:
                System.out.println("Zug nicht möglich, bitte erneut auswählen!");

                break;
            case ziehen:
                for (int u = (this.gameModel.getActivePlayerId() * 3); u < (this.gameModel.getActivePlayerId() * 3) + 3; u++) {
                    //Syso in View
                    System.out.println(this.gameModel.getFigurs().get(u).getId());
                }
                if (this.gameModel.state != wrongFigure) {
                    System.out.println("Welche Figur möchten Sie ziehen? Geben sie hierfür den Buchstaben der Figur an und drücken Sie enter!");
                }
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


}
