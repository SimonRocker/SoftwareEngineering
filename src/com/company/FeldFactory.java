package com.company;

public class FeldFactory {

    public Feld build(int id){
        return new Feld(id);
    }
}
