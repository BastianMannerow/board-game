package tnt.model;

/**
 * Class for checking and executing the movements/builds
 */
public class ExecuteGameInputs {

    /**
     * placed the figure on the field (in the actual game)
     * @param figure the figure to get moved
     * @param field the field the figure should be moved to
     * @return if the movement was successful
     */
    public static boolean placeFigure(Figure figure, Field field){

        Game game = Settings.getActualGame();
        boolean return_val = false;
        switch (game.getGameStatus()) {
            case SELECT_PLAYER:
            case BUILD:
                return_val = false;
                break;
            case PLACE_FIGURES:
                return_val = checkPlaceFig(game, figure, field);
                break;
            case MOVE_FIGURE:
                return_val = checkMoveFig(game, figure, field);
                break;
        }
        game.getPlayersTurn().prePlayersTurn();
        return return_val;
    }

    /**
     * Checks if the Figure can Move and if not applys all rules neccessary
     * @param game ,the actual game
     * @param figure ,the Figure which can move
     * @param field ,the field the figure should move to
     * @return  if the movement was successful
     */
    private static boolean checkMoveFig(Game game, Figure figure, Field field) {
        if (game.getPlayersTurn().getFigure().contains(figure) && figure.getValidMoves(game.getBoard()).contains(field)){
            Settings.getNetworkHandler().place(figure, field);
            game.setLastMovedFigure(figure);
            game.getPlayersTurn().executeMove(field,game.getBoard(), figure);
            game.setBuildMode();
            game.checkBlockedBuilding(game.getPlayersTurn());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the Figure be placed and if not applys all rules neccessary
     * @param game ,the actual game
     * @param figure ,the Figure which should be placed
     * @param field ,the field the figure should be placed on
     * @return if the movement was successful
     */
    private static boolean checkPlaceFig(Game game, Figure figure, Field field) {
        if (!field.getIsFigureHere() && game.getPlayersTurn().getFigure().contains(figure) && !figure.isPlaced()){
            Settings.getNetworkHandler().place(figure, field);
            figure.setX(field.getX());
            figure.setY(field.getY());
            figure.setPlaced();
            field.setFigure(figure);
            if (game.getPlayersTurn().allFiguresPlaced()){
                game.nextPlayersTurn();
            }
            if (game.getPlayersTurn().allFiguresPlaced()){
                game.startGame();
                game.checkBlockedMovement(game.getPlayersTurn());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * build a buildung with given level on the field
     * @param buildLevel the level of the building
     * @param field the field where the building should be set
     * @return if build has finished successfully
     */
    public static boolean buildObject(int buildLevel, Field field) {

        Game game = Settings.getActualGame();
        if (game.isBuildMode() && game.getLastMovedFigure().getValidBuilds(game.getBoard()).contains(field)) {
            Settings.getNetworkHandler().build(buildLevel, field);

            // Here you can change where it is possible to build a dome
            if (field.getTowerLevel() == game.getVictoryHeight() && (buildLevel == 0 || buildLevel > game.getVictoryHeight())) {
                field.setTowerComplete(true);
            }
            else if (field.getTowerLevel() == buildLevel - 1) {
                field.setTowerLevel(buildLevel);
            }
            else {
                game.getPlayersTurn().prePlayersTurn();
                return false;
            }
            if (game.getPlayersTurn().getNrTile(buildLevel) == 0) {
                game.getPlayersTurn().prePlayersTurn();
                return false;
            }
            game.getPlayersTurn().removeTile(buildLevel);
            game.nextPlayersTurn();
            game.setMoveMode();
            game.checkBlockedMovement(game.getPlayersTurn());
            game.getPlayersTurn().prePlayersTurn();
            return true;
        }
        game.getPlayersTurn().prePlayersTurn();
        return false;
    }
}
