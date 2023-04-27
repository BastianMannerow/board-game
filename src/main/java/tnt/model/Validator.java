package tnt.model;
import java.util.ArrayList;

/**
 * The Validator is responsible for suggesting the active player actions to choose from. It also prevents the player
 * from doing illegal actions and serves the AI as a method for predictions.
 */
public class Validator {

    /**
     * @param figure the figure object which's valid moves should be calculated
     * @param board the board the possible actions should be calculated on
     * @return possibleFields is a list of Field Objects, which are legal to use
     */
    public static ArrayList<Field> getValidMoves(Figure figure, Board board) {
        int figureX = figure.getX();
        int figureY = figure.getY();
        int ownTowerLevel = board.getField(figureX,figureY).getTowerLevel();
        int boardX = board.getXSize();
        int boardY = board.getYSize();
        ArrayList<Field> reachableFields;
        ArrayList<Field> possibleFields;

        Card card = figure.getCard();
        if (card != null) {
            System.out.println("Kartenmöglichkeit muss berücksichtigt werden.");
        }
        else {
            for (int i = figureX-1; i <= figureX+1; i++) {
                for (int j = figureY-1; j <= figureY+1; j++) {

                    // "Wahlpflichtfeature - Die Welt ist eine Kugel"
                    if(figureX<0 && figureY < 0){
                        reachableFields.add(board.getField(boardX-figureX, boardY-figureY));
                    }
                    else-if(figureX <0 && figureY >=0){
                        reachableFields.add(board.getField(boardX-figureX, figureY));
                    }
                    else-if(figureY<0&&figureX>=0){
                        reachableFields.add(board.getField(figureX, boardY-figureY));
                    }
                    else {
                        reachableFields.add(board.getField(figureX, figureY));
                    }
                }
            }

            // Is a reachable field also legal, hence possible?
            for (Field field : reachableFields) {
                if(!field.getIsFigureHere() && !field.getTowerComplete() && field.getTowerLevel() <= ownTowerLevel+1){
                    possibleFields.add(field);
                }
            }
        }
        return possibleFields;
    }

    public void getValidBuilds(Player player){
        // Daran denken, dass man nicht auf Feldern bauen darf, wo Figuren sind.
    }
}
