package com.company;

public class Fassade extends Subject implements IModel {
    Model gameModel;
    State currentState;


    public Fassade() {
        this.gameModel = new Model();
        this.currentState = new State();
    }

    @Override
    public void throwDice() {
        if (this.currentState.getState() == State.State_Roll_Dice) {
            this.currentState.setState(gameModel.rollDice());
            notify(this.currentState.getState());
        }
    }

    @Override
    public int getCurrentPlayer() {
        return this.gameModel.getCurrentPlayer();
    }
}
