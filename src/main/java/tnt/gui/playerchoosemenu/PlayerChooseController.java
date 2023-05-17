package tnt.gui.playerchoosemenu;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import tnt.model.Game;
import tnt.model.Player;
import tnt.util.Observable;

import java.util.ArrayList;


public class PlayerChooseController{

//    @FXML
//    private void initialize(){
//    }
    private Game game;

    @FXML
    private void runGame() {
        System.out.println("B");
    }
    @FXML
    private void addPlayer() {
        game.addPlayer();
    }


    public void removePlayer(Player player) {
        game.removePlayer(player);
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
