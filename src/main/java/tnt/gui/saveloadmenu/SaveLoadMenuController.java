package tnt.gui.saveloadmenu;

import javafx.fxml.FXML;
import tnt.gui.SceneHandler;
import tnt.model.FileManager;
import tnt.model.Game;
import tnt.model.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the save load menu
 */
public class SaveLoadMenuController {
    private SceneHandler sceneHandler;
    private FileManager fileManager= new FileManager();



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

    public List<String> loadFiles(){
        return fileManager.getSavedGames();
    }

    @FXML
    private void save(){
        try {
            fileManager.saveGame(Settings.getActualGame());
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Could not save");
        }
    }


    public void load(String save) {
        Game game = new Game(0);
        fileManager.loadGame(save, Settings.getActualGame());
        Settings.setActualGame(game);
    }
}
