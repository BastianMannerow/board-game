package tnt.model;
import java.util.Random;
import java.util.ArrayList;

public class ArtificialPlayer{
    /**
     * Random decision-making.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn
     */
    public static void easyAI(Board board, Player player, Game game){
        ArrayList<Figure> figureList = player.getFigure();
        // execute random movement
        if(game.isMoveMode()) {
            ArrayList<Field> possibleMoves = new ArrayList<>();
            int randomFigureMoveNumber = new Random().nextInt(figureList.size());
            Figure randomMoveFigure = figureList.get(randomFigureMoveNumber);
            possibleMoves.addAll(randomMoveFigure.getValidMoves(board));
            int randomMoveFieldNumber = new Random().nextInt(possibleMoves.size());
            Field randomMove = possibleMoves.get(randomMoveFieldNumber);
            player.executeMove(randomMove, board, randomMoveFigure);
        }

        // execute random building
        if(game.isBuildMode()) {
            ArrayList<Field> possibleBuilds = new ArrayList<>();
            int randomFigureBuildNumber = new Random().nextInt(figureList.size());
            Figure randomBuildFigure = figureList.get(randomFigureBuildNumber);
            possibleBuilds.addAll(randomBuildFigure.getValidBuilds(board));
            int randomBuildFieldNumber = new Random().nextInt(possibleBuilds.size());
            Field randomBuild = possibleBuilds.get(randomBuildFieldNumber);
            player.executeBuild(randomBuild);
        }

        // initial figure placement
        if(game.placeFigures()){
            int x=1;
            int y=1;
            for(Figure figure:figureList){
                Field targetField = board.getField(x,y);
                player.executeMove(targetField, board, figure);
            }
        }
    }

    /**
     * Greedy decision-making.
     *
     * @param board the current status of the board
     */
    public void mediumAI(Board board){
        int x = 1;
    }

    /**
     * Greedy decision -aking with some tweaks.
     *
     * @param board the current status of the board
     */
    public void hardAI(Board board){
        int x = 1;
    }
}
