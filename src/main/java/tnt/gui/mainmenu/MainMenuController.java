package tnt.gui.mainmenu;

import javafx.scene.Parent;
import javafx.scene.Scene;
import tnt.gui.game.GameView;
import tnt.gui.playerchoosemenu.PlayerChooseView;

public class MainMenuController {
    public MainMenuController(MainMenuView mainMenuView, GameView gameView, Parent playerChooseView, Scene scene) {
        mainMenuView.getBtnContinue().setOnAction(event -> {
            scene.setRoot(gameView);
        });
        mainMenuView.getBtnContinue2().setOnAction(event -> {
            scene.setRoot(playerChooseView);
        });
    }
}
