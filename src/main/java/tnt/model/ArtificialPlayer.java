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
                    if (moveToWin(field, game)) {
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

        // execute building
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
                    if (moveToWin(field, game)){
                        bestFigure = figure;
                        bestMove = field;
                        bestScore = 0;
                        break;
                    }
                    // Calculating the heuristic and comparing it with the old best value
                    int ownProgression = ownMoveProgressionHeuristic(figure, field, game);
                    int teamProgression = teamMoveProgressionHeuristic(figure, field, game);
                    int sabotageEnemy = sabotageMoveEnemyHeuristic(figure, field, game);
                    int punishment = punishMovement(figure, game, ownProgression);

                    // If move is better than the current best move, replace it
                    if (ownProgression + teamProgression + sabotageEnemy + punishment < bestScore){
                        bestTeamProgression = teamProgression;
                        bestSabotageEnemy = sabotageEnemy;
                        bestMove = field;
                        bestFigure = figure;
                    }
                    else if (ownProgression + teamProgression + sabotageEnemy + punishment == bestScore){
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

    /**
     * Calculates a way to the nearest win.
     *
     */
    public static int ownMoveProgressionHeuristic(Figure figure, Field field, Game game){
        // Search for a winning path iteratively. However, don't consider every enemy move
        int maximumCost = 5; // how deep should the searching tree be. 5 is the maximum amount of moves at start (Zusatzfeature)
        int i = 0;
        int bestCost = 100;
        Board fictiveBoard = createFictiveBoard(game.getBoard());
        Figure fictiveFigure = new Figure(figure.getX(), figure.getY(), game, figure.getOwner());
        Field fictiveField = fictiveBoard.getField(field.getX(), field.getY());
        fictiveFigure.setX(fictiveField.getX());
        fictiveFigure.setY(fictiveField.getY());
        int cost = recursiveIterativeWinSearch(maximumCost, bestCost, fictiveFigure, fictiveBoard, game, i);
        System.out.println("Hier die Kosten des Zuges: " + String.valueOf(cost));
        return cost;
    }

    public static int recursiveIterativeWinSearch(int maximumCost, int bestCost, Figure fictiveFigure, Board fictiveBoard, Game game, int i){
        boolean searchIsOver = false;
        while(!searchIsOver){
            Field fictiveField = fictiveBoard.getField(fictiveFigure.getX(), fictiveFigure.getY());
            fictiveField.setFigure(fictiveFigure);

            for (Field fictiveReachableField: fictiveFigure.getValidMoves(fictiveBoard)){
                if(moveToWin(fictiveReachableField, game)){
                    searchIsOver = true;
                    break;
                }
                else if(maximumCost == i){
                    searchIsOver = true;
                    break;
                }
                else{
                    fictiveFigure.setX(fictiveReachableField.getX());
                    fictiveFigure.setX(fictiveReachableField.getY());
                    i++;
                    bestCost = recursiveIterativeWinSearch(maximumCost, bestCost, fictiveFigure, fictiveBoard, game, i);
                }
            }
        }
        if(i<bestCost) {
            bestCost = i;
        }
        return bestCost;
    }

    public static int teamMoveProgressionHeuristic(Figure figure, Field field, Game game){
        return 1;
    }

    public static int sabotageMoveEnemyHeuristic(Figure figure, Field field, Game game){
        return 1;
    }

    /**
     * Punishes if an enemy will get an advantage
     *
     * @param figure the figure which should be moved away
     * @param game the game which is played on
     *
     * @return the punishment
     */
    public static int punishMovement(Figure figure, Game game, int ownProgression){
        int punishment = 0;
        Field currentField = game.getBoard().getField(figure.getX(),figure.getY());
        ArrayList<Player> allPlayer = game.getPlayerOrder();
        for (Player player: allPlayer){
            if(!player.getTeam().equals(figure.getOwner().getTeam())){
                ArrayList<Figure> enemyFigures = player.getFigure();
                for (Figure enemyFigure:enemyFigures){
                    ArrayList<Field> possibleEnemyMoves = enemyFigure.getValidMoves(game.getBoard());
                    if(possibleEnemyMoves.contains(currentField)){
                        int enemyCost = ownMoveProgressionHeuristic(enemyFigure, currentField, game);
                        punishment = Math.max(0, ownProgression - enemyCost); // If the enemy will get more value, than the player himself
                    }
                }
            }
        }
        return punishment;
    }

    public static int ownBuildingProgressionHeuristic(Figure figure, Field field, Game game){
        return 1;
    }

    public static int teamBuildingProgressionHeuristic(Figure figure, Field field, Game game){
        return 1;
    }

    public static int sabotageBuildingEnemyHeuristic(Figure figure, Field field, Game game){
        return 1;
    }

    /**
     * Punishes if an enemy will get an advantage
     *
     * @param figure the figure which should be moved away
     * @param field the field which will be increased
     * @param game the game which is played on
     *
     * @return the punishment
     */
    public static int punishBuilding(Figure figure, Field field, Game game, int ownBuildingProgression){
        int punishment = 0;
        ArrayList<Player> allPlayer = game.getPlayerOrder();
        Board fictiveBoard = createFictiveBoard(game.getBoard());
        Field fictiveField = fictiveBoard.getField(field.getX(), field.getY());
        fictiveField.setTowerLevel(fictiveField.getTowerLevel()+1);

        for (Player player: allPlayer){
            if(!player.getTeam().equals(figure.getOwner().getTeam())){
                ArrayList<Figure> enemyFigures = player.getFigure();
                for (Figure enemyFigure:enemyFigures){
                    ArrayList<Field> possibleEnemyMoves = enemyFigure.getValidMoves(fictiveBoard);
                    if(possibleEnemyMoves.contains(fictiveField) && fictiveField.getTowerLevel() != game.getVictoryHeight()+1){
                        punishment = punishment+2;
                    }
                }
            }
        }
        return punishment;
    }

    /**
     * Checks if an instant win is possible
     *
     * @param field the field in reach
     * @param game the game which is played on
     *
     * @return the boolean
     */
    public static boolean moveToWin(Field field, Game game){
        if (field.getTowerLevel() == game.getVictoryHeight()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Creates a fictive board to perform operations on. It is a copy of the given board
     *
     * @param originalBoard the game which data needs to be duplicated
     *
     * @return the fictive board
     */
    public static Board createFictiveBoard(Board originalBoard){
        // Duplicating the fields first
        ArrayList<Field> allFields = new ArrayList<>();
        int xSize = originalBoard.getXSize();
        int ySize = originalBoard.getYSize();
        for (int i = 0; i < xSize; i++){
            for (int j = 0; j < ySize; j++) {
                Field fieldObject = new Field(i, j);
                fieldObject.setTowerLevel(originalBoard.getField(i,j).getTowerLevel());
                fieldObject.setTowerComplete(Boolean.valueOf(originalBoard.getField(i,j).getTowerComplete()));
                allFields.add(fieldObject);
            }
        }

        // Finalising the board object
        Field[][] fields = new Field[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Field field = new Field(i, j);
                fields[i][j] = field;
            }
        }
        Board fictiveBoard = new Board(fields, xSize, ySize);
        fictiveBoard.setRoundWorld(originalBoard.getRoundWorld());
        for (Field field: allFields){
            fictiveBoard.setField(field.getX(), field.getY(), field);
        }
        return fictiveBoard;
    }
}