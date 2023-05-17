package tnt.gui.game;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import tnt.gui.SceneHandler;
import tnt.model.Game;
import tnt.model.Player;
import tnt.util.Observable;

import java.util.ArrayList;


public class GameController{

    //    @FXML
//    private void initialize(){
//    }
    private Game game;
    private SceneHandler sceneHandler;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }
}
