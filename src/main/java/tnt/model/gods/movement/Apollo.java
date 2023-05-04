package tnt.model.gods.movement;
import tnt.model.Field;
import tnt.model.Figure;
import tnt.model.Player;
import tnt.model.Board;
import java.util.ArrayList;

public class Apollo{
    /**
     * Gets the possible fields
     *
     * @param players all players
     * @param board the board which is played on
     * @return possible fields
     */
    public static ArrayList<Field> getValidMove(ArrayList<Player> players, Board board){
        ArrayList<Field> possibleFields = new ArrayList<>();

        for (int i = 1; i < players.size(); i++) {
            Player passivePlayer = players.get(i);
            ArrayList<Figure> passiveFigures = passivePlayer.getFigure();
            for (Figure figure : passiveFigures) {
                possibleFields.add(board.getField(figure.getX(), figure.getY()));
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