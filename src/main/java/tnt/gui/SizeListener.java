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

    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {


        if (isheight) {
            int height = ((int) (double) t1 - 50) / SizeHandler.getNrFieldsY();
            SizeHandler.getInstance().setPrefSizeY(height);
        }
        else {
            int width = ((int) (double) t1 - 100) / (SizeHandler.getNrFieldsX()+1);
            SizeHandler.getInstance().setPrefSizeX(width);
        }
    }
}
