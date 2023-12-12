package de.inform.hsh.layers.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

import de.inform.hsh.layers.model.Antwort;
import de.inform.hsh.layers.model.Frage;
import de.inform.hsh.layers.model.Spieler;
import de.inform.hsh.layers.utils.QuizFacade;

/**
 * Diese Klasse implementiert die Logik des Spiels.
 * 
 * @author thorb
 * 
 */
public class QuizController implements QuizFacade {
	// Singleton
	private static volatile QuizFacade instance;
	// Aktuelle Zeile zum Einlesen der Frage
	private int currentLine = 0;
	// Aktuelle Frage + Antworten
	private Frage frage;
	// Objekt zum halten der Spielerinformationen
	private Spieler spieler;
	// Dateipfad zum setzen des Schwierigkeitsgrades
	private String dateiPfad;
	private ArrayList<String> zeilen = new ArrayList<>();

	/**
	 * Konstruktor zum erstellen des QuizControllers
	 * 
	 * @param Spieler spieler2
	 */
	private QuizController(Spieler spieler2) {
		this.spieler = spieler2;
	}

	/**
	 * Anlegen bzw. return des QuizControllers als Singleton
	 * 
	 * @return QuizFacade
	 */
	public static synchronized QuizFacade getInstance() {
		if (instance == null)
			instance = new QuizController(Spieler.getInstance());
		return instance;
	}

	/**
	 * Methode zum setzen des Dateipfads
	 * 
	 * @param String level
	 */
	public void chooseFragenKatalog(String level) {
		if (level.equals("Leicht")) {
			dateiPfad = "techmaster-fragentestleicht";
		} else if (level.equals("Mittel")) {
			dateiPfad = "techmaster-fragentestmittel";
		} else {
			dateiPfad = "techmaster-fragentestschwer";
		}
	}
	
	/**
	 * Gibt die nächste Frage zurück
	 * 
	 * @return Frage
	 */
	public Frage getFrage() {
		try {
	        String zeile;
	        if (zeilen.isEmpty()) {
	            // Lade die Ressource über den ClassLoader
	            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dateiPfad);
	            if (inputStream != null) {
	                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
	                BufferedReader bufferedReader = new BufferedReader(streamReader);
	                // Alle Zeilen in eine ArrayList einlesen
	                while ((zeile = bufferedReader.readLine()) != null) {
	                    zeilen.add(zeile);
	                    System.setOut(new PrintStream(System.out, true, "UTF-8"));
	                    System.out.println(zeile);
	                }
	                bufferedReader.close();
	            }
        		// Zeilen zufällig durchmischen
        		Collections.shuffle(zeilen );
        	}
        	// Die erste zufällige Zeile auswählen
        	if(currentLine == zeilen.size()) {
        		return new Frage(new ArrayList<>(),"");
        	}
        	zeile = zeilen.get(currentLine);
        	currentLine++;
            int fragezeichenIndex = zeile.indexOf('?');
            if (fragezeichenIndex != -1) {
                String teilVorFragezeichen = zeile.substring(0, fragezeichenIndex + 1);
                String teilNachFragezeichen = zeile.substring(fragezeichenIndex + 1);
                String[] antworten = teilNachFragezeichen.split(";");
                ArrayList<Antwort> antwortenList = new ArrayList<>();
                for (String antwort : antworten) {
                    String[] teile = antwort.split(",");
                    Boolean zustand = false;
                    if (teile[1].equals("R")) {
                        zustand = true;
                    }
                    Antwort cAntwort = new Antwort(teile[0], zustand);
                    antwortenList.add(cAntwort);
                }
                frage = new Frage(antwortenList, teilVorFragezeichen);
                return frage;
            } else {
                System.out.println("Error: Keine Frage gefunden!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	/**
	 * überprüft die Antwort auf Korrektheit und ändert die Spieler Informationen
	 * dementsprechend
	 * 
	 * @return boolean
	 * @param String antwort
	 */
	public boolean proofAntwort(String a) {
		for (Antwort antwort : frage.getA()) {
			if (antwort.getA().equals(a)) {
				if (antwort.isK()) {
					spieler.addPunkte();
					return true;
				} else {
					if (spieler.getLeben() > 0) {
						spieler.removeLeben();
						spieler.removeRiar();
					}
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Überprüft ob bereits ein Joker genutzt wurde oder nutzt den Joker und ändert
	 * die Spieler Informationen.
	 * 
	 * @return boolean
	 * @param Frage frage
	 */
	public boolean useJoker(Frage frage) {
		if (!frage.getJokerUsed() && spieler.getJoker() > 0) {
			frage.jokerUsed();
			spieler.useJoker();
			return false;
		}
		return true;
	}

	/**
	 * @return Spieler
	 */
	public Spieler getSpieler() {
		return spieler;
	}

	/**
	 * Setzt die Spieler Informationen zurück sowie die Fragen
	 */
	public void restart() {
		currentLine = 0;
		spieler.setJoker(3);
		spieler.setLeben(3);
		spieler.setPunkte(0);
	}
}
