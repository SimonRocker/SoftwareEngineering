package com.company.logic;

import com.company.factory.FieldFactory;
import com.company.factory.FigureFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameModel {

    private static List<Field> fields;
    private static FigureFactory figureFactory;
    private static FieldFactory fieldFactory;

    private Player currentPlayerObject;
    private List<Player> players;
    private List<Figure> figures;
    private int tries;
    private int diceNumber;
    private int previousField;
    private int actualField;
    private Figure figureToMove;

    public GameModel() {

        this.players = new ArrayList<>(3);
        this.figures = new ArrayList<>(12);
        fields = new ArrayList<>(48);

        figureFactory = new FigureFactory();
        fieldFactory = new FieldFactory();
        this.tries = 1;
        this.initialise();
        this.currentPlayerObject = this.players.get(0);

    }

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
        figureId = String.valueOf(this.currentPlayerObject.getId() + figureId);
        for (int o = ((this.currentPlayerObject.getId()) * 3); o < ((this.currentPlayerObject.getId()) * 3) + 3; o++) {
            String temp = figures.get(o).getId();
            if (figureId.equals(temp)) {
                this.figureToMove = this.figures.get(o);
            }
        }
        boolean isFigureAtHome = isFigureAtHome(this.figureToMove);
        if (this.figureToMove == null) {
            try {
                throw new Exception("Figure not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int returnState = -1;
        if (isFigureAtHome) {
            if (this.diceNumber != 6) {
                returnState = State.MState_InvalidMove;
            } else {
                int startFieldId = currentPlayerObject.getStartField();
                boolean isOccupied = isOccupied(startFieldId);
                if (!isOccupied) {

                    this.figureToMove.setField(new Field(startFieldId, true));
                    returnState = State.State_Next_Player;
                } else {
                    returnState = State.MState_InvalidMove;
                }
            }
        } else {

            int position = (this.figureToMove.getField().getPosition() + this.diceNumber) % 48;
            while (isOccupied(position)) {
                position++;
            }
            this.figureToMove.setField(new Field(position, true));
            returnState = State.State_Next_Player;
        }

        return returnState;
    }

    private boolean isOccupied(int fieldId) {
        for (Figure f : this.figures) {
            if (f.getField().getPosition() == fieldId) {
                return true;

            }
        }
        return false;
    }

    private boolean isFigureAtHome(Figure figureToMove) {
        if (figureToMove.getField().getPosition() == -1) {
            return true;
        }
        return false;
    }

    public int moveFigure() {

        Field newPosition;
        if (this.figureToMove.getField().getPosition() == -1)
            newPosition = fields.get((this.currentPlayerObject.getId()) * 12);
        else
            newPosition = fields.get((this.figureToMove.getField().getPosition() + this.diceNumber) % 48);
        this.previousField = this.figureToMove.getField().getPosition();
        this.figureToMove.setField(newPosition);
        this.actualField = this.figureToMove.getField().getPosition();
        return State.MState_Moved_Figure;

    }

    public int rollDice() {

        int randomNumber = 6;

        this.diceNumber = randomNumber;

        //boolean notAbleToMakeTurn = randomNumber != 6 && areAllFiguresInHouse(this.currentPlayerObject.getId());
        boolean allFiguresInHouse = areAllFiguresInHouse(this.currentPlayerObject.getId());

        boolean triesNotExpired = this.tries + 1 <= 3;
        int returnState = -1;

        if (allFiguresInHouse) {
            if (randomNumber == 6) {
                returnState = State.State_Make_Turn;
            } else if (tries >= 3) {
                returnState = State.State_Next_Player;
            } else {
                returnState = State.MState_Check_Turn;
                tries++;
            }

        } else {
            returnState = State.State_Make_Turn;
        }

        return returnState;
    }

    public int nextPlayer() {
        this.resetTries();
        this.currentPlayerObject = this.players.get((this.currentPlayerObject.getId() + 1) % 4);
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

    public Player getCurrentPlayer() {
        return this.currentPlayerObject;
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
