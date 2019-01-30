package com.company;

public class Figur {
    private String id;
    private Feld field;

    public Figur(String id) {
        this.id = id;
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

    public static class Factory implements FigurFactory {

        @Override
        public Figur build(String id){
            return new Figur(id);
        }
    }
}
