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
            case State.State_Next_Player:
                this.showDiceNumber();
                System.out.println("No tries left, next player on the line.");
                this.model.nextPlayer();
                break;
            case State.State_Make_Turn:
                this.showDiceNumber();
                this.showFields();
                this.askForFigureSelection();
                break;

            case State.MState_Turn_Valid:
                this.model.zieheFigur();
                break;
            case State.MState_Moved_Figure:
                System.out.println("Figure moved from Field " + this.model.getPreviousField() + " nach Field " + this.model.getActualField() + "!");
                this.model.nextPlayer();
                break;
            case State.MState_InvalidMove:
                invalidMove();
                this.model.resetStateAndTryAgain();
                break;
            case State.MState_Startfield_Occupied:
                startfieldOccupied();
                this.model.resetStateAndTryAgain();
                break;
            case State.MState_Collision:
                collison();
                this.model.resetStateAndTryAgain();
                break;
            case State.MState_Check_Turn:
                this.showDiceNumber();
                System.out.println("Try again");
                this.model.tryAgain();
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

    private void startfieldOccupied(){
        System.out.println("Your Homefield is occupied, please select the figure on there.");
    }

    private void collison() {
        System.out.println("Field already occupied, please select another figure.");
    }

    private void invalidMove() {
        System.out.println("You can only leave your house with a 6.");
    }
}
