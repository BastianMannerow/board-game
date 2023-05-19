package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.model.Game;
import tnt.util.Observer;

import java.io.IOException;


public class GameView extends BorderPane implements Observer {

//    private Button btnBack = new Button("Back!");
//
//    private Button btnCounterUp = new Button("+");
//    private Button btnCounterDown = new Button("-");
//    private Label lblCounter = new Label();
    private Game game;

    public GameView(SceneHandler sceneHandler, Game game) throws IOException {
//        setAlignment(Pos.CENTER);
//        setSpacing(10);
//        getChildren().addAll(btnCounterUp, lblCounter, btnCounterDown, btnBack);
        this.game = game;
        FXMLLoader gameLoader = ResourceHandler.getInstance().getFXML("game");
        gameLoader.setRoot(this);
        gameLoader.load();
        GameController controller = gameLoader.getController();
        controller.setGame(game);
        controller.setSceneHandler(sceneHandler);
        sceneHandler.add("gameView", this);

        game.addObserver(this);
        update();
        GridPane gridPane = (GridPane) ((ScrollPane) this.getCenter()).getContent();
        for (int i = 0; i < game.getBoard().getYSize(); i++){
            for(int j = 0 ; j < game.getBoard().getXSize() ; j++){
                HBox tpane = new HBox();
                FXMLLoader fieldLayout = ResourceHandler.getInstance().getFXML("field");
                fieldLayout.setRoot(tpane);
                fieldLayout.load();
                gridPane.add(tpane,j, i);
                GridPane.setConstraints(tpane, j, i);

                ((Button)tpane.getChildren().get(0)).setText("B: " + i + ", " + j);
            }
        }


    }

    @Override
    public void update() {
    }


}
