package tnt.model;
import tnt.model.Validator;
import java.util.Random;

import java.util.ArrayList;

public class ArtificialPlayer {
    /**
     * Random decision making.
     *
     * @param player the active player
     * @param board the current status of the board
     */
    public void easyAI(Player player, Board board){
        ArrayList<Figure> figureList = player.getFigure();
        int randomNumber = new Random().nextInt(figureList.size());
        Figure randomFigure =  figureList.get(randomNumber);
        ArrayList<Field>  possibleMoves = Validator.getValidMoves(randomFigure, board);
        int randomNumber = new Random().nextInt(possibleMoves.size());
        Field randomMove = possibleMoves.get(randomNumber);
        int x = randomMove.getX();
        int y = randomMove.getY();
        randomMove.setIsFigureHere(true);
        randomFigure.setX(x);
        randomFigure.setY(y);

        // Anschließend muss noch gebaut werden.
    }

    /**
     * Greedy decision making.
     *
     * @param player the active player
     * @param board the current status of the board
     */
    public void mediumAI(Player player, Board board){
    }

    /**
     * Greedy decision making with some tweeks.
     *
     * @param player the active player
     * @param board the current status of the board
     */
    public void hardAI(Player player, Board board){
    }
}
