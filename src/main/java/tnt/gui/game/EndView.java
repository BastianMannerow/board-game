package tnt.gui.game;

import javafx.fxml.FXMLLoader;
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
            for (String string:highscores) {
                Label label = new Label();
                label.setText(string);
                this.getChildren().add(label);
            }
            notInitialized=false;
            Button button = new Button("Menu");
            button.setOnMouseClicked(event -> controller.goToMenu());
            this.getChildren().add(button);
        }

        // fileManager.saveHighscore(game,game.getPlayerOrder());TODO need fix error Nullpointer exception
    }

    public void setController(GameController controller){
        this.controller=controller;
    }
    /*
     ArrayList<String> highscores=new ArrayList<>();//= fileManager.loadHighscore();//TODO implement loadHighscore
            for (String score: highscores) {
                Label label = new Label();
                label.setText(score);
                this.getChildren().add(label);
            }
            int NumberinHighscores=1;//TODO when loadHighscore is done determine which number is the newly finished game
            String print = new String(NumberinHighscores + ":    Highscore: " + game.getAmountOfTurns() +
                    "     Turns: " + game.getAmountOfTurns() + "     Winner: ");
            String losers = game.getPlayersTurn().getTeam();
            for (Player player : game.getPlayerOrder()) {
                if (player.getTeam() != losers) {
                    print = print + player.getName();
                    print = print + " ";
                }
            }
            Label label = new Label();
            label.setText(print);


            this.getChildren().add(label);
            notInitialized=false;
            Button button = new Button("Menu");
            button.setOnMouseClicked(event -> controller.goToMenu());
            this.getChildren().add(button);
     */
}
