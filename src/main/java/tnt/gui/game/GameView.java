package tnt.gui.game;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.model.Field;
import tnt.model.Figure;
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
    private DragableObject dragableObject;
    private Map<Field, FieldView> fieldHolder = new HashMap<Field, FieldView>();
    private Map<Figure, FigureView> figureHolder = new HashMap<Figure, FigureView>();
    GameController controller;

    public GameView(SceneHandler sceneHandler, Game game) throws IOException {

        this.game = game;
        dragableObject = makeFigure();
        FXMLLoader gameLoader = ResourceHandler.getInstance().getFXML("game");
        gameLoader.setRoot(this);
        gameLoader.load();
        this.controller = gameLoader.getController();
        controller.setGame(game);
        controller.setSceneHandler(sceneHandler);
        sceneHandler.add("gameView", this);

        this.getChildren().add(dragableObject);

        game.addObserver(this);
        update();
    }

    @Override
    public void update() {

        if (game.placeFigures()){


            dragableObject.setMouseTransparent(true);
            dragableObject.setVisible(false);

            ((VBox) this.getRight()).getChildren().clear();

            int leftFigures  = 0;

            ArrayList<Player> players = game.getPlayerOrder();
            for (Player player: players) {
                for (Figure fig : player.getFigure()) {
                    if (!fig.isPlaced()) {
                        leftFigures++;
                        if (!figureHolder.containsKey(fig)) {
                            FigureView figureView = null;
                            try {
                                figureView = new FigureView(player);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            figureView.setFigure(fig);
                            FigureView finalFigureView = figureView;

                            figureHolder.put(fig, figureView);


                            figureView.setOnDragDetected(event -> {
                                this.getChildren().remove(dragableObject);
                                dragableObject = makeFigure();
                                ((FigureView) dragableObject).setPlayer(player);
                                this.getChildren().add(dragableObject);
                                finalFigureView.setVisible(false);
                                dragableObject.setVisible(true);
                                finalFigureView.startFullDrag();
                            });
                            figureView.setOnMouseDragged((MouseEvent event) -> {
                                dragableObject.setLayoutX(event.getSceneX());
                                dragableObject.setLayoutY(event.getSceneY());
                            });
                            figureView.setOnMouseReleased(event -> {
                                dragableObject.setVisible(false);
                                finalFigureView.setVisible(true);
                            });
                        }

//                        System.out.println("Right: " + (figureHolder.get(fig).hashCode()));
                        ((VBox) this.getRight()).getChildren().add(figureHolder.get(fig));
                    }
                }
            }

            if (leftFigures <=0 ){
                game.startGame();
            }

            if (game.getGameStatus()== Game.GameStatus.RUNNING){
                BuildingLevel1View buildingLevel1View = null;
                try {
                    buildingLevel1View = new BuildingLevel1View();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((VBox) this.getRight()).getChildren().add(buildingLevel1View);
                BuildingLevel1View buildingLevel1View1 = buildingLevel1View;

                buildingLevel1View.setOnDragDetected(event -> {
                    this.getChildren().remove(dragableObject);
                    try {
                        dragableObject = new BuildingLevel1View();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    this.getChildren().add(dragableObject);
//                    buildingLevel1View1.setVisible(false);
                    dragableObject.setVisible(true);
                    buildingLevel1View1.startFullDrag();
                });
                buildingLevel1View.setOnMouseDragged((MouseEvent event) -> {
                    dragableObject.setLayoutX(event.getSceneX());
                    dragableObject.setLayoutY(event.getSceneY());
                });
                buildingLevel1View.setOnMouseReleased(event -> {
                    dragableObject.setVisible(false);
//                    buildingLevel1View1.setVisible(true);
                });
            }
        }

        GridPane gridPane = (GridPane) ((ScrollPane) this.getCenter()).getContent();
        for (int i = 0; i < game.getBoard().getXSize(); i++){
            for(int j = 0 ; j < game.getBoard().getYSize() ; j++){
                System.out.println("B: x: " + i + " y: " + j);
                Field field = game.getBoard().getField(i, j);
                int finalI = i;
                int finalJ = j;
                // delete element before
                gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == finalI && GridPane.getRowIndex(node) == finalJ);
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
                    fieldView.setOnMouseDragReleased(event -> {
                        if (event.getGestureSource() instanceof FigureView) {
                            FigureView source = (FigureView) event.getGestureSource();

                            controller.placeFigure(source.getFigure(), field);

                            dragableObject.setVisible(false);
                            source.setVisible(true);

                        } else if (event.getGestureSource() instanceof BuildingLevel1View) {
//                            BuildingLevel1View source = (BuildingLevel1View) event.getGestureSource();
//                            if (controller.placeFigure(source.getFigure(), field)){
//                                fieldView.getChildren().add(source);
//                            }
//
//                            dragableObject.setVisible(false);
//                            source.setVisible(true);
//                            switch (game.getGameStatus()) {
//                                case SELECT_PLAYER:
//                                    break;
//                                case PLACE_FIGURES:
//                                    break;
//                                case RUNNING:
//    //                                    if (game.isValidMove(source.getFigure(), fieldView.getField())) {
//    //                                        ((Pane) source.getParent()).getChildren().remove(source);
//    //                                        fieldView.getChildren().add(source);
//                                        dragableObject.setVisible(false);
//                                        if(field.getTowerLevel()==0) {
//                                            field.setTowerLevel(1);
//                                        }
//    //                                    }
//                                    break;
//                            }
                        }
                    });
                }

                gridPane.add(fieldHolder.get(field),i, j);
                GridPane.setConstraints(fieldHolder.get(field), i, j);

                FieldView fieldView = fieldHolder.get(field);
                System.out.println("fieldv: " + fieldView.hashCode() + " field: " + field.hashCode());
                ((Label)((VBox)fieldView.getChildren().get(1)).getChildren().get(1)).setText(" " + field.getTowerLevel());
                if (field.getIsFigureHere()){
                    ((Label)((VBox)fieldView.getChildren().get(1)).getChildren().get(0)).setText(" Player here ");
                    System.out.println("fieldv: " + fieldView.hashCode() + " figureview: " + figureHolder.get(field.getFigure()).hashCode());
                    for(Node n : fieldView.getChildren()){
                        System.out.println("A: " + n.hashCode());
                    }
                    if (!fieldView.getChildren().contains(figureHolder.get(field.getFigure()))) {
                        fieldView.getChildren().add(figureHolder.get(field.getFigure()));
                    }
                    // Do anything what is nececarry

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
