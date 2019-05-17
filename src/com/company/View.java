package com.company;

import com.company.observer.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        if (this.gameModel.state == Model.State.zugNichtMoeglich) {
            System.out.println("Zug nicht möglich, bitte erneut auswählen!");

        }

        if (this.gameModel.getCounter() == 3) {
            System.out.println("Nächster Spieler an der Reihe! \n");
        }

    }
}
