package tnt.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tnt.gui.mainmenu.MainMenuView;
import tnt.gui.saveloadmenu.SaveLoadMenuView;
import tnt.gui.settingsmenu.SettingsView;


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
        SizeHandler.getInstance();
        primaryStage.setTitle("TNT");

        // create a scene handler, which holds all scenes, so that we can change between them
        SceneHandler sceneHandler = new SceneHandler(primaryStage);

        //generate the SaveLoadMenu
        new SaveLoadMenuView(sceneHandler);

        // generate the main menu
        MainMenuView mainView = new MainMenuView(sceneHandler);

        // adding the main menu to the scenehandler
        sceneHandler.addMain(new Scene(mainView, 1000, 800));
//        sceneHandler.loadStyle(GUISettings.getDefaultTheme());

        new SettingsView(sceneHandler);

        // sets the scene of the scenehandler to the primary stage
        primaryStage.setScene(sceneHandler.getScene());

        SizeListener width = new SizeListener(false);
        primaryStage.widthProperty().addListener(width);
        SizeListener height = new SizeListener(true);
        primaryStage.heightProperty().addListener(height);
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
