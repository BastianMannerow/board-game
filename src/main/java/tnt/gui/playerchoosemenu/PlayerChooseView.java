package tnt.gui.playerchoosemenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.model.Game;
import tnt.model.Player;
import tnt.util.Observer;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerChooseView extends VBox implements Observer {

    private Map<Player, PlayerAloneChooseView> playerHolder = new HashMap<Player, PlayerAloneChooseView>();
    private Game game;
    public PlayerChooseView(SceneHandler sceneHandler, Game game) throws IOException {
        this.game = game;
        FXMLLoader choosePlayerMenu = ResourceHandler.getInstance().getFXML("choosePlayerMenu");
        choosePlayerMenu.setRoot(this);
        choosePlayerMenu.load();
        PlayerChooseController controller = choosePlayerMenu.getController();
        controller.setGame(game);
        controller.setView(this);
        controller.setSceneHandler(sceneHandler);
        sceneHandler.add("playerMenu", this);
        ((ScrollPane) this.getChildren().get(0)).setFitToHeight(true);
        ((ScrollPane) this.getChildren().get(0)).setFitToWidth(true);
        ((VBox)((ScrollPane) this.getChildren().get(0)).getContent()).setSpacing(20);
        ((VBox)((ScrollPane) this.getChildren().get(0)).getContent()).setPadding(new Insets(20,0,5,0));
        ((TextField) ((HBox) ((VBox) ((HBox) this.getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).setPromptText("5"); //Todo: get default size
        ((TextField) ((HBox) ((VBox) ((HBox) this.getChildren().get(1)).getChildren().get(1)).getChildren().get(0)).getChildren().get(2)).setPromptText("5"); //Todo: get default size
        game.addObserver(this);
        update();
    }

    @Override
    public void update() {
        ArrayList<Player> players = game.getPlayerOrder();
        VBox playerBox = (VBox) ((ScrollPane) this.getChildren().get(0)).getContent();
        int i = 0;
        for (Player playerHere: playerHolder.keySet()){
            if (!players.contains(playerHere)){
                playerBox.getChildren().remove(playerHolder.get(playerHere));
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
                playerBox.getChildren().add(playerAloneChooseView);

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
                        try {
                            player.setAmountOfFigures(Integer.parseInt(countFigures.getText()));
                        } catch (NumberFormatException e) {
                            player.setAmountOfFigures(2);
                        }

                    }
                });
            }
            playerHolder.get(player).setPlayerNumber(i);
        }
    }
}
