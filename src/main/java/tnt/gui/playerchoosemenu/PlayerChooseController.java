package tnt.gui.playerchoosemenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tnt.gui.game.GameView;
import tnt.model.Player;
import tnt.model.enums.Gods;
import tnt.util.Observable;

import java.util.ArrayList;

public class PlayerChooseController extends Observable {

    private Color[] def_colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.PINK};
    @FXML
    private void initialize(){
        addPlayer();
        addPlayer();
    }
    private ArrayList<Player> players = new ArrayList<>();

    @FXML
    private void runGame() {
        System.out.println("B");
    }
    @FXML
    private void addPlayer() {
        players.add(new Player("" , "Player " + (players.size()+1), def_colors[players.size() % def_colors.length]));
        notifyObservers();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void removePlayer(Player player) {
        players.remove(player);
        notifyObservers();
    }
}
