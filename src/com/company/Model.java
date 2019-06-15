package com.company;

import com.company.factory.FieldFactory;
import com.company.factory.FigureFactory;
import com.company.model.Field;
import com.company.model.Figure;
import com.company.model.Spieler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Model {

    private int currentPlayer;
    private List<Spieler> players;
    private List<Figure> figures;


    private static List<Field> fields;

    private static FigureFactory figureFactory;
    private static FieldFactory fieldFactory;

    private int tries;
    private int diceNumber;

    public Model() {
        this.currentPlayer = 1;
        this.players = new ArrayList<>(3);
        this.figures = new ArrayList<>(12);
        this.fields = new ArrayList<>(48);

        this.figureFactory = new FigureFactory();
        this.fieldFactory = new FieldFactory();
        this.tries = 1;
        this.initialise();
    }

    public void initialise() {

        this.players.addAll(List.of(new Spieler("Spieler 1", 0), new Spieler("Spieler 2", 1),
                new Spieler("Spieler 3", 2), new Spieler("Spieler 4", 3)));
        for (Spieler spieler : players) {
            this.figures.addAll(this.figureFactory.buildForPlayer(spieler.getId() + 1));
        }
        for (int i = 0; i < 48; i++) {
            this.fields.add(i, this.fieldFactory.build(i));
        }
    }

    public int rollDice() {

        int randomNumber = calculateRandomNumber();
        this.diceNumber = randomNumber;

        boolean notAbleToMakeTurn = randomNumber != 6 && areAllFiguresInHouse(this.currentPlayer);
        boolean triesNotExpied = this.tries + 1 <= 3;
        if (notAbleToMakeTurn && triesNotExpied) {
            this.tries++;
        } else if (!triesNotExpied) {

            return State.State_Next_Player;

        } else if (randomNumber == 6) {
            return State.State_Make_Turn;
        }
        return State.State_Roll_Dice;

    }

    public int nextPlayer() {
        this.resetTries();
        this.currentPlayer = (this.currentPlayer + 1) % 4;
        return State.State_Roll_Dice;
    }

    private void resetTries() {
        this.tries = 1;
    }

    private boolean areAllFiguresInHouse(int playerId) {
        for (int p = (playerId * 3); p < (playerId * 3) + 3; p++) {
            if (!(figures.get(p).getField().getId() == -1))
                return false;
        }
        return true;
    }

    private int calculateRandomNumber() {
        return 2;
        //return 6;
        //return ThreadLocalRandom.current().nextInt(1, 7);
    }

    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Figure> getFigures() {
        return this.figures;
    }

    public int getTries() {
        return this.tries;
    }

    public int getDiceNumber() {
        return this.diceNumber;
    }
}
