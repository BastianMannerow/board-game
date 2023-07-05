package tnt.gui.mainmenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import tnt.gui.SceneHandler;
import tnt.model.DefaultConfiguration;
import tnt.model.FileManager;
import tnt.model.Game;
import tnt.model.Settings;


/**
 * The controller for the main menu
 */
public class MainMenuController{

    private SceneHandler sceneHandler;
    ObservableList<DefaultConfiguration> defaultConfigs;
    @FXML
    ChoiceBox defaultConfig;

    @FXML
    Label defaultlabel;
    @FXML
    Button playButton;
    @FXML
    Button newGameButton;
    @FXML
    Button loadSaveButton;
    @FXML
    Button settingsButton;
    @FXML
    Button connectButton;
    @FXML
    Button serverButton;


    @FXML
    private void initialize(){
        defaultConfigs = FXCollections.observableArrayList(DefaultConfiguration.getDefaultConfig());

    }
    /**
     * method to get called when goto game is pressed
     */
    @FXML
    private void gotoGame() {
        if (Settings.getActualGame() == null) {
            DefaultConfiguration defaultConf = (DefaultConfiguration) defaultConfig.getValue();
            Game game = new Game(defaultConf.getAmountOfPlayer());
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
