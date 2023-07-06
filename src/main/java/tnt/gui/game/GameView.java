package tnt.gui.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import tnt.ResourceHandler;
import tnt.gui.SceneHandler;
import tnt.gui.SizeHandler;
import tnt.model.*;
import tnt.util.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The view of the game
 */
public class GameView extends BorderPane implements Observer {

    private Game game;
    private DragableObject dragableObject;
    static private Map<Field, FieldView> fieldHolder = new HashMap<Field, FieldView>();
    static private Map<Figure, FigureView> figureHolder = new HashMap<Figure, FigureView>();
    static private Map<Integer, BuildingLevel> initBuildingHolder = new HashMap<Integer, BuildingLevel>();
    GameController controller;
    private List<ImageView> highlighted = new ArrayList<>();
    private List<ImageView> highlightedtemp = new ArrayList<>();

    /**
     * Constructor for the game
     * @param sceneHandler the scenehandler holding all the scenes
     * @throws IOException Exception when the fxml file has an error / does not exist
     */
    public GameView(SceneHandler sceneHandler) throws IOException {
        game = Settings.getActualGame();
        dragableObject = makeBuilding();
        FXMLLoader gameLoader = ResourceHandler.getInstance().getFXML("game");
        gameLoader.setRoot(this);
        gameLoader.load();
        this.controller = gameLoader.getController();
        controller.setSceneHandler(sceneHandler);
        sceneHandler.add("gameView", this);

        this.getChildren().add(dragableObject);

        SizeHandler.getInstance().addObserver(this);
        game.addObserver(this);
        update();
    }

    @Override
    public void update() {
        if (game != Settings.getActualGame()){
            game.removeObserver(this);
            game = Settings.getActualGame();
            game.addObserver(this);
            fieldHolder.clear();
            figureHolder.clear();
            initBuildingHolder.clear();
        }
        removeHighlights(highlighted);

        ((ScrollPane) this.getCenter()).setFitToHeight(true);

        updateFiguresRightBar();

        updateBuildings();

        updateFields();

        for(ImageView img : highlighted){
            img.setFitHeight(SizeHandler.getPrefSize());
        }
        for(ImageView img : highlightedtemp){
            img.setFitHeight(SizeHandler.getPrefSize());
        }

        prepareEnd();
    }

    private void updateFields() {

        if (game.isRunnung() || game.placeFigures()) {
            GridPane gridPane = (GridPane) ((ScrollPane) this.getCenter()).getContent();
            gridPane.setPrefHeight(SizeHandler.getPrefSize() * SizeHandler.getNrFieldsY());
            for (int i = 0; i < game.getBoard().getXSize(); i++) {
                for (int j = 0; j < game.getBoard().getYSize(); j++) {
                    updateField(gridPane ,i, j);
                }
            }
        }
    }

    private void updateField(GridPane gridPane, int i, int j) {
        Field field = game.getBoard().getField(i, j);
        if (!fieldHolder.containsKey(field)) {
            FieldView fieldView = null;
            try {
                fieldView = new FieldView(field);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fieldHolder.put(field, fieldView);

            fieldView.setOnMouseDragReleased(event -> {

                removeHighlights(highlightedtemp);

                if (event.getGestureSource() instanceof FigureView) {
                    FigureView source = (FigureView) event.getGestureSource();

                    controller.placeFigure(source.getFigure(), field);


                    dragableObject.setVisible(false);
                    source.setVisible(true);

                } else if (event.getGestureSource() instanceof BuildingLevel) {
                    BuildingLevel source = (BuildingLevel) event.getGestureSource();

                    controller.buildObject(source.getLevel(), field);

                    dragableObject.setVisible(false);
                }
            });
            gridPane.add(fieldHolder.get(field), i, j);
        }
    }

    private void updateBuildings() {
        if (game.isRunnung()){
            for (int level = 1; level <= game.getMaxBuildingLevel() ; level++) {
                updateBuilding(level);
            }
            updateBuilding(-1);

            makeHighlight();
        }
    }

    private void updateBuilding(int level) {
        if (!initBuildingHolder.containsKey(level)) {
            BuildingLevel buildingLevel = null;
            try {
                buildingLevel = new BuildingLevel(level);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            initBuildingHolder.put(level, buildingLevel);
            setDragableEvents(buildingLevel, true);
        }

        BuildingLevel buildingLevel = initBuildingHolder.get(level);
        if (!((VBox) this.getRight()).getChildren().contains(buildingLevel)) {
            ((VBox) this.getRight()).getChildren().add(buildingLevel);
        }
    }

    private void updateFiguresRightBar() {
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
                                figureView = new FigureView(player, fig);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            figureHolder.put(fig, figureView);

                            setDragableEvents(figureView, false);
                        }

                        ((VBox) this.getRight()).getChildren().add(figureHolder.get(fig));
                    }
                }
            }

        }
        ((VBox) this.getRight()).setPrefWidth(SizeHandler.getPrefSize());
    }

    /**
     * Sets the events for a dragable object
     * @param sourceObject the object where the events should be registered
     * @param oldNodeVisible define if the old node should be visible if it gets dragged
     */
    private void setDragableEvents(DragableObject sourceObject, boolean oldNodeVisible) {
        sourceObject.setOnDragDetected(event -> {
            try {
                DragableObject dragFig = sourceObject.copy();
                this.getChildren().remove(dragableObject);
                dragableObject = dragFig;
                this.getChildren().add(dragableObject);
            } catch (IOException e) {
                System.err.println("Copy of dragableObject didnt work");
            }

            if (sourceObject instanceof FigureView) {
                FigureView figureViewF = (FigureView) sourceObject;
                if (game.isMoveMode() && game.getPlayersTurn() == figureViewF.getPlayer()) {
                    for (Field field : figureViewF.getFigure().getValidMoves(game.getBoard())) {
                        makeHighlightField(highlightedtemp, fieldHolder.get(field), "Spielfeld_Highlight");
                    }
                }
            }

            dragableObject.setDisable(true);
            dragableObject.setLayoutX(event.getSceneX() - dragableObject.getBoundsInParent().getWidth()/2);
            dragableObject.setLayoutY(event.getSceneY() - dragableObject.getBoundsInParent().getHeight()/2);
            sourceObject.setVisible(oldNodeVisible);
            dragableObject.setVisible(true);

            sourceObject.startFullDrag();
        });
        sourceObject.setOnMouseReleased(event -> {
            this.dragableObject.setVisible(false);
            sourceObject.setVisible(true);
            removeHighlights(highlightedtemp);

        });
        sourceObject.setOnMouseDragged(event -> {
            this.dragableObject.setLayoutX(event.getSceneX() - this.dragableObject.getBoundsInParent().getWidth()/2);
            this.dragableObject.setLayoutY(event.getSceneY() - this.dragableObject.getBoundsInParent().getHeight()/2);
        });
    }

    /**
     * highlight the fields with figures could get moved/ where the dragged figure could get placed / where a building could be placed
     */
    private void makeHighlight() {
        if (game.getGameStatus() == Game.GameStatus.MOVE_FIGURE) {

            for (Figure fig : game.getPlayersTurn().getFigure()) {
                FieldView fieldv = fieldHolder.get(game.getBoard().getField(fig.getX(), fig.getY()));
                makeHighlightField(highlighted, fieldv, "Spielfeld_Highlight");
            }
        }

        if (game.getGameStatus() == Game.GameStatus.BUILD) {
            for (Field field :game.getLastMovedFigure().getValidBuilds(game.getBoard())){
                FieldView fieldv = fieldHolder.get(field);
                makeHighlightField(highlighted, fieldv, "Spielfeld_Highlight");
            }

        }
    }

    /**
     * remove higlights (temporary or a little bit more static)
     * @param list the list holding highlight to be removed
     */
    private void removeHighlights(List<ImageView> list) {
        for (ImageView image: list){
            ((StackPane) image.getParent()).getChildren().remove(image);
        }
        list.clear();
    }

    /**
     * Removes the given highlight images and their display from the FieldView.
     *
     * @param highlightImages The list of highlight images to be removed.
     * @param fieldView The FieldView from which the highlight images should be removed.
     */
    public void callRemoveHighlights(List<ImageView> highlightImages, FieldView fieldView) {
        removeHighlights(highlightImages);
        fieldView.getChildren().removeAll(highlightImages);
    }


    /**
     * Highlights a specific field with the given layout
     * @param list the list (temporary or not) where the highlight should be added (for removal)
     * @param fieldv the field view which should be highlighted
     * @param picture the name of the picture, which is used to highlight the field
     */
    private void makeHighlightField(List list, FieldView fieldv, String picture) {
        try {
            ImageView highlight = new ImageView();
            highlight.setImage(ResourceHandler.getInstance().getImage(picture));
            highlight.setPreserveRatio(true);
            highlight.setDisable(true);
            highlight.setFitHeight(SizeHandler.getPrefSize());
//            highlighted.put(fieldv, highlight);
            list.add(highlight);
            ((StackPane) fieldv.getChildren().get(0)).getChildren().add(highlight);
        } catch (Exception e) {
            System.err.println("Could not load image " + e);
            e.printStackTrace();
        }
    }


    /**
     * making a building level 1 (only for initializing the dragable object)
     * @return the building level
     */
    private BuildingLevel makeBuilding() {
        BuildingLevel buildingView;
        try {
            buildingView = new BuildingLevel(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return buildingView;
    }

    /**
     * getting the view of the given field
     * @param fig the figure the view is looked for
     * @return the view of the figure
     */
    static FigureView getFigureView(Figure fig){
        return figureHolder.get(fig);
    }

    private void prepareEnd(){
        if(game.getGameStatus() == Game.GameStatus.GAME_OVER) {
            controller.goToEnd();
        }
    }
}
