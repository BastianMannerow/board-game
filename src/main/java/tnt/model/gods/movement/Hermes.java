package tnt.model.gods.movement;
import tnt.model.Board;
import tnt.model.Field;
import tnt.model.Figure;
import java.util.ArrayList;

public class Hermes {
    /**
     * Gets all reachable fields
     *
     * @param figure the figure, which the player decided to move
     * @param board the board the game is taking place on
     *
     * @return all reachable fields
     */
    public static ArrayList<Field> getValidMove(Figure figure, Board board){
        ArrayList<Field> possibleFields = new ArrayList<Field>();
        int x = figure.getX();
        int y = figure.getY();
        int figureHeight = board.getField(x,y).getTowerLevel();

        for (int i = x; i <= board.getXSize(); i++) {
            if(!board.getField(i, y).getIsFigureHere() && figureHeight == board.getField(i, y).getTowerLevel()) {
                possibleFields.add(board.getField(i, y));
            }
        }

        for (int i = x-1; i >= 0; i--) {
            if(!board.getField(i, y).getIsFigureHere() && figureHeight == board.getField(i, y).getTowerLevel()) {
                possibleFields.add(board.getField(i, y));
            }
        }

        for (int i = y+1; i <= board.getYSize(); i++) {
            if(!board.getField(x, i).getIsFigureHere() && figureHeight == board.getField(x, i).getTowerLevel()) {
                possibleFields.add(board.getField(x, i));
            }
        }

        for (int i = y-1; i >= 0; i--) {
            if(!board.getField(x, i).getIsFigureHere() && figureHeight == board.getField(x, i).getTowerLevel()) {
                possibleFields.add(board.getField(x, i));
            }
        }

        return possibleFields;
    }
}