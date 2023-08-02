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
@SuppressWarnings("PMD.UnusedPrivateMethod")
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

    /**
     * Initializes the Controller
     */
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

    /**
     * Starts a new Game
     */
    @FXML
    private void newGame() {
        Settings.setActualGame(new Game(getAmountOfPlayer((DefaultConfig) defaultConfig.getValue())));
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
        Settings.getNetworkHandler().listen();
    }

    /**
     * method to get called when connect to server is pressed
     */
    @FXML
    private void connectToServer() {
        Settings.getNetworkHandler().askForHost().ifPresent(Settings.getNetworkHandler()::startClient);
    }

    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }

    /**
     * Get´s the amount of Players in accordance with the defaultconfig
     * @param defaultConfig ,the default configuration of the game
     * @return AmountOfPlayer ,the amount of Players in the game
     */
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
