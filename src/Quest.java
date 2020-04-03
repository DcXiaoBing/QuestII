import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * a class represents game Quest it has rules of this game
 */
public class Quest extends RectangularRPGBoardGame {
    private static String END_MESSAGE = "Game End!";
    private static final String WELCOME_MESSAGE = "Welcom to Quest. You will play on a 8*8 board with 3 hero.";
    private static final String START_MESSAGE = "Game Start!";
    private static int defaultTeamCount = 1;
    private int counter = 0; // counter for re-spawn monsters

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
        creatNewBoard(ConstantVariables.DEFAULT_BOARD_LENGTH, ConstantVariables.DEFAULT_BOARD_WIDTH); // fixed size board
        setUpHero(); // choose hero and set its position
        Monster.spawnMonster(getBoard(), monsters, heros);
    }

    /**
     * handle the choose hero process
     */
    private void setUpHero() {
        RectangularRPGBoard b = getBoard();

        // get hero. Displayed idx is 1-based
        List<Hero> list = Infos.getAllHeros();
        for (int i = 0; i < ConstantVariables.DEFAULT_HERO_COUNT; i++) {
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
            h.setAlias("H" + (i+1)); 

            // set position for this hero
            Coordinate c = new Coordinate(ConstantVariables.HERO_NEXUS_ROW_IDX, i * 3);
            h.enter(c, b.getEntry(c));
        }
        OutputTools.printYellowString("These are your heros, Input any key to continue");
        Hero.printHeroList(heros);
        InputTools.getLine();
    }






    /**
     * a function responsible for running game
     */
    private void gameStart(){
        RectangularRPGBoard b = getBoard();
        
        int status = isEnd();
        while(status < 0){ // -1 not end, 0 - stale, 1 - player win, 2 - monster win
            roundStart();

            // each monster make its move
            for(Monster m : monsters){
                m.act(b);
            }

            // print board once and get all input info
            // do not print too much
            b.printBoard(); 
            for(Hero h : heros){
                h.act(b);
            }

            status = isEnd();
        }

        printWinningMessage(status);
        end();
    }

    /**
     * a function handle things happened at round beginning
     */
    private void roundStart(){
        RectangularRPGBoard b = getBoard();
        
        if(counter == ConstantVariables.SPAWN_MONSTER_ROUNDS){ // spawn monster logic
            counter = 0;
            Monster.spawnMonster(b, monsters, heros);;
        }

        for(Hero h : heros){ // Regain 10% hp and mana at the beginning of every round.
            HeroAttribute ha = h.getAttribute();
            ha.addCurHp((int)(ConstantVariables.heroHpRegainRate * ha.getCurHp()));
            ha.addMana((int)(ConstantVariables.heroManaRegainRate * ha.getMana()));
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

    /**
     * ending condition. when hero or monster each opponent's nexus
     */
    public int isEnd() {
        boolean heroWin = false, monsterWin = false; 
        RectangularRPGBoard b = getBoard();

        for(int j = 0; j < b.getWidth() && !heroWin; j++){
            if(b.getEntry(ConstantVariables.MONSTER_NEXUS_ROW_IDX, j).hasHero()){ // check herowin
                heroWin = true;
            }
            if(b.getEntry(ConstantVariables.HERO_NEXUS_ROW_IDX, j).hasMonster()){ // check monster win
                monsterWin = true;
            }
        }
        if(heroWin) return 1;
        if(monsterWin) return 2;
        return -1; // no one win
    }

    @Override
    public void end(){
        OutputTools.printYellowString(END_MESSAGE);
    }

    private void printWinningMessage(int status){
        if(status == 1){
            OutputTools.printGreenString("Hero Win!");
        }else{
            OutputTools.printGreenString("Monster Win!");
        }
    }
}