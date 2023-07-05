package tnt.gui.playerchoosemenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
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
    }

    @Override
    public void update() {
        if (game != Settings.getActualGame()){
            game.removeObserver(this);
            game = Settings.getActualGame();
            game.addObserver(this);
        }
        if (controller.maxBuildingHeight.getText().equals("")){
            controller.maxBuildingHeight.setPromptText(String.valueOf(game.getMaxBuildingLevel()));
        } else {
            controller.maxBuildingHeight.setText(String.valueOf(game.getMaxBuildingLevel()));
        }
        if (controller.maxStepUp.getText().equals("")){
            controller.maxStepUp.setPromptText(String.valueOf(game.getMaxStepUpHeight()));
        } else {
            controller.maxStepUp.setText(String.valueOf(game.getMaxStepUpHeight()));
        }
        if (controller.maxStepDown.getText().equals("")){
            controller.maxStepDown.setPromptText(String.valueOf(game.getMaxStepDownHeight()));
        } else {
            controller.maxStepDown.setText(String.valueOf(game.getMaxStepDownHeight()));
        }
        ArrayList<Player> players = game.getPlayerOrder();
        int i = 0;
        for (Player playerHere: playerHolder.keySet()){
            if (!players.contains(playerHere)){
                controller.playerPaneSingle.getChildren().remove(playerHolder.get(playerHere));
            }
        }
        for(Player player: players){
            i++;
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

                ChoiceBox playerType = (ChoiceBox) ((VBox) playerAloneChooseView.getChildren().get(6)).getChildren().get(1);
                playerType.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        player.setLevelOfIntelligence((Player.PlayerType) playerType.getValue());
                    }
                });
            }
            playerHolder.get(player).setPlayerNumber(i);
        }
    }
}
