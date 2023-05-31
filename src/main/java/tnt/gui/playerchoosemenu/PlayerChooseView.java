package tnt.gui.playerchoosemenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerChooseView extends VBox implements Observer {

    private Map<Player, HBox> playerHolder = new HashMap<Player, HBox>();
    private Game game;
    public PlayerChooseView(SceneHandler sceneHandler, Game game) throws IOException {
        this.game = game;
        FXMLLoader choosePlayerMenu = ResourceHandler.getInstance().getFXML("choosePlayerMenu");
        choosePlayerMenu.setRoot(this);
        choosePlayerMenu.load();
        PlayerChooseController controller = choosePlayerMenu.getController();
        controller.setGame(game);
        controller.setSceneHandler(sceneHandler);
        sceneHandler.add("playerMenu", this);
        ((ScrollPane) this.getChildren().get(0)).setFitToHeight(true);
        ((ScrollPane) this.getChildren().get(0)).setFitToWidth(true);
        ((VBox)((ScrollPane) this.getChildren().get(0)).getContent()).setSpacing(20);
        ((VBox)((ScrollPane) this.getChildren().get(0)).getContent()).setPadding(new Insets(20,0,5,0));
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
            if (playerHolder.containsKey(player)){
                ((Label) playerHolder.get(player).getChildren().get(1)).setText("Player " + (i + 1));
            } else {
                HBox hBox = new HBox();
                playerHolder.put(player, hBox);
                playerBox.getChildren().add(hBox);
                FXMLLoader playerLayout1 = ResourceHandler.getInstance().getFXML("choosePlayer");
                playerLayout1.setRoot(hBox);
                try {
                    playerLayout1.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((Label) hBox.getChildren().get(1)).setText("Player " + (i + 1));
                ((TextField) ((VBox) hBox.getChildren().get(2)).getChildren().get(1)).setPromptText(player.getName());
                ((ColorPicker) ((VBox) hBox.getChildren().get(4)).getChildren().get(1)).setValue(player.getColor());
                ((Button) hBox.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        game.removePlayer(player);
                    }
                });
            }
            i++;
        }
    }
}
