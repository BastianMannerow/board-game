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
        switch (game.getGameStatus()) {
            case SELECT_PLAYER:
            case BUILD:
                return false;
            case PLACE_FIGURES:
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
            case MOVE_FIGURE:
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
        return false;
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
            if (field.getTowerLevel() == game.getMaxBuildingLevel() && buildLevel == -1) {
                field.setTowerComplete(true);
            }
            else if (field.getTowerLevel() == buildLevel - 1) {
                field.setTowerLevel(buildLevel);
            }
            else {
                return false;
            }
            game.nextPlayersTurn();
            game.setMoveMode();
            game.checkBlockedMovement(game.getPlayersTurn());
            return true;
        }
        return false;
    }
}
