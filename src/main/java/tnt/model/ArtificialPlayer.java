package tnt.model;
import java.util.Random;
import java.util.ArrayList;

public class ArtificialPlayer {

    // Ändern, dass von Player geerbt wird.

    /**
     * Random decision making.
     *
     * @param player the active player
     * @param board the current status of the board
     */

    /*
    public void easyAI(Player player, Board board){
        ArrayList<Figure> figureList = player.getFigure();
        int randomFigureNumber = new Random().nextInt(figureList.size());
        Figure randomFigure =  figureList.get(randomFigureNumber);
        ArrayList<Field>  possibleMoves = Validator.getValidMoves(randomFigure, board);
        int randomFieldNumber = new Random().nextInt(possibleMoves.size());
        Field randomMove = possibleMoves.get(randomFieldNumber);
        int x = randomMove.getX();
        int y = randomMove.getY();
        randomMove.setIsFigureHere(true);
        randomFigure.setX(x);
        randomFigure.setY(y);

        // Anschließend muss noch gebaut werden.
    }
    */

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