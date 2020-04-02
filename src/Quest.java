import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * a class represents game Quest it has rules of this game
 */
public class Quest extends RectangularRPGBoardGame {
    private static final String WELCOME_MESSAGE = "Welcom to Quest. You will play on a 8*8 board with 3 hero.";
    private static final String START_MESSAGE = "Game Start!";
    private static String NO_COMBAT = "Nothing happen, press enter to continue";
    private static int defaultTeamCount = 1;

    Random ran = new Random();
    List<Hero> heros; 
    List<Monster> monsters; // list of monsters in this game

    Quest() {
        super(ConstantVariables.DEFAULT_BOARD_LENGTH, ConstantVariables.DEFAULT_BOARD_LENGTH, ConstantVariables.DEFAULT_BOARD_WIDTH, ConstantVariables.DEFAULT_BOARD_WIDTH, defaultTeamCount);

        heros = new ArrayList<>();
        monsters = new ArrayList<>();
    }

    /**
     * entrence of game
     */ 
    public void start() {
        OutputTools.printYellowString(WELCOME_MESSAGE); // print welcome message
        init(); // init game
        OutputTools.printYellowString(START_MESSAGE);
        gameStart(); // get instructions and game response
    }

    /**
     * initialize game: initialize board, initial position, Character choose, data
     */
    private void init() {
        creatNewBoard(DEFAULT_BOARD_LENGTH, DEFAULT_BOARD_WIDTH); // fixed size board
        setUpHero(); // choose hero and set its position
    }

    /**
     * handle the choose hero process
     */
    private void setUpHero() {
        RectangularRPGBoard b = getBoard();

        // get hero. Displayed idx is 1-based
        List<Hero> list = Infos.getAllHeros();
        for (int i = 0; i < DEFAULT_HERO_COUNT; i++) {
            Hero.printHeroList(list);
            OutputTools.printYellowString(InputTools.CHOOSE_HERO_MESSAGE);

            int idx = InputTools.getAnInteger();
            while (idx - 1 < 0 || idx - 1 >= list.size()) {
                System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
                OutputTools.printYellowString(InputTools.CHOOSE_HERO_MESSAGE);
                idx = InputTools.getAnInteger();
            }

            // get that hero
            Hero h = list.remove(idx - 1);
            heros.add(h);
            
            // set alias according to the order of choosing them
            h.setAlias("H" + i); 

            // set position for this hero
            Coordinate c = new Coordinate(ConstantVariables.HERO_NEXUS_ROW_IDX, i * 3);
            h.enter(c, b.getEntry(c));
        }
        OutputTools.printYellowString("These are your heros");
        Hero.printHeroList(heros);
    }






    /**
     * a function responsible for running game
     */
    private void gameStart(){
        RectangularRPGBoard b = getBoard();
        int counter = 0; // counter for re-spawn monsters
        
        while(isEnd() < 0){ // -1 not end, 0 - stale, 1 - player win, 2 - monster win

            if(counter == ConstantVariables.SPAWN_MONSTER_ROUNDS){ // spawn monster logic
                counter = 0;
                Monster.spawnMonster(b, monsters);;
            }

            // each monster make its move
            for(Monster m : monsters){
                m.act(b);
            }

            b.printBoard(); // print board once and get all input info
            for(Hero h : heros){
                h.act(b);

                int dx = 0, dy = 0; // automatically set to 0
                
                

                
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
     * 
     */
    public int isEnd() {
        // quest is special, game do not end
        return 0;
    }
}