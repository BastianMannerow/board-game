package tnt.gui.mainmenu;

import javafx.fxml.FXML;
import tnt.gui.SceneHandler;



public class MainMenuController{

    private SceneHandler sceneHandler;

    //    @FXML
//    private void initialize(){
//    }
    @FXML
    private void gotoGame() {
        sceneHandler.loadView("playerMenu");
    }
    @FXML
    private void gotoSaveMenu() {
        sceneHandler.loadView("saveMenu");
    }
    @FXML
    private void gotoSettings() {
        sceneHandler.loadView("settings");
    }


    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }
}

//
//public class MainMenuController {

//    public MainMenuController(SceneHandler sceneHandler, Parent playerChooseMenu) {
//
//        this.playerChooseMenu = playerChooseMenu;
//        this.sceneHandler = sceneHandler;
//    }
//
//    public Parent getPlayerChoose() {
//        return this.playerChooseMenu;
//
//    }
//}
