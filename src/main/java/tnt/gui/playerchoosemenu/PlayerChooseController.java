package tnt.gui.playerchoosemenu;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import tnt.gui.SceneHandler;
import tnt.model.Game;
import tnt.model.Player;
import tnt.model.Settings;

/**
 * The controller for the view where the player can be choosen
 */
@SuppressWarnings({"PMD.TooManyFields", "PMD.UnusedPrivateMethod"})
public class PlayerChooseController{

    @FXML
    HBox tileBox;
    @FXML
    VBox sepBox;
    @FXML
    Label labelGlobalTilePool;
    @FXML
    CheckBox tilesSepBox;
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
    TextField victoryHeight;
    @FXML
    Button mainMenu;
    @FXML
    Label maxStepUpHeight;
    @FXML
    Label maxStepDownHeight;
    @FXML
    Label maxBuildingHeightLabel;
    @FXML
    Button addPlayerBtn;
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


    /**
     * The method getting called, when user pressed the play button
     */
    @FXML
    private void runGame() {
        updateGameSettings();
        Game game = Settings.getActualGame();
        if (game.selectingPlayers()){
            game.initGame();
            game.startPlaceFigures();
        }
        Settings.getNetworkHandler().sendGame(game);
        sceneHandler.loadView("gameView");
    }

    /**
     * Updates the game to the actual game
     */
    @FXML
    private void updateGameSettings(){
        Game game = Settings.getActualGame();
        int nrOfFigures = updatePlayer(game);

        updateGameSettings(game, nrOfFigures);
    }

    /**
     * Updates the game with paramaters that differ from the standart such as:
     * maxStepUp,maxStepDown,victoryHeight,sizeX,sizeY,globalTilePool,
     * @param game ,the actual game
     * @param nrOfFigures ,the amount of figures selected within the Menu
     */
    private void updateGameSettings(Game game, int nrOfFigures) {
        // Todo: get the other fields

        int maxStepUp = updateValueOfTextfield(this.maxStepUp, game.getMaxStepUpHeight());
        int maxStepDown = updateValueOfTextfield(this.maxStepDown, game.getMaxStepDownHeight());
        int victoryHeight = updateValueOfTextfield(this.victoryHeight, game.getVictoryHeight());

        int sizeX = updateValueOfTextfield(this.fieldSizeX, Settings.getFieldSizeX());
        int sizeY = updateValueOfTextfield(this.fieldSizeY, Settings.getFieldSizeY());


        int[] tiles = new int[victoryHeight + 1];
        tilesCalc(tiles,game);


        boolean globalTilePool = this.tilesSepBox.isSelected();

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
            game.setGlobalTilePool(globalTilePool);
            game.createBoard(sizeX, sizeY);
            game.getBoard().setRoundWorld(roundWorld.isSelected());
            game.setMaxStepUpHeight(maxStepUp);
            game.setMaxStepDownHeight(maxStepDown);
            game.setVictoryHeight(victoryHeight);
            for(int i = 0; i < game.getTileSize(); i++){
                if (i >= tiles.length){
                    break;
                }
                game.setNrTile(i, tiles[i]);
            }
            game.setVictoryHeight(victoryHeight);
            Settings.getActualGame().setGlobalTilePool(tilesSepBox.isSelected());
        }
    }

    /**
     * Helps Calculating stuff for the Tiles
     * @param tiles
     * @param game ,the actual game
     */
    private void tilesCalc(int[] tiles,Game game){
        for(int i = 0; i < tiles.length; i++){
            if (i < this.tileBox.getChildren().size() - 1) {
                TextField tilenr = (TextField) ((VBox) this.tileBox.getChildren().get(i + 1)).getChildren().get(1);
                if (i >= game.getTileSize()) {
                    tiles[i] = updateValueOfTextfield(tilenr, 0);
                } else {
                    tiles[i] = updateValueOfTextfield(tilenr, game.getNrTile(i));
                }
            } else {
                if (i >= game.getTileSize()) {
                    tiles[i] = 0;
                } else {
                    tiles[i] = game.getNrTile(i);
                }
            }
        }
    }
    /**
     * Updates the integer Value in the given textfield
     * @param textfield ,the given textfield which you want to update
     * @param initvalue ,the value with which you want to intialize TODO: Seeing if this line is correct
     * @return
     */
    private int updateValueOfTextfield(TextField textfield, int initvalue) {
        int value = initvalue;
        try {
            value = Integer.parseInt(textfield.getText());
        } catch (NumberFormatException e) {
            if (!textfield.getText().equals("")) {
                System.err.println("could not convert " + textfield.getId() + ": " + textfield.getText() + " Error: " + e);
            }
        }
        return value;
    }

    /**
     * Updates the Player to the correct Team,Name,levelofintelligence
     * @param game ,the actual game
     * @return nrOfFigures ,the number of Figures in the Game
     */
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
//                int amount = player.getRealAmountFigure();
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
//                nrOfFigures += player.getAmountOfFigures();
                nrOfFigures += player.getRealAmountFigure();
            }
        }
        return nrOfFigures;
    }

    /**
     * The method getting called, when user pressed the add player button
     */
    @FXML
    private void addPlayer() {
        Settings.getActualGame().addPlayer(2, String.valueOf(Settings.getActualGame().getPlayerOrder().size()), Settings.getActualGame().getAmountOfTurns());
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

    /**
     * Swaps the scene to the MainMenu
     */
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
