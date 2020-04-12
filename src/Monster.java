import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * a class represents monster in Quest
 */
public class Monster extends GameCharacter implements Fightable{
    private MonsterAttribute attribute;
    private MonsterType type;
    private static int totalMonster = 0;

    private static Random ran = new Random();

    Monster(String _name, MonsterAttribute _attribute, MonsterType _type) {
        super(_name);
        attribute = _attribute;
        type = _type;
    }
    Monster(Monster m){
        this(m.getName(), new MonsterAttribute(m.getAttribute()), m.getType());
    }
    Monster(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, MonsterAttribute _attribute, MonsterType _type) {
        super(_name, _alias, _coord, _cell);
        attribute = _attribute;
        type = _type;
    }

    /**
     * a function handle monster' ai. Move forward unless has target to attack. Random attack a hero when have multiple target.
     * @param m the reference to the monster who wants to act
     */
    public void act(RectangularRPGBoard b, List<Monster> monsters, List<Hero> heros){
        List<Hero> targets = searchTargets(b);
        
        if(targets.isEmpty()){ // no target, move forward
            move(b);
        }else{ // attack hero randomly
            attack(targets, b);
        }
    }

    /**
     * a function handle monster's move
     * @return return whether monster move success. return false when no empty entry around this monster
     */
    public boolean move(RectangularRPGBoard b){
        Coordinate curCoord = getCoord();
        Coordinate newCoord = new Coordinate(curCoord.getX() + ConstantVariables.MONSTER_DIRECTION,  curCoord.getY());
        
        // can go straight. When reach nexus, game will end, so
        if(b.validCoord(newCoord) && b.getEntry(newCoord).canEnter(this)){
            this.enter(newCoord, b.getEntry(newCoord));
            return true;
        }

        // try to go left
        newCoord = new Coordinate(curCoord.getX(), curCoord.getY() - 1);
        if(b.validCoord(newCoord) && b.getEntry(newCoord).canEnter(this)){ 
            this.enter(newCoord, b.getEntry(newCoord));
            return true;
        }

        // try to go right
        newCoord = new Coordinate(curCoord.getX(), curCoord.getY() + 1);
        if(b.validCoord(newCoord) && b.getEntry(newCoord).canEnter(this)){
            this.enter(newCoord, b.getEntry(newCoord));
            return true;
        }

        return false;
    }
    
    /**
     * a function handle monster's attack. Randomly choose an hero to attack when there are more than 1 target.
     * @param targets list of heros that monster can attack. Need to contain at least one target
     */
    private void attack(List<Hero> targets, RectangularRPGBoard b){
        Hero target = targets.get(ran.nextInt(targets.size()));

        if(QuestCombat.dodge(target.getDodgeChance())){
            QuestCombat.printDodgeInfo(this, target);
        }else{
            int damage = Math.max(0, getDamage() - target.getDefense());

            target.getAttribute().addCurHp(-damage);
            if(target.getAttribute().getCurHp() < 0){
                target.die(b);
            }
            QuestCombat.printDamageInfo(damage, this, target);
        }
    }

    /**
     * a function to search hero in radius 1 of given coord. Because monster might have different search target logic, so I didn't put this function in GameCharacter class.
     * @param c the center
     * @return list of heros in this range
     */
    private List<Hero> searchTargets(RectangularRPGBoard b){
        List<Hero> res = new ArrayList<>();
        Coordinate c = getCoord();

        for(int x = Math.max(0, c.getX() - 1); x <= Math.min(b.getLength() - 1, c.getX() + 1); x++){
            for(int y = Math.max(0, c.getY() - 1); y <= Math.min(b.getWidth() - 1, c.getY() + 1); y++){
                RPGBoardEntry e = b.getEntry(x, y);
                if(e.getHero() != null) res.add(e.getHero());
            }
        }
        
        return res;
    }
    
    public boolean isAlive(){
        return getAttribute().getCurHp() > 0;
    }

    /**
     * generate monster randomly from list, and set alias by its order of generate
     */
    public static void spawnMonster(RectangularRPGBoard b, List<Monster> monsters, List<Hero> heros){
        int maxLevel = 0;
        for (Hero h : heros)
            maxLevel = Math.max(maxLevel, h.getAttribute().getLevel());

        List<Monster> temp = new ArrayList<>();
        for (Monster m : Infos.getAllMonster()) {
            if (m.getAttribute().getLevel() == maxLevel)
                temp.add(m);
        }

        // randomly choose monster from list
        // do not check repeat
        Random ran = new Random();
        for (int i = 0; i < ConstantVariables.SPAWN_MONSTER_COUNT; i++) {
            int idx = ran.nextInt(temp.size()); // it has at least one
            Monster m = new Monster(temp.get(idx));

            m.getAttribute().setCurHp(1);
            m.getAttribute().setDamage(1);
            m.getAttribute().setDogeChance(1);
            m.getAttribute().setDefense(1);
            monsters.add(m);
            // set alias
            totalMonster++; // increase to get a new id for this monster
            m.setAlias("M" + totalMonster); // monster's name could exceed 9

            // set position
            Coordinate c = new Coordinate(ConstantVariables.MONSTER_NEXUS_ROW_IDX, i * 3);
            m.enter(c, b.getEntry(c));
        }
    }



    // name, type, level, curHp  damage defense  dodge
    //  20    15     5      8      8       8       8
    private static final String MONSTER_INFO_SEPERATOR = "|-----|--------------------|---------------|-----|--------|--------|--------|--------|";
    private static final String MONSTER_INFO_HEADER = "|Index|  Monster     Name  |     Type      |Level| cur Hp | damage | defense|  dodge |";
    private static final String MONSTER_INFO_FORMAT = "|%-5d|%-20s|%-15s|%-5d|%-8d|%-8d|%-8d|%-8d|%n";
    public static void printMonsterList(List<Monster> list){
        if(OutputTools.emptyList(list)) return;

        System.out.println(MONSTER_INFO_SEPERATOR);
        System.out.println(MONSTER_INFO_HEADER);
        System.out.println(MONSTER_INFO_SEPERATOR);

        int counter = 1;
        for(Monster h : list){
            MonsterAttribute a = h.getAttribute();
            System.out.format(MONSTER_INFO_FORMAT, counter, h.getName(), h.getType(), a.getLevel(), a.getCurHp(), a.getDamage(), a.getDefense(), a.getDogeChance());
            System.out.println(MONSTER_INFO_SEPERATOR);

            counter++;
        }
    }

    // monster's damage and defense are pre-defined
    public int getDamage() {
        return attribute.getDamage();
    }
    public int getDefense() {
        return attribute.getDefense();
    }
    public int getDogeChance(){
        return attribute.getDogeChance();
    }

    public MonsterAttribute getAttribute() {
        return this.attribute;
    }
    public void setAttribute(MonsterAttribute attribute) {
        this.attribute = attribute;
    }
    
    public MonsterType getType() {
        return this.type;
    }
    public void setType(MonsterType type) {
        this.type = type;
    }

}