package tnt.gui.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import tnt.gui.SceneHandler;

public class MainMenuController {

    Parent playerChooseMenu;
    Parent mainMenu;
    SceneHandler sceneHandler;
    @FXML
    private HBox wholeMenu;
    @FXML
    private Button gotoGame;

    public MainMenuController(SceneHandler sceneHandler, Parent playerChooseMenu) {

        this.playerChooseMenu = playerChooseMenu;
        this.sceneHandler = sceneHandler;
    }

    @FXML
    private void initialize(){
    }

    @FXML
    private void gotoGame() {
        sceneHandler.getScene().setRoot(playerChooseMenu);
    }
    public Parent getPlayerChoose() {
        return this.playerChooseMenu;

    }

    public void setMainView(MainMenuView mainView) {
        mainMenu = mainView;
        sceneHandler.getScene().setRoot(mainMenu);
    }

//    public void setMainScene(Scene mainScene) {
//        this.sceneHandler.getScene() = mainScene;
//    }

    public void setChoosePlayerMenu(Parent choosePlayerMenu) {
        this.playerChooseMenu = choosePlayerMenu;
    }
}
