package tnt.gui.playerchoosemenu;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tnt.gui.SceneHandler;
import tnt.model.Game;
import tnt.model.Player;
import tnt.util.Observable;

import java.util.ArrayList;


public class PlayerChooseController{

//    @FXML
//    private void initialize(){
//    }
    private Game game;
    private SceneHandler sceneHandler;
    private PlayerChooseView view;
    @FXML
    private void runGame() {
        for (Node node : ((VBox)((ScrollPane) view.getChildren().get(0)).getContent()).getChildren()){
            if (node instanceof PlayerAloneChooseView){
                PlayerAloneChooseView playerView = (PlayerAloneChooseView) node;

                Player player = playerView.getPlayer();
                player.setName(((TextField) ((VBox) playerView.getChildren().get(2)).getChildren().get(1)).getText());
                try {
                    int amount = Integer.parseInt(((TextField) ((VBox) playerView.getChildren().get(3)).getChildren().get(1)).getText());
                    player.setAmountOfFigures(amount);
                } catch (NumberFormatException e) {
                    System.out.println("could not convert the amount of figures to int " + e);
                }
                player.setColor(((ColorPicker) ((VBox) playerView.getChildren().get(4)).getChildren().get(1)).getValue());

            }
        }
        game.initGame();
        game.startPlaceFigures();
        sceneHandler.loadView("gameView");
    }
    @FXML
    private void addPlayer() {
        game.addPlayer(2);
    }


//    public void removePlayer(Player player) {
//        game.removePlayer(player);
//    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }

    public void setView(PlayerChooseView view) {
        this.view = view;
    }
}
