package tnt.model;

import tnt.remote.NetworkHandler;

/**
 * Class for holding the actual settings and default settings
 */
public class Settings {
    public enum RemoteMode {
        SERVER,
        CLIENT
    }
    private static int fieldSizeX = 5;
    private static int fieldSizeY = 5;
    private static int defaultPlayer = 2;
    private static int maxStepUp = 1;
    private static int maxStepDown = -1; // Negative numbers allow all step height steps
    private static int maxBuildingLevel = 3;


    private static int maxFieldcount = 500;
    private static RemoteMode remoteMode = RemoteMode.SERVER;
    static NetworkHandler networkHandler = new NetworkHandler();

    static Game actualGame;

    /**
     * getter for the default number of player
     * @return the default number of player
     */
    public static int getDefaultPlayer() {
        return defaultPlayer;
    }

    /**
     * getter for the default number of board in x direction
     * @return the boards width
     */
    public static int getFieldSizeX() {
        return fieldSizeX;
    }

    /**
     * getter for the default number of board in y direction
     * @return the boards height
     */
    public static int getFieldSizeY() {
        return fieldSizeY;
    }

    /**
     * getter for the actual game
     * @return the game running right now
     */
    public static Game getActualGame() {
        return actualGame;
    }

    /**
     * setter for the acutal game
     * @param actualGame the game that should be run now
     */
    public static void setActualGame(Game actualGame) {
        Settings.actualGame = actualGame;
        if (isServerMode()){
            networkHandler.sendGame(actualGame);
        }
    }

    /**
     * getter for the network manager
     * @return the network manager
     */
    public static NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    /**
     * checks if this application runs in server mode (instead of client mode)
     * @return wether this is the server instance
     */
    public static boolean isServerMode() {
        return remoteMode == RemoteMode.SERVER;
    }

    /**
     * setter for the mode of this instance
     * @param serverMode if this instance should be a server (instead of client)
     */
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

    public static int getMaxFieldcount() {
        return maxFieldcount;
    }

    public static int getMaxStepUp() {
        return maxStepUp;
    }

    public static int getMaxStepDown() {
        if (maxStepDown < 0){
            return maxBuildingLevel;
        }
        return maxStepDown;
    }

    public static int getMaxBuildingLevel() {
        return maxBuildingLevel;
    }
}
