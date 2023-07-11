package tnt.model;

import java.util.Random;
import java.util.ArrayList;
import tnt.model.ExecuteGameInputs;

public class ArtificialPlayer{
    /**
     * Random decision-making.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn
     */
    public void easyAI(Board board, Player player){
        // execute random movement
        ArrayList<Figure> figureList = player.getFigure();
        ArrayList<Field>  possibleMoves = new ArrayList<>();
        int randomFigureMoveNumber = new Random().nextInt(figureList.size());
        Figure randomMoveFigure =  figureList.get(randomFigureMoveNumber);
        possibleMoves.addAll(randomMoveFigure.getValidMoves(board));
        int randomMoveFieldNumber = new Random().nextInt(possibleMoves.size());
        Field randomMove = possibleMoves.get(randomMoveFieldNumber);
        ExecuteGameInputs.placeFigure(randomMoveFigure, randomMove);

        // execute random building
        ArrayList<Field>  possibleBuilds = new ArrayList<>();
        int randomFigureBuildNumber = new Random().nextInt(figureList.size());
        Figure randomBuildFigure =  figureList.get(randomFigureBuildNumber);
        possibleBuilds.addAll(randomBuildFigure.getValidBuilds(board));
        int randomBuildFieldNumber = new Random().nextInt(possibleMoves.size());
        Field randomBuild = possibleBuilds.get(randomBuildFieldNumber);
        ExecuteGameInputs.placeFigure(randomBuildFigure, randomBuild);
    }

    /**
     * Greedy decision-making.
     *
     * @param board the current status of the board
     */
    public void mediumAI(Board board){
    }

    /**
     * Greedy decision -aking with some tweaks.
     *
     * @param board the current status of the board
     */
    public void hardAI(Board board){
    }
}
