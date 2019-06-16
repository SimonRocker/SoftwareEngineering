package com.company;

import com.company.model.Figure;
import com.company.model.IModel;
import com.company.model.State;

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
            System.out.println(figure.getId() + "  " + figure.getField().getId());
        }
    }

    private void showPlayersFields() {
        String figure;
        for (int u = ((this.model.getCurrentPlayer()) * 3); u < ((this.model.getCurrentPlayer())* 3) + 3; u++) {
            figure = this.model.getFigures().get(u).getId();
            System.out.println(figure.charAt(1));
        }
    }

    private void showTryNumber() {
        System.out.printf("This is try number %d\n", this.model.getTries());
    }

    public void showDiceNumber() {
        System.out.printf("You diced a %s\n", this.model.getDiceValue());
    }
    @Override
    public void update(int state) {
        // TODO - Debugging: System.out.println("State: " + state);
        // TODO - Debugging: System.out.println("CurrentPlayer: " + this.model.getCurrentPlayer());
        switch (state) {
            case State.State_Roll_Dice:
                System.out.println("\n --- ");
                this.askForDiceRoll();
                this.showTryNumber();
                break;
            case State.MState_Diced:
                this.showDiceNumber();
                break;
            case State.State_Next_Player:
                this.model.nextPlayer();
                break;
            case State.State_Make_Turn:

                this.showPlayersFields();
                this.askForFigureSelection();
                break;

            case State.MState_Turn_Valid:
                this.model.zieheFigur();
                break;
            case State.MState_Moved_Figure:
                System.out.println("Figure moved from Field " + this.model.getPreviousField() + " nach Field " + this.model.getActualField() + "!");
                this.model.nextPlayer();
                break;
            default:
                throw new IllegalStateException("State: " + String.valueOf(state));
        }
    }

    private void askForFigureSelection() {
        System.out.println("Please select your figure to be moved: ");
    }

    public void wrongInput() {
        System.out.println("wrong input");
    }
}
