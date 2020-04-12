import java.util.List;

/**
 * a class represents spell
 */
public class Spell extends Item implements Sellable{
    
    private int damage;
    private int manaRequired;
    private SpellType type;
    private boolean canBeSold = false;

    Spell(String _name, int _price, int _minimumLevel, int _damage, int _manaRequired, SpellType _type){
        super(_name, _price, _minimumLevel);
        damage = _damage;
        manaRequired = _manaRequired;
        type = _type;
    }

    /**
     * a function handle the effect of using a spell
     * all effect are handled in this function
     */
    public void castSpell(Monster m, int dexterity){
        if(m == null){
            OutputTools.printRedString("No target! Cannot use spell out of battle.");
            return ;
        }

        MonsterAttribute a = m.getAttribute();
        a.addCurHp(-getDamage(dexterity));

        switch(type){
            case Ice: a.addDamage((int)(a.getDamage() * ConstantVariables.heroSpellEffectRate)); break;
            case Fire: a.addDefense((int)(a.getDefense() * ConstantVariables.heroSpellEffectRate)); break;
            case Lighting: a.addDogeChange((int)(a.getDogeChance() * ConstantVariables.heroSpellEffectRate));
        }
        OutputTools.printGreenString("Use spell success, dealed " + getDamage(dexterity) + " damge");
    }
    // name price minlevel damage  minMana type
    //  15    7      8       8        7     10
    private static final String SPELL_INFO_SEPERATOR = "|-----|---------------|-------|--------|--------|-------|----------|";
    private static final String SPELL_INFO_HEADER = "|index|  Spell  Name  | Price |minLevel| damage |minMana|   Type   |";
    private static final String SPELL_INFO_FORMAT = "|%-5d|%-15s|%-7d|%-8d|%-8d|%-7d|%-10s|%n";
    public static void printSpellList(List<Spell> list){
        if(OutputTools.emptyList(list)) return;
        
        System.out.println(SPELL_INFO_SEPERATOR);
        System.out.println(SPELL_INFO_HEADER);
        System.out.println(SPELL_INFO_SEPERATOR);

        int counter = 1;
        for(Spell s : list){
            System.out.format(SPELL_INFO_FORMAT, counter, s.getName(), s.getPrice(), s.getMimumLevel(), s.getBaseDamage(), s.getManaRequired(), s.getType());
            System.out.println(SPELL_INFO_SEPERATOR);
            counter++;
        }
    }

    @Override
    public int getHalfPrice() {
        return (int)(0.5 * getPrice());
    }

    @Override
    public boolean canSoldToMarket() {
        return canBeSold;
    }

    public int getBaseDamage(){
        return damage;
    }

    public int getDamage(int dexterity) {
        return damage + (int)(((double)dexterity / ConstantVariables.heroSpellDamgeRate) * damage); 
    }

    public int getManaRequired() {
        return this.manaRequired;
    }

    public void setManaRequired(int manaRequired) {
        this.manaRequired = manaRequired;
    }

    public SpellType getType() {
        return this.type;
    }

    public void setType(SpellType type) {
        this.type = type;
    }
}
