/**
 * a class represents all attributes for hero in Quest
 */
public class HeroAttribute extends Attribute{
    private int mana; // mana do not have upper limit
    private int strength;
    private int dexterity;
    private int agility;

    private int money;
    private int curExp;
    private int maxExp;

    HeroAttribute(int _mana, int _strenght, int _agility, int _dexterity, int _money, int _experience){
        super();

        mana = _mana;
        strength = _strenght;
        dexterity = _dexterity;
        agility = _agility;
        
        money = _money;
        curExp = _experience;
        setMaxExpByLevel();
    }
    HeroAttribute(int _hp, int _mana, int _strenght, int _agility, int _dexterity, int _money, int _experience){
        super(1, _hp);

        mana = _mana;
        strength = _strenght;
        dexterity = _dexterity;
        agility = _agility;
        
        money = _money;
        curExp = _experience;
        setMaxExpByLevel();
    }

    public void setMaxExpByLevel(){
        maxExp = getLevel() * ConstantVariables.expUpgradeRate;
    }
    
    public int getMana() {
        return this.mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
    
    public void addMana(int incre){
        mana += incre;
    }

    // return attribute with grid type influence
    public int getStrength(RPGBoardEntry cell){
        if(cell == null || cell.getType() == BoardEntryType.Koulou){
            return (int)(this.strength * ConstantVariables.KOULOU_STRENGTH_BOOST);
        }else 
            return this.strength;
    }
    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void addStrength(int incre){
        strength += incre;
    }

    
    // return attribute with grid type influence
    public int getDexterity(RPGBoardEntry cell){
        if(cell == null || cell.getType() == BoardEntryType.Bush){
            return (int)(this.dexterity * ConstantVariables.BUSH_DEXTERITY_BOOST);
        }else 
            return this.dexterity;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void addDexterity(int incre){
        dexterity += incre;
    }

    // return attribute with grid type influence
    public int getAgility(RPGBoardEntry cell){
        if(cell == null || cell.getType() == BoardEntryType.Cave){
            return (int)(this.agility * ConstantVariables.KOULOU_STRENGTH_BOOST);
        }else 
            return this.agility;
    }
    public int getAgility() {
        return this.agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void addAgility(int incre){
        agility += incre;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int incre){
        money += incre;
    }

    public int getCurExp() {
        return this.curExp;
    }

    public void setCurExp(int experience) {
        this.curExp = experience;
    }

    public void addCurExp(int incre){
        curExp += incre;
    }

    public int getMaxExp() {
        return this.maxExp;
    }

    public void setMaxExp(int upExp) {
        this.maxExp = upExp;
    }
}