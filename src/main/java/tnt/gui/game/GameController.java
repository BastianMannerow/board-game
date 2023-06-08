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
                if (!field.getIsFigureHere() && game.getPlayersTurn().getFigure().contains(figure)){
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
            case RUNNING:
                if (game.getPlayersTurn().getFigure().contains(figure) && figure.getValidMoves(game.getBoard()).contains(field)){
                    game.getPlayersTurn().executeMove(field,game.getBoard(),figure);

                    // Todo: first handle build stuff
                    game.nextPlayersTurn();
                    return true;
                } else {
                    return false;
                }
        }
        return false;
    }
}
