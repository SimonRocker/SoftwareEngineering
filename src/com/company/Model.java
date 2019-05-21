package com.company;

import com.company.factory.FeldFactory;
import com.company.factory.FigurFactory;
import com.company.model.Spieler;
import com.company.model.Feld;
import com.company.model.Figur;
import com.company.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Observer pattern note: This is the subject.
 */
public class Model {

    private List<Observer> observers = new ArrayList<Observer>();


    private static List<Feld> fields = new ArrayList<>(48);
    private static FigurFactory f = new FigurFactory();
    private static FeldFactory feldFactory = new FeldFactory();
    private boolean isFinished = false;
    private List<Spieler> players = new ArrayList<>(3);


    private List<Figur> figurs = new ArrayList<>(12);
    private View view = new View();

    private int counter = -1;


    private int wuerfelErgebnis = -1;
    public State state = State.init;

    enum State {
        init,
        wuerfeln,
        ziehen,
        zugNichtMoeglich,
        wrongFigure,
        sixrequired,
        startfieldOccupied,
        figureNotInHouse,
        collision,
        waitForDiceThrow
    }


    private boolean playerChanged = false;
    private String activePlayer = "";
    private int activePlayerId = 0;


    //TODO following line val should come from controller, fix soon
    private String input;

    public void initialize() {

        players.addAll(List.of(new Spieler("Spieler 1", 0), new Spieler("Spieler 2", 1),
                new Spieler("Spieler 3", 2), new Spieler("Spieler 4", 3)));
        for (Spieler spieler : players) {
            figurs.addAll(f.buildForPlayer(spieler.getId() + 1));
        }
        for (int i = 0; i < 48; i++) {
            fields.add(i, feldFactory.build(i));
        }
    }

    //int methode zum zurückgeben an den Controller, nötig beim Aufruf des Controllers. Oder wir halten es hier als Variable
    public void wuerfeln() throws IOException {
        this.playerChanged = false;

        boolean weiterWuerfeln = true;
        this.activePlayer = players.get(activePlayerId).getName();
        this.state = State.wuerfeln;
        String activePlayerName = players.get(activePlayerId).getName();
        do {
            this.state = State.waitForDiceThrow;
            notifyAllModellObservers();
            this.state = State.wuerfeln;
            this.wuerfelErgebnis = getRandomDiceNumber();
            //Figuren holen, jeweils ausgeben wo sie stehen, muss noch in View

            if (alleImHaus(activePlayerId) && this.wuerfelErgebnis != 6) {
                counter++;
            } else {
                weiterWuerfeln = false;
            }
            notifyAllModellObservers();
        } while (counter < 3 && weiterWuerfeln);

        //Zweite Methode (ziehen)
        if (counter != 3) {
            ziehen(activePlayerId);
        } else {
            //Syso in View, Aufruf des Zugendes in Controller
            this.playerChanged = true;
            this.activePlayerId = ++this.activePlayerId % 4;
            notifyAllModellObservers();
        }
        counter = 0;
    }

    private void ziehen(int activePlayerId) throws IOException {
        boolean gezogen = false;
        this.state = State.ziehen;

        do {
            notifyAllModellObservers();
            //Input in Controller, Übergabe des Eingabewertes wieder hier hin zurück, neue Methode


            boolean korrekteFigurAusgewaehlt = false;
            for (int o = (activePlayerId * 3); o < (activePlayerId * 3) + 3; o++) {
                if (this.input.equals(figurs.get(o).getId())) {
                    korrekteFigurAusgewaehlt = true;
                    gezogen = zieheFigur(figurs.get(o), activePlayerId);
                    if (!gezogen) {
                        //Syso in View
                        zugNichtMoeglich(activePlayerId);
                    }
                }
            }
            if (!korrekteFigurAusgewaehlt) {
                //Syso in View
                // TODO replace while with recurrence to beautify the following three lines
                this.state = State.wrongFigure;
                notifyAllModellObservers();
                this.state = State.ziehen;
            }
        } while (!gezogen);
        this.activePlayerId = ++this.activePlayerId % 4;
    }

    private void zugNichtMoeglich(int activePlayerId) {
        this.state = State.zugNichtMoeglich;
        notifyAllModellObservers();
        try {
            this.wuerfeln();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int getRandomDiceNumber() {
        return ThreadLocalRandom.current().nextInt(1, 7);
    }

    private boolean
    zieheFigur(Figur figur, int playerId) {
        if (zugMoeglich(figur, playerId)) {
            int newPosition = (figur.getField().getId() + this.wuerfelErgebnis) % 48;
            if (figur.getField().getId() == -1)
                figur.setField(fields.get(playerId * 12));
            else
                figur.setField(fields.get(newPosition));


            // TODO - this notifies observers
            //notifyAllModellObservers();

            return true;
        }
        return false;
    }

    private boolean alleImHaus(int playerId) {
        for (int p = (playerId * 3); p < (playerId * 3) + 3; p++) {
            if (!(figurs.get(p).getField().getId() == -1))
                return false;
        }
        return true;
    }

    private boolean zugMoeglich(Figur figur, int playerId) {

        boolean startfeldBelegt = false;
        if (figur.getField().getId() != -1) {
            for (Figur f : figurs) {
                if ((figur.getField().getId() + this.wuerfelErgebnis) % 48 == f.getField().getId()) {
                    //Syso in View
                    this.state = State.collision;
                    notifyAllModellObservers();
                    this.state = State.ziehen;
                    return false;
                }
            }
        } else {
            if (this.wuerfelErgebnis != 6) {
                //Syso in View
                this.state = State.sixrequired;
                notifyAllModellObservers();
                this.state = State.ziehen;
                return false;
            }
        }

        for (Figur f : figurs) {
            if (f.getField().getId() == playerId * 12) {
                startfeldBelegt = true;

            }
        }

        boolean mindestensEinerImHaus = false;
        for (int p = (playerId * 3); p < (playerId * 3) + 3; p++) {
            if (figurs.get(p).getField().getId() == -1)
                mindestensEinerImHaus = true;
        }

        if (mindestensEinerImHaus && this.wuerfelErgebnis == 6 && !(figur.getField().getId() == -1)) {
            if (startfeldBelegt)
                return true;
            //Syso in View
            this.state = State.figureNotInHouse;
            notifyAllModellObservers();
            this.state = State.ziehen;
            return false;
        } else if (mindestensEinerImHaus && this.wuerfelErgebnis == 6 && figur.getField().getId() == -1 && startfeldBelegt) {
            //Syso in View
            this.state = State.startfieldOccupied;
            notifyAllModellObservers();
            this.state = State.ziehen;
            return false;
        }
        return true;
    }

    public static List<Feld> getFields() {
        return fields;
    }

    public static void setFields(List<Feld> fields) {
        Model.fields = fields;
    }

    /**
     * Observer pattern
     */
    public void attach(Observer modelObserver) {
        observers.add(modelObserver);
    }

    public void notifyAllModellObservers() {
        for (Observer modelObserver : observers) {
            modelObserver.update();
        }
    }

    public List<Figur> getFigurs() {
        return figurs;
    }

    public void setFigurs(List<Figur> figurs) {
        this.figurs = figurs;
    }


    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getActivePlayerId() {
        return activePlayerId;
    }

    public void setActivePlayerId(int activePlayerId) {
        this.activePlayerId = activePlayerId;
    }


    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getWuerfelErgebnis() {
        return wuerfelErgebnis;
    }

    public void setWuerfelErgebnis(int wuerfelErgebnis) {
        this.wuerfelErgebnis = wuerfelErgebnis;
    }
    public boolean isPlayerChanged() {
        return playerChanged;
    }

    public void setPlayerChanged(boolean playerChanged) {
        this.playerChanged = playerChanged;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

}


