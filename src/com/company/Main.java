package com.company;

public class Main {
    private static Model model = new Model();
    public static void main (String args[]) {
        try {
            model.initialize();
        } catch(Exception e) {
            System.out.println("Dumm gelaufen");
        }
    }
}
