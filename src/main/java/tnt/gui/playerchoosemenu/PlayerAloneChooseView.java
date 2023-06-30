package tnt.gui.playerchoosemenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ObservableList<Object> playerTypeList;


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
        playerTypeList = FXCollections.observableArrayList(Player.PlayerType.values());
        ChoiceBox playerType = (ChoiceBox) ((VBox) this.getChildren().get(6)).getChildren().get(1);
        playerType.setItems(playerTypeList);
    }

    @Override
    public void update() {
        ((Label) this.getChildren().get(1)).setText("Player " + playerNumber);
        TextField name = (TextField) ((VBox) this.getChildren().get(2)).getChildren().get(1);
        if (name.getText().equals("")){
            name.setPromptText("Player " + player.getName());
        } else {
            name.setText(player.getName());
        }
        ((ColorPicker) ((VBox) this.getChildren().get(4)).getChildren().get(1)).setValue(player.getColor());
        TextField team = (TextField) ((VBox) this.getChildren().get(5)).getChildren().get(1);
        if (team.getText().equals("")){
            team.setPromptText(player.getTeam());
        } else {
            team.setText(player.getTeam());
        }
        TextField amountOfFigures = (TextField) ((VBox) this.getChildren().get(3)).getChildren().get(1);
        if (amountOfFigures.getText().equals("")){
            amountOfFigures.setPromptText(Integer.toString(player.getAmountOfFigures()));
        } else {
            amountOfFigures.setText(Integer.toString(player.getAmountOfFigures()));
        }
        ChoiceBox playerType = (ChoiceBox) ((VBox) this.getChildren().get(6)).getChildren().get(1);
        playerType.setValue(player.getLevelOfIntelligence());
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
