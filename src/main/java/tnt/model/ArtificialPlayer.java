package tnt.model;
import java.util.Random;
import java.util.ArrayList;

public class ArtificialPlayer{
    /**
     * Random decision-making.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn
     * @param game the game which is be played on
     */
    public static void easyAI(Board board, Player player, Game game){
        ArrayList<Figure> figureList = player.getFigure();
        // execute random movement
        if(game.isMoveMode()) {
            int randomFigureMoveNumber = new Random().nextInt(figureList.size());
            Figure randomMoveFigure = figureList.get(randomFigureMoveNumber);
            ArrayList<Field> possibleMoves = new ArrayList<>(randomMoveFigure.getValidMoves(board));
            int randomMoveFieldNumber = new Random().nextInt(possibleMoves.size());
            Field randomMove = possibleMoves.get(randomMoveFieldNumber);
            ExecuteGameInputs.placeFigure(randomMoveFigure, randomMove);
        }

        // execute random building
        if(game.isBuildMode()) {
            int randomFigureBuildNumber = new Random().nextInt(figureList.size());
            Figure randomBuildFigure = figureList.get(randomFigureBuildNumber);
            ArrayList<Field> possibleBuilds = new ArrayList<>(randomBuildFigure.getValidBuilds(board));
            int randomBuildFieldNumber = new Random().nextInt(possibleBuilds.size());
            Field randomBuild = possibleBuilds.get(randomBuildFieldNumber);
            ExecuteGameInputs.buildObject(randomBuild.getTowerLevel()+1, randomBuild);
        }

        // initial figure placement
        if(game.placeFigures()){
            int x = board.getXSize();
            int y = board.getYSize();
            ArrayList<Field> possibleFields = new ArrayList<>();;

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if(!board.getField(i,j).getIsFigureHere()){
                        possibleFields.add(board.getField(i,j));
                    }
                }
            }

            for(Figure figure:figureList){
                if (!figure.isPlaced()) {
                    int randomFigureMoveNumber = new Random().nextInt(possibleFields.size());
                    Field targetField = possibleFields.get(randomFigureMoveNumber);
                    ExecuteGameInputs.placeFigure(figure, targetField);
                    possibleFields.remove(randomFigureMoveNumber);
                    break;
                }
            }
        }
    }

    /**
     * Greedy decision-making based on a simple heuristic.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn
     * @param game the game which is be played on
     */
    public void mediumAI(Board board, Player player, Game game){
        ArrayList<Figure> figureList = player.getFigure();
        // execute movement
        if(game.isMoveMode()) {
            Figure bestFigure = new Figure();
            Field bestMove = new Field();
            int bestProgression = new int;

            for (Figure figure:figureList) {
                ArrayList<Field> possibleMoves = figure.getValidMoves(board);
                // For each field the following heuristic is going to be calculated, which determines the best move
                for (Field field : possibleMoves) {
                    // If an instant win is possible, it will be executed
                    if (moveToWin()) {
                        bestFigure = figure;
                        bestMove = field;
                        break;
                    }
                    int ownProgression = game.getVictoryHeight()-field.getTowerLevel();
                    if (ownProgression < bestProgression){
                        bestFigure = figure;
                        bestMove = field;
                    }
                }
            }
            ExecuteGameInputs.placeFigure(bestFigure, bestMove);
        }

        // execute building
        if(game.isBuildMode()) {
            ExecuteGameInputs.buildObject(randomBuild.getTowerLevel()+1, randomBuild);
        }
        // initial figure placement
        if(game.placeFigures()){
            ExecuteGameInputs.placeFigure(figure, targetField);
        }
    }

    /**
     * Greedy decision with an advanced reward and punishment heuristic
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn010
     * @param game the game which is be played on
     */
    public void hardAI(Board board, Player player, Game game){
        ArrayList<Figure> figureList = player.getFigure();

        // execute movement
        if(game.isMoveMode()) {
            Figure bestFigure;
            Field bestMove;
            int bestScore;
            int bestTeamProgression;
            int bestSabotageEnemy;

            for (Figure figure:figureList){
                ArrayList<Field> possibleMoves = figure.getValidMoves(board);
                // For each field the following heuristic is going to be calculated, which determines the best move
                for (Field field:possibleMoves){
                    // If an instant win is possible, it will be executed
                    if (moveToWin()){
                        bestFigure = figure;
                        bestMove = field;
                        bestScore = 0;
                        break;
                    }
                    // Calculating the heuristic and comparing it with the old best value
                    int ownProgression = ownProgressionHeuristic();
                    int teamProgression = teamProgressionHeuristic();
                    int sabotageEnemy = sabotageEnemyHeuristic();

                    if (ownProgression + teamProgression + sabotageEnemy < bestScore){
                        bestTeamProgression = teamProgression;
                        bestSabotageEnemy = sabotageEnemy;

                    }

                    // If move is as good as the current best move
                    else if (ownProgression + teamProgression + sabotageEnemy == bestScore){
                        if(sabotageEnemy < bestSabotageEnemy){
                            bestTeamProgression = teamProgression;
                            bestSabotageEnemy = sabotageEnemy;
                            bestMove = field;
                            bestFigure = figure;
                        }

                        else if(teamProgression < bestTeamProgression){
                            bestTeamProgression = teamProgression;
                            bestSabotageEnemy = sabotageEnemy;
                            bestMove = field;
                            bestFigure = figure;
                        }

                        // If the moves are equally good, randomise
                        else{
                            Random random = new Random();
                            int randomInt = random.nextInt(2);
                            if (randomInt == 0){
                                bestMove = field;
                                bestFigure = figure;
                            }

                            else{
                                bestTeamProgression = teamProgression;
                                bestSabotageEnemy = sabotageEnemy;
                                bestMove = field;
                                bestFigure = figure;
                            }
                        }
                    }
                }
            }
            ExecuteGameInputs.placeFigure(bestFigure, bestMove);
        }
        // execute building
        if(game.isBuildMode()) {
            ExecuteGameInputs.buildObject(randomBuild.getTowerLevel()+1, randomBuild);
        }
        // initial figure placement
        if(game.placeFigures()){
            ExecuteGameInputs.placeFigure(figure, targetField);
        }
    }

    public int teamProgressionHeuristic(){
        return int;
    }

    public int ownProgressionHeuristic(){
        return int;
    }

    public int sabotageEnemyHeuristic(){
        return int;
    }

    public boolean moveToWin(){

        return;
    }
}
