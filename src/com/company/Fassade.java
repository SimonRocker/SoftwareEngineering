package com.company;

import com.company.model.Figure;

import java.util.List;

public class Fassade extends Subject implements IModel {
    Model gameModel;
    State currentState;
    List fields;
    List figures;

    public Fassade() {
        this.gameModel = new Model();
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
    public int getCurrentPlayer() {
        return this.gameModel.getCurrentPlayer();
    }

    @Override
    public List getFields() {
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


    public List getFigurs() {
        return this.gameModel.getFigures();
    }

}
