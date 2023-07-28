package tnt.model;

/**
 * The Board containing Field Objects. It serves the players to make moves on. Simultaneously, the AI is able to create
 * a separate board for its predictions.
 */
public class Board {
    private Field[][] board;
    private int xSize;
    private int ySize;
    private boolean roundWorld = false;

    /**
     * Constructing an object Board.
     * @param board initial matrix, which contains the single Field objects
     * @param x the initial width of the matrix
     * @param y the initial height of the matrix
     */
    public Board(Field[][] board, int x, int y) {
        this.board = board;
        this.xSize = x;
        this.ySize = y;
    }

    /**
     * @param x the x coordinate of the desired field
     * @param y the y coordinate of the desired field
     * @return One Field object
     */
    public Field getField(int x, int y) {
        return board[x][y];
    }

    /**
     * @return x size of the board as int
     */
    public int getXSize(){
        return xSize;
    }

    /**
     * @return y size of the board as int
     */
    public int getYSize(){
        return ySize;
    }

    public void setRoundWorld(boolean selected) {
        this.roundWorld = selected;
    }
    public boolean getRoundWorld() {
        return this.roundWorld;
    }
}
