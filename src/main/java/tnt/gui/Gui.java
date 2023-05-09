package tnt.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tnt.game.GameController;
import tnt.game.GameView;
import tnt.mainmenu.MainMenuController;
import tnt.mainmenu.MainMenuView;
import tnt.model.Game;
import tnt.model.Player;

import java.util.ArrayList;

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
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TNT");
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(event -> System.out.println("Hello World!"));

//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
        ArrayList<Player> player = new ArrayList<Player>();
//        player.add()

        MainMenuView mainView = new MainMenuView();
        GameView gameView = new GameView();

        Game g = new Game(player);
        gameView.setGame(g);

        Scene scene = new Scene(mainView, 300, 260);

        MainMenuController mainController = new MainMenuController(mainView, gameView, scene);
        GameController gameController = new GameController(gameView, mainView, scene);

        primaryStage.setScene(scene);
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
