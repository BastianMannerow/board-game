package tnt.playerchoosemenu.mainmenu;

import javafx.scene.Scene;
import tnt.game.GameView;
import tnt.model.Figure;
import tnt.model.Player;
import tnt.model.enums.Gods;

import java.util.ArrayList;

public class PlayerChooseController {
    public PlayerChooseController(PlayerChooseView playerChooseView, GameView gameView, Scene scene) {
        playerChooseView.getBtnContinue().setOnAction(event -> {
            scene.setRoot(gameView);
        });
        playerChooseView.getBtnContinue2().setOnAction(event -> {
            gameView.getGame().addPlayer(new Player("", "player ", "red", new ArrayList<Figure>(), new ArrayList<Gods>()));
        });
    }
}
