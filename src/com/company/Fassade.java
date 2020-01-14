package com.company;

import com.company.logic.*;
import com.company.logic.ports.MVCPort;

import java.util.List;

public class Fassade extends Subject implements IGameModel {
    private GameModel gameModel;
    private MVCPort mvcPort;
    private State currentState;

    public Fassade() {
        this.gameModel = new GameModel();
        this.currentState = new State();
        this.currentState.setState(State.State_Roll_Dice);
    }

    @Override
    public void rollDice() {
        if (this.currentState.getState() == State.State_Roll_Dice  ) {
            this.currentState.setState(gameModel.rollDice());
            notify(this.currentState.getState());
        }
    }

    @Override
    public void nextPlayer() {
        if (this.currentState.getState() == State.State_Next_Player) {
            this.currentState.setState(gameModel.nextPlayer());
            notify(this.currentState.getState());
        }
    }

    @Override
    public void turnValid(String figure) {
        if (this.currentState.getState() == State.State_Make_Turn) {
            this.currentState.setState(gameModel.zugMoeglich(figure));
            notify(this.currentState.getState());
        }
    }

    @Override
    public void moveFigure() {
        if (this.currentState.getState() == State.MState_Turn_Valid) {
            this.currentState.setState(gameModel.moveFigure());
            notify(this.currentState.getState());
            this.currentState.setState(State.State_Next_Player);
            notify(this.currentState.getState());
        }
    }


    @Override
    public void resetStateAndTryAgain() {
        if(this.currentState.getState() == State.MState_Startfield_Occupied || this.currentState.getState() == State.MState_InvalidMove) {
            this.currentState.setState(State.State_Make_Turn);
            notify(this.currentState.getState());
        }
    }

    @Override
    public void tryAgain() {
        if(this.currentState.getState() == State.MState_Check_Turn) {
            this.currentState.setState(State.State_Roll_Dice);
            notify(this.currentState.getState());
        }
    }

    @Override
    public int getCurrentPlayer() {
        return this.gameModel.getCurrentPlayer();
    }

    @Override
    public List<Field> getFields() {
        return this.gameModel.getFields();
    }

    @Override
    public List<Figure> getFigures() {
        return this.gameModel.getFigures();
    }

    @Override
    public int getTries() {
        return this.gameModel.getTries();
    }

    @Override
    public int getDiceValue() {
        return this.gameModel.getDiceNumber();
    }

    @Override
    public String getPreviousField() {
        return String.valueOf(this.gameModel.getPreviousField());
    }

    @Override
    public String getActualField() {
        return String.valueOf(this.gameModel.getActualField());
    }
}
