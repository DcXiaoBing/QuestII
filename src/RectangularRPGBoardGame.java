import java.util.Random;

/**
 * BoardGame class boardgame regularly is a turn game Creat a board and add
 * setter and getter Though it do not add new abstract method, it inherited
 * abstract method from Game, so it keeps abstract
 */
public abstract class RectangularRPGBoardGame extends Game {
    
    private static String CREAT_BOARD_FAIL = "board creation failed, check input range";
    private static String CREAT_BOARD_SUCCESS = "board creation success";

    private RectangularRPGBoard board;
    private final int minLength, maxLength, minWidth, maxWidth;
    private int pointer, countOfTeam; // to get which team should move now

    private Random ran; // generate random int

    /**
     * constructor for boardgame, limit of length and width will set to infinite
     * 
     * @param _countOfTeam: count of team to play this game
     */
    RectangularRPGBoardGame(int _countOfTeam) {
        super(_countOfTeam);
        minLength = 0;
        maxLength = Integer.MAX_VALUE;
        minWidth = 0;
        maxWidth = Integer.MAX_VALUE;

        pointer = 1;
        countOfTeam = _countOfTeam;

        ran = new Random();
        board = null;
    }

    /**
     * creat a board with specified length and width
     * 
     * @param length length of board
     * @param width  width of board
     * @return true is board is created. false if length or widh is illeagle
     */
    public boolean creatNewBoard(int length, int width) {
        if (length < minLength || length > maxLength || width < minWidth || width > maxWidth) {
            System.out.println(CREAT_BOARD_FAIL);
            return false;
        }

        board = new RectangularRPGBoard(length, width);
        System.out.println(CREAT_BOARD_SUCCESS);
        return true;
    }

    /**
     * constructor for boardgame
     * 
     * @param _minLength   minimum length for board
     * @param _maxLength   maximum length for board
     * @param _minWidth    minimum width for board
     * @param _maxWidth    maximum width for baord
     * @param _countOfTeam count of team to play this game
     */
    RectangularRPGBoardGame(int _minLength, int _maxLength, int _minWidth, int _maxWidth, int _countOfTeam) {
        super(_countOfTeam);
        minLength = _minLength;
        maxLength = _maxLength;
        minWidth = _minWidth;
        maxWidth = _maxWidth;

        pointer = 1;
        countOfTeam = _countOfTeam;

        ran = new Random();
        board = null;
    }

    public RectangularRPGBoard getBoard() {
        return board;
    }
}