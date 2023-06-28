package tnt.model;
import tnt.model.interfaces.Gods;

import java.util.Random;
import java.util.ArrayList;

public class ArtificialPlayer{


    public void artificialPlayerTurn(Board board, Player player){
        if(player.getLevelOfIntelligence().equals("Easy")){
            //easyAI(board);
            int i = 9;
        }
        else if(player.getLevelOfIntelligence().equals("Medium")){
            mediumAI(board);
        }
        else{
            hardAI(board);
        }
    }

    /**
     * Random decision making.
     *
     * @param board the current status of the board
     */
    public void easyAI(Board board, Player player){

        // Execute random Movement
        ArrayList<Figure> figureList = player.getFigure();
        ArrayList<Field>  possibleMoves = new ArrayList<>();
        for (Figure figure : figureList) {
            possibleMoves.addAll(figure.getValidMoves(board));
        }

        int randomFigureNumber = new Random().nextInt(possibleMoves.size());
        Figure randomFigure =  figureList.get(randomFigureNumber);

        int randomFieldNumber = new Random().nextInt(possibleMoves.size());
        Field randomMove = possibleMoves.get(randomFieldNumber);
        int x = randomMove.getX();
        int y = randomMove.getY();
        randomFigure.setX(x);
        randomFigure.setY(y);

        // Anschließend muss noch gebaut werden.
    }

    /**
     * Greedy decision making.
     *
     * @param board the current status of the board
     */
    public void mediumAI(Board board){
    }

    /**
     * Greedy decision making with some tweeks.
     *
     * @param board the current status of the board
     */
    public void hardAI(Board board){
    }
}
