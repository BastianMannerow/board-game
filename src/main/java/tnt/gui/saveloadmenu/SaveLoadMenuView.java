package tnt.gui.saveloadmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import javafx.scene.layout.VBox;


import java.io.IOException;
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
        SaveLoadMenuController.setSaveLoadMenuView(this);
        this.getSaves();
    }

    /**
     * Gets the saves of the Games and displays them with their respective loading Button
     */
    public void getSaves(){
        ((VBox)((HBox)this.getChildren().get(0)).getChildren().get(0)).getChildren().clear();
        this.setAlignment(Pos.CENTER);

        ((HBox) this.getChildren().get(0)).setAlignment(Pos.CENTER);
        List<String> saves =SaveLoadMenuController.loadNames();
        for (String save: saves) {
             Label label= new Label();
             label.setText(save);
             label.setPadding(new Insets(0,100,10,50));
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
