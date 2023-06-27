package tnt.gui.saveloadmenu;

import javafx.fxml.FXML;
import tnt.gui.SceneHandler;

public class SaveLoadMenuController {
    private SceneHandler sceneHandler;


    @FXML
    private void goToMenu() {
        sceneHandler.loadView("mainMenu");
    }

    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }
}
