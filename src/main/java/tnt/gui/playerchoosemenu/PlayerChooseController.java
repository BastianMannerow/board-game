package tnt.gui.playerchoosemenu;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tnt.gui.SceneHandler;
import tnt.model.Game;
import tnt.model.Player;
import tnt.util.Observable;

import java.util.ArrayList;


public class PlayerChooseController{

    @FXML
    CheckBox roundWorld;

    @FXML
    VBox playerPaneSingle;
    @FXML
    TextField fieldSizeX;
    @FXML
    TextField fieldSizeY;

//    @FXML
//    private void initialize(){
//    }
    private Game game;
    private SceneHandler sceneHandler;
    private PlayerChooseView view;
    @FXML
    private void runGame() {
        for (Node node : playerPaneSingle.getChildren()){
            if (node instanceof PlayerAloneChooseView){
                PlayerAloneChooseView playerView = (PlayerAloneChooseView) node;

                Player player = playerView.getPlayer();
                String name = ((TextField) ((VBox) playerView.getChildren().get(2)).getChildren().get(1)).getText();
                if (!(name.length()<1)){
                    player.setName(name);
                }
                else{
                    player.setName(((TextField) ((VBox) playerView.getChildren().get(2)).getChildren().get(1)).getPromptText().substring(7));
                }
                try {
                    int amount = Integer.parseInt(((TextField) ((VBox) playerView.getChildren().get(3)).getChildren().get(1)).getText());
                    player.setAmountOfFigures(amount);
                } catch (NumberFormatException e) {
                    System.out.println("could not convert the amount of figures to int " + e);
                }
                player.setColor(((ColorPicker) ((VBox) playerView.getChildren().get(4)).getChildren().get(1)).getValue());

            }
        }

        int sizeX = 5; // Todo: get default size
        int sizeY = 5;

        try {
            int amount = Integer.parseInt(fieldSizeX.getText());
            sizeX = amount;
        } catch (NumberFormatException e) {
            System.out.println("could not convert the field size x to int " + e);
        }
        try {
            int amount = Integer.parseInt(fieldSizeY.getText());
            sizeY = amount;
        } catch (NumberFormatException e) {
            System.out.println("could not convert the field size y to int " + e);
        }

        game.createBoard(sizeX, sizeY);

        game.getBoard().setRoundWorld(roundWorld.isSelected());

        game.initGame();
        game.startPlaceFigures();
        sceneHandler.loadView("gameView");
    }
    @FXML
    private void addPlayer() {
        game.addPlayer(2);
    }


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
