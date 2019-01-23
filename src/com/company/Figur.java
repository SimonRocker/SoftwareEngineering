package com.company;

public class Figur {
    private String name;
    private int position;

    public Figur(String name) {
        this.name = name;
        this.position = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
