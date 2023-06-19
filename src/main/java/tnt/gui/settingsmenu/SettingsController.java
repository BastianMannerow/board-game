package tnt.gui.settingsmenu;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tnt.gui.SceneHandler;
import tnt.model.Game;
import tnt.model.Player;


public class SettingsController {

    private SceneHandler sceneHandler;
    @FXML
    private void gotoMainMenu() {
        sceneHandler.loadView("mainMenu");
    }
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }

}
