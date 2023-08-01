package tnt.gui;

import tnt.util.Observable;

/**
 * Settings of the max and min size of the field
 */
public class GUISettings extends Observable {

    /**
     * representation for different themes
     */
    private enum Theme {
        DEFAULT,
        HORROR_1,
        HORROR_2
    }

    private static GUISettings instance = new GUISettings();
    private static Theme theme = Theme.DEFAULT;
    private static final int maxFieldsize = 150;
    private static final int minFieldsize = 40;

    /**
     * getter for the max size of a single field
     * @return the max size of a field
     */
    public static int getMaxFieldsize() {
        return maxFieldsize;
    }

    /**
     * getter for the min size of a single field
     * @return the min size of a field
     */
    public static int getMinFieldsize() {
        return minFieldsize;
    }


    /**
     * getter for the actual theme with string representation
     * @return the prefix of the current theme
     */
    public static String getTheme() {
        switch (theme){
            case HORROR_1:
                return "Horrortheme_";
            case HORROR_2:
                return "Horrortheme2_";
            default:
                return "";
        }
    }

    /**
     * setter for the actual theme
     * @param themeIn the string representation for the theme to be set
     */
    public void setTheme(String themeIn) {
        if (themeIn.equals("Horror1")){
            theme = Theme.HORROR_1;
        }
        else if (themeIn.equals("Horror2")){
            theme = Theme.HORROR_2;
        }
        else {
            theme = Theme.DEFAULT;
        }
        notifyObservers();
    }

    /**
     * The getter for the instance of these settings
     * @return the instance of the GUISettings
     */
    public static GUISettings getInstance() {
        if (instance == null){
            instance = new GUISettings();
        }
        return instance;
    }
}
