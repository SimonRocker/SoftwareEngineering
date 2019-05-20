package com.company;

import com.company.controller.Controller;

public class Main {
    private static Controller controller = new Controller();
    public static void main (String args[]) {
        try {
            controller.initialize();
        } catch(Exception e) {
            System.out.println(e);
        }
        controller.startGame();
    }
}
