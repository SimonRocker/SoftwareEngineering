package com.company.factory;

import com.company.model.Feld;

public class FeldFactory {

    public Feld build(int id){
        return new Feld(id);
    }
}
