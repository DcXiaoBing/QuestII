import java.util.*;

/**
 * a class represents hero in Quest
 */
public class Hero extends GameCharacter{
    public static final String HERO_INVENTORY_INDEX_INFO = ConstantVariables.ANSI_YELLOW
    + "0 - return, 1 - weapon, 2 - armor, 3 - spell, 4 - potion, 5 - unequip weapon and armor."
    + ConstantVariables.ANSI_RESET;

    private HeroAttribute attribute;
    private HeroType type;

    private Armor equipedArmor = null;
    private Weapon equipedWeapon = null;

    private List<Weapon> weapons;
    private List<Armor> armors;
    private List<Spell> spells;
    private List<Potion> potions;

    // no position info
    Hero(String _name, HeroAttribute _attribute, HeroType _type){
        super(_name);

        attribute = _attribute;
        type = _type;
        weapons = new ArrayList<>();
        armors = new ArrayList<>();
        spells = new ArrayList<>();
        potions = new ArrayList<>();
    }
    Hero(String _name, Coordinate _coord, RPGBoardEntry _cell, HeroAttribute _attribute, HeroType _type){
        super(_name, _coord, _cell);

        attribute = _attribute;
        type = _type;
        weapons = new ArrayList<>();
        armors = new ArrayList<>();
        spells = new ArrayList<>();
        potions = new ArrayList<>();
    }

    // upgrade logic
    public void upgrade(){
        if(attribute.getCurExp() < attribute.getMaxExp()) return ;

        attribute.levelUp();

        setAttributeWhenUpgrade();
    }
    public void setAttributeWhenUpgrade(){
        attribute.addCurExp(-attribute.getMaxExp()); // set exp
        attribute.setMaxExpByLevel();
        
        // because hp is not capped, so curHp could larger than level*100
        // when this happen, add 100 to curHp
        if(attribute.getCurHp() > attribute.getLevel() * ConstantVariables.heroHpRate){
            attribute.addCurHp(ConstantVariables.heroHpRate);
        }else{
            attribute.setHpByLevel();
        }
        attribute.addMana((int)(attribute.getMana() * ConstantVariables.manaUpgradeRate));

        // attribute growth
        switch (this.type) {
            case Warriors:
                attribute.addStrength((int)(attribute.getStrength() * ConstantVariables.warriorStrengthUpgradeRate));
                attribute.addDexterity((int)(attribute.getDexterity() * ConstantVariables.warriorDexterityUpgradeRate));
                attribute.addAgility((int)(attribute.getAgility() * ConstantVariables.warriorAgilityUpgradeRate));
                break;

            case Sorcerers:
                attribute.addStrength((int)(attribute.getStrength() * ConstantVariables.sorcererStrengthUpgradeRate));
                attribute.addDexterity((int)(attribute.getDexterity() * ConstantVariables.sorcererStrengthUpgradeRate));
                attribute.addAgility((int)(attribute.getAgility() * ConstantVariables.sorcererStrengthUpgradeRate));
                break;

            case Paladins:
                attribute.addStrength((int)(attribute.getStrength() * ConstantVariables.paladinStrengthUpgradeRate));
                attribute.addDexterity((int)(attribute.getDexterity() * ConstantVariables.paladinStrengthUpgradeRate));
                attribute.addAgility((int)(attribute.getAgility() * ConstantVariables.paladinStrengthUpgradeRate));
                break;

            default:
                break;
        }
    }


    // name, type, level, curHp, mana, money, curExp, maxExp, str, dex, agi
    //  20    10     5     5      5      7       5      5      8    9    7
    private static final String HERO_INFO_SEPERATOR = "|-----|--------------------|----------|-----|-----|-----|-------|------|-----|----------|----------|----------|";
    private static final String HERO_INFO_HEADER = "|Index|  Character   Name  |   Type   |Level|CurHp|Mana | Money |curExp|upExp|strength  |dexterity |agility   |";
    private static final String HERO_INFO_FORMAT = "|%-5d|%-20s|%-10s|%-5d|%-5d|%-5d|%-7d|%-6d|%-5d|%-10d|%-10d|%-10d|%n";
    public static void printHeroList(List<Hero> list){
        if(OutputTools.emptyList(list)) return;

        System.out.println(HERO_INFO_SEPERATOR);
        System.out.println(HERO_INFO_HEADER);
        System.out.println(HERO_INFO_SEPERATOR);

        int counter = 1;
        for(Hero h : list){
            HeroAttribute a = h.getAttribute();
            System.out.format(HERO_INFO_FORMAT, counter, h.getName(), h.getType(), a.getLevel(), a.getCurHp(), a.getMana(), a.getMoney(), a.getCurExp(), a.getMaxExp(), a.getStrength(), a.getDexterity(), a.getAgility());
            System.out.println(HERO_INFO_SEPERATOR);

            counter++;
        }
    }


    // check inventory and use
    // return false means hero did not use item
    public boolean useItem(Monster m){
        boolean exit = false, res = false;
        while(!exit){
            System.out.println(ConstantVariables.ANSI_YELLOW + "Input index for what you want use" + ConstantVariables.ANSI_RESET);
            System.out.println(HERO_INVENTORY_INDEX_INFO);

            int idx = InputTools.getAnInteger();
            while(idx < 0 || idx > 5){
                System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
                System.out.println(HERO_INVENTORY_INDEX_INFO);

                idx = InputTools.getAnInteger();
            }
            
            Item item = null;
            switch (idx) { // weapon, armor, spell, potion
                case 0:
                    return false; // did not use
                case 1:
                    item = getItem(weapons);
                    break;
                case 2:
                    item = getItem(armors);
                    break;
                case 3:
                    item = getItem(spells);
                    break;
                case 4:
                    item = getItem(potions);
                    break;
                case 5:
                    armors.add(equipedArmor);
                    equipedArmor = null;
                    weapons.add(equipedWeapon);
                    equipedWeapon = null;
                    System.out.println(ConstantVariables.ANSI_GREEN + "Weapon and Armor unequiped." + ConstantVariables.ANSI_RESET);
                    break;
                default:
                    break;
            }

            if(item != null){
                useItem(item, m);
                res = true;
                exit = true;
            }
        }
        return res;
    }

    /**
     * a function wraps the use of an item
     * @param item the item want to use
     * @param m the enemy target. Input null when do not need
     */
    public void useItem(Item item, Monster m){
        if(item instanceof Weapon){
            Weapon w = (Weapon)item;
            if(equipedWeapon == null){
                equipedWeapon = w;
            }else{
                weapons.add(equipedWeapon);
                equipedWeapon = w;
            }
            OutputTools.printGreenString("Use weapon success");
        }else if(item instanceof Armor){
            Armor a = (Armor)item;
            if(equipedArmor == null){
                equipedArmor = a;
            }else{
                armors.add(equipedArmor);
                equipedArmor = a;
            }
            OutputTools.printGreenString("Use armor success");
        }else if(item instanceof Spell){
            Spell s = (Spell)item;
            s.castSpell(m, attribute.getDexterity());
        }else if(item instanceof Potion){
            Potion p = (Potion)item;
            p.usePotion(this);
        }
    }

    /**
     * a function to get valid object or null
     * @param <E> type
     * @param list the object list
     * @return the object to use. Null means have not choose item in this list
     */
    public <E extends Item> E getItem(List<E> list){
        if(OutputTools.emptyList(list)){
            return null;
        }
        
        Object o = list.get(0);
        if(o instanceof Weapon){
            Weapon.printWeaponList(weapons);
        }else if(o instanceof Armor){
            Armor.printArmorList(armors);
        }else if(o instanceof Spell){
            Spell.printSpellList(spells);
        }else if(o instanceof Potion){
            Potion.printPotionList(potions);
        }

        System.out.println(InputTools.INPUT_INDEX_MESSAGE);
        int idx = InputTools.getAnInteger();
        while(idx < 0 || idx > list.size()){
            System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
            System.out.println(InputTools.INPUT_INDEX_MESSAGE);
            idx = InputTools.getAnInteger();
        }

        if(idx == 0){
            return null;
        }else{
            idx-=1;
            if(o instanceof Weapon || o instanceof Armor || o instanceof Potion)
                return list.remove(idx); // remove it from its list, re-add it when done
            else
                return list.get(idx);
        }
    }

    // TODO: handle respawn
    public void respawn(){
    }
    

    // compute damage in battle
    public int getDamage(){
        return (int)((attribute.getStrength() + getWeaponDamage()) * ConstantVariables.heroDamageRate);
    }
    public int getWeaponDamage(){
        if(equipedWeapon == null) return 0;

        return equipedWeapon.getDamage();
    }

    public boolean isAlive(){
        return getAttribute().getCurHp() > 0;
    }

    // compute defense in battle
    public int getDefense(){
        return getArmorDefense();
    }
    public int getArmorDefense(){
        if(equipedArmor == null) return 0;
        return equipedArmor.getDefense();
    }

    // compute dogechance in battle
    public int getDodgeChange(){
        return (int)(ConstantVariables.heroDogeRate * attribute.getDexterity());
    }


    public HeroAttribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(HeroAttribute attribute) {
        this.attribute = attribute;
    }

    public HeroType getType() {
        return this.type;
    }

    public void setType(HeroType type) {
        this.type = type;
    }

    public Armor getEquipedArmor() {
        return this.equipedArmor;
    }

    public void setEquipedArmor(Armor equipedArmor) {
        this.equipedArmor = equipedArmor;
    }

    public Weapon getEquipedWeapon() {
        return this.equipedWeapon;
    }

    public void setEquipedWeapon(Weapon equipedWeapon) {
        this.equipedWeapon = equipedWeapon;
    }

    public List<Weapon> getWeapons() {
        return this.weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public List<Armor> getArmors() {
        return this.armors;
    }

    public void setArmors(List<Armor> armors) {
        this.armors = armors;
    }

    public List<Spell> getSpells() {
        return this.spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public List<Potion> getPotions() {
        return this.potions;
    }

    public void setPotions(List<Potion> potions) {
        this.potions = potions;
    }
}