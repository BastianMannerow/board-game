package tnt.gui.game;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.model.Field;
import tnt.model.Game;
import tnt.model.Player;
import tnt.util.Observer;

//import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GameView extends BorderPane implements Observer {

    private Game game;
    private Circle dragCircle  = makeCircle();
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

        dragCircle.setMouseTransparent(true);
        dragCircle.setVisible(false);


        this.getChildren().add(dragCircle);

        Circle circle = new Circle();
        circle.radiusProperty().set(20);
        ((VBox) this.getRight()).getChildren().add(circle);
        circle.setFill(Color.RED);
        circle.setOnDragDetected(event -> {
            circle.setVisible(false);
            dragCircle.setVisible(true);
            dragCircle.setFill(Color.GREEN);
            circle.startFullDrag();
        });
        circle.setOnMouseDragged((MouseEvent event) -> {
            dragCircle.setCenterX(event.getSceneX());
            dragCircle.setCenterY(event.getSceneY());
        });
        circle.setOnMouseReleased(event -> {
            dragCircle.setVisible(false);
            circle.setVisible(true);
        });



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
                    hBox.setOnMouseDragReleased(event -> {
                        Circle source = (Circle) event.getGestureSource();
                        ((Pane) source.getParent()).getChildren().remove(source);
                        hBox.getChildren().add(source);
                        dragCircle.setVisible(false);
                        source.setVisible(true);
                    });
                    gridPane.add(hBox,i, j);
                    GridPane.setConstraints(hBox, i, j);
                }

                HBox hBox = fieldHolder.get(field);
                ((Label)((VBox)hBox.getChildren().get(1)).getChildren().get(1)).setText(" " + field.getTowerLevel());
                if (field.getIsFigureHere()){
                    ((Label)((VBox)hBox.getChildren().get(1)).getChildren().get(0)).setText(" Player here ");
                } else {
                    ((Label)((VBox)hBox.getChildren().get(1)).getChildren().get(0)).setText("");
                }
            }
        }
    }
    private Circle makeCircle() {
        Circle circle = new Circle();
        circle.radiusProperty().set(20);
        return circle;
    }


}
