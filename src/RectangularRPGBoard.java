import java.util.HashSet;
import java.util.Random;

/**
 * a class represents board with shape of rectangle
 */
public class RectangularRPGBoard extends Board {

    private final int length, width;
    private RPGBoardEntry[][] board;

    public RectangularRPGBoard(int _length, int _width) {
        length = _length;
        width = _width;
        board = new RPGBoardEntry[length][width];
        initBoard();
    }

    /**
     * a function set board as it is
     */
    public void initBoard() {
        // set all entry to empty first

        // assign random type for all cell first
        for(int i = 0; i < length; i++) for(int j = 0; j < width; j++){
            board[i][j] = new RPGBoardEntry();
            getEntry(i, j).setType(getRandomEmptyType());
        }
        
        // set nexus
        for(int j = 0; j < width; j++){
            getEntry(ConstantVariables.MONSTER_NEXUS_ROW_IDX, j).setType(BoardEntryType.MonsterNexus); // monster nexus
            getEntry(ConstantVariables.HERO_NEXUS_ROW_IDX, j).setType(BoardEntryType.HeroNexus);
        }

        // set lane sepeartor
        for(int i = 0; i < length; i++){
            getEntry(i, 2).setType(BoardEntryType.InAccessible);
            getEntry(i, 5).setType(BoardEntryType.InAccessible);
        }
    }
    private BoardEntryType getRandomEmptyType(){
        Random ran = new Random();
        int idx = ran.nextInt(4);
        switch (idx) {
            case 0: return BoardEntryType.Regular;
            case 1: return BoardEntryType.Bush;
            case 2: return BoardEntryType.Koulou;
            case 3: return BoardEntryType.Cave;
            default:
                break;
        }
        return null;
    }
    
    /**
     * a function to print board. character use alias when print
     */
    public void printBoard(){
        for(RPGBoardEntry[] row : board){
            String[] rowString = new String[]{"", "", ""};
            for(int i = 0; i < width; i++){
                String[] cell = row[i].getEntryString();
                for(int k = 0; k < 3; k++) {
                    rowString[k] += cell[k];
                    if(i != width -1) rowString[k] += "  ";
                }
            }

            for(String s: rowString) System.out.println(s);

            System.out.println(); // print line seperator
        }
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
        for(int i = width - 1; i >= nx; i--){
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

    /**
     * a function to judge whether to 
     * @param a
     * @param b
     * @return
     */
    public boolean sameLane(Coordinate a, Coordinate b){
        if(a.getY() / ConstantVariables.DEFAULT_LANE_WIDTH == b.getY() / ConstantVariables.DEFAULT_LANE_WIDTH)
            return true;
        else
            return false;
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