package tnt.model;

public class Board {
    private Field[][] board;

    public Board(Field[][] board) {
        this.board = board;
    }

    public Field getField(int x, int y) {
        return board[x][y];
    }
}
