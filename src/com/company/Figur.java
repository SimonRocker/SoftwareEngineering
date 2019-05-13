package com.company;

public class Figur {
    private String id;
    private Feld field;

    public Figur(String id, Feld field) {
        this.id = id;
        this.field = field;
    }

    public Feld getField() {
        return this.field;
    }

    public void setField(Feld field) {
        this.field = field;
    }

    public String getId() {
        return id;
    }
}
