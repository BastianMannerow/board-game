package tnt.gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import tnt.gui.game.GameController;
import tnt.gui.game.GameView;
import tnt.gui.mainmenu.MainMenuController;
import tnt.gui.mainmenu.MainMenuView;
import tnt.gui.playerchoosemenu.PlayerChooseView;
import tnt.model.Game;

import java.io.IOException;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {
    /**
     * This method is called by the Application to start the GUI.
     *
     * @param primaryStage The initial root stage of the application.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("TNT");

        Parent scene = new TilePane();

        Game game = new Game();

        Scene mainScene = new Scene(scene, 1000, 800);

        Parent choosePlayerMenu = new PlayerChooseView(game);

        MainMenuController mainMenuController = new MainMenuController(mainScene, choosePlayerMenu);
        MainMenuView mainView = new MainMenuView(mainMenuController);
//        MainMenuView mainView = new MainMenuView(mainScene, choosePlayerMenu);

        mainMenuController.setMainView(mainView);

        GameView gameView = new GameView(game);

//        mainMenuController.setPlayerChoose(choosePlayerMenu);

//        mainScene.setRoot(choosePlayerMenu);
//        Game g = new Game(1);
//        GameView gameView = new GameView(g);
//        gameView.setGame(g);


//        MainMenuController mainController = new MainMenuController();

//        GameController gameController = new GameController(gameView, mainView, mainScene);


        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * The entry point of the GUI application.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
