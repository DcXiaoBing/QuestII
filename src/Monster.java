import java.util.List;

/**
 * a class represents monster in Quest
 */
public class Monster extends GameCharacter{
    private MonsterAttribute attribute;
    private MonsterType type;

    Monster(String _name, MonsterAttribute _attribute, MonsterType _type) {
        super(_name);
        attribute = _attribute;
        type = _type;
    }
    Monster(String _name, Coordinate _coord, MonsterAttribute _attribute, MonsterType _type) {
        super(_name, _coord);
        attribute = _attribute;
        type = _type;
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
    public boolean isAlive(){
        return getAttribute().getCurHp() > 0;
    }

    // TODO: respawn monster in nexus.
    public static void spawn(){

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