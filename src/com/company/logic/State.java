package com.company.logic;

import com.company.logic.ports.MVCPort;

public class State implements MVCPort {
    public static final int State_Next_Player = 0;
    public static final int State_Roll_Dice = 1;
    public static final int State_Make_Turn = 2;
    public static final int MState_Collision = 3;
    public static final int MState_Turn_Valid = 4;
    public static final int MState_Startfield_Occupied = 5;
    public static final int MState_Check_Turn = 6;
    public static final int MState_Moved_Figure = 7;
    public static final int MState_InvalidMove = 8;

    private int state = State_Next_Player;

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public int getState() {
        return this.state;
    }
}
