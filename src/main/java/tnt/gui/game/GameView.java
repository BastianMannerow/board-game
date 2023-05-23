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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GameView extends BorderPane implements Observer {

    private Game game;
    private FigureView dragFigure;
    private Map<Field, FieldView> fieldHolder = new HashMap<Field, FieldView>();

    public GameView(SceneHandler sceneHandler, Game game) throws IOException {

        this.game = game;
        dragFigure  = makeFigure();
        FXMLLoader gameLoader = ResourceHandler.getInstance().getFXML("game");
        gameLoader.setRoot(this);
        gameLoader.load();
        GameController controller = gameLoader.getController();
        controller.setGame(game);
        controller.setSceneHandler(sceneHandler);
        sceneHandler.add("gameView", this);

        this.getChildren().add(dragFigure);

        game.addObserver(this);
        update();
    }

    @Override
    public void update() {

        if (game.placeFigures()){


            dragFigure.setMouseTransparent(true);
            dragFigure.setVisible(false);
            ((VBox) this.getRight()).getChildren().clear();

            int leftFigures  = 0;

            ArrayList<Player> players = game.getPlayerOrder();
            for (Player player: players) {
                for (int i = 0; i < player.figuresLeftToSet(); i++) {
                    FigureView figureView = null;
                    try {
                        figureView = new FigureView(player);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
//            figureView.radiusProperty().set(20);
                    leftFigures++;
                    ((VBox) this.getRight()).getChildren().add(figureView);
                    FigureView finalFigureView = figureView;

                    figureView.setOnDragDetected(event -> {
                        finalFigureView.setVisible(false);
                        dragFigure.setVisible(true);
                        finalFigureView.startFullDrag();
                    });
                    figureView.setOnMouseDragged((MouseEvent event) -> {
                        dragFigure.setPlayer(player);
                        dragFigure.setLayoutX(event.getSceneX());
                        dragFigure.setLayoutY(event.getSceneY());
                    });
                    figureView.setOnMouseReleased(event -> {
                        dragFigure.setVisible(false);
                        finalFigureView.setVisible(true);
                    });
                }
            }
            if (leftFigures <=0 ){
                game.startGame();
            }
        }

        GridPane gridPane = (GridPane) ((ScrollPane) this.getCenter()).getContent();
        for (int i = 0; i < game.getBoard().getXSize(); i++){
            for(int j = 0 ; j < game.getBoard().getYSize() ; j++){
                Field field = game.getBoard().getField(i, j);
                if (!fieldHolder.containsKey(field)){
                    FieldView fieldView = new FieldView(field);
                    fieldHolder.put(field, fieldView);
                    FXMLLoader fieldLayout = ResourceHandler.getInstance().getFXML("field");
                    fieldLayout.setRoot(fieldView);
                    try {
                        fieldLayout.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int finalI = i;
                    int finalJ = j;
                    fieldView.setOnMouseDragReleased(event -> {
                        FigureView source = (FigureView) event.getGestureSource();
                        switch (game.getGameStatus()){
                            case SELECT_PLAYER:
                                break;
                            case PLACE_FIGURES:
                                if (game.isValidPlacement(fieldView.getField()) && !(source.getParent() instanceof FieldView)) {
                                    ((Pane) source.getParent()).getChildren().remove(source);
                                    fieldView.getChildren().add(source);
                                    dragFigure.setVisible(false);
                                    source.setVisible(true);
                                    source.getPlayer().addFigure(finalI, finalJ);
                                    fieldView.getField().setIsFigureHere(true);
                                }
                                break;
                            case RUNNING:
                                if (game.isValidMove(source.getFigure(), fieldView.getField())){
                                    ((FieldView) source.getParent()).getField().setIsFigureHere(false);
                                    ((HBox) source.getParent()).getChildren().remove(source);
                                    fieldView.getChildren().add(source);
                                    dragFigure.setVisible(false);
                                    source.setVisible(true);
                                    source.getPlayer().addFigure(finalI, finalJ);
                                    fieldView.getField().setIsFigureHere(true);
                                }
                                break;
                        }
                    });
                    gridPane.add(fieldView,i, j);
                    GridPane.setConstraints(fieldView, i, j);
                }

                FieldView fieldView = fieldHolder.get(field);
                ((Label)((VBox)fieldView.getChildren().get(1)).getChildren().get(1)).setText(" " + field.getTowerLevel());
                if (field.getIsFigureHere()){
                    ((Label)((VBox)fieldView.getChildren().get(1)).getChildren().get(0)).setText(" Player here ");
                } else {
                    ((Label)((VBox)fieldView.getChildren().get(1)).getChildren().get(0)).setText("");
                }
            }
        }
    }
    private FigureView makeFigure() {
        FigureView figureView;
        try {
            figureView = new FigureView(game.getPlayersTurn());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return figureView;
    }


}
