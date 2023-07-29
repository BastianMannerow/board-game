package tnt.model;
import java.util.Random;
import java.util.ArrayList;

public class ArtificialPlayer{
    /**
     * Random decision-making.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn
     * @param game the game which is played on
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
        randomInitialPlacement(game, board, figureList);
    }

    /**
     * Greedy decision-making based on a simple heuristic.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn
     * @param game the game which is played on
     */
    public static void mediumAI(Board board, Player player, Game game){
        ArrayList<Figure> figureList = player.getFigure();
        // execute movement
        if(game.isMoveMode()) {
            Figure bestFigure = null;
            Field bestMove = null;
            int bestProgression = 100; // An initial value, which will never be reached

            for (Figure figure:figureList) {
                ArrayList<Field> possibleMoves = figure.getValidMoves(board);
                // For each field the following heuristic is going to be calculated, which determines the best move
                for (Field field : possibleMoves) {
                    // If an instant win is possible, it will be executed
                    if (moveToWin()) {
                        bestFigure = figure;
                        bestMove = field;
                        bestProgression = 0;
                        break;
                    }
                    int ownProgression = game.getVictoryHeight()-field.getTowerLevel();
                    if (ownProgression < bestProgression){
                        bestFigure = figure;
                        bestMove = field;
                        bestProgression = ownProgression;
                    }

                    // Randomise if the moves are equally good
                    else if (bestProgression == ownProgression){
                        Random random = new Random();
                        int randomInt = random.nextInt(2);
                        if (randomInt == 0){
                            bestMove = field;
                            bestFigure = figure;
                        }

                        else{
                            bestMove = field;
                            bestFigure = figure;
                        }
                    }
                }
            }
            ExecuteGameInputs.placeFigure(bestFigure, bestMove);
        }

        // execute random building
        if(game.isBuildMode()) {
            Field bestBuild = new Field();
            int bestProgression = 100; // An initial value, which will never be reached
            for (Figure figure:figureList) {
                ArrayList<Field> possibleBuilds = figure.getValidMoves(board);
                // For each field the following heuristic is going to be calculated, which determines the best move
                for (Field field : possibleBuilds) {
                    int ownProgression = game.getVictoryHeight() - field.getTowerLevel();
                    if (ownProgression < bestProgression) {
                        bestBuild = field;
                        bestProgression = ownProgression;
                    }
                    // Sabotage an enemy if possible
                    // Randomise if the moves are equally good
                    else if (bestProgression == ownProgression) {
                        Random random = new Random();
                        int randomInt = random.nextInt(2);
                        if (randomInt == 0) {
                            bestBuild = field;
                        } else {
                            bestBuild = field;
                        }
                    }
                }
            }
            ExecuteGameInputs.buildObject(bestBuild.getTowerLevel()+1, bestBuild);
        }

        // initial figure placement
        randomInitialPlacement(game, board, figureList);
    }

    /**
     * Greedy decision with an advanced reward and punishment heuristic. It also includes considering the team-mate.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn010
     * @param game the game which is played on
     */
    public static void hardAI(Board board, Player player, Game game){
        ArrayList<Figure> figureList = player.getFigure();

        // execute movement
        if(game.isMoveMode()) {
            Figure bestFigure = null;
            Field bestMove = null;
            int bestScore = 100; // An initial value, which will never be reached
            int bestTeamProgression = 100; // An initial value, which will never be reached
            int bestSabotageEnemy = 100; // An initial value, which will never be reached

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
                    int ownProgression = ownProgressionHeuristic(figure, field, game);
                    int teamProgression = teamProgressionHeuristic(figure, field, game);
                    int sabotageEnemy = sabotageEnemyHeuristic(figure, field, game);

                    // If move is better than the current best move, replace it
                    if (ownProgression + teamProgression + sabotageEnemy < bestScore){
                        bestTeamProgression = teamProgression;
                        bestSabotageEnemy = sabotageEnemy;
                        bestMove = field;
                        bestFigure = figure;
                    }
                    else if (ownProgression + teamProgression + sabotageEnemy == bestScore){
                        // If move is as good as the current best move, prefer the sabotage
                        if(sabotageEnemy < bestSabotageEnemy){
                            bestTeamProgression = teamProgression;
                            bestSabotageEnemy = sabotageEnemy;
                            bestMove = field;
                            bestFigure = figure;
                        }
                        // If move is as good as the current best move, prefer the teamProgression, since team-mate is able to move earlier
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
        randomInitialPlacement(game, board, figureList);
    }

    /**
     * Places the figures at the start of the game in a random way to ensure a unique game experience.
     *
     * @param game the game which is played on
     * @param board the current status of the board
     * @param figureList all figures of the player
     */
    public static void randomInitialPlacement(Game game, Board board, ArrayList<Figure> figureList){
        // initial figure placement
        if(game.placeFigures()){
            int x = board.getXSize();
            int y = board.getYSize();
            ArrayList<Field> possibleFields = new ArrayList<>();

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

    public static int teamProgressionHeuristic(Figure figure, Field field, Game game){
        return 1;
    }

    public static int ownProgressionHeuristic(Figure figure, Field field, Game game){
        return 1;
    }

    public static int sabotageEnemyHeuristic(Figure figure, Field field, Game game){
        return 1;
    }

    public static boolean moveToWin(){

        return false;
    }
}