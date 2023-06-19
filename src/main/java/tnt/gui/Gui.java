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
import tnt.gui.settingsmenu.SettingsView;
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

        Game game = new Game();

        SceneHandler sceneHandler = new SceneHandler();

        Parent choosePlayerMenu = new PlayerChooseView(sceneHandler, game);

        MainMenuView mainView = new MainMenuView(sceneHandler, choosePlayerMenu);

        sceneHandler.addMain(new Scene(mainView, 1000, 800));

        GameView gameView = new GameView(sceneHandler, game);
        SettingsView settingsView = new SettingsView(sceneHandler);


        primaryStage.setScene(sceneHandler.getScene());
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
