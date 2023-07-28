package tnt.model;
import tnt.util.Observable;

import java.util.ArrayList;
import java.util.Set;

/**
 * A figure owned by the Player.
 */
public class Figure extends Observable {
    private int x = 0;
    private int y = 0;
    private Game game;
    private boolean placed;
    private Player player;

    /**
     * Constructing an object Figure.
     * @param x initial x coordinate
     * @param y initial y coordinate
     * @param game the game which is played on
     * @param player the owner of the figure
     */
    public Figure(int x, int y, Game game, Player player) {
        this.x = x;
        this.y = y;
        this.placed = true;
        this.game = game;
        this.player = player;
    }

    /**
     * Constructor for a figure
     */
    public Figure(Player player) {
        this.placed = false;
        this.player = player;
    }

    /**
     * @return x coordinate of the figure
     */
    public int getX() {
        return x;
    }

    /**
     * @param x new x coordinate of the figure
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return x coordinate of Figure
     */
    public int getY() {
        return y;
    };

    /**
     * @param y new y coordinate of the figure
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return player who owns the figure
     */
    public Player getOwner() {
        return player;
    };

    /**
     * @param player player who owns the figure
     */
    public void setOwner(Player player) {
        this.player = player;
    }

    /**
     * Calculating the reachable fields.
     *
     * @param board the board the possible actions should be calculated on
     * @return reachable fields
     */
    public ArrayList<Field> getValidMoves(Board board){
        ArrayList<Field> reachableFields = new ArrayList<Field>();
        int boardX = board.getXSize();
        int boardY = board.getYSize();

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {

                // "Wahlpflichtfeature - Die Welt ist eine Kugel"
                if(board.getRoundWorld()){
                    reachableFields.add(board.getField((i+boardX)%boardX, (j+boardY)%boardY));
                }
                else if(i>= 0 && i < boardX && j>= 0 && j < boardY) {
                    reachableFields.add(board.getField(i, j));
                }
            }
        }

        // Filter the reachable fields, so that only the legal fields remain
        int ownTowerLevel = board.getField(x,y).getTowerLevel();
        reachableFields.removeIf(field -> field.getIsFigureHere() || field.getTowerComplete() || field.getTowerLevel() > ownTowerLevel + game.getMaxStepUpHeight()
                || field.getTowerLevel() < ownTowerLevel - game.getMaxStepDownHeight());
        return reachableFields;
    }

    /**
     * Filters the reachable build positions by legal build options
     *
     * @return valid build positions
     */
    public ArrayList<Field> getValidBuilds(Board board){
        ArrayList<Field> validBuilds = new ArrayList<Field>();

        int boardX = board.getXSize();
        int boardY = board.getYSize();

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {

                // "Wahlpflichtfeature - Die Welt ist eine Kugel"
                if(board.getRoundWorld()){
                    validBuilds.add(board.getField((i+boardX)%boardX, (j+boardY)%boardY));
                }
                else if(i>= 0 && i < boardX && j>= 0 && j < boardY) {
                    validBuilds.add(board.getField(i, j));
                }
            }
        }

        // Filter the fields, so that only the legal fields remain
        validBuilds.removeIf(field -> field.getIsFigureHere() || field.getTowerComplete());

        // Check if tiles are available
        for (Field field: validBuilds) {

            // distinguishes between global and local tile pool
//            if (game.isGlobalTilePool()) {
//                if (tile == 0 && game.getLevelOneTile() == 0) {
//                    validBuilds.remove(field);
//                } else if (tile == 1 && game.getLevelTwoTile() == 0) {
//                    validBuilds.remove(field);
//                } else if (tile == 2 && game.getLevelThreeTile() == 0) {
//                    validBuilds.remove(field);
//                } else if (tile == 3 && game.getLevelFourTile() == 0) {
//                    validBuilds.remove(field);
//                } else if (tile == 4 && game.getLevelFiveTile() == 0) {
//                validBuilds.remove(field);
//                } else if (tile == 5 && game.getLevelSixTile() == 0) {
//                validBuilds.remove(field);
//                }
//            }
//            else{
            int tile = field.getTowerLevel();
            for (int i = 0; i < game.getVictoryHeight(); i++){
                if ((player.getNrOfTiles(tile + 1) == 0 && tile != game.getVictoryHeight()) || (tile == game.getVictoryHeight() && player.getNrOfTiles(0) == 0)){
                    validBuilds.remove(field);
                }
            }
//            if (tile == 0 && player.getLevelOneTile() == 0) {
//
//            } else if (tile == 1 && player.getLevelTwoTile() == 0) {
//                validBuilds.remove(field);
//            } else if (tile == 2 && player.getLevelThreeTile() == 0) {
//                validBuilds.remove(field);
//            } else if (tile == 3 && player.getLevelFourTile() == 0) {
//                validBuilds.remove(field);
//            } else if (tile == 4 && player.getLevelFiveTile() == 0) {
//                validBuilds.remove(field);
//            } else if (tile == 5 && player.getLevelSixTile() == 0) {
//                validBuilds.remove(field);
//            }
//            }
        }
        return validBuilds;
    }

    /**
     * checks if the figure is placed on the board
     */
    public boolean isPlaced(){
        return placed;
    }

    /**
     * set the figure to be placed on the board
     */
    public void setPlaced() {
        placed = true;
    }
}