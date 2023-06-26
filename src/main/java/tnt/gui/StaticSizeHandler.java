package tnt.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import tnt.util.Observable;

import java.util.Set;

/**
 * Starting point of the JavaFX GUI
 */
public class StaticSizeHandler extends Observable {

    private static StaticSizeHandler instance;
    private static int prefSize;
    private static int fieldX;
    private static int fieldY;

    private StaticSizeHandler() {
    }

    public static StaticSizeHandler getInstance() {
        if (instance == null) {
            instance = new StaticSizeHandler();
            fieldX = Settings.fieldSizeX;
            fieldY = Settings.fieldSizeY;
        }
        return instance;
    }


    public static int getPrefSize(){
        return prefSize;
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

    public void setPrefSize(int prefSizeNew) {
        prefSize = Math.max(Math.min(prefSizeNew, Settings.getMaxFieldsize()), Settings.getMinFieldsize());
        notifyObservers();
    }
}
