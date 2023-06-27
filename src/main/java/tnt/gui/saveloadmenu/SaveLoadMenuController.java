package tnt.gui.saveloadmenu;

import javafx.fxml.FXML;
import tnt.gui.SceneHandler;
import tnt.model.FileManager;
import tnt.model.Game;

import java.util.ArrayList;

public class SaveLoadMenuController {
    private SceneHandler sceneHandler;
    private FileManager fileManager= new FileManager();
    private Game game;

    @FXML
    private void goToMenu() {
        sceneHandler.loadView("mainMenu");
    }

    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }

    public ArrayList<String> loadFiles(){
        return fileManager.getSavedGames();

    }

    public void setGame(Game game){
        this.game=game;
    }

    public void Save(){
        fileManager.saveGame(game);
    }

}
