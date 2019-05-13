package com.company;

import java.util.List;

public class FigurFactory {

    FeldFactory factory = new FeldFactory();
    public List<Figur> buildForPlayer(int id){
        Feld field = factory.build(-1);
        Figur figurA = new Figur(id + "A", field);
        Figur figurB = new Figur(id + "B", field);
        Figur figurC = new Figur(id + "C", field);
        Figur figurD = new Figur(id + "D", field);
        return List.of(figurA,figurB, figurC, figurD);
    }
}
