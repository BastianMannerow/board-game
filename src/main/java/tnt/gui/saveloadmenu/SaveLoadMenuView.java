package tnt.gui.saveloadmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.gui.mainmenu.MainMenuController;
import tnt.gui.saveloadmenu.SaveLoadMenuController;
import javafx.scene.layout.VBox;
import tnt.model.Game;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.layout.StackPane.setAlignment;

public class SaveLoadMenuView extends VBox{

    @FXML
    private SaveLoadMenuController SaveLoadMenuController;
    private Game game;
    private HashMap<Button,String> saveHolder;

    public SaveLoadMenuView(SceneHandler sceneHandler,Game game) throws IOException {
        FXMLLoader SaveLoadMenuLayout = ResourceHandler.getInstance().getFXML("saveLoadMenu");
        SaveLoadMenuLayout.setRoot(this);
        SaveLoadMenuLayout.load();
        this.SaveLoadMenuController = SaveLoadMenuLayout.getController();
        this.SaveLoadMenuController.setSceneHandler(sceneHandler);
        sceneHandler.add("SaveLoad", this);
        Label lblTitle = new Label("SaveLoad");
        this.game =game;
        SaveLoadMenuController.setGame(game);
        this.getSaves();
    }

    private void getSaves(){
        ArrayList<String> saves =SaveLoadMenuController.loadFiles();
        for (String save: saves) {
             Label label= new Label();
             label.setText(save);
             Button loadButton = new Button("load");
             loadButton.setOnMouseClicked(event-> {
                 Button button = (Button) event.getSource();
                 SaveLoadMenuController.Load(saveHolder.get(button));
             });
             saveHolder.put(loadButton,save);
             HBox saved = new HBox();
             saved.getChildren().addAll(label,loadButton);
             ((VBox)((HBox)this.getChildren().get(0)).getChildren().get(0)).getChildren().add(saved);
        }
    }








}
