package tnt.gui.mainmenu;

import javafx.fxml.FXML;
import tnt.gui.SceneHandler;


/**
 * The controller for the main menu
 */
public class MainMenuController{

    private SceneHandler sceneHandler;

    //    @FXML
//    private void initialize(){
//    }

    /**
     * method to get called when goto game is pressed
     */
    @FXML
    private void gotoGame() {
        sceneHandler.loadView("playerMenu");
    }


    /**
     * method to get called when load/save game is pressed
     */
    @FXML
    private void gotoSaveMenu() {
        sceneHandler.loadView("saveMenu");
    }


    /**
     * method to get called when settings is pressed
     */
    @FXML
    private void gotoSettings() {
        sceneHandler.loadView("settings");
    }

    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }
}
