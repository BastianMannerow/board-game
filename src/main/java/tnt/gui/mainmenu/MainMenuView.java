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

    @Override
    public void update() {
        mainMenuController.defaultlabel.setText(Language.getDefaultLabel());
        mainMenuController.playButton.setText(Language.gotoGame());
        mainMenuController.newGameButton.setText(Language.newGame());
        mainMenuController.serverButton.setText(Language.getStartServerLabel());
        mainMenuController.connectButton.setText(Language.getConnectAsClientLabel());
        mainMenuController.loadSaveButton.setText(Language.loadSaveMenu());
        mainMenuController.settingsButton.setText(Language.settingsButton());
        mainMenuController.defaultConfig.setItems(FXCollections.observableArrayList()); // set emtpy list
        mainMenuController.defaultConfig.setItems(mainMenuController.defaultConfigs);
        mainMenuController.defaultConfig.getSelectionModel().selectFirst();
    }
}
