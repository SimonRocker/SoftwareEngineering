package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws IOException {

        Spieler[] players = {new Spieler("Spieler 1", 1), new Spieler("Spieler 2", 2),
                new Spieler("Spieler 3", 3), new Spieler("Spieler 4", 4)};
        Figur[] figurs = {new Figur("1A"), new Figur("1B"), new Figur("1C"),
                new Figur("2A"), new Figur("2B"), new Figur("2C"),
                new Figur("3A"), new Figur("3B"), new Figur("3C"),
                new Figur("4A"), new Figur("4B"), new Figur("4C")};
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
                    System.out.println(figur.getName() + "  " + figur.getPosition());
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
                        System.out.println(figurs[u].getName());
                    }
                    String input = String.valueOf(i + 1) + c.readLine();
                    boolean korrekteFigurAusgewaehlt = false;
                    for (int o = (i * 3); o < (i * 3) + 3; o++) {
                        if (input.equals(figurs[o].getName())) {
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
            }
            i++;
        }
    }

    private static int getRandomDiceNumber() {
        return ThreadLocalRandom.current().nextInt(1, 6 + 1);
    }

    private static boolean zieheFigur(Figur[] figurs, Figur figur, int wuerfelergebnis, int playerId) {
        if(zugMöglich(figurs, figur, wuerfelergebnis, playerId)) {
            int newPosition = (figur.getPosition() + wuerfelergebnis) % 48;
            if(figur.getPosition() == -1)
                figur.setPosition(playerId * 12);
            else
                figur.setPosition(newPosition);
            return true;
        }
        return false;
    }

    private static boolean alleImHaus(Figur[] figurs, int playerId) {
        boolean alleImHaus = true;
        for (int p = ((playerId -1) * 3); p < ((playerId -1) * 3) + 3; p++) {
            if (!(figurs[p].getPosition() == -1))
               alleImHaus = false;
        }
        return alleImHaus;
    }

    private static boolean zugMöglich(Figur[] figurs, Figur figur, int wuerfelergebnis, int playerId) {

        boolean startfeldBelegt = false;
        if(figur.getPosition() != -1) {
            for (Figur f: figurs) {
                if((figur.getPosition() + wuerfelergebnis) % 48 == f.getPosition()) {
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
            if(f.getPosition() == playerId * 12) {
                startfeldBelegt = true;
            }
        }

        boolean mindestensEinerImHaus = false;
        for (int p = (playerId * 3); p < (playerId * 3) + 3; p++) {
            if (figurs[p].getPosition() == -1)
                mindestensEinerImHaus = true;
        }

        if(mindestensEinerImHaus && wuerfelergebnis == 6 && !(figur.getPosition() == -1)) {
            if(startfeldBelegt)
                return true;
            System.out.println("Bitte wählen Sie eine Figur im Haus aus!");
            return false;
        } else if(mindestensEinerImHaus && wuerfelergebnis == 6 && figur.getPosition() == -1 && startfeldBelegt) {
            System.out.println("Ihr Startfeld ist belegt, bitte wählen Sie eine andere Figur aus!");
            return false;
        }
        return true;
    }
}
