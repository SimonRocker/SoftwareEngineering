package com.company.logic;

import com.company.ISubject;

import java.util.List;

public interface IGameModel extends ISubject {

    void rollDice();

    void nextPlayer();

    void turnValid(String figure);

    void moveFigure();

    int getCurrentPlayer();

    List<Field> getFields();

    List<Figure> getFigures();

    int getTries();

    int getDiceValue();

    String getPreviousField();

    String getActualField();

    void resetStateAndTryAgain();

    void tryAgain();
}
