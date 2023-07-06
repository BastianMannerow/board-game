package tnt.gui.mainmenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import tnt.gui.Language;
import tnt.gui.SceneHandler;
import tnt.model.FileManager;
import tnt.model.Game;
import tnt.model.Settings;


/**
 * The controller for the main menu
 */
public class MainMenuController{

    /**
     * Enum for handling different default configurations
     */
    private enum DefaultConfig {
        PLAYER_2,
        PLAYER_3,
        PLAYER_4
    }
    private SceneHandler sceneHandler;
    ObservableList<DefaultConfig> defaultConfigs;
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
        defaultConfigs = FXCollections.observableArrayList(DefaultConfig.values());
        defaultConfig.setConverter(new StringConverter<DefaultConfig>() {
            @Override
            public String toString(DefaultConfig defaultConfig) {
                if (defaultConfig==null){
                    return "";
                }
                StringBuilder str = new StringBuilder();
                switch (defaultConfig) {
                    case PLAYER_2:
                        str.append("2");
                        break;
                    case PLAYER_3:
                        str.append("3");
                        break;
                    case PLAYER_4:
                        str.append("4");
                }
                str.append(" ").append(Language.getTranslation("player"));
                return str.toString();
            }

            @Override
            public DefaultConfig fromString(String s) {
                return null;
            }
        });

    }
    /**
     * method to get called when goto game is pressed
     */
    @FXML
    private void gotoGame() {
        if (Settings.getActualGame() == null) {
            DefaultConfig defaultConf = (DefaultConfig) defaultConfig.getValue();
            Game game = new Game(getAmountOfPlayer(defaultConf));
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


    private static int getAmountOfPlayer(DefaultConfig defaultConfig) {
        switch (defaultConfig){
            case PLAYER_3:
                return 3;
            case PLAYER_4:
                return 4;
            default:
                return 2;
        }
    }
}
