package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

public class Model {

    private static Feld[] fields = null;
    private static FigurFactory f = new Figur.Factory();
    private static FeldFactory feldFactory = new Feld.Factory();
    private boolean initialized = false;

    public Model(){}

    public void initialize() throws IOException {

        Spieler[] players = {new Spieler("Spieler 1", 1), new Spieler("Spieler 2", 2),
                new Spieler("Spieler 3", 3), new Spieler("Spieler 4", 4)};
        Figur[] figurs = {f.build("1A"), f.build("1B"), f.build("1C"),
                f.build("2A"), f.build("2B"), f.build("2C"),
                f.build("3A"), f.build("3B"), f.build("3C"),
                f.build("4A"), f.build("4B"), f.build("4C")};

        for(int i = 0; i < 48; i++) {
            fields[i] = feldFactory.build(i);
        }
        boolean isFinished = false;
        int i = 0;
        int wuerfelErgebnis;
        while (!isFinished) {
            i = i % 4;
            int counter = 0;
            String activePlayer = players[i].getName();
            BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
            do{
                System.out.println(activePlayer + " ist dran. \n" +
                        //Figuren holen, jeweils ausgeben wo sie stehen
                        "Bitte drücken sie Enter zum würfeln!");
                c.readLine();
                wuerfelErgebnis = getRandomDiceNumber();
                System.out.println("Das Würfelergebnis ist " + wuerfelErgebnis);
                for (Figur figur : figurs) {
                    System.out.println(figur.getId() + "  " + figur.getField().getId());
                }
                if (alleImHaus(figurs, i + 1) && wuerfelErgebnis != 6) {
                    counter++;
                    System.out.println(counter + "er Versuch! Sie haben 3 Versuche!");
                } else {
                    break;
                }
            } while (counter < 3);
            if (counter != 3) {
                boolean gezogen = false;
                do {
                    System.out.println("Welche Figur möchten Sie ziehen? Geben sie hierfür den Buchstaben vom Zeilenanfang an und drücken Sie enter!");
                    for (int u = (i * 3); u < (i * 3) + 3; u++) {
                        System.out.println(figurs[u].getId());
                    }
                    String input = String.valueOf(i + 1) + c.readLine().toUpperCase();
                    boolean korrekteFigurAusgewaehlt = false;
                    for (int o = (i * 3); o < (i * 3) + 3; o++) {
                        if (input.equals(figurs[o].getId())) {
                            korrekteFigurAusgewaehlt = true;
                            gezogen = zieheFigur(figurs, figurs[o], wuerfelErgebnis, i);
                            if (!gezogen) {
                                System.out.println("Zug nicht möglich, bitte erneut auswählen!");
                            }
                        }
                    }
                    if(!korrekteFigurAusgewaehlt)
                        System.out.println("Bitte wählen Sie eine Figur korrekt aus!");
                } while (!gezogen);
            } else {
                System.out.println("Nächster Spieler an der Reihe! \n");
            }
            i++;
        }
        this.initialized = true;
    }

    private static int getRandomDiceNumber() {
        return ThreadLocalRandom.current().nextInt(1, 7);
    }

    private static boolean zieheFigur(Figur[] figurs, Figur figur, int wuerfelergebnis, int playerId) {
        if(zugMoeglich(figurs, figur, wuerfelergebnis, playerId)) {
            int newPosition = (figur.getField().getId() + wuerfelergebnis) % 48;
            if(figur.getField().getId() == -1)
                figur.setField(fields[playerId * 12]);
            else
                figur.setField(fields[newPosition]);
            return true;
        }
        return false;
    }

    private static boolean alleImHaus(Figur[] figurs, int playerId) {
        boolean alleImHaus = true;
        for (int p = ((playerId -1) * 3); p < ((playerId -1) * 3) + 3; p++) {
            if (!(figurs[p].getField().getId() == -1))
                alleImHaus = false;
        }
        return alleImHaus;
    }

    private static boolean zugMoeglich(Figur[] figurs, Figur figur, int wuerfelergebnis, int playerId) {

        boolean startfeldBelegt = false;
        if(figur.getField().getId() != -1) {
            for (Figur f: figurs) {
                if((figur.getField().getId() + wuerfelergebnis) % 48 == f.getField().getId()) {
                    System.out.println("Es gibt eine Kollision! Bitte wählen Sie eine andere Figur aus!");
                    return false;
                }
            }
        } else {
            if(wuerfelergebnis != 6) {
                System.out.println("Sie müssen eine 6 würfeln um aus dem Haus zu kommen.");
                return false;
            }
        }

        for (Figur f: figurs) {
            if(f.getField().getId() == playerId * 12) {
                startfeldBelegt = true;
            }
        }

        boolean mindestensEinerImHaus = false;
        for (int p = (playerId * 3); p < (playerId * 3) + 3; p++) {
            if (figurs[p].getField().getId() == -1)
                mindestensEinerImHaus = true;
        }

        if(mindestensEinerImHaus && wuerfelergebnis == 6 && !(figur.getField().getId() == -1)) {
            if(startfeldBelegt)
                return true;
            System.out.println("Bitte wählen Sie eine Figur im Haus aus!");
            return false;
        } else if(mindestensEinerImHaus && wuerfelergebnis == 6 && figur.getField().getId() == -1 && startfeldBelegt) {
            System.out.println("Ihr Startfeld ist belegt, bitte wählen Sie eine andere Figur aus!");
            return false;
        }
        return true;
    }
}
