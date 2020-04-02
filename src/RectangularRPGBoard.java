import java.util.HashSet;

/**
 * a class represents board with shape of rectangle
 */
public class RectangularRPGBoard extends Board {
    private static String MAP_MARK = "M means market, X means inaccessible, O is hero";

    private final int length, width;
    private RPGBoardEntry[][] board;

    public RectangularRPGBoard(int _length, int _width) {
        length = _length;
        width = _width;
        board = new RPGBoardEntry[length][width];
        initBoard();
    }

    // TODO: need refactory
    public void initBoard() {
        // set all entry to empty first
        for (int i = 0; i < getLength(); i++) for (int j = 0; j < getWidth(); j++) {
            getBoard()[i][j] = new RPGBoardEntry();
        }

        HashSet<Coordinate> seen = new HashSet<>();
        int nonAccessibleCount = (int) (getLength() * getWidth() * ConstantVariables.nonAccessibleRate);
        int marketCount = (int) (getLength() * getWidth() * ConstantVariables.marketRate);

        // generate all non-accessible entry
        for (int i = 0; i < nonAccessibleCount; i++) {
            Coordinate coord = Coordinate.generateRandomCoordinate(getLength(), getWidth());
            while (seen.contains(coord))
                coord = Coordinate.generateRandomCoordinate(getLength(), getWidth());

            getBoard()[coord.getX()][coord.getY()].setType(BoardEntryType.InAccessible);
        }

        // generate all market entry
        for (int i = 0; i < marketCount; i++) {
            Coordinate coord = Coordinate.generateRandomCoordinate(getLength(), getWidth());
            while (seen.contains(coord))
                coord = Coordinate.generateRandomCoordinate(getLength(), getWidth());

            getBoard()[coord.getX()][coord.getY()].setType(BoardEntryType.Market);
        }
    }
    
    // TODO: need refactor
    public void printBoard(){
        for(RPGBoardEntry[] row : board){
            // to seperate from last line
            printLineSeperator();
            for(int i = 0; i < row.length; i++){
                System.out.print("|" + row[i].toString() + " ");
            }
            System.out.println("|");
        }
        // to print the bottom of board
        printLineSeperator();
        System.out.println(ConstantVariables.ANSI_GREEN + MAP_MARK + ConstantVariables.ANSI_RESET);
    }
    // TODO: need refactory
    private void printLineSeperator(){
        for(int i = 0; i < width; i++){
            // notice, we donot change line here
            System.out.print("+--");
        }
        // print last char and change line
        System.out.println("+");
    }

    /**
     * a function to check wheter has monster ahead so that hero cannot accoorss
     * @param nx new x coord
     * @param ny new y coord
     * @return whether can move
     */
    public boolean canMove(int nx, int ny){
        boolean res = true;

        int laneIdx = ny / ConstantVariables.DEFAULT_LANE_WIDTH;
        for(int i = 0; i <= nx; i++){
            if(getEntry(i, laneIdx * ConstantVariables.DEFAULT_LANE_WIDTH).hasMonster() || getEntry(i, laneIdx * ConstantVariables.DEFAULT_LANE_WIDTH + 1).hasMonster()){
                res = false;
                break;
            }
        }

        return res;
    }

    /**
     * a function to check wheter has monster ahead so that hero cannot accoorss
     * @param c new coord
     * @return whether can move
     */
    public boolean canMove(Coordinate c){
        return canMove(c.getX(), c.getY());
    }

    public int getLength() {
		return length;
    }
    public int getWidth(){
        return width;
    }
    public RPGBoardEntry[][] getBoard(){
        return  board;
    }

    public RPGBoardEntry getEntry(int x, int y){
        return board[x][y];
    }
    public RPGBoardEntry getEntry(Coordinate c){
        return getEntry(c.getX(), c.getY());
    }
    
    // judge whether an entry is in board
    public boolean validCoord(int x, int y) {
        return x >=0 && x < length && y >= 0 && y < width;
    }
    public boolean validCoord(Coordinate c){
        return validCoord(c.getX(), c.getY());
    }
}