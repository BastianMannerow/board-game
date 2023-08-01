package tnt.gui.mainmenu;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.Language;
import tnt.gui.SceneHandler;
import tnt.util.Observer;
import java.io.IOException;

/**
 * The view for the main menu
 */
public class MainMenuView extends VBox implements Observer {

    private MainMenuController mainMenuController;

    // Todo: Maybe hold the scenebuilder in another class extending FXMLLoader and load every scene with that (than you dont have to call setScenebuilder each time)

    /**
     * Constructor for the main menu view
     * @param sceneHandler the scene handler holding all scenes
     * @throws IOException Exception when the fxml file has an error / does not exist
     */
    public MainMenuView(SceneHandler sceneHandler) throws IOException {
        FXMLLoader mainMenuLayout = ResourceHandler.getInstance().getFXML("mainMenu");
        mainMenuLayout.setRoot(this);
        Language.getInstance().addObserver(this);
        mainMenuLayout.load();
        this.mainMenuController = mainMenuLayout.getController();
        this.mainMenuController.setSceneHandler(sceneHandler);
        sceneHandler.add("mainMenu", this);
        setAlignment(Pos.CENTER);
        setSpacing(10);
        update();
    }

    /**
     * The update Method for Language if the Language button is pressed
     */
    @Override
    public void update() {
        mainMenuController.defaultlabel.setText(Language.getTranslation("getDefaultLabel"));
        mainMenuController.playButton.setText(Language.getTranslation("gotoGame"));
        mainMenuController.newGameButton.setText(Language.getTranslation("newGame"));
        mainMenuController.serverButton.setText(Language.getTranslation("getStartServerLabel"));
        mainMenuController.connectButton.setText(Language.getTranslation("getConnectAsClientLabel"));
        mainMenuController.loadSaveButton.setText(Language.getTranslation("loadSaveMenu"));
        mainMenuController.settingsButton.setText(Language.getTranslation("settingsButton"));
        mainMenuController.defaultConfig.setItems(FXCollections.observableArrayList()); // set emtpy list
        mainMenuController.defaultConfig.setItems(mainMenuController.defaultConfigs);
        mainMenuController.defaultConfig.getSelectionModel().selectFirst();
    }
}
