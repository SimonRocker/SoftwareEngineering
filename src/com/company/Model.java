package com.company;

import com.company.factory.FieldFactory;
import com.company.factory.FigureFactory;
import com.company.model.Field;
import com.company.model.Figure;
import com.company.model.Spieler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Model {

    private int currentPlayer;
    private List<Spieler> players;
    private List<Figure> figurs;
    private static List<Field> fields;

    private static FigureFactory figureFactory;
    private static FieldFactory fieldFactory;



    public Model() {
        this.currentPlayer = 1;
        this.players =  new ArrayList<>(3);
        this.figurs = new ArrayList<>(12);
        this.fields = new ArrayList<>(48);

        this.figureFactory = new FigureFactory();
        this.fieldFactory = new FieldFactory();
        this.initialize();
    }

    public void initialize() {

        this.players.addAll(List.of(new Spieler("Spieler 1", 0), new Spieler("Spieler 2", 1),
                new Spieler("Spieler 3", 2), new Spieler("Spieler 4", 3)));
        for (Spieler spieler : players) {
            figurs.addAll(figureFactory.buildForPlayer(spieler.getId() + 1));
        }
        for (int i = 0; i < 48; i++) {
            fields.add(i, fieldFactory.build(i));
        }
    }

    public int rollDice() {
        int randomNumber = calculateRandomNumber();
        if (randomNumber == 6) {

        } else {

        }
        return State.State_Roll_Dice;
    }

    private int calculateRandomNumber() {

        return ThreadLocalRandom.current().nextInt(1, 7);
    }
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }
}
