package tnt.gui.saveloadmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * menu for saving and loading games
 */
public class SaveLoadMenuView extends VBox{

    @FXML
    private SaveLoadMenuController SaveLoadMenuController;
    private Map<Button,String> saveHolder=new HashMap<Button,String>();

    /**
     * Constructor for the save load menu
     * @param sceneHandler the scenehandler holding all the scenes
     * @throws IOException Exception when the fxml file has an error / does not exist
     */
    public SaveLoadMenuView(SceneHandler sceneHandler) throws IOException {
        FXMLLoader SaveLoadMenuLayout = ResourceHandler.getInstance().getFXML("saveLoadMenu");
        SaveLoadMenuLayout.setRoot(this);
        SaveLoadMenuLayout.load();
        this.SaveLoadMenuController = SaveLoadMenuLayout.getController();
        this.SaveLoadMenuController.setSceneHandler(sceneHandler);
        sceneHandler.add("SaveLoad", this);
        this.getSaves();
    }

    private void getSaves(){
        List<String> saves =SaveLoadMenuController.loadFiles();
        for (String save: saves) {
             Label label= new Label();
             label.setText(save);
             Button loadButton = new Button("load");
             loadButton.setOnMouseClicked(event-> {
                 Button button = (Button) event.getSource();
                 SaveLoadMenuController.load(saveHolder.get(button));
             });
             saveHolder.put(loadButton,save);
             HBox saved = new HBox();
             saved.getChildren().addAll(label,loadButton);
             ((VBox)((HBox)this.getChildren().get(0)).getChildren().get(0)).getChildren().add(saved);
       }
    }
}
