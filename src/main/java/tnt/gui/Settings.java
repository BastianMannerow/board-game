package tnt.gui;

public class Settings {

    static int fieldSizeX = 5;
    static int fieldSizeY = 5;
    static int maxFieldsize = 100;
    static int minFieldsize = 40;
    static int defaultPlayer = 2;
    public static int maxFieldcount = 1000;


    public static int getFieldSizeX() {
        return fieldSizeX;
    }

    public static int getFieldSizeY() {
        return fieldSizeY;
    }

    public static int getMaxFieldsize() {
        return maxFieldsize;
    }

    public static int getMinFieldsize() {
        return minFieldsize;
    }

    public static int getDefaultPlayer() {
        return defaultPlayer;
    }
}
