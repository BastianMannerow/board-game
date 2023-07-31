package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.model.FileManager;
import tnt.model.Game;
import tnt.model.Player;
import tnt.model.Settings;
import tnt.util.Observer;


import java.io.IOException;
import java.util.ArrayList;

/**
 * The class EndView holds all important things for the Endscreen if the game is finished
 */
public class EndView extends VBox {

    private GameController controller;
    private Game game;
    private Boolean notInitialized = true;
    private FileManager fileManager= new FileManager();

    /**
     * Constructor for the view
     * @param sceneHandler the scenehandler holding all the scenes
     * @throws IOException Exception, when fxml file could not get loaded (the file of the scenebuilder)
     */
    public EndView(SceneHandler sceneHandler) throws IOException {
        FXMLLoader EndScreen = ResourceHandler.getInstance().getFXML("End");
        EndScreen.setRoot(this);
        EndScreen.load();
        sceneHandler.add("End", this);
        this.game=Settings.getActualGame();

    }

    /**
     * initializes the Endview if called with everything that will show on the vbox
     */
    public void initialize(){
        this.setAlignment(Pos.CENTER);
        if(notInitialized) {

            String losers = game.getPlayersTurn().getTeam();
            String winner = "";
            for (Player player : game.getPlayerOrder()) {
                if (player.getTeam() != losers) {
                   winner = player.getName();
                }
            }
            fileManager.checkHighscore(game,winner);
            ArrayList<String> highscores = fileManager.loadHighscore();
            for (int i=0;i<highscores.size();i=i+4) {
                String print="";
                print += "Name: "+highscores.get(i)+" LevelOfIntelligence: "+
                        highscores.get(i+1)+" AmountOfTurns: "+highscores.get(i+2)+" Team Name: "+highscores.get(i+3);
                Label label = new Label();
                label.setText(print);
                label.setPadding(new Insets(10,0,0,0));
                this.getChildren().add(label);
            }
            notInitialized=false;
            Button button = new Button("Menu");
            button.setOnMouseClicked(event -> controller.goToMenu());
            this.getChildren().add(button);
        }

    }

    /**
     * Sets the Controller of this View
     * @param controller
     */
    public void setController(GameController controller){
        this.controller=controller;
    }

}
