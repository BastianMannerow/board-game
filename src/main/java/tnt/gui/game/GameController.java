package tnt.gui.game;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import tnt.gui.SceneHandler;
import tnt.model.Field;
import tnt.model.Figure;
import tnt.model.Game;

/**
 * The controller for the game
 */
public class GameController{

    private Game game;
    private SceneHandler sceneHandler;

    /**
     * Setter for the game / model
     * @param game the game controlled by this controller
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Setter for the scene handler, so the controller can change the view
     * @param sceneHandler the scenehandler, for changing views
     */
    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }

    /**
     * places a figure on the field if possible
     * @param figure the figure to be placed
     * @param field the field the figure should be placed to
     * @return if the placing was successful
     */
    public boolean placeFigure(Figure figure, Field field) {
        switch (game.getGameStatus()) {
            case SELECT_PLAYER:
                return false;
            case PLACE_FIGURES:
                if (!field.getIsFigureHere() && game.getPlayersTurn().getFigure().contains(figure) && !figure.isPlaced()){
                    figure.setX(field.getX());
                    figure.setY(field.getY());
                    figure.setPlaced();
                    field.setFigure(figure);
                    if (game.getPlayersTurn().allFiguresPlaced()){
                        game.nextPlayersTurn();
                    }
                    if (game.getPlayersTurn().allFiguresPlaced()){
                        game.startGame();
                    }
                    return true;
                } else {
                    return false;
                }
            case MOVE_FIGURE:
                if (game.getPlayersTurn().getFigure().contains(figure) && figure.getValidMoves(game.getBoard()).contains(field)){
                    game.setLastMovedFigure(figure);
                    game.getPlayersTurn().executeMove(field,game.getBoard(), figure);
                    game.setBuildMode();
                    return true;
                } else {
                    return false;
                }
            case BUILD:
                return false;
        }
        return false;
    }

    /**
     * build on the field if possible
     * @param buildLevel the level of the level to be build
     * @param field the field where the building should be placed
     * @return if the build was successful
     */
    public boolean buildObject(int buildLevel, Field field) {
        if (game.isBuildMode()) {
            if (game.getLastMovedFigure().getValidBuilds(game.getBoard()).contains(field)) {
                if (field.getTowerLevel() > 0 && buildLevel == -1) {
                    field.setTowerComplete(true);
                }
                else if (field.getTowerLevel() == buildLevel - 1) {
                    field.setTowerLevel(buildLevel);
                }
                else {
                    return false;
                }
                game.nextPlayersTurn();
                game.setMoveMode();
                return true;
            }
        }
        return false;
    }
}
