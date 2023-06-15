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

public class PlayerAloneChooseView extends HBox implements Observer {

    private int playerNumber = 0;
    private Player player;
    
    public PlayerAloneChooseView(Player player) throws IOException {
        this.player = player;
        FXMLLoader playerLayout = ResourceHandler.getInstance().getFXML("choosePlayer");
        playerLayout.setRoot(this);
        playerLayout.load();
        player.addObserver(this);
    }

    @Override
    public void update() {
        ((Label) this.getChildren().get(1)).setText("Player " + playerNumber);
        ((TextField) ((VBox) this.getChildren().get(2)).getChildren().get(1)).setPromptText(player.getName());
        ((ColorPicker) ((VBox) this.getChildren().get(4)).getChildren().get(1)).setValue(player.getColor());
    }

    public void setPlayerNumber(int i) {
        this.playerNumber = i;
        update();
    }

    public Player getPlayer() {
        return this.player;
    }
}
