package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import tnt.ResourceHandler;

import java.io.IOException;


public class BuildingLevel1View extends DragableObject {

    public BuildingLevel1View() throws IOException {
        FXMLLoader loader = ResourceHandler.getInstance().getFXML("buildingLevel1View");
        loader.setRoot(this);
        loader.load();
    }
}
