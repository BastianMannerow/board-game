package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;


import java.io.IOException;

public class EndView extends VBox {

    private GameController controller;
    /**
     * Constructor for the view
     * @param sceneHandler the scenehandler holding all the scenes
     * @throws IOException Exception, when fxml file could not get loaded (the file of the scenebuilder)
     */
    public EndView(SceneHandler sceneHandler) throws IOException {
        FXMLLoader EndScreen = ResourceHandler.getInstance().getFXML("End");
        EndScreen.setRoot(this);
        EndScreen.load();
        sceneHandler.add("End", this);

    }


}
