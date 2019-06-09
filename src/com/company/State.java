package com.company;

public class State {
    public static final int State_Roll_Dice = 0;

    public void setState(int state) {
        this.state = state;
    }

    private int state = State_Roll_Dice;

    public int getState() {
        return this.state;
    }

    public String getName() {
        String name;
        switch (state) {
            case 1:
                name = "rolldice";
                break;
            default:
                name = "default";
                break;
        }
        return name;
    }
}
