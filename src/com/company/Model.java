package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Model {
    private static List<Feld> fields = new ArrayList<>();
    private static FigurFactory f = new FigurFactory();
    private static FeldFactory feldFactory = new FeldFactory();
    private boolean isFinished = false;
    private List<Spieler> players = new ArrayList<>();
    private List<Figur> figurs = new ArrayList<>();
    private View view = new View();
    private int counter = 0;

    public void initialize() {
        players.addAll(List.of(new Spieler("Spieler 1", 1), new Spieler("Spieler 2", 2),
                new Spieler("Spieler 3", 3), new Spieler("Spieler 4", 4)));
        for (Spieler spieler : players) {
            figurs.addAll(f.buildForPlayer(spieler.getId()));
        }
        for (int i = 0; i < 48; i++) {
            fields.set(i, feldFactory.build(i));
        }
    }

    //int methode zum zurückgeben an den Controller, nötig beim Aufruf des Controllers. Oder wir halten es hier als Variable
        public void wuerfeln(int activePlayerId)throws IOException {
        int wuerfelErgebnis;
        String activePlayerName = players.get(activePlayerId).getName();
            do {
                view.wuerfel(activePlayerName);
                wuerfelErgebnis = getRandomDiceNumber();
                //Syso in View
                System.out.println("Das Würfelergebnis ist " + wuerfelErgebnis);
                //Figuren holen, jeweils ausgeben wo sie stehen, muss noch in View
                for (Figur figur : figurs) {
                    System.out.println(figur.getId() + "  " + figur.getField().getId());
                }
                if (alleImHaus(activePlayerId) && wuerfelErgebnis != 6) {
                    counter++;
                    view.counterAusgeben(counter);
                } else {
                    break;
                }
            } while (counter < 3);
            //Zweite Methode (ziehen)
            if (counter != 3) {
                boolean gezogen = false;
                do {
                    //Syso in View
                    System.out.println("Welche Figur möchten Sie ziehen? Geben sie hierfür den Buchstaben vom Zeilenanfang an und drücken Sie enter!");
                    for (int u = (activePlayerId * 3); u < (activePlayerId * 3) + 3; u++) {
                        //Syso in View
                        System.out.println(figurs.get(u).getId());
                    }
                    //Input in Controller, Übergabe des Eingabewertes wieder hier hin zurück, neue Methode
                    BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
                    String input = String.valueOf(activePlayerId) + c.readLine().toUpperCase();
                    boolean korrekteFigurAusgewaehlt = false;
                    for (int o = (activePlayerId * 3); o < (activePlayerId * 3) + 3; o++) {
                        if (input.equals(figurs.get(o).getId())) {
                            korrekteFigurAusgewaehlt = true;
                            gezogen = zieheFigur(figurs.get(o), wuerfelErgebnis, activePlayerId);
                            if (!gezogen) {
                                //Syso in View
                                System.out.println("Zug nicht möglich, bitte erneut auswählen!");
                            }
                        }
                    }
                    if(!korrekteFigurAusgewaehlt)
                        //Syso in View
                        System.out.println("Bitte wählen Sie eine Figur korrekt aus!");
                } while (!gezogen);
            } else {
                //Syso in View, Aufruf des Zugendes in Controller
                System.out.println("Nächster Spieler an der Reihe! \n");
                counter = 0;
            }
        }

    private static int getRandomDiceNumber() {
        return ThreadLocalRandom.current().nextInt(1, 7);
    }

    private boolean zieheFigur(Figur figur, int wuerfelergebnis, int playerId) {
        if(zugMoeglich(figur, wuerfelergebnis, playerId)) {
            int newPosition = (figur.getField().getId() + wuerfelergebnis) % 48;
            if(figur.getField().getId() == -1)
                figur.setField(fields.get(playerId * 12));
            else
                figur.setField(fields.get(newPosition));
            return true;
        }
        return false;
    }

    private boolean alleImHaus(int playerId) {
        System.out.println(playerId);
        for (int p = (playerId * 3); p < (playerId * 3) + 3; p++) {
            if (!(figurs.get(p).getField().getId() == -1))
               return false;
        }
        return true;
    }

    private boolean zugMoeglich(Figur figur, int wuerfelergebnis, int playerId) {

        boolean startfeldBelegt = false;
        if(figur.getField().getId() != -1) {
            for (Figur f: figurs) {
                if((figur.getField().getId() + wuerfelergebnis) % 48 == f.getField().getId()) {
                    //Syso in View
                    System.out.println("Es gibt eine Kollision! Bitte wählen Sie eine andere Figur aus!");
                    return false;
                }
            }
        } else {
            if(wuerfelergebnis != 6) {
                //Syso in View
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
            if (figurs.get(p).getField().getId() == -1)
                mindestensEinerImHaus = true;
        }

        if(mindestensEinerImHaus && wuerfelergebnis == 6 && !(figur.getField().getId() == -1)) {
            if(startfeldBelegt)
                return true;
            //Syso in View
            System.out.println("Bitte wählen Sie eine Figur im Haus aus!");
            return false;
        } else if(mindestensEinerImHaus && wuerfelergebnis == 6 && figur.getField().getId() == -1 && startfeldBelegt) {
            //Syso in View
            System.out.println("Ihr Startfeld ist belegt, bitte wählen Sie eine andere Figur aus!");
            return false;
        }
        return true;
    }
}
