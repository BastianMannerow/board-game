package tnt.gui;

import tnt.model.Player;
import tnt.util.Observable;

import java.util.Locale;

import static java.util.Locale.*;

/**
 * Class for handling multiple languages
 */
public class Language extends Observable {



    private static Locale actualLanguage = GERMAN;
    private static Language instance;


    public static String getDefaultLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Wählen Sie bitte ein Preset:";
        }
        return "Please choose default config:";
    }

    public static String getStartServerLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Starte Server";
        }
        return "start server";
    }

    public static String getConnectAsClientLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Verbinde zu Server";
        }
        return "connect as client";
    }

    public static String gotoGame() {
        if (actualLanguage.equals(GERMAN)) {
            return "Zum Spiel";
        }
        return "goto game";
    }

    public static String newGame() {
        if (actualLanguage.equals(GERMAN)) {
            return "Starte neues Spiel";
        }
        return "start new game";
    }

    public static String loadSaveMenu() {
        if (actualLanguage.equals(GERMAN)) {
            return "Laden und speichern";
        }
        return "load and save";
    }

    public static String settingsButton() {
        if (actualLanguage.equals(GERMAN)) {
            return "Einstellungen";
        }
        return "settings";
    }

    public static String language() {
        if (actualLanguage.equals(GERMAN)) {
            return "Sprache";
        }
        return "language";
    }

    public static String languageGerman() {
        if (actualLanguage.equals(GERMAN)) {
            return "Deutsch";
        }
        return "german";
    }

    public static String languageEnglish() {
        if (actualLanguage.equals(GERMAN)) {
            return "Englisch";
        }
        return "english";
    }

    public static String theme() {
        if (actualLanguage.equals(GERMAN)) {
            return "Thema";
        }
        return "theme";
    }

    public static String themeBlood() {
        if (actualLanguage.equals(GERMAN)) {
            return "Blut";
        }
        return "blood";
    }

    public static String themeZombie() {
        if (actualLanguage.equals(GERMAN)) {
            return "Zombies";
        }
        return "zombies";
    }

    public static String mainMenuLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Hauptmenü";
        }
        return "main menu";
    }

    public static String addPlayerLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Spieler hinzufügen";
        }
        return "add a player";
    }

    public static String maxStepUpLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Max. Anzahl Stufen hochsteigen";
        }
        return "max height for step up";
    }

    public static String maxStepDownLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Max. Anzahl Stufen runtersteigen";
        }
        return "max height for step down";
    }

    public static String maxHeightOfBuildingLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Max. Gebäude Höhe";
        }
        return "max height for buildings";
    }

    public static String boardSizeLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Spielfeldgröße";
        }
        return "board size";
    }

    public static String sphereWorldLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Aktive Welt als Kugel";
        }
        return "enable sphere world";
    }

    public static String setNrOfFiguresLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Setze Figurenanzahl aller Spieler";
        }
        return "set nr of figures for all players";
    }

    public static String setNrOfFiguresButtonLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Setze Figurenanzahl";
        }
        return "set amount of figures";
    }

    public static String playButtonLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Spiel starten";
        }
        return "play";
    }

    public static String playerLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Spieler";
        }
        return "player";
    }

    public static String nameLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Name";
        }
        return "name";
    }
    public static String amountFiguresLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Anzahl Figuren";
        }
        return "amount of figures";
    }
    public static String colorLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Farbe";
        }
        return "color";
    }
    public static String teamLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Team";
        }
        return "team";
    }
    public static String playerTypeLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Art des Spielers";
        }
        return "player type";
    }

    public static String playerMenuLabel() {
        if (actualLanguage.equals(GERMAN)) {
            return "Spieler Menü";
        }
        return "player menu";
    }

    public static String playerType(Player.PlayerType playerType) {
        StringBuilder str = new StringBuilder();
        if (actualLanguage.equals(GERMAN)) {
            str.append("KI ");
        } else {
            str.append("AI");
        }
        str.append(" ");
        switch (playerType){
            case AI_1:
                str.append(difficulty(1));
                break;
            case AI_2:
                str.append(difficulty(2));
                break;
            case AI_3:
                str.append(difficulty(3));
                break;
            default:
                return human();
        }
        return str.toString();
    }

    public static String difficulty(int i) {
        if (actualLanguage.equals(GERMAN)) {
            switch (i){
                case 1:
                    return "Einfach";
                case 2:
                    return "Mittel";
                case 3:
                    return "Schwer";
                default:
                    return "Suuper Schwer";
            }
        }
        switch (i){
            case 1:
                return "easy";
            case 2:
                return "medium";
            case 3:
                return "hard";
            default:
                return "super hard";
        }
    }

    public static String human() {
        if (actualLanguage.equals(GERMAN)) {
            return "Mensch";
        }
        return "human";
    }


    /**
     * getter for the actual language set
     * @return the acutal language
     */
    public static Locale getActualLanguage() {
        return actualLanguage;
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
        Language.actualLanguage = language;
        Locale.setDefault(language);
        notifyObservers();
    }

}
