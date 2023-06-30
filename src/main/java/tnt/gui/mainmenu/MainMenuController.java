package tnt.gui.mainmenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import tnt.gui.SceneHandler;
import tnt.remote.NetworkHandler;

import java.util.ArrayList;


/**
 * The controller for the main menu
 */
public class MainMenuController{

    private SceneHandler sceneHandler;

    @FXML
    private ChoiceBox defaultConfig;


    @FXML
    private void initialize(){
        ArrayList<String> list = new ArrayList<>();
        list.add("2 Spieler");
        list.add("3 Spieler");
        list.add("4 Spieler");
        ObservableList<String> defaults = FXCollections.observableArrayList(list);
        defaultConfig.setItems(defaults);
    }
    /**
     * method to get called when goto game is pressed
     */
    @FXML
    private void gotoGame() {
        sceneHandler.loadView("playerMenu");
    }


    /**
     * method to get called when load/save game is pressed
     */
    @FXML
    private void gotoSaveMenu() {
        sceneHandler.loadView("SaveLoad");
    }


    /**
     * method to get called when settings is pressed
     */
    @FXML
    private void gotoSettings() {
        sceneHandler.loadView("settings");
    }

    /**
     * method to get called when start server is pressed
     */
    @FXML
    private void startServer() {
        System.out.println("about to start the server");
        NetworkHandler networkHandler = new NetworkHandler();
        networkHandler.listen();
        System.out.println("startet start the server");
    }

    /**
     * method to get called when connect to server is pressed
     */
    @FXML
    private void connectToServer() {
        System.out.println("about to connect as client to server");
        NetworkHandler networkHandler = new NetworkHandler();
        networkHandler.startClient("");
        System.out.println("connected to server");
    }

    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }
}
