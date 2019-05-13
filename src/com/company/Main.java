package com.company;

public class Main {
    private static Controller controller = new Controller();
    public static void main (String args[]) {
        try {
            controller.initialize();
        } catch(Exception e) {
            System.out.println("Dumm gelaufen");
        }
        controller.startGame();
    }
}
