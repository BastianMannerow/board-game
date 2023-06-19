package tnt.gui.game;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import tnt.gui.SceneHandler;
import tnt.model.Field;
import tnt.model.Figure;
import tnt.model.Game;


public class GameController{

//    @FXML
//    private void initialize(){
//    }
    private Game game;
    private SceneHandler sceneHandler;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setSceneHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
    }

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
