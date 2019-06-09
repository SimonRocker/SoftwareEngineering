package com.company;

public class ObserverView implements IObserver {

    private IModel model;

    public ObserverView(IModel model) {
        this.model = model;
        this.model.attach(this);
        this.initialise();
    }

    public void askForDiceRoll() {
        System.out.println("Please press enter to roll the dice!");
    }

    private void initialise() {

    }

    @Override
    public void update(int state) {
        switch (state) {
            case State.State_Roll_Dice:
                System.out.printf("Player %d's turn.", this.model.getCurrentPlayer());
                System.out.printf("Please press enter to roll the dice!");
                break;
            default:
                throw new IllegalStateException();
        }
    }
}
