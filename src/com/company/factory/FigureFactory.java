package com.company.factory;

import com.company.model.Field;
import com.company.model.Figure;

import java.util.List;

public class FigureFactory {

    private FieldFactory factory = new FieldFactory();
    public List<Figure> buildForPlayer(int id){
        Field field = factory.build(-1);
        Figure figurA = new Figure(id + "A", field);
        Figure figurB = new Figure(id + "B", field);
        Figure figurC = new Figure(id + "C", field);
        return List.of(figurA,figurB, figurC);
    }
}
