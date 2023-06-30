package tnt.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import tnt.util.Observable;

/**
 * Starting point of the JavaFX GUI
 */
public class SizeHandler implements ChangeListener{


    private boolean isheight;

    public SizeHandler(boolean isheight){
        this.isheight = isheight;
    }

    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {


        if (isheight) {
            int height = ((int) (double) t1 - 50) / StaticSizeHandler.getNrFieldsY();
            StaticSizeHandler.getInstance().setPrefSizeY(height);
        }
        else {
            int width = ((int) (double) t1 - 100) / (StaticSizeHandler.getNrFieldsX()+1);
            StaticSizeHandler.getInstance().setPrefSizeX(width);
        }
    }
}
