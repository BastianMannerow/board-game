package tnt.gui.game3d;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import tnt.gui.SceneHandler;

import tnt.model.Game;
import tnt.util.Observer;


import static javafx.geometry.Pos.CENTER;

/**
 * The view of the game
 */
public class GameView3D extends BorderPane implements Observer {

    private Game game;

    public GameView3D(SceneHandler sceneHandler, Game game){
        this.game = game;
        VBox left = new VBox();
        left.setAlignment(CENTER);
        left.setSpacing(10);
        left.getChildren().addAll(new Button("Button 1"), new Button("Button 2"), new Button("Button 3"));
        setLeft(left);

        Box box = new Box(70, 50, 20);

        Group group = new Group(box);

        SubScene subScene = new SubScene(group, 300, 300, true, SceneAntialiasing.BALANCED);
        subScene.widthProperty().bind(sceneHandler.getScene().widthProperty());
        subScene.heightProperty().bind(sceneHandler.getScene().widthProperty());

        subScene.setFill(Color.rgb(80, 80, 80));
        Camera camera = new PerspectiveCamera();
        subScene.setCamera(camera);

        box.translateXProperty().bind(subScene.widthProperty().divide(2));
        box.translateYProperty().bind(subScene.heightProperty().divide(2));

        setCenter(subScene);

        sceneHandler.add("gameView3D", this);
    }

    @Override
    public void update() {

    }
}
