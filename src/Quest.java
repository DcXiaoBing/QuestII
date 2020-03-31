import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * a class represents game Quest it has rules of this game
 */
public class Quest extends RectangularRPGBoardGame {
    private static String SET_BOARD_MESSAGE = "input the board size you want. format : length,width. length and width should between 6 to 10";
    private static String CHOOSE_HERO_COUNT_MESSAGE = "input the count of the hero, between 1~3";
    private static String CHOOSE_HERO_MESSAGE = "input the index of the hero you want. Index might change after a choose";
    
    private static String INPUT_INSTRUCTION_MESSAGE = "Input W/A/S/D to move, item to check inventory, info to show hero info, quit to exit game.";

    private static String MOVE_FAIL = "The destination is inaccessible or out of boundary";
    private static String NO_COMBAT = "Nothing happen, press enter to continue";

    

    private static int minLength = 8, maxLength = 8;
    private static int minWidth = 8, maxWidth = 8;
    private static int defaultTeamCount = 1;
    private static int minHeroCount = 1, maxHeroCount = 3;

    Random ran = new Random();
    Coordinate heroPosition;
    List<Hero> heros;

    Quest() {
        super(minLength, maxLength, minWidth, maxWidth, defaultTeamCount);
        heros = new ArrayList<>();
    }

    /**
     * entrence of game
     */ 
    public void start() {
        OutputTools.printWelCome(); // print welcome message

        init(); // init game

        System.out.println("Game Start!");

        // get instructions and game response
        gameStart();
    }


    private void init() {
        // initialize game: initialize board, initial position, Character choose, data
        // related
        setupBoard(); // set board by user's input
        setUpInitialPosition(); // initialize the position
        setUpHero(); // choose hero
    }
    /**
     * a wrap of creatation of board
     */
    private void setupBoard() {
        OutputTools.printYellowString(SET_BOARD_MESSAGE);
        int[] len = InputTools.getCoord();

        while (!creatNewBoard(len[0], len[1])) {
            OutputTools.printYellowString(SET_BOARD_MESSAGE);
            len = InputTools.getCoord();
        }
    }

    private void setUpHero() {
        // get hero count
        OutputTools.printYellowString(CHOOSE_HERO_COUNT_MESSAGE);
        int count = InputTools.getAnInteger();
        while (count < minHeroCount || count > maxHeroCount) {
            System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
            OutputTools.printYellowString(CHOOSE_HERO_COUNT_MESSAGE);
            count = InputTools.getAnInteger();
        }

        // get hero. Displayed idx is 1-based
        List<Hero> list = Infos.getAllHeros();
        for (int i = 0; i < count; i++) {
            Hero.printHeroList(list);
            OutputTools.printYellowString(CHOOSE_HERO_MESSAGE);

            int idx = InputTools.getAnInteger();
            while (idx - 1 < 0 || idx - 1 >= list.size()) {
                System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
                OutputTools.printYellowString(CHOOSE_HERO_MESSAGE);
                idx = InputTools.getAnInteger();
            }

            heros.add(list.get(idx - 1));
            list.remove(idx - 1);
        }

        System.out.println("These are your heros");
        Hero.printHeroList(heros);
    }

    private void setUpInitialPosition() {
        RectangularRPGBoard b = getBoard();
        heroPosition = Coordinate.generateRandomCoordinate(b.getLength(), b.getWidth());
        BoardEntryType t = b.getBoard()[heroPosition.getX()][heroPosition.getY()].getType();
        while (t == BoardEntryType.InAccessible) {
            heroPosition = Coordinate.generateRandomCoordinate(b.getLength(), b.getWidth());
            t = b.getBoard()[heroPosition.getX()][heroPosition.getY()].getType();
        }
    }

    /**
     * a function responsible for running game
     */
    private void gameStart(){
        // use while(true) because there are no specific end criterion
        while(true){
            int dx = 0, dy = 0; // automatically set to 0
            
            getBoard().printBoard(heroPosition);
            System.out.println(INPUT_INSTRUCTION_MESSAGE);

            String instruction = InputTools.getLine().toUpperCase(); // transfered to upper case
            switch (instruction) {
                case "W":
                    dx = -1;
                    makeMove(dx, dy);
                    break;
                case "A":
                    dy = -1;
                    makeMove(dx, dy);
                    break;
                case "S":
                    dx = 1;
                    makeMove(dx, dy);
                    break;
                case "D":
                    dy = 1;
                    makeMove(dx, dy);
                    break;
                case "ITEM":
                    Hero h = chooseHero();
                    while(h != null){
                        h.useItem(null);
                        h = chooseHero();
                    }
                    break;
                case "INFO":
                    Hero.printHeroList(heros);
                    OutputTools.printYellowString("Press enter to continue");
                    InputTools.getLine();
                    break;
                default:
                    System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
                    break;
            }
        }
    }

    /**
     * a functoin hanlde the event when enter a cell
     */
    public void enter(RPGBoardEntry e) {
        if(e.getType() == BoardEntryType.Empty){ // empty entry
            int dice = 1 + ran.nextInt(100); // dice 1~100
            if(dice <= ConstantVariables.COMBAT_POSSIBILITY){ // combat
                QuestCombat qc = new QuestCombat(heros);
                qc.run();
            }else{
                OutputTools.printGreenString(NO_COMBAT);
                InputTools.getLine(); // press enter
            }
        }else if(e.getType() == BoardEntryType.Market){ // market
            Market m = new Market(heros);
            m.start();
        }
        
    }

    /**
     * a function handle the move and corresponding event
     * @param dx
     * @param dy
     */
    public void makeMove(int dx, int dy) {
        Coordinate nc = new Coordinate(heroPosition
        .getX() + dx, heroPosition.getY() + dy);

        if(getBoard().validCoord(nc) && !(getBoard().getBoard()[nc.getX()][nc.getY()].getType() == BoardEntryType.InAccessible)){
            heroPosition = nc;
            enter(getBoard().getEntry(heroPosition));
        }else{
            System.out.println(ConstantVariables.ANSI_RED + MOVE_FAIL + ConstantVariables.ANSI_RESET);
            InputTools.getLine();
        }
    }


    public Hero chooseHero() {
        Hero.printHeroList(heros);
        System.out.println(ConstantVariables.ANSI_YELLOW + "Input 0 to exit, input index of the hero to choose which hero to look up inventory." + ConstantVariables.ANSI_RESET);
        System.out.println(InputTools.INPUT_INDEX_MESSAGE);

        int idx = InputTools.getAnInteger();
        while (idx < 0 || idx - 1 >= heros.size()) {
            System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
            System.out.println(InputTools.INPUT_INDEX_MESSAGE);

            idx = InputTools.getAnInteger();
        }

        if(idx == 0) 
            return null;
        else
            return heros.get(idx-1);
    }

    @Override
    /**
     * this function do not used in quest becasue game continous forever.
     */
    public int isEnd() {
        // quest is special, game do not end
        return 0;
    }
}