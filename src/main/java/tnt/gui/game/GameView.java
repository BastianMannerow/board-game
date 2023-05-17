package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
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

//        System.out.println(gameLoader.getController().toString());
        game.addObserver(this);
        update();
        HBox tpane = new HBox();
        FXMLLoader fieldLayout = ResourceHandler.getInstance().getFXML("field");
        fieldLayout.setRoot(tpane);
        ((GridPane) this.getCenter()).add(tpane,0, 0);
        fieldLayout.load();
        System.out.println(tpane.getChildren().get(0));

    }

    @Override
    public void update() {
    }


}
