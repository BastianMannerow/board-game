package tnt.model;

import tnt.remote.NetworkHandler;

/**
 * Class for holding the actual settings and default settings
 */
public class Settings {

    /**
     * Representation for this instance network type: server or client
     */
    public enum RemoteMode {
        SERVER,
        CLIENT
    }
//    private static int fieldSizeX = 5;
//    private static int fieldSizeY = 5;
    private static int[] fieldSize = {5, 5};
    private static int defaultPlayer = 2;
    private static int maxStepUp = 1;
    private static int maxStepDown = -1; // Negative numbers allow all step height steps
    private static int victoryHeight = 3;


    private static int maxFieldcount = 500;
    private static RemoteMode remoteMode = RemoteMode.SERVER;
    static NetworkHandler networkHandler = new NetworkHandler();


    private static int[] defaultNrTiles = {18, 22, 20, 14};
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
        return fieldSize[0];
    }

    /**
     * getter for the default number of board in y direction
     * @return the boards height
     */
    public static int getFieldSizeY() {
        return fieldSize[1];
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

    /**
     * makes this instance to be a client and not a server
     */
    public static void setClientMode() {
        Settings.remoteMode = RemoteMode.CLIENT;
    }


    /**
     * returns the maximum number of fields for a board
     * @return the maximum number of fields to get set
     */
    public static int getMaxFieldcount() {
        return maxFieldcount;
    }

    /**
     * getter for the default maximum available level to go up
     * @return the maximum number of level to got up
     */
    public static int getMaxStepUp() {
        return maxStepUp;
    }

    /**
     * getter for the default maximum available level to go down
     * @return the maximum number of level to got down
     */
    public static int getMaxStepDown() {
        if (maxStepDown < 0){
            return victoryHeight;
        }
        return maxStepDown;
    }

    /**
     * getter for the default victory height
     * @return the values of the default highest building level (except the dome)
     */
    public static int getVictoryHeight() {
        return victoryHeight;
    }

    /**
     * getter for the default number of tiles available
     * @param i the building level for getting the number of tiles (dome = 0)
     * @return the default available number of tiles for given building level
     */
    public static int getNrOfTile(int i){
        if (i >= defaultNrTiles.length || i < 0){
            return 0;
        }
        return defaultNrTiles[i];
    }
}
