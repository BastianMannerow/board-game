package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
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
    static private HashMap<Integer, BuildingLevel> initBuildingHolder = new HashMap<Integer, BuildingLevel>();
    GameController controller;
//    ArrayList<ImageView> highlighted
    private Map<FieldView, ImageView> highlighted = new HashMap<>();

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
        for (FieldView fieldv: highlighted.keySet()){
            ((StackPane) highlighted.get(fieldv).getParent()).getChildren().remove(highlighted.get(fieldv));
        }
        highlighted.clear();

        if (game.placeFigures()){


            dragableObject.setMouseTransparent(true);
            dragableObject.setVisible(false);

            // Todo: dont add the init figures every update anew
            ((VBox) this.getRight()).getChildren().clear();

            ArrayList<Player> players = game.getPlayerOrder();
            for (Player player: players) {
                for (Figure fig : player.getFigure()) {
                    if (!fig.isPlaced()) {
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
                                dragableObject.setLayoutX(event.getSceneX() - dragableObject.getBoundsInParent().getWidth()/2);
                                dragableObject.setLayoutY(event.getSceneY() - dragableObject.getBoundsInParent().getHeight()/2);
                            });
                        }

                        ((VBox) this.getRight()).getChildren().add(figureHolder.get(fig));
                    }
                }
            }

        }

        if (game.isRunnung()){
            for (int level = 1; level <=3 ; level++) {
                if (!initBuildingHolder.containsKey(level)) {
                    BuildingLevel buildingLevel = null;
                    try {
                        buildingLevel = new BuildingLevel(level);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    initBuildingHolder.put(level, buildingLevel);

                    // Drag and drop for figure handling set here
                    BuildingLevel finalBuildingLevel = buildingLevel;
                    int finalLevel = level;
                    buildingLevel.setOnDragDetected(event -> {
                        try {
                            BuildingLevel dragFig = (BuildingLevel) finalBuildingLevel.copy();
                            this.getChildren().remove(dragableObject);
                            dragableObject = dragFig;
                            this.getChildren().add(dragableObject);
                        } catch (IOException e) {
                            System.out.println("Copy of building level " + finalLevel + " didnt work!");
                        }

                        dragableObject.setVisible(true);
                        dragableObject.setDisable(true);

                        finalBuildingLevel.startFullDrag();
                    });
                    buildingLevel.setOnMouseReleased(event -> {
                        dragableObject.setVisible(false);

                    });
                    buildingLevel.setOnMouseDragged(event -> {
                        dragableObject.setLayoutX(event.getSceneX() - dragableObject.getBoundsInParent().getWidth()/2);
                        dragableObject.setLayoutY(event.getSceneY() - dragableObject.getBoundsInParent().getHeight()/2);
                    });
                }

                BuildingLevel buildingLevel = initBuildingHolder.get(level);
                if (!((VBox) this.getRight()).getChildren().contains(buildingLevel)) {
                    ((VBox) this.getRight()).getChildren().add(buildingLevel);
                }
            }
            if (!initBuildingHolder.containsKey(-1)) {
                BuildingLevel buildingKuppel = null;
                try {
                    buildingKuppel = new BuildingLevel(-1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                initBuildingHolder.put(-1, buildingKuppel);

                // Drag and drop for figure handling set here
                BuildingLevel finalBuildingKuppel = buildingKuppel;
                buildingKuppel.setOnDragDetected(event -> {
                    try {
                        BuildingLevel dragFig = (BuildingLevel) finalBuildingKuppel.copy();
                        this.getChildren().remove(dragableObject);
                        dragableObject = dragFig;
                        this.getChildren().add(dragableObject);
                    } catch (IOException e) {
                        System.out.println("Copy of building: Kuppel didnt work!");
                    }

                    dragableObject.setVisible(true);
                    dragableObject.setDisable(true);

                    finalBuildingKuppel.startFullDrag();
                });
                buildingKuppel.setOnMouseReleased(event -> {
                    dragableObject.setVisible(false);

                });
                buildingKuppel.setOnMouseDragged(event -> {
                    dragableObject.setLayoutX(event.getSceneX() - dragableObject.getBoundsInParent().getWidth()/2);
                    dragableObject.setLayoutY(event.getSceneY() - dragableObject.getBoundsInParent().getHeight()/2);
                });
            }
            BuildingLevel buildingLevel = initBuildingHolder.get(-1);
            if (!((VBox) this.getRight()).getChildren().contains(buildingLevel)) {
                ((VBox) this.getRight()).getChildren().add(buildingLevel);
            }


            makeHighlight();
        }

        if (game.isRunnung() || game.placeFigures()) {
            GridPane gridPane = (GridPane) ((ScrollPane) this.getCenter()).getContent();
            for (int i = 0; i < game.getBoard().getXSize(); i++) {
                for (int j = 0; j < game.getBoard().getYSize(); j++) {
                    Field field = game.getBoard().getField(i, j);
                    int finalI = i;
                    int finalJ = j;
                    if (!fieldHolder.containsKey(field)) {
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

                                if (controller.placeFigure(source.getFigure(), field)){

                                }

                                dragableObject.setVisible(false);
                                source.setVisible(true);

                            } else if (event.getGestureSource() instanceof BuildingLevel) {
                                BuildingLevel source = (BuildingLevel) event.getGestureSource();

                                controller.buildObject(source.getLevel(), field);

                                dragableObject.setVisible(false);
                            }
                        });
                        gridPane.add(fieldHolder.get(field), i, j);
                        GridPane.setConstraints(fieldHolder.get(field), i, j);
                    }
                }
            }
        }
    }

    private void makeHighlight() {
        if (game.getGameStatus() == Game.GameStatus.MOVE_FIGURE) {

            for (Figure fig : game.getPlayersTurn().getFigure()) {
                FieldView fieldv = fieldHolder.get(game.getBoard().getField(fig.getX(), fig.getY()));
                makeHighlightField(fieldv, "Spielfeld_Highlight");
            }
        }

        if (game.getGameStatus() == Game.GameStatus.BUILD) {
            for (Field field :game.getLastMovedFigure().getValidBuilds(game.getBoard())){
                FieldView fieldv = fieldHolder.get(field);
                makeHighlightField(fieldv, "Spielfeld_Highlight");
            }

        }
    }


    private void makeHighlightField(FieldView fieldv, String picture) {
        try {
            ImageView highlight = new ImageView();
            highlight.setImage(ResourceHandler.getInstance().getImage(picture));
            highlight.setPreserveRatio(true);
            highlight.setFitHeight(80);
            highlight.setDisable(true);
            highlighted.put(fieldv, highlight);
            ((StackPane) fieldv.getChildren().get(0)).getChildren().add(highlight);
        } catch (Exception e) {
            System.out.println("Could not load image " + e);
            e.printStackTrace();
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
