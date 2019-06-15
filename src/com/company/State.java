package com.company;

public class State {
    public static final int State_Next_Player = 0;
    public static final int State_Roll_Dice = 1;
    public static final int State_Make_Turn = 2;

    public void setState(int state) {
        this.state = state;
    }

    private int state = State_Next_Player;

    public int getState() {
        return this.state;
    }

    public String getName() {
        String name;
        switch (state) {
            case 0:
                name = "nextplayer";
                break;
            case 1:
                name = "rolldice";
                break;
            case 2:
                name = "maketurn";
                break;
            default:
                name = "default";
                break;
        }
        return name;
    }


}
