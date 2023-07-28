package tnt.gui.settingsmenu;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.Language;
import tnt.gui.SceneHandler;
import tnt.util.Observer;
import java.io.IOException;

/**
 * The view of the settings menu
 */
public class SettingsView extends VBox implements Observer {

    private SettingsController controller;
    /**
     * Constructor for the view
     * @param sceneHandler the scenehandler holding all the scenes
     * @throws IOException Exception, when fxml file could not get loaded (the file of the scenebuilder)
     */
    public SettingsView(SceneHandler sceneHandler) throws IOException {
        FXMLLoader settingsMenu = ResourceHandler.getInstance().getFXML("settings");
        settingsMenu.setRoot(this);
        settingsMenu.load();
        controller = settingsMenu.getController();
        controller.setSceneHandler(sceneHandler);
        sceneHandler.add("settings", this);
        Language.getInstance().addObserver(this);
        update();
    }

    @Override
    public void update() {
        controller.language.setText(Language.getTranslation("language"));
        controller.german.setText(Language.getTranslation("languageGerman"));
        controller.english.setText(Language.getTranslation("languageEnglish"));
        controller.theme.setText(Language.getTranslation("theme"));
        controller.blood.setText(Language.getTranslation("themeBlood"));
        controller.zombies.setText(Language.getTranslation("themeZombie"));
        controller.defaultTheme.setText(Language.getTranslation("themeDefault"));
        controller.mainMenu.setText(Language.getTranslation("mainMenuLabel"));
    }
}
