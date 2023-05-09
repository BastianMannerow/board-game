package tnt.game;

import javafx.scene.Scene;
import tnt.mainmenu.MainMenuView;

public class GameController {
    public GameController(GameView gameView, MainMenuView mainView, Scene scene){

        gameView.getBtnCounterUp().setOnAction(event -> {
            gameView.getGame().setPlayersTurn(gameView.getGame().getPlayersTurn() + 1);
        });

        gameView.getBtnCounterDown().setOnAction(event -> {
            gameView.getGame().setPlayersTurn(gameView.getGame().getPlayersTurn() - 1);
        });

        gameView.getBtnBack().setOnAction(event -> {
            scene.setRoot(mainView);
        });

    }
}
