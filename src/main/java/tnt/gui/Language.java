package tnt.gui;

import tnt.model.Settings;
import tnt.util.Observable;

import java.util.Locale;

import static java.util.Locale.*;

/**
 * Class for handling multiple languages
 */
public class Language extends Observable {

    public static String getDefaultLabel() {
        if (language.equals(GERMAN)) {
            return "Wählen Sie bitte ein Preset:";
        }
        return "Please choose default config:";
    }

    public static String getStartServerLabel() {
        if (language.equals(GERMAN)) {
            return "Starte Server";
        }
        return "start server";
    }

    public static String getConnectAsClientLabel() {
        if (language.equals(GERMAN)) {
            return "Verbinde zu Server";
        }
        return "connect as client";
    }

    public static String gotoGame() {
        if (language.equals(GERMAN)) {
            return "Zum Spiel";
        }
        return "goto game";
    }

    public static String newGame() {
        if (language.equals(GERMAN)) {
            return "Starte neues Spiel";
        }
        return "start new game";
    }

    public static String loadSaveMenu() {
        if (language.equals(GERMAN)) {
            return "Laden und speichern";
        }
        return "load and save";
    }

    public static String settingsButton() {
        if (language.equals(GERMAN)) {
            return "Einstellungen";
        }
        return "settings";
    }

    public static String language() {
        if (language.equals(GERMAN)) {
            return "Sprache";
        }
        return "language";
    }

    public static String languageGerman() {
        if (language.equals(GERMAN)) {
            return "Deutsch";
        }
        return "german";
    }

    public static String languageEnglish() {
        if (language.equals(GERMAN)) {
            return "Englisch";
        }
        return "english";
    }

    public static String theme() {
        if (language.equals(GERMAN)) {
            return "Thema";
        }
        return "theme";
    }

    public static String themeBlood() {
        if (language.equals(GERMAN)) {
            return "Blut";
        }
        return "blood";
    }

    public static String themeZombie() {
        if (language.equals(GERMAN)) {
            return "Zombies";
        }
        return "zombies";
    }

    public static String mainMenu() {
        if (language.equals(GERMAN)) {
            return "Hauptmenü";
        }
        return "main menu";
    }

    public static String addPlayer() {
        if (language.equals(GERMAN)) {
            return "Spieler hinzufügen";
        }
        return "add a player";
    }

    public static String maxStepUp() {
        if (language.equals(GERMAN)) {
            return "Max. Anzahl Stufen hochsteigen";
        }
        return "max height for step up";
    }

    public static String maxStepDown() {
        if (language.equals(GERMAN)) {
            return "Max. Anzahl Stufen runtersteigen";
        }
        return "max height for step down";
    }

    public static String maxHeightOfBuilding() {
        if (language.equals(GERMAN)) {
            return "Max. Gebäude Höhe";
        }
        return "max height for buildings";
    }

    public static String boardSize() {
        if (language.equals(GERMAN)) {
            return "Spielfeldgröße";
        }
        return "board size";
    }

    public static String sphereWorld() {
        if (language.equals(GERMAN)) {
            return "Aktive Welt als Kugel";
        }
        return "enable sphere world";
    }

    public static String setNrOfFiguresLabel() {
        if (language.equals(GERMAN)) {
            return "Setze Figurenanzahl aller Spieler";
        }
        return "set nr of figures for all players";
    }

    public static String setNrOfFiguresButton() {
        if (language.equals(GERMAN)) {
            return "Setze Figurenanzahl";
        }
        return "set amount of figures";
    }

    public static String playButton() {
        if (language.equals(GERMAN)) {
            return "Spiel Starten";
        }
        return "play";
    }

    public static String player() {
        if (language.equals(GERMAN)) {
            return "Spieler";
        }
        return "player";
    }

    private static Locale language = GERMAN;
    private static Language instance;


    /**
     * getter for the actual language set
     * @return the acutal language
     */
    public static Locale getLanguage() {
        return language;
    }

    /**
     * Getter for the only one instance of the language handler
     * @return the only one resource handler
     */
    public static Language getInstance() {
        if (instance == null) {
            instance = new Language();
        }
        return instance;
    }

    /**
     * Setter for the actual language
     * @param language the language to be set
     */
    public void setLanguage(Locale language) {
        Language.language = language;
        Locale.setDefault(language);
        notifyObservers();
    }

}
