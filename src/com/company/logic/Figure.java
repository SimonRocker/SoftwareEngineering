package com.company.logic;

public class Figure {
    private String id;
    private Field field;

    public Figure(String id, Field field) {
        this.id = id;
        this.field = field;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getId() {
        return id;
    }
}
