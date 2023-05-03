package tnt.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tnt.controller.Controller;
import tnt.model.Game;
import tnt.model.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
        Game g = new Game(player);
		View v = new View();
        v.setModel(g);
        Controller c = new Controller();
        c.setView(v);
        primaryStage.setScene(new Scene(v, 300, 250));
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
