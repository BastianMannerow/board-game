package tnt.gui.saveloadmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.gui.mainmenu.MainMenuController;
import tnt.gui.saveloadmenu.SaveLoadMenuController;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static javafx.scene.layout.StackPane.setAlignment;

public class SaveLoadMenuView extends VBox{

    @FXML
    private SaveLoadMenuController SaveLoadMenuController;

    public SaveLoadMenuView(SceneHandler sceneHandler) throws IOException {
        FXMLLoader SaveLoadMenuLayout = ResourceHandler.getInstance().getFXML("saveLoadMenu");
        SaveLoadMenuLayout.setRoot(this);
        SaveLoadMenuLayout.load();
        this.SaveLoadMenuController = SaveLoadMenuLayout.getController();
        this.SaveLoadMenuController.setSceneHandler(sceneHandler);
        sceneHandler.add("SaveLoad", this);
        Label lblTitle = new Label("SaveLoad");
    }
}
