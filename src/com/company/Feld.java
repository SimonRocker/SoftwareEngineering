package com.company;

public class Feld {
    private int id;

    public Feld(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static class Factory implements FeldFactory{

        public Feld build(int id){
            return new Feld(id);
        }
    }
    }
