package tnt.gui.settingsmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.model.Game;
import tnt.model.Player;
import tnt.util.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
