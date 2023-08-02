package tnt.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Starting point of the JavaFX GUI
 */
public class SizeListener implements ChangeListener{


    private final boolean isheight;

    /**
     * Constructor for the sizehandler, this is a listener for changing the window size
     * @param isheight sets the sizehandler handling the height instead of the width
     */

    public SizeListener(boolean isheight){
        this.isheight = isheight;
    }


    /**
     * listener for changing window size
     * @param observableValue the width or height of the window
     * @param o old value of the width / height
     * @param t1 new values of width / height
     */
    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {
        if (isheight) {
            int height = ((int) (double) t1 - 90) / SizeHandler.getNrFieldsY();
            SizeHandler.getInstance().setPrefSizeY(height);
        }
        else {
            int width = ((int) (double) t1 - 60) / (SizeHandler.getNrFieldsX()+1);
            SizeHandler.getInstance().setPrefSizeX(width);
        }
    }
}
