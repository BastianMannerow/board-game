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
        if(game.placeFigures()) {
            randomInitialPlacement(board, player);
        }
    }

    /**
     * Greedy decision-making based on a simple heuristic.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn
     * @param game the game which is played on
     */
    public static void mediumAI(Board board, Player player, Game game){
        // execute movement
        if(game.isMoveMode()) {
            mediumAIMove(board, player, game);
        }

        // execute building
        if(game.isBuildMode()) {
            mediumAIBuilding(board, game);
        }

        // initial figure placement
        if(game.placeFigures()) {
            randomInitialPlacement(board, player);
        }
    }

    /**
     * Greedy movement to reach the winner height.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn
     * @param game the game which is played on
     */
    public static void mediumAIMove(Board board, Player player, Game game){
        ArrayList<Figure> figureList = player.getFigure();
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

    /**
     * Greedy building to reach the winner height.
     *
     * @param board the current status of the board
     * @param game the game which is played on
     */
    public static void mediumAIBuilding(Board board, Game game){
        Figure figure = game.getLastMovedFigure();
        Field bestBuild = new Field();
        int bestProgression = 100; // An initial value, which will never be reached
        ArrayList<Field> possibleBuilds = figure.getValidBuilds(board);
        // For each field the following heuristic is going to be calculated, which determines the best move
        for (Field field : possibleBuilds) {
            int ownProgression = ownBuildingProgressionHeuristic(field, game);
            if (ownProgression < bestProgression) {
                bestBuild = field;
                bestProgression = ownProgression;
            }

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
        ExecuteGameInputs.buildObject(bestBuild.getTowerLevel()+1, bestBuild);
    }


    /**
     * Greedy decision with an advanced reward and punishment heuristic. It also includes considering the team-mate.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn010
     * @param game the game which is played on
     */
    public static void hardAI(Board board, Player player, Game game){
        // execute movement
        if(game.isMoveMode()) {
            hardAIMove(board, player, game);
        }

        // execute building
        if(game.isBuildMode()) {
            hardAIBuilding(board, game);
        }

        // initial figure placement
        if(game.placeFigures()) {
            randomInitialPlacement(board, player);
        }
    }

    /**
     * Greedy movement with an advanced reward and punishment heuristic. It also includes considering the team-mate.
     *
     * @param board the current status of the board
     * @param player the player object executing the AI turn010
     * @param game the game which is played on
     */
    public static void hardAIMove(Board board, Player player, Game game){
        ArrayList<Figure> figureList = player.getFigure();
        // execute movement
        Figure bestFigure = null;
        Field bestMove = null;
        int bestProgression = 100; // An initial value, which will never be reached
        int ownCostsToWin =  100; // An initial value, which will never be reached

        for (Figure figure:figureList){
            ArrayList<Field> possibleMoves = figure.getValidMoves(board);
            // For each field the following heuristic is going to be calculated, which determines the best move
            for (Field field:possibleMoves){
                // If an instant win is possible, it will be executed
                if (moveToWin(field, game)){
                    bestFigure = figure;
                    bestMove = field;
                    bestProgression = 0;
                    ownCostsToWin = 0;
                    break;
                }
                // Calculating the heuristic and comparing it with the old best value
                int ownProgression = ownMoveProgressionHeuristic(field, game);
                int punishment = punishMovement(figure, game, ownProgression);

                // If move is better than the current best move, replace it
                if (ownProgression + punishment < bestProgression){
                    bestMove = field;
                    bestFigure = figure;
                    bestProgression = ownProgression + punishment;
                    ownCostsToWin = ownProgression;
                }
                else if (ownProgression + punishment == bestProgression){
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
        // Checks if the own chances to win are better than the enemies and if moving to enemy is crucial
        boolean enemySeemsToWin = ownCostsToWin >= ownMoveProgressionHeuristic(sabotageMoveEnemyHeuristic(player, game), game);
        Figure fictiveFigure = new Figure(bestMove.getX(),bestMove.getY(), game, player);
        Field enemyBestField = sabotageMoveEnemyHeuristic(player, game);
        boolean enemyFieldAlreadyInReach = fictiveFigure.getValidBuilds(board).contains(enemyBestField);

        if(enemySeemsToWin &&! enemyFieldAlreadyInReach){
            moveToPreventEnemyVictory(board, enemyBestField, player, bestFigure, bestMove);
        }
        // If chances are better, execute
        else{
            ExecuteGameInputs.placeFigure(bestFigure, bestMove);
        }
    }

    /**
     * Tries to find a quick way to prevent the enemy from winning
     *
     * @param board the current status of the board
     */
    public static void moveToPreventEnemyVictory(Board board, Field enemyBestField, Player player, Figure bestFigure, Field bestMove){
        Field closestFieldToEnemy = bestMove;
        Figure closestFigureToEnemy = bestFigure;
        int xDistance;
        int yDistance;
        int closestDistance = 100;
        int targetX = enemyBestField.getX();
        int targetY = enemyBestField.getY();
        ArrayList<Figure> figureList = player.getFigure();
        for (Figure figure:figureList) {
            ArrayList<Field> possibleMoves = figure.getValidMoves(board);
            for (Field field : possibleMoves) {
                if(board.isRoundWorld()){
                    xDistance = Math.min(board.getXSize() - field.getX() + targetX, Math.abs(field.getX() - targetX));
                    yDistance = Math.min(board.getYSize() - field.getY() + targetY, Math.abs(field.getY() - targetY));
                }
                else{
                    xDistance = Math.abs(field.getX() - targetX);
                    yDistance = Math.abs(field.getY() - targetY);
                }
                if(xDistance + yDistance < closestDistance){
                    closestFigureToEnemy = figure;
                    closestFieldToEnemy = field;
                    closestDistance = xDistance + yDistance;
                }
            }
        }
        ExecuteGameInputs.placeFigure(closestFigureToEnemy, closestFieldToEnemy);
    }

    /**
     * Greedy building with an advanced reward and punishment heuristic. It also includes considering the team-mate.
     *
     * @param board the current status of the board
     * @param game the game which is played on
     */
    public static void hardAIBuilding(Board board, Game game){
        // execute random building
        Field bestBuild = new Field();
        int bestProgression = 100; // An initial value, which will never be reached
        int bestTeamProgression = 100; // An initial value, which will never be reached
        int bestSabotageEnemy = 100; // An initial value, which will never be reached
        Figure figure = game.getLastMovedFigure();
        ArrayList<Field> possibleBuilds = figure.getValidBuilds(board);
        // For each field the following heuristic is going to be calculated, which determines the best move
        for (Field field : possibleBuilds) {
            // Calculating the heuristic and comparing it with the old best value
            int ownProgression = ownBuildingProgressionHeuristic(field, game);
            int teamProgression = teamBuildingProgressionHeuristic(figure, field, game);
            int sabotageEnemy = sabotageBuildingEnemyHeuristic(figure, field, game);
            int punishment = punishBuilding(figure, field, game);

            // If move is better than the current best move, replace it
            if (ownProgression + teamProgression + sabotageEnemy + punishment < bestProgression){
                bestTeamProgression = teamProgression;
                bestSabotageEnemy = sabotageEnemy;
                bestBuild = field;
                bestProgression = ownProgression + teamProgression + sabotageEnemy + punishment;
            }
            else if (ownProgression + teamProgression + sabotageEnemy + punishment == bestProgression){
                // If move is as good as the current best move, prefer the sabotage
                if(sabotageEnemy < bestSabotageEnemy){
                    bestTeamProgression = teamProgression;
                    bestSabotageEnemy = sabotageEnemy;
                    bestBuild = field;
                }
                // If move is as good as the current best move, prefer the teamProgression, since team-mate is able to move earlier
                else if(teamProgression < bestTeamProgression){
                    bestTeamProgression = teamProgression;
                    bestSabotageEnemy = sabotageEnemy;
                    bestBuild = field;
                }

                // If the moves are equally good, randomise
                else{
                    Random random = new Random();
                    int randomInt = random.nextInt(2);
                    if (randomInt == 0){
                        bestBuild = field;
                    }
                    else{
                        bestTeamProgression = teamProgression;
                        bestSabotageEnemy = sabotageEnemy;
                        bestBuild = field;
                    }
                }
            }
        }
        ExecuteGameInputs.buildObject(bestBuild.getTowerLevel()+1, bestBuild);
    }

    /**
     * Places the figures at the start of the game in a random way to ensure a unique game experience.
     *
     * @param board the current status of the board
     * @param player the active player
     */
    public static void randomInitialPlacement(Board board, Player player){
        // initial figure placement
        int x = board.getXSize();
        int y = board.getYSize();
        ArrayList<Figure> figureList = player.getFigure();
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

    /**
     * Reduces costs if moving brings the player closer to victory
     *
     * @param field the targeted field
     * @param game the game which is played on
     *
     * @return the costs
     */
    public static int ownMoveProgressionHeuristic(Field field, Game game) {
        return game.getVictoryHeight() - field.getTowerLevel();
    }

    /**
     * Reduces costs if moving will enable the player to prevent an opponents victory.
     *
     * @param currentPlayer the current player
     * @param game the game which is played on
     *
     * @return the costs
     */
    public static Field sabotageMoveEnemyHeuristic(Player currentPlayer, Game game){
        Field dangerousEnemyField = new Field();
        int lowestEnemyCost = 100;

        ArrayList<Player> allPlayer = game.getPlayerOrder();
        for (Player player: allPlayer) {
            if (!player.getTeam().equals(currentPlayer.getTeam())) {
                ArrayList<Figure> enemyFigures = player.getFigure();
                for (Figure enemyFigure : enemyFigures) {
                    ArrayList<Field> possibleEnemyMoves = enemyFigure.getValidMoves(game.getBoard());
                    for (Field enemyMove: possibleEnemyMoves){
                        int enemyCosts = ownMoveProgressionHeuristic(enemyMove, game);
                        if(enemyCosts < lowestEnemyCost){
                            lowestEnemyCost = enemyCosts;
                            dangerousEnemyField = enemyMove;
                        }
                    }
                }
            }
        }
        return dangerousEnemyField;
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
                        int enemyCost = ownMoveProgressionHeuristic(currentField, game);
                        punishment = Math.max(0, ownProgression - enemyCost); // If the enemy will get more value, than the player himself
                    }
                }
            }
        }
        return punishment;
    }

    /**
     * Reduces costs if building brings the player closer to victory and if it is in reach
     *
     * @param field the targeted field
     * @param game the game which is played on
     *
     * @return the punishment
     */
    public static int ownBuildingProgressionHeuristic(Field field, Game game){
        Figure lastMovedFigure = game.getLastMovedFigure();
        Board fictiveBoard = createFictiveBoard(game.getBoard());
        Field fictiveField = fictiveBoard.getField(field.getX(), field.getY());
        fictiveField.setTowerLevel(fictiveField.getTowerLevel()+1);
        if(lastMovedFigure.getValidMoves(fictiveBoard).contains(fictiveField)){
            int costs = game.getVictoryHeight() - field.getTowerLevel() + 1;
            return Math.max(0, costs);
        }
        else{
            int actualHeight = game.getBoard().getField(lastMovedFigure.getX(), lastMovedFigure.getY()).getTowerLevel();
            int costs = game.getVictoryHeight() - field.getTowerLevel() + 1;
            return Math.abs(actualHeight - fictiveField.getTowerLevel()) + costs;
        }
    }

    /**
     * Reduces costs if building will increase team-mate chances to win
     *
     * @param field the targeted field
     * @param game the game which is played on
     *
     * @return the punishment
     */
    public static int teamBuildingProgressionHeuristic(Figure figure, Field field, Game game){
        int reward = 0;
        ArrayList<Player> allPlayer = game.getPlayerOrder();
        for (Player player: allPlayer) {
            if (player.getTeam().equals(figure.getOwner().getTeam()) && game.getLastMovedFigure().getOwner() != player) {
                ArrayList<Figure> teamMateFigures = player.getFigure();
                for (Figure teamMateFigure : teamMateFigures) {
                    Board fictiveBoard = createFictiveBoard(game.getBoard());
                    Field fictiveField = fictiveBoard.getField(field.getX(), field.getY());
                    fictiveField.setTowerLevel(fictiveField.getTowerLevel()+1);
                    ArrayList<Field> possibleTeamMateMoves = teamMateFigure.getValidMoves(fictiveBoard);
                    if(possibleTeamMateMoves.contains(fictiveField)) {
                        int oldCosts = ownMoveProgressionHeuristic(field, game);
                        int newCosts = ownMoveProgressionHeuristic(fictiveField, game);
                        reward = Math.min(0, newCosts - oldCosts);
                    }
                }
            }
        }
        return reward;
    }

    /**
     * Reduces costs if building will decrease enemies chances to win
     *
     * @param field the targeted field
     * @param game the game which is played on
     *
     * @return the punishment
     */
    public static int sabotageBuildingEnemyHeuristic(Figure figure, Field field, Game game){
        int reward = 0;
        ArrayList<Player> allPlayer = game.getPlayerOrder();
        for (Player player: allPlayer) {
            if (!player.getTeam().equals(figure.getOwner().getTeam())) {
                ArrayList<Figure> enemyFigures = player.getFigure();
                for (Figure enemyFigure : enemyFigures) {
                    ArrayList<Field> possibleEnemyMoves = enemyFigure.getValidMoves(game.getBoard());
                    if(possibleEnemyMoves.contains(field)){
                        // the enemies potential costs if there is no building
                        int enemyCost = ownMoveProgressionHeuristic(field, game);
                        // checks if a move is sabotaged and rewards the player for sabotage
                        Board fictiveBoard = createFictiveBoard(game.getBoard());
                        Field fictiveField = fictiveBoard.getField(field.getX(), field.getY());
                        fictiveField.setTowerLevel(fictiveField.getTowerLevel()+1);

                        if(!enemyFigure.getValidMoves(fictiveBoard).contains(fictiveField)){
                            if(reward>(game.getVictoryHeight() - enemyCost) * (-1)){
                                reward = (game.getVictoryHeight() - enemyCost) * (-1);
                            }
                        }
                    }
                }
            }
        }
        return reward;
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
    public static int punishBuilding(Figure figure, Field field, Game game){
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
                        punishment = game.getVictoryHeight()-ownMoveProgressionHeuristic(field, game);

                        // punishment = punishment+2;
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
        return (field.getTowerLevel() == game.getVictoryHeight());
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
        fictiveBoard.setRoundWorld(originalBoard.isRoundWorld());
        for (Field field: allFields){
            fictiveBoard.setField(field.getX(), field.getY(), field);
        }
        return fictiveBoard;
    }
}