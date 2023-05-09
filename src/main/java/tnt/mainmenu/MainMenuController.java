package tnt.mainmenu;

import javafx.scene.Scene;
import tnt.game.GameView;
import tnt.playerchoosemenu.mainmenu.PlayerChooseView;

public class MainMenuController {
    public MainMenuController(MainMenuView mainMenuView, GameView gameView, PlayerChooseView playerChooseView, Scene scene) {
        mainMenuView.getBtnContinue().setOnAction(event -> {
            scene.setRoot(gameView);
        });
        mainMenuView.getBtnContinue2().setOnAction(event -> {
            scene.setRoot(playerChooseView);
        });
    }
}
