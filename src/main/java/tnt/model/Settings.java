package tnt.model;

public class Settings {
    static int fieldSizeX = 5;
    static int fieldSizeY = 5;
    static int defaultPlayer = 2;

    public static int getDefaultPlayer() {
        return defaultPlayer;
    }
    public static int getFieldSizeX() {
        return fieldSizeX;
    }

    public static int getFieldSizeY() {
        return fieldSizeY;
    }

}