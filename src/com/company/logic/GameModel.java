package com.company.logic;

import com.company.factory.FieldFactory;
import com.company.factory.FigureFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameModel {

    private int currentPlayer;
    private List<Player> players;
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

    public GameModel() {
        this.currentPlayer = 0;
        this.players = new ArrayList<>(3);
        this.figures = new ArrayList<>(12);
        fields = new ArrayList<>(48);

        figureFactory = new FigureFactory();
        fieldFactory = new FieldFactory();
        this.tries = 1;
        this.initialise();
    }

    private void initialise() {

        this.players.addAll(List.of(new Player("Player 1", 0), new Player("Player 2", 1),
                new Player("Player 3", 2), new Player("Player 4", 3)));
        for (Player player : players) {
            this.figures.addAll(figureFactory.buildForPlayer(player.getId()));
        }
        for (int i = 0; i < 48; i++) {
            fields.add(i, fieldFactory.build(i));
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
        if (this.figureToMove .getField().getPosition() != -1) {
            for (Figure f : this.figures) {
                if ((this.figureToMove .getField().getPosition() + this.diceNumber) % 48 == f.getField().getPosition()) {
                    return State.MState_Collision;

                }
            }
        }


        for (Figure f : this.figures) {
            if (f.getField().getPosition() == this.currentPlayer * 12) {
                startfeldBelegt = true;

            }
        }

        boolean mindestensEinerImHaus = false;
        for (int p = (this.currentPlayer * 3); p < (this.currentPlayer * 3) + 3; p++) {
            if (figures.get(p).getField().getPosition() == -1)
                mindestensEinerImHaus = true;
        }

        if (mindestensEinerImHaus && this.diceNumber == 6 && !(this.figureToMove.getField().getPosition() == -1) && startfeldBelegt) {
            return State.MState_Turn_Valid;
        } else if (mindestensEinerImHaus && this.diceNumber == 6 && this.figureToMove.getField().getPosition() == -1 && startfeldBelegt) {
            return State.MState_Startfield_Occupied;
        } else if(mindestensEinerImHaus && this.diceNumber != 6 && this.figureToMove.getField().getPosition() == -1) {
            return State.MState_InvalidMove;
        }
        return State.MState_Turn_Valid;
    }

    public int moveFigure() {

        Field newPosition;
        if (this.figureToMove.getField().getPosition() == -1)
            newPosition = fields.get((this.currentPlayer) * 12);
        else
            newPosition = fields.get((this.figureToMove.getField().getPosition() + this.diceNumber) % 48);
        this.previousField = this.figureToMove.getField().getPosition();
        this.figureToMove.setField(newPosition);
        this.actualField = this.figureToMove.getField().getPosition();
        return State.MState_Moved_Figure;

    }

    public int rollDice() {

        int randomNumber = calculateRandomNumber();
        this.diceNumber = randomNumber;

        boolean notAbleToMakeTurn = randomNumber != 6 && areAllFiguresInHouse(this.currentPlayer);
        boolean triesNotExpied = this.tries + 1 <= 3;
        if (notAbleToMakeTurn && triesNotExpied) {
            this.tries++;
            return State.MState_Check_Turn;
        } else if (!triesNotExpied) {

            return State.State_Next_Player;

        } else if (randomNumber == 6) {
            return State.State_Make_Turn;
        }
        if(areAllFiguresInHouse(this.currentPlayer))
            return State.State_Roll_Dice;
        else
            return State.State_Make_Turn;

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
        for (int p = (playerId * 3); p < ((playerId + 1) * 3); p++) {
            if (figures.get(p).getField().getPosition() != -1)
                return false;
        }
        return true;
    }

    private int calculateRandomNumber() {
        return ThreadLocalRandom.current().nextInt(1, 7);
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
