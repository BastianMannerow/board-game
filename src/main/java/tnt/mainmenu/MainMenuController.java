package tnt.mainmenu;

import javafx.scene.Scene;
import tnt.game.GameView;

public class MainMenuController {
    public MainMenuController(MainMenuView v1, GameView v2, Scene scene) {
        v1.getBtnContinue().setOnAction(event -> {
            scene.setRoot(v2);
        });
    }
}
