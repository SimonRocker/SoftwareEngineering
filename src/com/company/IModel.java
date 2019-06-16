package com.company;

import com.company.model.Field;
import com.company.model.Figure;

import java.util.List;

public interface IModel extends ISubject {

    void rollDice();

    void nextPlayer();

    void turnValid(String figure);

    void zieheFigur();

    int getCurrentPlayer();

    List<Field> getFields();

    List<Figure> getFigures();

    int getTries();

    int getDiceValue();

    String getPreviousField();

    String getActualField();
}
