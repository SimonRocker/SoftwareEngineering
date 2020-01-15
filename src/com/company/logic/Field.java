package com.company.logic;

public class Field {
    private int id;
    private boolean isOccupied = false;
    public Field(int id){
        this.id = id;
    }

    public Field(int id, boolean isOccupied) {
        this.id = id;
        this.isOccupied = isOccupied;
    }

    public int getPosition() {
        return id;
    }

    public void setIsOccupied(boolean isOccupied) {
    this.isOccupied = isOccupied;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }
}
