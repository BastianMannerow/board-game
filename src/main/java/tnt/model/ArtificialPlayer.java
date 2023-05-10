package tnt.model;
import tnt.model.interfaces.Gods;

import java.util.Random;
import java.util.ArrayList;

public class ArtificialPlayer extends Player{
    public ArtificialPlayer(String levelOfIntelligence, String name, String colour, ArrayList<Figure> figures, ArrayList<Gods> god) {
        super(levelOfIntelligence, name, colour, figures, god);
    }

    public void artificialPlayerTurn(Board board){
        if(getLevelOfIntelligence().equals("Easy")){
            easyAI(board);
        }
        else if(getLevelOfIntelligence().equals("Medium")){
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
    public void easyAI(Board board){

        // Execute random Movement
        ArrayList<Figure> figureList = getFigure();
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
        randomMove.setIsFigureHere(true);
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
