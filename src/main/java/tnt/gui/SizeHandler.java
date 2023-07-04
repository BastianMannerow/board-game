package tnt.gui;

import tnt.util.Observable;
import tnt.model.Settings;
/**
 * Class holding the field size at the moment
 */
public class SizeHandler extends Observable {

    private static SizeHandler instance;
    private static int prefSizeX;
    private static int prefSizeY;
    private static int fieldX;
    private static int fieldY;

    private SizeHandler() {
    }

    /**
     * getter for the only one sizehandler instance
     * @return the instance of the sizehandler
     */

    public static SizeHandler getInstance() {
        if (instance == null) {
            instance = new SizeHandler();
            fieldX = Settings.getFieldSizeX();
            fieldY = Settings.getFieldSizeY();
        }
        return instance;
    }

    /**
     * getter for the field size
     * @return the size a field should become
     */
    public static int getPrefSize(){
        return Math.max(Math.min(Math.min(prefSizeX, prefSizeY), GUISettings.getMaxFieldsize()), GUISettings.getMinFieldsize());
    }

    /**
     * setter for the amount of fields in x direction
     * @param boardX the number of fields: width
     */
    public static void setNrFieldsX(int boardX) {
        fieldX = boardX;
    }

    /**
     * setter for the amount of fields in y direction
     * @param boardY the number of fields: height
     */
    public static void setNrFieldsY(int boardY) {
        fieldY = boardY;
    }

    /**
     * getter for the amount of fields in x direction
     * @return the number of fields: width
     */
    public static int getNrFieldsX() {
        return fieldX;
    }

    /**
     * getter for the amount of fields in y direction
     * @return the number of fields: height
     */
    public static int getNrFieldsY() {
        return fieldY;
    }

    /**
     * setter for the preferred size in x direction
     * @param prefSizeNew the new preferred size for a fields width
     */
    public void setPrefSizeX(int prefSizeNew) {
        prefSizeX = prefSizeNew;
        notifyObservers();
    }

    /**
     * setter for the preferred size in y direction
     * @param prefSizeNew the new preferred size for a fields height
     */
    public void setPrefSizeY(int prefSizeNew) {
        prefSizeY = prefSizeNew;
        notifyObservers();
    }
}
