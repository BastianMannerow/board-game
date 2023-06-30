package tnt.gui.playerchoosemenu;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import tnt.gui.SceneHandler;
import tnt.gui.GUISettings;
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

    private Game game;
    private SceneHandler sceneHandler;
    final Popup popup = new Popup();


    /**
     * The method getting called, when user pressed the play button
     */
    @FXML
    private void runGame() {
        int nrOfFigures = 0;
        for (Node node : playerPaneSingle.getChildren()){
            if (node instanceof PlayerAloneChooseView){
                PlayerAloneChooseView playerView = (PlayerAloneChooseView) node;

                Player player = playerView.getPlayer();
                String name = ((TextField) ((VBox) playerView.getChildren().get(2)).getChildren().get(1)).getText();

                if ((name.length()<1)){
                    name = (((TextField) ((VBox) playerView.getChildren().get(2)).getChildren().get(1)).getPromptText().substring(7));
                }
                int amount = player.getAmountOfFigures();
                try {
                    amount = Integer.parseInt(((TextField) ((VBox) playerView.getChildren().get(3)).getChildren().get(1)).getText());
                } catch (NumberFormatException e) {
                    System.err.println("could not convert the amount of figures to int: " + ((TextField) ((VBox) playerView.getChildren().get(3)).getChildren().get(1)).getText() + " Error: " + e);
                }

                player.setColor(((ColorPicker) ((VBox) playerView.getChildren().get(4)).getChildren().get(1)).getValue());
                player.setName(name);
                player.setAmountOfFigures(amount);
                nrOfFigures += player.getAmountOfFigures();
            }
        }

        int sizeX = Settings.getFieldSizeX();
        int sizeY = Settings.getFieldSizeY();

        try {
            int amount = Integer.parseInt(fieldSizeX.getText());
            sizeX = amount;
        } catch (NumberFormatException e) {
            System.err.println("could not convert the field size x to int " + e);
        }
        try {
            int amount = Integer.parseInt(fieldSizeY.getText());
            sizeY = amount;
        } catch (NumberFormatException e) {
            System.err.println("could not convert the field size y to int " + e);
        }

        if (sizeX * sizeY <= nrOfFigures){
            System.err.println("Too many figures for that board size: fig:" + nrOfFigures + " sizeX: " + sizeX + " sizeY: " + sizeY);
            ((Label)((VBox) popup.getContent().get(0)).getChildren().get(0)).setText("Too many figures for that board size");
            popup.show(sceneHandler.getStage());
            return;
        }

        if (sizeX * sizeY > GUISettings.maxFieldcount){
            System.err.println("Too many fields, the amount of field is limited by " + GUISettings.maxFieldcount + " but you entered " + sizeX * sizeY);
            ((Label)((VBox) popup.getContent().get(0)).getChildren().get(0)).setText("Too many fieldss");
            popup.show(sceneHandler.getStage());
            return;
        }

        if (game.selectingPlayers()){
            game.createBoard(sizeX, sizeY);

            game.getBoard().setRoundWorld(roundWorld.isSelected());

            game.initGame();
            game.startPlaceFigures();
        }
        sceneHandler.loadView("gameView");
    }

    /**
     * The method getting called, when user pressed the add player button
     */
    @FXML
    private void addPlayer() {
        game.addPlayer(2);
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
        for (Player player: game.getPlayerOrder()) {
            player.setAmountOfFigures(amountOfFigures);
        }
    }

    /**
     * the setter for the game, so the controller knows its model
     * @param game the actual game
     */
    public void setGame(Game game) {
        this.game = game;
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
