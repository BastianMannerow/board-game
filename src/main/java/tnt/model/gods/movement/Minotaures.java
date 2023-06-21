package tnt.model.gods.movement;
import tnt.model.Board;
import tnt.model.Field;
import tnt.model.Figure;
import tnt.model.Player;
import tnt.model.interfaces.Gods;

import java.util.ArrayList;

public class Minotaures implements Gods{
    @Override
    public String getName() {
        return "Minotaures";
    }

    /**
     * Identifies possible fields
     *
     * @param players all players
     * @param figure the figure, which the player might be moving
     * @param board the board the game is played on
     *
     * @return list of possible fields
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

        // Check if the field behind is free
        for(Field possibility:possibleFields){
            int possibleX = possibility.getX();
            int possibleY = possibility.getY();

            if(possibleX > x && possibleY == y){
                if(board.getField(possibleX+2, possibleY).getIsFigureHere()){
                    possibleFields.remove(possibility);
                }
            }
            else if(possibleX < x && possibleY == y){
                if(board.getField(possibleX-2, possibleY).getIsFigureHere()){
                    possibleFields.remove(possibility);
                }
            }
            else if(possibleX == x && possibleY > y){
                if(board.getField(possibleX, possibleY+2).getIsFigureHere()){
                    possibleFields.remove(possibility);
                }
            }
            else if(possibleX == x && possibleY < y){
                if(board.getField(possibleX, possibleY-2).getIsFigureHere()){
                    possibleFields.remove(possibility);
                }
            }
            else if(possibleX < x && possibleY > y){
                if(board.getField(possibleX - 2, possibleY + 2).getIsFigureHere()){
                    possibleFields.remove(possibility);
                }
            }
            else if(possibleX > x && possibleY < y){
                if(board.getField(possibleX + 2, possibleY - 2).getIsFigureHere()){
                    possibleFields.remove(possibility);
                }
            }
            else if(possibleX > x && possibleY > y){
                if(board.getField(possibleX + 2, possibleY + 2).getIsFigureHere()){
                    possibleFields.remove(possibility);
                }
            }
            else{
                if(board.getField(possibleX - 2, possibleY - 2).getIsFigureHere()){
                    possibleFields.remove(possibility);
                }
            }
        }

        return possibleFields;
    }

    /**
     * Executes the movement
     *
     * @param field the targeted field
     * @param figure the figure, which the player wants to move
     * @param players all players
     * @param board the board the game is played on
     */
    public static void executeMove(Field field, Figure figure, ArrayList<Player> players, Board board){
        field.figureLeft();
        int x = figure.getX();
        int y = figure.getY();
        int newX = field.getX();
        int newY = field.getY();

        // Moves the figure of the active player
        figure.setX(newX);
        figure.setY(newY);

        // Identifies corresponding figure
        for(Player player:players){
            for (int i = 1; i < players.size(); i++) {
                Player passivePlayer = players.get(i);
                ArrayList<Figure> passiveFigures = passivePlayer.getFigure();
                for(Figure passiveFigure:passiveFigures){
                    if(passiveFigure.getX() != newX || passiveFigure.getY() != newY){
                        passiveFigures.remove(passiveFigure);
                    }
                }
                Figure movingFigure = passiveFigures.get(0);

                // Updates field and figure
                if(newX > x && newY == y){
                    movingFigure.setX(newX+2);
                    movingFigure.setY(newY);
                    board.getField(newX+2, newY).setFigure(movingFigure);
                }
                else if(newX < x && newY == y){
                    movingFigure.setX(newX-2);
                    movingFigure.setY(newY);
                    board.getField(newX-2, newY).setFigure(movingFigure);
                }
                else if(newX == x && newY > y){
                    movingFigure.setX(newX);
                    movingFigure.setY(newY+2);
                    board.getField(newX, newY+2).setFigure(movingFigure);
                }
                else if(newX == x && newY < y){
                    movingFigure.setX(newX);
                    movingFigure.setY(newY-2);
                    board.getField(newX, newY-2).setFigure(movingFigure);
                }
                else if(newX < x && newY > y){
                    movingFigure.setX(newX-2);
                    movingFigure.setY(newY+2);
                    board.getField(newX - 2, newY + 2).setFigure(movingFigure);
                }
                else if(newX > x && newY < y){
                    movingFigure.setX(newX + 2);
                    movingFigure.setY(newY - 2);
                    board.getField(newX + 2, newY - 2).setFigure(movingFigure);
                }
                else if(newX > x && newY > y){
                    movingFigure.setX(newX + 2);
                    movingFigure.setY(newY + 2);
                    board.getField(newX + 2, newY + 2).setFigure(movingFigure);
                }
                else{
                    movingFigure.setX(newX-2);
                    movingFigure.setY(newY-2);
                    board.getField(newX - 2, newY - 2).setFigure(movingFigure);
                }
            }
        }
    }
}
