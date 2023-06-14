package tnt.gui.game;

import javafx.fxml.FXMLLoader;
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
    static private Map<Field, FieldView> fieldHolder = new HashMap<Field, FieldView>();
    static private Map<Figure, FigureView> figureHolder = new HashMap<Figure, FigureView>();
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

            // Todo: dont add the init figures every update anew
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

                            // Drag and drop for figure handling set here
                            figureView.setOnDragDetected(event -> {
                                try {
                                    FigureView dragFig = finalFigureView.copy();
                                    this.getChildren().remove(dragableObject);
                                    dragableObject = dragFig;
                                    this.getChildren().add(dragableObject);
                                } catch (IOException e) {
                                    System.out.println("Copy of figureView didnt work!");
                                }
                                finalFigureView.setVisible(false);
                                dragableObject.setVisible(true);
                                dragableObject.setDisable(true);

                                finalFigureView.startFullDrag();
                            });
                            figureView.setOnMouseReleased(event -> {
                                dragableObject.setVisible(false);
                                finalFigureView.setVisible(true);
                            });
                            figureView.setOnMouseDragged(event -> {
                                dragableObject.setLayoutX(event.getSceneX());
                                dragableObject.setLayoutY(event.getSceneY());
                            });
                        }

                        ((VBox) this.getRight()).getChildren().add(figureHolder.get(fig));
                    }
                }
            }

            if (game.isRunnung()){
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
                Field field = game.getBoard().getField(i, j);
                int finalI = i;
                int finalJ = j;
                if (!fieldHolder.containsKey(field)){
                    FieldView fieldView = null;
                    try {
                        fieldView = new FieldView(field);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    fieldHolder.put(field, fieldView);

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
                    gridPane.add(fieldHolder.get(field),i, j);
                    GridPane.setConstraints(fieldHolder.get(field), i, j);
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

    static FigureView getFigureView(Figure fig){
        return figureHolder.get(fig);
    }

    static FieldView getFieldView(Field field){
        return fieldHolder.get(field);
    }


}
