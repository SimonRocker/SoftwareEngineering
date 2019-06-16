package com.company.model;

public class State {
    public static final int State_Next_Player = 0;
    public static final int State_Roll_Dice = 1;
    public static final int State_Make_Turn = 2;
    public static final int MState_Collision = 3;
    public static final int MState_Turn_Valid = 4;
    public static final int MState_Startfield_Occupied = 5;
    public static final int MState_Check_Turn = 6;
    public static final int MState_Moved_Figure = 7;
    public static final int MState_Diced = 8;

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
            case 3:
                name = "collision";
                break;
            case 4:
                name = "turnvalid";
                break;
            case 5:
                name = "startfieldOccupied";
                break;
            default:
                name = "default";
                break;
        }
        return name;
    }


}
