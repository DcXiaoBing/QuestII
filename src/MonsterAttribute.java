/**
 * a class contains all special attribute of monster
 */
public class MonsterAttribute extends Attribute{
    private int damage;
    private int defense;
    private int dogeChance;

    MonsterAttribute(int _damage, int _defense, int _dogeChance){
        super();
        damage = _damage;
        defense = _defense;
        dogeChance = _dogeChance;
    }
    MonsterAttribute(int _level,int _damage, int _defense, int _dogeChance){
        super(_level);
        damage = _damage;
        defense = _defense;
        dogeChance = _dogeChance;
    }
    

    public int getDogeChance() {
        return this.dogeChance;
    }

    public void setDogeChance(int dogeChance) {
        this.dogeChance = dogeChance;
    }

    public void addDogeChange(int incre){
        dogeChance += incre;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void addDamage(int incre){
        damage += incre;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void addDefense(int incre){
        defense += incre;
    }
}