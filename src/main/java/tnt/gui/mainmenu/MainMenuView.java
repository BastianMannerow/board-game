package tnt.gui.mainmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;

import java.io.IOException;

public class MainMenuView extends VBox {

    @FXML
    private Button playButton;

    public MainMenuView(MainMenuController mainMenuController) throws IOException {
        FXMLLoader mainMenuLayout = ResourceHandler.getInstance().getFXML("mainMenu");
        mainMenuLayout.setRoot(this);
        mainMenuLayout.setController(mainMenuController);
        VBox mainMenu = mainMenuLayout.load();

        setAlignment(Pos.CENTER);
        setSpacing(10);
        Label lblTitle = new Label("Main Menu!");
    }

    public Button getPlayButton() {
        return playButton;
    }

}
