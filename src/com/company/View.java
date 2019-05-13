package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class View {

    public String wuerfel(String activePlayer) throws IOException {
        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(activePlayer + " ist dran. \n" +
                "Bitte drücken sie Enter zum würfeln!");
        return c.readLine();
    }

    public void counterAusgeben(int counter) {
        System.out.println(counter + "er Versuch! Sie haben 3 Versuche!");
    }
}
