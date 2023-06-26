package tnt.gui;

public class Settings {
    public static int getFieldSizeX() {
        return fieldSizeX;
    }

    public static int getFieldSizeY() {
        return fieldSizeY;
    }

    public static int getMaxFieldsize() {
        return maxFieldsize;
    }

    static int fieldSizeX = 5;
    static int fieldSizeY = 5;
    static int maxFieldsize = 100;
    static int minFieldsize = 40;

    public static int getMinFieldsize() {
        return minFieldsize;
    }
}
