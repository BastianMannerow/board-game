package tnt.model;

import tnt.remote.NetworkHandler;

public class Settings {
    static int fieldSizeX = 5;
    static int fieldSizeY = 5;
    static int defaultPlayer = 2;

    static NetworkHandler networkHandler = new NetworkHandler();

    static Game actualGame;

    public static int getDefaultPlayer() {
        return defaultPlayer;
    }
    public static int getFieldSizeX() {
        return fieldSizeX;
    }

    public static int getFieldSizeY() {
        return fieldSizeY;
    }

    public static Game getActualGame() {
        return actualGame;
    }

    public static void setActualGame(Game actualGame) {
        Settings.actualGame = actualGame;
    }

    public static NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

}
