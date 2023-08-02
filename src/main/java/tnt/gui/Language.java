package tnt.gui;

import tnt.model.Player;
import tnt.util.Observable;

import java.util.Locale;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Class for handling multiple languages
 */
public class Language extends Observable {

    private static Map<String, String> actualLanguage;
    private static Map<String, String> english = Map.ofEntries(
            entry("TeamNameEnd"," Team name: "),
            entry("AmountOfTurns"," Amount of turns: "),
            entry("LevelOfIntelligence"," Playertype: "),
            entry("NameEnd","Name: "),
            entry("getDefaultLabel", "Please choose default config:"),
            entry("getStartServerLabel", "start server"),
            entry("getConnectAsClientLabel", "connect as client"),
            entry("gotoGame", "goto game"),
            entry("newGame", "start new game"),
            entry("loadSaveMenu", "load and save"),
            entry("settingsButton", "settings"),
            entry("language", "language"),
            entry("languageGerman", "german"),
            entry("languageEnglish", "english"),
            entry("theme", "theme"),
            entry("themeBlood", "blood"),
            entry("themeZombie", "zombies"),
            entry("themeDefault", "default"),
            entry("mainMenuLabel", "main menu"),
            entry("addPlayerLabel", "add a player"),
            entry("maxStepUpLabel", "max height for step up"),
            entry("maxStepDownLabel", "max height for step down"),
            entry("victoryHeightLabel", "height for victory"),
            entry("boardSizeLabel", "board size"),
            entry("sphereWorldLabel", "enable sphere world"),
            entry("setNrOfFiguresLabel", "set nr of figures for all players"),
            entry("setNrOfFiguresButtonLabel", "set amount of figures"),
            entry("playButtonLabel", "play"),
            entry("player", "player"),
            entry("nameLabel", "name"),
            entry("amountFiguresLabel", "amount of figures"),
            entry("colorLabel", "color"),
            entry("teamLabel", "team"),
            entry("playerTypeLabel", "player type"),
            entry("playerMenuLabel", "player menu"),
            entry("human_label", "human"),
            entry("domeLabel", "dome"),
            entry("buildingLevelLabel", "building level"),
            entry("seperateLabel", "use global pool for\nbuilding levels\nfor all player"),
            entry("", "")
    );
    private static Map<String, String> german = Map.ofEntries(
            entry("TeamNameEnd"," Team Name: "),
            entry("AmountOfTurns"," Spielzuganzahl: "),
            entry("LevelOfIntelligence"," Spielertyp: "),
            entry("NameEnd","Name: "),
            entry("getDefaultLabel", "Wählen Sie bitte ein Preset:"),
            entry("getStartServerLabel", "Starte Server"),
            entry("getConnectAsClientLabel", "Verbinde zu Server"),
            entry("gotoGame", "Zum Spiel"),
            entry("newGame", "Starte neues Spiel"),
            entry("loadSaveMenu", "Laden und speichern"),
            entry("settingsButton", "Einstellungen"),
            entry("language", "Sprache"),
            entry("languageGerman", "Deutsch"),
            entry("languageEnglish", "Englisch"),
            entry("theme", "Thema"),
            entry("themeBlood", "Blut"),
            entry("themeZombie", "Zombies"),
            entry("themeDefault", "Standard"),
            entry("mainMenuLabel", "Hauptmenü"),
            entry("addPlayerLabel", "Spieler hinzufügen"),
            entry("maxStepUpLabel", "Max. Anzahl Stufen hochsteigen"),
            entry("maxStepDownLabel", "Max. Anzahl Stufen runtersteigen"),
            entry("victoryHeightLabel", "Sieghöhe"),
            entry("boardSizeLabel", "Spielfeldgröße"),
            entry("sphereWorldLabel", "Aktive Welt als Kugel"),
            entry("setNrOfFiguresLabel", "Setze Figurenanzahl aller Spieler"),
            entry("setNrOfFiguresButtonLabel", "Setze Figurenanzahl"),
            entry("playButtonLabel", "Spiel starten"),
            entry("player", "Spieler"),
            entry("nameLabel", "Name"),
            entry("amountFiguresLabel", "Anzahl Figuren"),
            entry("colorLabel", "Farbe"),
            entry("teamLabel", "Team"),
            entry("playerTypeLabel", "Art des Spielers"),
            entry("playerMenuLabel", "Spieler Menü"),
            entry("human_label", "Mensch"),
            entry("domeLabel", "Kuppel"),
            entry("buildingLevelLabel", "Stockwerk"),
            entry("seperateLabel", "Globaler Pool\nfür Stockwerke\nfür alle Spieler"),
            entry("", "")
    );

    private static Language instance;

    /**
     * get the translation of the player type
     * @param playerType the player type to be translated
     * @return the string representation of the player type
     */
    public static String playerType(Player.PlayerType playerType) {
        StringBuilder str = new StringBuilder();
        if (Locale.getDefault().equals(Locale.GERMAN)) {
            str.append("KI");
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
                return getTranslation("human_label");
        }
        return str.toString();
    }

    private static String difficulty(int i) {
        if (Locale.getDefault().equals(Locale.GERMAN)) {
            switch (i){
                case 1:
                    return "Einfach";
                case 2:
                    return "Mittel";
                case 3:
                    return "Schwer";
                default:
                    return "Super Schwer";
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


    /**
     * getter for the actual language set
     * @return the acutal language
     */
    public static Locale getActualLanguage() {
        return Locale.getDefault();
    }

    /**
     * Getter for the only one instance of the language handler
     * @return the only one resource handler
     */
    public static Language getInstance() {
        if (instance == null) {
            instance = new Language();
            actualLanguage = english;
        }
        return instance;
    }

    /**
     * Setter for the actual language
     * @param language the language to be set
     */
    public void setLanguage(Locale language) {
        Locale.setDefault(language);
        if (language.equals(Locale.GERMAN)) {
            actualLanguage = german;
        }
        else {
            actualLanguage = english;
        }
        notifyObservers();
    }

    /**
     * get words for the actual language of the given label
     * @param word the label name
     * @return the chars the label should become
     */
    public static String getTranslation(String word){
        if (!actualLanguage.containsKey(word)){
            return "Language for that label not available";
        }
        return actualLanguage.get(word);
    }

}
