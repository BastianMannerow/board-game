package tnt.gui.mainmenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import tnt.gui.SceneHandler;
import tnt.gui.playerchoosemenu.PlayerChooseView;
import tnt.model.FileManager;
import tnt.model.Game;
import tnt.model.Settings;

import java.io.IOException;
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
        defaultConfig.setValue("2 Spieler");
        defaultConfig.setItems(defaults);
    }
    /**
     * method to get called when goto game is pressed
     */
    @FXML
    private void gotoGame() {
        if (Settings.getActualGame() == null) {
            // generating the game
            String defaultConf = (String) defaultConfig.getValue();
            int defaultNrPlayer = 2;
            switch (defaultConf) {
                case "2 Spieler":
                    defaultNrPlayer = 2;
                    break;
                case "3 Spieler":
                    defaultNrPlayer = 3;
                    break;
                case "4 Spieler":
                    defaultNrPlayer = 4;
                    break;
                default:
                    defaultNrPlayer = 2;
                    break;
            }
            Game game = new Game(defaultNrPlayer);
            Settings.setActualGame(game);
            game.setGameName("newGame");
        }
        sceneHandler.loadView("playerMenu");
    }

    @FXML
    private void newGame() {
        Settings.setActualGame(null);
        System.out.println(Settings.getActualGame());
        gotoGame();
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
        Settings.getNetworkHandler().listen();
        System.out.println("startet the server");
    }

    /**
     * method to get called when connect to server is pressed
     */
    @FXML
    private void connectToServer() {
        System.out.println("about to connect as client to server");
//        Settings.getNetworkHandler().startClient("localhost");
        Settings.getNetworkHandler().askForHost().ifPresent(Settings.getNetworkHandler()::startClient);
//        Settings.setClientMode();
        System.out.println("connected to server");
    }

    @FXML
    private void sendToPartner() {
//        networkHandler.sendMsg("Hello\nabc");
        FileManager fileManager = new FileManager();
        String data = FileManager.makeString(fileManager.getGameData(Settings.getActualGame()));
        Settings.getNetworkHandler().sendMsg("game" + data);

    }

    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }
}
