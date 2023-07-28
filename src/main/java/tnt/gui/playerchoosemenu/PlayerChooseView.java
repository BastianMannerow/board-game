package tnt.gui.playerchoosemenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.Language;
import tnt.gui.SceneHandler;
import tnt.gui.SizeHandler;
import tnt.model.Game;
import tnt.model.Player;
import tnt.model.Settings;
import tnt.util.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The view where the player can be choosen
 */
public class PlayerChooseView extends VBox implements Observer {

    private Map<Player, PlayerAloneChooseView> playerHolder = new HashMap<Player, PlayerAloneChooseView>();
    private Game game;
    private PlayerChooseController controller;

    /**
     * The constructor for the view
     * @param sceneHandler the scenehandler holding all views
     * @throws IOException Exception when the fxml file has an error / does not exist
     */
    public PlayerChooseView(SceneHandler sceneHandler) throws IOException {
        game = Settings.getActualGame();
        FXMLLoader choosePlayerMenu = ResourceHandler.getInstance().getFXML("choosePlayerMenu");
        choosePlayerMenu.setRoot(this);
        choosePlayerMenu.load();
        this.controller = choosePlayerMenu.getController();
        controller.setSceneHandler(sceneHandler);
        sceneHandler.add("playerMenu", this);
        controller.playerPane.setFitToHeight(true);
        controller.playerPane.setFitToWidth(true);
        controller.playerPaneSingle.setSpacing(20);
        controller.playerPaneSingle.setPadding(new Insets(20,0,5,0));
        controller.fieldSizeX.setPromptText(Integer.toString(SizeHandler.getNrFieldsX()));
        controller.fieldSizeY.setPromptText(Integer.toString(SizeHandler.getNrFieldsX()));
        game.addObserver(this);
        update();
        VBox popup = new VBox();
        FXMLLoader popup_loader = ResourceHandler.getInstance().getFXML("error");
        popup_loader.setRoot(popup);
        popup_loader.load();
        controller.setPopup(popup);
        Language.getInstance().addObserver(this);
    }

    @Override
    public void update() {
        updateGame();
        updateTextfield(controller.victoryHeight, String.valueOf(game.getVictoryHeight()));
        updateTextfield(controller.maxStepUp, String.valueOf(game.getMaxStepUpHeight()));
        updateTextfield(controller.maxStepDown, String.valueOf(game.getMaxStepDownHeight()));
        updateTileResources();

        ArrayList<Player> players = game.getPlayerOrder();
        int i = 0;
        for (Player playerHere: playerHolder.keySet()){
            if (!players.contains(playerHere)){
                controller.playerPaneSingle.getChildren().remove(playerHolder.get(playerHere));
            }
        }
        for(Player player: players){
            i++;
            updatePlayer(player, i);
        }
        updateLabels();
    }

    private void updateTileResources() {
        controller.tilesSepBox.setSelected(game.isGlobalTilePool());
        controller.tileBox.getChildren().clear();
        controller.tileBox.getChildren().add(controller.sepBox);
        controller.labelGlobalTilePool.setText(Language.getTranslation("seperateLabel"));
        for (int i = 0; i <= game.getVictoryHeight(); i++){
            VBox tilePane = new VBox();
            Label tileLabel = new Label();
            TextField textField = new TextField();
            if (i==0) {
                tileLabel.setText(Language.getTranslation("domeLabel")); // Todo
            } else {
                tileLabel.setText(Language.getTranslation("buildingLevelLabel") + " " + i); // Todo, but what was it?
            }
            textField.setText(Integer.toString(game.getPlayersTurn().getNrTile(i)));
            textField.setPrefWidth(USE_COMPUTED_SIZE);
            textField.setPrefHeight(USE_COMPUTED_SIZE);
            tilePane.getChildren().addAll(tileLabel, textField);
            controller.tileBox.getChildren().add(tilePane);
        }
    }

    private void updateLabels() {
        controller.mainMenu.setText(Language.getTranslation("mainMenuLabel"));
        controller.maxStepUpHeight.setText(Language.getTranslation("maxStepUpLabel"));
        controller.maxStepDownHeight.setText(Language.getTranslation("maxStepDownLabel"));
        controller.maxBuildingHeightLabel.setText(Language.getTranslation("maxHeightOfBuildingLabel"));
        controller.addPlayerBtn.setText(Language.getTranslation("addPlayerLabel"));
        controller.boardSize.setText(Language.getTranslation("boardSizeLabel"));
        controller.sphereWorldLabel.setText(Language.getTranslation("sphereWorldLabel"));
        controller.setAmountOfFiguresLabel.setText(Language.getTranslation("setNrOfFiguresLabel"));
        controller.amountFiguresButton.setText(Language.getTranslation("setNrOfFiguresButtonLabel"));
        controller.btnPlay.setText(Language.getTranslation("playButtonLabel"));
    }

    private void updatePlayer(Player player, int i) {
        if (!playerHolder.containsKey(player)){
            PlayerAloneChooseView playerAloneChooseView;
            try {
                playerAloneChooseView = new PlayerAloneChooseView(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            playerHolder.put(player, playerAloneChooseView);
            controller.playerPaneSingle.getChildren().add(playerAloneChooseView);

            ((Button) playerAloneChooseView.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    game.removePlayer(player);
                }
            });

            ColorPicker colorP = (ColorPicker) ((VBox) playerAloneChooseView.getChildren().get(4)).getChildren().get(1);
            colorP.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    player.setColor(colorP.getValue());
                }
            });

            TextField nameField = (TextField) ((VBox) playerAloneChooseView.getChildren().get(2)).getChildren().get(1);
            nameField.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    player.setName(nameField.getText());
                }
            });

            TextField countFigures = (TextField) ((VBox) playerAloneChooseView.getChildren().get(3)).getChildren().get(1);
            countFigures.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (game.selectingPlayers()) {
                        try {
                            player.setAmountOfFigures(Integer.parseInt(countFigures.getText()));
                        } catch (NumberFormatException e) {
                            player.setAmountOfFigures(2);
                        }
                    }

                }
            });

            TextField team = (TextField) ((VBox) playerAloneChooseView.getChildren().get(5)).getChildren().get(1);
            team.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    player.setTeam(team.getText());
                }
            });

            // Todo: Check if this is necessary, often playertype is null when action is called...

//            ChoiceBox playerType = (ChoiceBox) ((VBox) playerAloneChooseView.getChildren().get(6)).getChildren().get(1);
//            playerType.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    player.setLevelOfIntelligence((Player.PlayerType) playerType.getValue());
//                }
//            });
        }
        playerHolder.get(player).setPlayerNumber(i);
    }

    private void updateTextfield(TextField textField, String value) {
        if (textField.getText().equals("")){
            textField.setPromptText(value);
        } else {
            textField.setText(value);
        }
    }
    private void updateGame() {
        if (game != Settings.getActualGame()){
            game.removeObserver(this);
            game = Settings.getActualGame();
            game.addObserver(this);
        }
    }
}
