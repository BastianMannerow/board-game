package tnt.gui;

import tnt.util.Observable;
import tnt.model.Settings;
/**
 * Starting point of the JavaFX GUI
 */
public class StaticSizeHandler extends Observable {

    private static StaticSizeHandler instance;
    private static int prefSizeX;
    private static int prefSizeY;
    private static int fieldX;
    private static int fieldY;

    private StaticSizeHandler() {
    }

    public static StaticSizeHandler getInstance() {
        if (instance == null) {
            instance = new StaticSizeHandler();
            fieldX = Settings.getFieldSizeX();
            fieldY = Settings.getFieldSizeY();
        }
        return instance;
    }


    public static int getPrefSize(){
        return Math.min(prefSizeX, prefSizeY);
    }

    public static void setNrFieldsX(int boardX) {
        fieldX = boardX;
    }

    public static void setNrFieldsY(int boardY) {
        fieldY = boardY;
    }

    public static int getNrFieldsX() {
        return fieldX;
    }

    public static int getNrFieldsY() {
        return fieldY;
    }

    public void setPrefSizeX(int prefSizeNew) {
        prefSizeX = Math.max(Math.min(prefSizeNew, GUISettings.getMaxFieldsize()), GUISettings.getMinFieldsize());
        notifyObservers();
    }

    public void setPrefSizeY(int prefSizeNew) {
        prefSizeY = Math.max(Math.min(prefSizeNew, GUISettings.getMaxFieldsize()), GUISettings.getMinFieldsize());
        notifyObservers();
    }
}
