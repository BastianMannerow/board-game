package tnt.gui.playerchoosemenu;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import tnt.gui.Language;
import tnt.gui.SceneHandler;
import tnt.gui.mainmenu.MainMenuController;
import tnt.model.Game;
import tnt.model.Player;
import tnt.model.Settings;

/**
 * The controller for the view where the player can be choosen
 */
public class PlayerChooseController{

    @FXML
    CheckBox roundWorld;
    @FXML
    VBox playerPaneSingle;
    @FXML
    TextField fieldSizeX;
    @FXML
    TextField fieldSizeY;
    @FXML
    TextField amountOfFiguresAll;
    @FXML
    ScrollPane playerPane;
    @FXML
    TextField maxStepUp;
    @FXML
    TextField maxStepDown;
    @FXML
    TextField maxBuildingHeight;
    @FXML
    Button mainMenu;
    @FXML
    Label maxStepUpHeight;
    @FXML
    Label maxStepDownHeight;
    @FXML
    Label maxBuildingHeightLabel;
    @FXML
    Button addPlayer;
    @FXML
    Label boardSize;
    @FXML
    Label sphereWorldLabel;
    @FXML
    Label setAmountOfFiguresLabel;
    @FXML
    Button amountFiguresButton;
    @FXML
    Button btnPlay;

    private SceneHandler sceneHandler;
    final Popup popup = new Popup();

//    @FXML
//    private void initialize(){
//        maxStepUp.setPromptText(String.valueOf(Settings.getMaxStepUp()));
//        maxStepDown.setPromptText(String.valueOf(Settings.getMaxStepDown()));
//        maxBuildingHeight.setPromptText(String.valueOf(Settings.getMaxBuildingLevel()));
//    }

    /**
     * The method getting called, when user pressed the play button
     */
    @FXML
    private void runGame() {
        Game game = Settings.getActualGame();
        int nrOfFigures = updatePlayer(game);


        updateGameSettings(game, nrOfFigures);

        sceneHandler.loadView("gameView");
    }

    private void updateGameSettings(Game game, int nrOfFigures) {
        // Todo: get the other fields

        int maxStepUp = updateValueOfTextfield(this.maxStepUp, game.getMaxStepUpHeight());
        int maxStepDown = updateValueOfTextfield(this.maxStepDown, game.getMaxStepDownHeight());
        int maxBuildingHeight = updateValueOfTextfield(this.maxBuildingHeight, game.getMaxBuildingLevel());

        int sizeX = updateValueOfTextfield(this.fieldSizeX, Settings.getFieldSizeX());
        int sizeY = updateValueOfTextfield(this.fieldSizeY, Settings.getFieldSizeY());

        if (sizeX * sizeY <= nrOfFigures){
            System.err.println("Too many figures for that board size: fig:" + nrOfFigures + " sizeX: " + sizeX + " sizeY: " + sizeY);
            ((Label)((VBox) popup.getContent().get(0)).getChildren().get(0)).setText("Too many figures for that board size");
            popup.show(sceneHandler.getStage());
            return;
        }

        if (sizeX * sizeY > Settings.getMaxFieldcount()){
            System.err.println("Too many fields, the amount of field is limited by " + Settings.getMaxFieldcount() + " but you entered " + sizeX * sizeY);
            ((Label)((VBox) popup.getContent().get(0)).getChildren().get(0)).setText("Too many fields");
            popup.show(sceneHandler.getStage());
            return;
        }

        if (game.selectingPlayers()){
            game.createBoard(sizeX, sizeY);
            game.getBoard().setRoundWorld(roundWorld.isSelected());
            game.setMaxStepUpHeight(maxStepUp);
            game.setMaxStepDownHeight(maxStepDown);
            game.setMaxBuildingLevel(maxBuildingHeight);
            game.initGame();
            game.startPlaceFigures();
        }
    }

    private int updateValueOfTextfield(TextField textfield, int initvalue) {
        int value = initvalue;
        try {
            value = Integer.parseInt(textfield.getText());
        } catch (NumberFormatException e) {
            System.err.println("could not convert " + textfield.getId() + ": " + textfield.getText() + " Error: " + e);
        }
        return value;
    }

    private int updatePlayer(Game game) {
        int nrOfFigures = 0;
        for (Node node : playerPaneSingle.getChildren()){
            if (node instanceof PlayerAloneChooseView){
                PlayerAloneChooseView playerView = (PlayerAloneChooseView) node;

                Player player = playerView.getPlayer();
                String name = ((TextField) ((VBox) playerView.getChildren().get(2)).getChildren().get(1)).getText();

                if (name.length()<1){
                    name = ((TextField) ((VBox) playerView.getChildren().get(2)).getChildren().get(1)).getPromptText().substring(7);
                }

                int amount = player.getAmountOfFigures();
                try {
                    amount = Integer.parseInt(((TextField) ((VBox) playerView.getChildren().get(3)).getChildren().get(1)).getText());
                } catch (NumberFormatException e) {
                    System.err.println("could not convert the amount of figures to int: " + ((TextField) ((VBox) playerView.getChildren().get(3)).getChildren().get(1)).getText() + " Error: " + e);
                }

                String team = ((TextField) ((VBox) playerView.getChildren().get(5)).getChildren().get(1)).getText();
                if (team.length()<1){
                    team = ((TextField) ((VBox) playerView.getChildren().get(5)).getChildren().get(1)).getPromptText();
                }
                Player.PlayerType playerType = (Player.PlayerType) ((ChoiceBox) ((VBox) playerView.getChildren().get(6)).getChildren().get(1)).getValue();

                player.setColor(((ColorPicker) ((VBox) playerView.getChildren().get(4)).getChildren().get(1)).getValue());
                player.setTeam(team);
                player.setLevelOfIntelligence(playerType);
                player.setName(name);

                if (game.selectingPlayers()){
                    player.setAmountOfFigures(amount);
                    // Todo: set Team and playertype (or outside this condition)
                }
                nrOfFigures += player.getAmountOfFigures();
            }
        }
        return nrOfFigures;
    }

    /**
     * The method getting called, when user pressed the add player button
     */
    @FXML
    private void addPlayer() {
        Settings.getActualGame().addPlayer(2, String.valueOf(Settings.getActualGame().getPlayerOrder().size()));
    }

    /**
     * The method getting called, when user pressed the add player button
     */
    @FXML
    private void setAmountOfFigures() {
        int amountOfFigures = 2;
        try {
            amountOfFigures = Integer.parseInt(amountOfFiguresAll.getText());
        } catch (NumberFormatException e){
            System.err.println("could not convert the amount of figures to int: " + amountOfFiguresAll.getText() + " Error: " + e);
        }
        for (Player player: Settings.getActualGame().getPlayerOrder()) {
            player.setAmountOfFigures(amountOfFigures);
        }
    }

    @FXML
    private void setMaxStepUp() {
//        Settings.getActualGame().set
    }

    @FXML
    private void gotoHome(){
        sceneHandler.loadView("mainMenu");
    }

    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }

    public void setPopup(VBox vbox) {
        this.popup.setX(300);
        vbox.setStyle("-fx-background-color: white");
        popup.setY(200);
        ((Button) vbox.getChildren().get(1)).setOnAction(event -> {
            popup.hide();
        });
        popup.getContent().add(vbox);
    }
}
