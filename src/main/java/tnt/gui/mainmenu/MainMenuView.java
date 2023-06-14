package tnt.gui.mainmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;

import java.io.IOException;

public class MainMenuView extends VBox {

    @FXML
    private Button playButton;
    private MainMenuController mainMenuController;

    // Todo: Maybe hold the scenebuilder in another class extending FXMLLoader and load every scene with that (than you dont have to call setScenebuilder each time)
    public MainMenuView(SceneHandler sceneHandler, Parent choosePlayerMenu) throws IOException {
        FXMLLoader mainMenuLayout = ResourceHandler.getInstance().getFXML("mainMenu");
        mainMenuLayout.setRoot(this);
        mainMenuLayout.load();
        this.mainMenuController = mainMenuLayout.getController();
        this.mainMenuController.setSceneHandler(sceneHandler);

        setAlignment(Pos.CENTER);
        setSpacing(10);
        Label lblTitle = new Label("Main Menu!");
    }

    public Button getPlayButton() {
        return playButton;
    }

}
