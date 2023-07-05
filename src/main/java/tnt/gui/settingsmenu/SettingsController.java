package tnt.gui.settingsmenu;

import javafx.fxml.FXML;
import tnt.gui.Language;
import tnt.gui.SceneHandler;

/**
 * The controller for the settings view
 */
public class SettingsController {

    private SceneHandler sceneHandler;

    /**
     * The method getting called, when user pressed the back to main menu button
     */
    @FXML
    private void gotoMainMenu() {
        sceneHandler.loadView("mainMenu");
    }

    @FXML
    private void chooseEnglish(){
        Language.getInstance().setLanguage(Language.Languages.ENGLISH);
    }
    @FXML
    private void chooseGerman(){
        Language.getInstance().setLanguage(Language.Languages.GERMAN);
    }
    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }

}
