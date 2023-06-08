package tnt.model.gods.movement;
import tnt.model.Board;
import tnt.model.Field;
import tnt.model.Figure;
import tnt.model.Player;
import tnt.model.interfaces.Gods;

import java.util.ArrayList;


public class Charon implements Gods{

    /**
     * @param figure the players figure
     * @param board the board the game is be played on
     * @param players all players
     *
     * @return list of opponents figures you could move
     */
    public static ArrayList<Figure> getValidMove(Figure figure, Board board, ArrayList<Player> players) {
        ArrayList<Figure> possibleFigures = new ArrayList<Figure>();
        int boardX = board.getXSize();
        int boardY = board.getYSize();
        int figureX = figure.getX();
        int figureY = figure.getY();
        if (!board.getField(boardX - figureX, boardY - figureY).getIsFigureHere()) {
            ArrayList<Field> nearbyFields = figure.getValidMoves(board);
            for (int i = 1; i < players.size(); i++) {
                Player passivePlayer = players.get(i);
                ArrayList<Figure> passiveFigures = passivePlayer.getFigure();
                for (Figure passiveFigure : passiveFigures) {
                    int passiveX = passiveFigure.getX();
                    int passiveY = passiveFigure.getY();
                    for (Field field : nearbyFields) {
                        if (passiveX == field.getX() && passiveY == field.getY()) {
                            possibleFigures.add(figure);
                        }
                    }
                }
            }
        }
        return possibleFigures;
    }

    /**
     * Executes the movement
     *
     * @param playerFigure the players figure executing the movement
     * @param opponentFigure the opponents figure to be moved
     * @param board the board where the game is taking place
     */
    public static void executeMove(Figure playerFigure, Figure opponentFigure, Board board){
        int boardX = board.getXSize();
        int boardY = board.getYSize();
        int figureX = playerFigure.getX();
        int figureY = playerFigure.getY();
        board.getField(opponentFigure.getX(), opponentFigure.getY()).figureLeft();
        opponentFigure.setX(boardX - figureX);
        opponentFigure.setY(boardY - figureY);
        board.getField(boardX - figureX, boardY - figureY).setFigure(opponentFigure);
    }
}
