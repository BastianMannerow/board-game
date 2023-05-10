package tnt.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tnt.gui.game.GameController;
import tnt.gui.game.GameView;
import tnt.gui.mainmenu.MainMenuController;
import tnt.gui.mainmenu.MainMenuView;
import tnt.model.Game;
import tnt.model.Player;
import tnt.gui.playerchoosemenu.PlayerChooseController;
import tnt.gui.playerchoosemenu.PlayerChooseView;
import tnt.ResourceHandler;

import java.io.IOException;
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
        FXMLLoader fxmlLoader = ResourceHandler.getInstance().getFXML("choosePlayer");

//        FXMLLoader fxmlLoader = ResourceHandler.getInstance().getFXML("gui");
        System.out.println(fxmlLoader.getLocation());
        try {
            Parent root = (Parent)fxmlLoader.load();
            System.out.println("95");
//            ArrayList<Player> player = new ArrayList<Player>();
            MainMenuView mainView = new MainMenuView();
//            PlayerChooseView playerChooseView = new PlayerChooseView();
            GameView gameView = new GameView();
            Game g = new Game(1);
//            gameView.setGame(g);
            System.out.println("216541");
            Scene mainScene = new Scene(mainView, 600, 400);
            System.out.println("liu");
            MainMenuController mainController = new MainMenuController(mainView, gameView, root, mainScene);
//            PlayerChooseController playerChooseController = new PlayerChooseController(playerChooseView, gameView, mainScene);
            GameController gameController = new GameController(gameView, mainView, mainScene);

            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("could not load fxml file");
//            throw RuntimeException;
            return;
        } catch (RuntimeException e){
            System.out.println("other fxml error");
//            throw RuntimeException;
            return;
        }
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
