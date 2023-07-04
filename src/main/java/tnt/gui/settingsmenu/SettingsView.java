package tnt.gui.settingsmenu;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.util.Observer;
import java.io.IOException;

/**
 * The view of the settings menu
 */
public class SettingsView extends VBox implements Observer {

    /**
     * Constructor for the view
     * @param sceneHandler the scenehandler holding all the scenes
     * @throws IOException Exception, when fxml file could not get loaded (the file of the scenebuilder)
     */
    public SettingsView(SceneHandler sceneHandler) throws IOException {
        FXMLLoader settingsMenu = ResourceHandler.getInstance().getFXML("settings");
        settingsMenu.setRoot(this);
        settingsMenu.load();
        SettingsController controller = settingsMenu.getController();
        controller.setSceneHandler(sceneHandler);
        sceneHandler.add("settings", this);
        update();
    }

    @Override
    public void update() {
    }
}
