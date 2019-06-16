package com.company;

import com.company.factory.FieldFactory;
import com.company.factory.FigureFactory;
import com.company.model.Field;
import com.company.model.Figure;
import com.company.model.Spieler;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private int currentPlayer;
    private List<Spieler> players;
    private List<Figure> figures;


    private static List<Field> fields;

    private static FigureFactory figureFactory;
    private static FieldFactory fieldFactory;

    private int tries;
    private int diceNumber;

    public int getPreviousField() {
        return previousField;
    }

    public void setPreviousField(int previousField) {
        this.previousField = previousField;
    }

    public int getActualField() {
        return actualField;
    }

    public void setActualField(int actualField) {
        this.actualField = actualField;
    }

    private int previousField;
    private int actualField;
    private Figure figureToMove;

    public Model() {
        this.currentPlayer = 0;
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
            this.figures.addAll(this.figureFactory.buildForPlayer(spieler.getId()));
        }
        for (int i = 0; i < 48; i++) {
            this.fields.add(i, this.fieldFactory.build(i));
        }
    }

    public int zugMoeglich(String figureId) {
        this.figureToMove = null;
        figureId = String.valueOf(this.currentPlayer + figureId);
        for (int o = ((this.currentPlayer) * 3); o < ((this.currentPlayer) * 3) + 3; o++) {
            String temp = figures.get(o).getId();
            if (figureId.equals(temp)) {
                this.figureToMove  = this.figures.get(o);
            }
        }
        if (this.figureToMove  == null) {
            try {
                throw new Exception("Figure not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        boolean startfeldBelegt = false;
        if (this.figureToMove .getField().getId() != -1) {
            for (Figure f : this.figures) {
                if ((this.figureToMove .getField().getId() + this.diceNumber) % 48 == f.getField().getId()) {
                    return State.MState_Collision;

                }
            }
        }


        for (Figure f : this.figures) {
            if (f.getField().getId() == this.currentPlayer * 12) {
                startfeldBelegt = true;

            }
        }

        boolean mindestensEinerImHaus = false;
        for (int p = (this.currentPlayer * 3); p < (this.currentPlayer * 3) + 3; p++) {
            if (figures.get(p).getField().getId() == -1)
                mindestensEinerImHaus = true;
        }

        if (mindestensEinerImHaus && this.diceNumber == 6 && !(this.figureToMove .getField().getId() == -1)) {
            return State.MState_Turn_Valid;

        } else if (mindestensEinerImHaus && this.diceNumber == 6 && this.figureToMove .getField().getId() == -1 && startfeldBelegt) {
            //Syso in View
            return State.MState_Startfield_Occupied;

        }
        return State.MState_Turn_Valid;
    }

    public int zieheFigur() {

        Field newPosition;
        if (this.figureToMove .getField().getId() == -1)
            newPosition = fields.get((this.currentPlayer) * 12);
        else
            newPosition = fields.get((this.figureToMove .getField().getId() + this.diceNumber) % 48);
        this.previousField = this.figureToMove .getField().getId();
        this.figureToMove .setField(newPosition);
        this.actualField = this.figureToMove .getField().getId();
        return State.MState_Moved_Figure;

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
        this.currentPlayer = (this.currentPlayer +1) % 4;
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
        //return 2;
        return 6;
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
