package tnt.gui.mainmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;

import java.io.IOException;

/**
 * The view for the main menu
 */
public class MainMenuView extends VBox {

    private MainMenuController mainMenuController;

    // Todo: Maybe hold the scenebuilder in another class extending FXMLLoader and load every scene with that (than you dont have to call setScenebuilder each time)

    /**
     * Constructor for the main menu view
     * @param sceneHandler the scene handler holding all scenes
     * @throws IOException Exception when the fxml file has an error / does not exist
     */
    public MainMenuView(SceneHandler sceneHandler) throws IOException {
        FXMLLoader mainMenuLayout = ResourceHandler.getInstance().getFXML("mainMenu");
        mainMenuLayout.setRoot(this);
        mainMenuLayout.load();
        this.mainMenuController = mainMenuLayout.getController();
        this.mainMenuController.setSceneHandler(sceneHandler);
        sceneHandler.add("mainMenu", this);
        setAlignment(Pos.CENTER);
        setSpacing(10);
    }
}
