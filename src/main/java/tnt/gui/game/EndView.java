package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.model.Game;
import tnt.model.Player;
import tnt.model.Settings;
import tnt.util.Observer;


import java.io.IOException;

public class EndView extends VBox {

    private GameController controller;
    private Game game;
    private Boolean notInitialized = true;

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
        if(notInitialized) {
            String print = new String("Highscore: " + game.getAmountOfTurns() +
                    " Turns: " + game.getAmountOfTurns() + " Winner: ");
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
        }
    }

}