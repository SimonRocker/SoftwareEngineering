package com.company;

import com.company.model.Field;
import com.company.model.Figure;

import java.util.List;

public interface IModel extends ISubject {

    void rollDice();

    void nextPlayer();

    int getCurrentPlayer();

    List<Field> getFields();

    List<Figure> getFigures();

    int getTries();

    int getDiceValue();
}
