package com.company;

import java.util.List;

public class FigurFactory {

    private FeldFactory factory = new FeldFactory();
    public List<Figur> buildForPlayer(int id){
        Feld field = factory.build(-1);
        Figur figurA = new Figur(id + "A", field);
        Figur figurB = new Figur(id + "B", field);
        Figur figurC = new Figur(id + "C", field);
        return List.of(figurA,figurB, figurC);
    }
}
