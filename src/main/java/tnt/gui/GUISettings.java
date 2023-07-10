package tnt.gui;

/**
 * Settings of the max and min size of the field
 */
public class GUISettings {
    private static final int maxFieldsize = 150;
    private static final int minFieldsize = 40;
    private static final String defaultTheme = "blood";

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
     * getter for the default theme
     * @return the theme gets set initial
     */
    public static String getDefaultTheme() {
        return defaultTheme;
    }

}
