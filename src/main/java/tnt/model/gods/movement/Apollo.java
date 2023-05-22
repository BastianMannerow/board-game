package tnt.model.gods.movement;
import tnt.model.Field;
import tnt.model.Figure;
import tnt.model.Player;
import tnt.model.Board;
import java.util.ArrayList;
import tnt.model.interfaces.Gods;

public class Apollo implements Gods{
    /**
     * Gets the possible fields
     *
     * @param players all players
     * @param figure the figure the player wants to move
     * @param board the board which is played on
     * @return possible fields
     */
    public static ArrayList<Field> getValidMove(ArrayList<Player> players, Figure figure, Board board){
        int x = figure.getX();
        int y = figure.getY();

        ArrayList<Field> reachableFields = new ArrayList<Field>();
        int boardX = board.getXSize();
        int boardY = board.getYSize();

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {

                // "Wahlpflichtfeature - Die Welt ist eine Kugel"
                if(x<0 && y < 0){
                    reachableFields.add(board.getField(boardX-x, boardY-y));
                }
                else if(x < 0 && y >=0){
                    reachableFields.add(board.getField(boardX-x, y));
                }
                else if(y <0 && x >=0){
                    reachableFields.add(board.getField(x, boardY-y));
                }
                else {
                    reachableFields.add(board.getField(x, y));
                }
            }
        }

        // Filter the reachable fields, so that only the legal fields remain
        int ownTowerLevel = board.getField(x,y).getTowerLevel();
        ArrayList<Field> possibleFields = new ArrayList<Field>();

        for (Field field : reachableFields) {
            if(field.getIsFigureHere() && !field.getTowerComplete() && field.getTowerLevel() <= ownTowerLevel+1){
                possibleFields.add(field);
            }
        }
        return possibleFields;
    }

    /**
     * Executes the movement
     *
     * @param field the target field object
     * @param figure the figure, which the player decided to move
     * @param players all players
     */
    public static void executeMove(Field field, Figure figure, ArrayList<Player> players){
        int originalX = figure.getX();
        int originalY = figure.getY();
        int targetX = field.getX();
        int targetY = field.getY();

        // move the figure of the active player
        figure.setX(targetX);
        figure.setY(targetY);

        // move the figure of the other player
        for (int i = 1; i < players.size(); i++) {
            Player passivePlayer = players.get(i);
            ArrayList<Figure> passiveFigures = passivePlayer.getFigure();
            for (Figure passiveFigure : passiveFigures) {
                int x = passiveFigure.getX();
                int y = passiveFigure.getY();
                if(x == targetX && y == targetY){
                    passiveFigure.setX(originalX);
                    passiveFigure.setY(originalY);
                }
            }
        }
    }
}