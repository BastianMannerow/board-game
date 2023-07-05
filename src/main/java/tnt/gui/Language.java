package tnt.gui;

import tnt.model.Settings;
import tnt.util.Observable;

/**
 * Class for handling multiple languages
 */
public class Language extends Observable {

    public static String getDefaultLabel() {
        switch (language){
            case GERMAN:
                return "Wählen Sie bitte ein Preset:";
            case ENGLISH:
            default:
                return "Please choose default config:";
        }
    }

    public static String getStartServerLabel() {
        switch (language){
            case GERMAN:
                return "Starte Server";
            case ENGLISH:
            default:
                return "start server";
        }
    }

    public static String getConnectAsClientLabel() {
        switch (language){
            case GERMAN:
                return "Verbinde zu Server";
            case ENGLISH:
            default:
                return "connect as client";
        }
    }

    public static String gotoGame() {
        switch (language){
            case GERMAN:
                return "Zum Spiel";
            case ENGLISH:
            default:
                return "goto game";
        }
    }

    public static String newGame() {
        switch (language){
            case GERMAN:
                return "Starte neues Spiel";
            case ENGLISH:
            default:
                return "start new game";
        }
    }

    public static String loadSaveMenu() {
        switch (language){
            case GERMAN:
                return "Laden und speichern";
            case ENGLISH:
            default:
                return "load and save";
        }
    }

    public static String settingsButton() {
        switch (language){
            case GERMAN:
                return "Einstellungen";
            case ENGLISH:
            default:
                return "settings";
        }
    }

    public static String language() {
        switch (language){
            case GERMAN:
                return "Sprache";
            case ENGLISH:
            default:
                return "language";
        }
    }

    public static String languageGerman() {
        switch (language){
            case GERMAN:
                return "Deutsch";
            case ENGLISH:
            default:
                return "german";
        }
    }

    public static String languageEnglish() {
        switch (language){
            case GERMAN:
                return "Englisch";
            case ENGLISH:
            default:
                return "english";
        }
    }

    public static String theme() {
        switch (language){
            case GERMAN:
                return "Thema";
            case ENGLISH:
            default:
                return "theme";
        }
    }

    public static String themeBlood() {
        switch (language){
            case GERMAN:
                return "Blut";
            case ENGLISH:
            default:
                return "blood";
        }
    }

    public static String themeZombie() {
        switch (language){
            case GERMAN:
                return "Zombies";
            case ENGLISH:
            default:
                return "zombies";
        }
    }

    public static String mainMenu() {
        switch (language){
            case GERMAN:
                return "Main Menu";
            case ENGLISH:
            default:
                return "main menu";
        }
    }


    public enum Languages {
        GERMAN,
        ENGLISH
    }
    private static Languages language = Languages.GERMAN;
    private static Language instance;


    /**
     * getter for the actual language set
     * @return the acutal language
     */
    public static Languages getLanguage() {
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
    public void setLanguage(Languages language) {
        Language.language = language;
        notifyObservers();
    }

}
