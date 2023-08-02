package tnt.gui.settingsmenu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tnt.gui.GUISettings;
import tnt.gui.Language;
import tnt.gui.SceneHandler;

import java.util.Locale;

/**
 * The controller for the settings view
 */
public class SettingsController {

    private SceneHandler sceneHandler;

    @FXML
    Button mainMenu;
    @FXML
    Button german;
    @FXML
    Button english;
    @FXML
    Button blood;
    @FXML
    Button zombies;
    @FXML
    Button defaultTheme;
    @FXML
    Label language;
    @FXML
    Label theme;
    /**
     * The method getting called, when user pressed the back to main menu button
     */
    @FXML @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void gotoMainMenu() {
        sceneHandler.loadView("mainMenu");
    }

    /**
     * Swaps the local Language to English
     */
    @FXML @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void chooseEnglish(){
        Language.getInstance().setLanguage(Locale.ENGLISH);
    }

    /**
     * Swaps the local Language to German
     */
    @FXML @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void chooseGerman(){
        Language.getInstance().setLanguage(Locale.GERMAN);
    }

    /**
     * Swaps the theme to Blood Theme
     */
    @FXML @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void chooseThemeBlood(){
//        sceneHandler.loadStyle("blood");
        GUISettings.getInstance().setTheme("Horror1");
    }

    /**
     * Swaps the theme to Zombie Theme
     */
    @FXML @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void chooseThemeZombies(){
//        sceneHandler.loadStyle("zombies");
        GUISettings.getInstance().setTheme("Horror2");
    }

    /**
     * Swaps the theme to Default Theme
     */
    @FXML @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void chooseThemeDefault(){
//        sceneHandler.loadStyle("zombies");
        GUISettings.getInstance().setTheme("");
    }


    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }
}
