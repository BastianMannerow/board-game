package tnt.model;

import tnt.remote.NetworkHandler;

public class Settings {
    public enum RemoteMode {
        SERVER,
        CLIENT
    }
    private static int fieldSizeX = 5;
    private static int fieldSizeY = 5;
    private static int defaultPlayer = 2;

    private static RemoteMode remoteMode = RemoteMode.SERVER;
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
        if (isServerMode()){
            networkHandler.sendGame(actualGame);
        }
    }

    public static NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public static boolean isServerMode() {
        return remoteMode == RemoteMode.SERVER;
    }

    public static void setServerMode(boolean serverMode) {
        if (serverMode) {
            Settings.remoteMode = RemoteMode.SERVER;
        } else {
            Settings.remoteMode = RemoteMode.CLIENT;
        }
    }

    public static void setClientMode() {
        Settings.remoteMode = RemoteMode.CLIENT;
    }
}
