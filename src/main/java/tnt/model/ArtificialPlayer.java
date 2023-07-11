package tnt.model;

import java.util.Random;
import java.util.ArrayList;
import java.util.Set;

import tnt.model.ExecuteGameInputs;

public class ArtificialPlayer{
    /**
     * Random decision-making.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn
     */
    public static void easyAI(Board board, Player player){
        switch (Settings.getActualGame().getGameStatus()){
            case MOVE_FIGURE:{
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
                possibleMoves.addAll(randomMoveFigure.getValidMoves(board));
                ArrayList<Field> possibleBuilds = new ArrayList<>();
                int randomFigureBuildNumber = new Random().nextInt(figureList.size());
                Figure randomBuildFigure = figureList.get(randomFigureBuildNumber);
                possibleBuilds.addAll(randomBuildFigure.getValidBuilds(board));
                int randomBuildFieldNumber = new Random().nextInt(possibleMoves.size());
                Field randomBuild = possibleBuilds.get(randomBuildFieldNumber);
//        ExecuteGameInputs.placeFigure(randomBuildFigure, randomBuild);
                ExecuteGameInputs.buildObject(randomBuild.getTowerLevel() == Settings.getActualGame().getVictoryHeight() ? -1 : randomBuild.getTowerLevel() + 1, randomBuild);
                break;
            }
            case PLACE_FIGURES: {
                // execute random movement
                ArrayList<Figure> figureList = player.getFigure();
                ArrayList<Field>  possibleMoves = new ArrayList<>();
                int randomFigureMoveNumber = new Random().nextInt(figureList.size());
                Figure randomMoveFigure =  figureList.get(randomFigureMoveNumber);
                possibleMoves.addAll(randomMoveFigure.getValidMoves(board));
                int randomMoveFieldNumber = new Random().nextInt(possibleMoves.size());
                Field randomMove = possibleMoves.get(randomMoveFieldNumber);
                ExecuteGameInputs.placeFigure(randomMoveFigure, randomMove);
                break;
            }
            default:
                break;
        }
    }

    /**
     * Greedy decision-making.
     *
     * @param board the current status of the board
     */
    public static void mediumAI(Board board){
    }

    /**
     * Greedy decision -aking with some tweaks.
     *
     * @param board the current status of the board
     */
    public static void hardAI(Board board){
    }
}
