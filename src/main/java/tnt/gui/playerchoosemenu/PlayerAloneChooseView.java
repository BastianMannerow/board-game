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

/**
 * The view for each player in the playerchoose menu
 */
public class PlayerAloneChooseView extends HBox implements Observer {

    private int playerNumber = 0;
    private Player player;

    /**
     * The constructor for the view
     * @param player the player belonging to this view
     * @throws IOException Exception when the fxml file has an error / does not exist
     */
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
        if (((TextField) ((VBox) this.getChildren().get(2)).getChildren().get(1)).getText().equals("")){
            ((TextField) ((VBox) this.getChildren().get(2)).getChildren().get(1)).setPromptText(player.getName());
        } else {
            ((TextField) ((VBox) this.getChildren().get(2)).getChildren().get(1)).setText(player.getName());
        }
        ((ColorPicker) ((VBox) this.getChildren().get(4)).getChildren().get(1)).setValue(player.getColor());
        if (((TextField) ((VBox) this.getChildren().get(5)).getChildren().get(1)).getText().equals("")){
            ((TextField) ((VBox) this.getChildren().get(5)).getChildren().get(1)).setPromptText(Integer.toString(player.getTeam()));
        } else {
            ((TextField) ((VBox) this.getChildren().get(5)).getChildren().get(1)).setText(Integer.toString(player.getTeam()));
        }
        if (((TextField) ((VBox) this.getChildren().get(3)).getChildren().get(1)).getText().equals("")){
            ((TextField) ((VBox) this.getChildren().get(3)).getChildren().get(1)).setPromptText(Integer.toString(player.getAmountOfFigures()));
        } else {
            ((TextField) ((VBox) this.getChildren().get(3)).getChildren().get(1)).setText(Integer.toString(player.getAmountOfFigures()));
        }

    }

    /**
     * Sets the player indentifier number
     * @param i the unique identifer for the player
     */
    public void setPlayerNumber(int i) {
        this.playerNumber = i;
        update();
    }

    /**
     * Getter for the player
     * @return the player belonging to this view
     */
    public Player getPlayer() {
        return this.player;
    }
}
