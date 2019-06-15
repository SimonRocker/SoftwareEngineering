package com.company;

import com.company.model.Figure;

import java.util.List;

public class ObserverView implements IObserver {

    private IModel model;

    public ObserverView(IModel model) {
        this.model = model;
        this.model.attach(this);
        this.initialise();
    }

    public void askForDiceRoll() {
        System.out.printf("Player %d, Please press enter to roll the dice!\n", this.model.getCurrentPlayer() + 1);
    }

    private void initialise() {

    }

    private void showFields() {
        List field = this.model.getFields();
        for (Figure figure : this.model.getFigures()) {
            System.out.print(figure.getId() + "  " + figure.getField().getId());
        }
    }

    public void showDiceNumber() {
        System.out.printf("You diced a %s\n", this.model.getDiceValue());
    }
    @Override
    public void update(int state) {
        switch (state) {
            case State.State_Roll_Dice:
                this.showDiceNumber();
                System.out.println("\n --- ");
                this.askForDiceRoll();
                System.out.printf("This is try number %d\n", this.model.getTries());

                break;
            case State.State_Next_Player:
                this.model.nextPlayer();
                break;
            case State.State_Make_Turn:
                this.showFields();
                break;
            default:
                throw new IllegalStateException();
        }
    }
}
