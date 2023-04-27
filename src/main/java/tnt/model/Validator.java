package tnt.model;
import java.util.ArrayList;

public class Validator {
    public ArrayList<Field> getValidMoves(Figure figure, Board board) {
        int x = figure.getX();
        int y = figure.getY();
        int ownTowerLevel = board.getField(x,y).getTowerLevel();
        ArrayList<Field> reachableFields;
        ArrayList<Field> possibleFields;

        Card card = figure.getCard();
        if (card != null) {
            System.out.println("Kartenmöglichkeit muss berücksichtigt werden.");
        }
        else {
            for (int i = x-1; i <= x+1; i++) {
                for (int j = y-1; j <= y+1; j++) {
                    reachableFields.add(board.getField(x,y));
                }
            }

            for (Field field : reachableFields) {
                if(!field.getIsFigureHere() && !field.getTowerComplete() && field.getTowerLevel() <= ownTowerLevel+1){
                    possibleFields.add(field);
                }
            }
        }
        return possibleFields;
    }

    public void getValidBuilds(Player player){

    }
}
