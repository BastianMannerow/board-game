package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.model.Field;
import tnt.model.Game;
import tnt.model.Player;
import tnt.util.Observer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GameView extends BorderPane implements Observer {

    private Game game;
    private Map<Field, HBox> fieldHolder = new HashMap<Field, HBox>();

    public GameView(SceneHandler sceneHandler, Game game) throws IOException {

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
    }

    @Override
    public void update() {

        GridPane gridPane = (GridPane) ((ScrollPane) this.getCenter()).getContent();
        for (int i = 0; i < game.getBoard().getXSize(); i++){
            for(int j = 0 ; j < game.getBoard().getYSize() ; j++){
                Field field = game.getBoard().getField(i, j);

                if (!fieldHolder.containsKey(field)){
                    HBox hBox = new HBox();
                    fieldHolder.put(field, hBox);
                    FXMLLoader fieldLayout = ResourceHandler.getInstance().getFXML("field");
                    fieldLayout.setRoot(hBox);
                    try {
                        fieldLayout.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    gridPane.add(hBox,i, j);
                    GridPane.setConstraints(hBox, i, j);
                }

                HBox hBox = fieldHolder.get(field);
//                ((Button)hBox.getChildren().get(0)).setText("B: " + i + ", " + j);
            }
        }
    }


}
