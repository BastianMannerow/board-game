package tnt.model;

public class ExecuteGameInputs {
    public static boolean placeFigure(Figure figure, Field field){
        Game game = Settings.getActualGame();
        switch (game.getGameStatus()) {
            case SELECT_PLAYER:
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
            case BUILD:
                return false;
        }
        return false;
    }

    public static boolean buildObject(int buildLevel, Field field) {
        Game game = Settings.getActualGame();
        if (game.isBuildMode()) {
            if (game.getLastMovedFigure().getValidBuilds(game.getBoard()).contains(field)) {
                Settings.getNetworkHandler().build(buildLevel, field);
                if (field.getTowerLevel() > 0 && buildLevel == -1) {
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
        }
        return false;
    }
}
