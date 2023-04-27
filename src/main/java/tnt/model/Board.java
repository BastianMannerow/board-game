package tnt.model;

public class Board {
    private Field[][] board;
    private int xSize;
    private int ySize;

    public Board(Field[][] board, int x, int y) {
        this.board = board;
        this.xSize = x;
        this.ySize = y;
    }

    public Field getField(int x, int y) {
        return board[x][y];
    }

    public int getXSize(){
        return xSize;
    }
    public int getYSize(){
        return ySize;
    }
}
