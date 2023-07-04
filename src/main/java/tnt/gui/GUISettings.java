package tnt.gui;

/**
 * Settings of the max and min size of the field
 */
public class GUISettings {
    static int maxFieldsize = 100;
    static int minFieldsize = 40;

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

}
