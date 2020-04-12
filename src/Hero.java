import java.util.*;

/**
 * a class represents hero in Quest
 */
public abstract class Hero extends GameCharacter implements Fightable{
    public static final String HERO_INVENTORY_INDEX_INFO = "0 - return, 1 - weapon, 2 - armor, 3 - spell, 4 - potion, 5 - unequip weapon and armor.";
    private static String MOVE_FAIL = "The destination is inaccessible or out of boundary";
    private static final String GENERAL_INSTRUCTION_MESSAGE = "Input W/A/S/D to move, B to return to the nexus, \"item\" to use item, \"info\" to check info, \"tele\" to teleport, \"quit\" to exit game.";
    private static final String ATTACK_INSTRUCTION_MESSAGE = "One or more monster is around you, input \"attack\" to attack";
    private static final String CHOOSE_TARGET_MESSAGE = "Input the index of the monster you want. Input 0 to cancel.";
    private static final String NEXUS_INSTRUCTION_MESSAGE = "You are in Nexus, input \"buy\" to buy/sell";
    private static final String TELE_INSTRUCTION_MESSAGE = "Input the coordinate that you want to teleport or input -1,-1 to cancel tp. Cannot tp to same lane.";
    private static final String BUY_SELL_NOT_NEXUS_ERROR = "There are no monster around you!";
    private static final String NO_TARGET_ERROR = "There are no monster around you!";
    private static final String BACK_NEXUS_MESSAGE = "You returned to the nexus";

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
    Hero(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, HeroAttribute _attribute, HeroType _type){
        super(_name, _alias, _coord, _cell);

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
        OutputTools.printGreenString(getAlias() + " Upgrade!");
        attribute.levelUp();
        setAttributeWhenUpgrade(); // effect is based on hero type
    }
    protected abstract void setAttributeWhenUpgrade();

    /**
     * a function handles the action of hero.
     * Hero could move, use item, teleport, 'b', buy/sell, attack
     */
    public void act(RectangularRPGBoard b, List<Monster> monsters, List<Hero> heros){
        OutputTools.printYellowString(getAlias() + "'s turn to make move");
        List<Monster> targets = searchTarget(b); // get monster's around

        // print instrction according to wheter in nexus or has monster around
        OutputTools.printYellowString(GENERAL_INSTRUCTION_MESSAGE);
        if(!targets.isEmpty()) OutputTools.printYellowString(ATTACK_INSTRUCTION_MESSAGE);
        if(getCell().getType() == BoardEntryType.HeroNexus) OutputTools.printYellowString(NEXUS_INSTRUCTION_MESSAGE);

        boolean acted = false;
        while(!acted){
            String instruction = InputTools.getLine().toUpperCase(); // transfered to upper case
            int dx = 0, dy = 0;
            switch (instruction) {
                case "W":
                    dx = -1;
                    acted = move(b, dx, dy);
                    break;
                case "A":
                    dy = -1;
                    acted = move(b, dx, dy);
                    break;
                case "S":
                    dx = 1;
                    acted = move(b, dx, dy);
                    break;
                case "D":
                    dy = 1;
                    acted = move(b, dx, dy);
                    break;
                case "ITEM":
                    acted = useItem(b, monsters);
                    break;
                case "TELE":
                    acted = teleport(b);
                    break;
                case "B": 
                    acted = backToNexus(b);
                    break;
                case "BUY": // do not count as move, but need to be at nexus
                    if(getCell().getType() != BoardEntryType.HeroNexus){
                        OutputTools.printRedString(BUY_SELL_NOT_NEXUS_ERROR);
                    }else{
                        Market m = new Market();
                        m.start(this);
                    }
                    break;
                case "INFO":
                    Hero.printSingleHero(this);
                    break;
                case "ATTACK":
                    if(targets.isEmpty()){ // no target to attack
                        OutputTools.printRedString(NO_TARGET_ERROR);
                    }else{
                        acted = attack(targets, monsters);
                    }
                    break;
                default:
                    System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
                    break;
            }
        }
    }

    /**
     * a function handle the move
     * @param dx change of x coord
     * @param dy change of y coord
     * @return whether this opeartion successed
     */
    public boolean move(RectangularRPGBoard b, int dx, int dy) {
        Coordinate heroPosition = getCoord();
        Coordinate nc = new Coordinate(heroPosition.getX() + dx, heroPosition.getY() + dy);

        boolean res = false;
        if(b.validCoord(nc) && b.getEntry(nc).canEnter(this) && b.canMove(nc)){
            enter(nc, b.getEntry(nc)); // enter
            res = true;
        }else{
            OutputTools.printRedString(MOVE_FAIL);
        }

        return res;
    }

    /**
     * a function for hero to do common attack
     * @param targets monsters nearby
     * @param monsters all monster list, use for delete monster die
     * @return wheter attack success
     */
    public boolean attack(List<Monster> targets, List<Monster> monsters) {
        // only need choose target when more than one
        Monster target = chooseTarget(targets);
        if(target == null) return false; // cancel this operation

        if(QuestCombat.dodge(target.getDogeChance())){
            QuestCombat.printDodgeInfo(this, target);
        }else{
            int damage = Math.max(0, getDamage() - target.getDefense());

            target.getAttribute().addCurHp(-damage);
            QuestCombat.printDamageInfo(damage, this, target);
            if(target.getAttribute().getCurHp() <= 0){
                kill(monsters);
                this.upgrade();
            }
        }
        return true;
    }

    /**
     * handle the reward after combat.
     * A little bit slow, can optimize after
     * @param targets
     */
    public void kill(List<Monster> targets){
        Iterator<Monster> it = targets.iterator();
        while(it.hasNext()){
            Monster m = it.next();
            HeroAttribute ha = getAttribute();
            if(m.getAttribute().getCurHp() <= 0) it.remove();
            else continue;

            int exp = ConstantVariables.heroCombatExpRate;
            int gold = ConstantVariables.heroCombatGoldRate * m.getAttribute().getLevel();
            ha.addCurExp(exp);
            ha.addMoney(gold); // 100 * level

            QuestCombat.printKillMessage(this, gold, exp);
        }
    }

    /**
     * a function to choose monster when there are multiple monster
     * @param targets
     * @return the hero player want, null means cancel
     */
    private Monster chooseTarget(List<Monster> targets){
        if(targets.size() == 1){
            return targets.get(0);
        }

        // need player input
        Monster.printMonsterList(targets);
        int idx = InputTools.getAnIndex(CHOOSE_TARGET_MESSAGE, 0, targets.size());
        if(idx == 0)
            return null;
        else
            return targets.get(idx - 1);
    }


    /**
     * a function for check inventory and use
     * @return whether hero use item, ie, return false means hero did not use item
     */
    public boolean useItem(RectangularRPGBoard b, List<Monster> monsters){
        boolean used = false;
        List<Monster> targets = searchTarget(b);
        while(!used){
            OutputTools.printYellowString("Input index for what you want use");
            OutputTools.printYellowString(HERO_INVENTORY_INDEX_INFO);

            int idx = InputTools.getAnIndex(HERO_INVENTORY_INDEX_INFO, 0, 5);            
            
            Item item = null;
            switch (idx) { // weapon, armor, spell, potion
                case 0:
                    return false; // did not use and want quit
                case 1:
                    item = getItem(weapons);
                    break;
                case 2:
                    item = getItem(armors);
                    break;
                case 3:
                    if(targets.isEmpty()){
                        OutputTools.printRedString(NO_TARGET_ERROR);
                        continue;
                    }else{
                        item = getItem(spells);
                    }
                    break;
                case 4:
                    item = getItem(potions);
                    break;
                case 5: // do not count as acted
                    armors.add(equipedArmor);
                    equipedArmor = null;
                    weapons.add(equipedWeapon);
                    equipedWeapon = null;
                    OutputTools.printGreenString("Weapon and Armor unequiped.");
                    break;
                default:
                    break;
            }

            if(item != null){
                useItem(item, targets, monsters);
                used = true;
            }
        }
        return used;
    }

    /**
     * a function wraps the use of an item
     * @param item the item want to use
     * @param targets possible targets
     * @param monsters all monsters' list, use for deletion 
     */
    public void useItem(Item item, List<Monster> targets, List<Monster> monsters){
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
            Monster m = chooseTarget(targets);
            Spell s = (Spell)item;
            s.castSpell(m, attribute.getDexterity(getCell()));
            if(m.getAttribute().getCurHp() <= 0){
                kill(monsters);
                upgrade();
            }
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

    /**
     * a function to search hero in radius 1 of given coord.
     * @param c the center
     * @return list of heros in this range
     */
    private List<Monster> searchTarget(RectangularRPGBoard b){
        List<Monster> res = new ArrayList<>();
        Coordinate c = getCoord();

        for(int x = Math.max(0, c.getX() - 1); x <= Math.min(b.getLength() - 1, c.getX() + 1); x++){
            for(int y = Math.max(0, c.getY() - 1); y <= Math.min(b.getWidth() - 1, c.getY() + 1); y++){
                RPGBoardEntry e = b.getEntry(x, y);
                if(e.getMonster() != null) res.add(e.getMonster());
            }
        }

        return res;
    }

    public void die(RectangularRPGBoard b){
        OutputTools.printRedString(getAlias() + " died. Respawn at Nexus.");
        backToNexus(b);
    }

    /**
     * teleport operation
     * @return whether teleport sucess
     */
    private boolean teleport(RectangularRPGBoard b){
        OutputTools.printYellowString(TELE_INSTRUCTION_MESSAGE);

        int[] nc = InputTools.getCoord();
        if(nc[0] == -1 && nc[1] == -1) return false;
        while(!b.validCoord(nc[0], nc[1]) && !b.canMove(nc[0], nc[1]) && !b.sameLane(getCoord(), new Coordinate(nc[0], nc[1]))){
            if(nc[0] == -1 && nc[1] == -1) return false;

            OutputTools.printYellowString(TELE_INSTRUCTION_MESSAGE);
            nc = InputTools.getCoord();
        }

        enter(nc[0], nc[1], b);
        return true;
    }



    /**
     * a function responsible for respawn hero, hero transfered back to nexus and recover some hp. Because different hero have different method to respawn, so put it here. 
     * I can also use behavior design pattern to handle this.
     * @param h the hero need to be respawn
     */
    public void reSpwanHero(RectangularRPGBoard b){
        backToNexus(b); // set position.
        getAttribute().setHpByLevel(); // recover hp by level
    }
    /**
     * set a hero's position to its lane's nexus
     * @param h the hero you want to set
     * @return whether operation successed
     */
    private boolean backToNexus(RectangularRPGBoard b){
        Coordinate c = getCoord();
        OutputTools.printYellowString(BACK_NEXUS_MESSAGE);
        
        // recover some hp if less than a value
        if(attribute.getCurHp() < attribute.getLevel() * ConstantVariables.heroHpRate){
            OutputTools.printYellowString("You recovered some hp");
            attribute.setHpByLevel();
        }
        // find it lane's empty nexus and put it there
        // laneCol = laneIdx * 3, laneIdx * 3 + 1
        Coordinate nc = null;
        int laneIdx = c.getY() / ConstantVariables.DEFAULT_LANE_WIDTH;
        if(b.getEntry(ConstantVariables.HERO_NEXUS_ROW_IDX, laneIdx * ConstantVariables.DEFAULT_LANE_WIDTH).canEnter(this)){
            nc = new Coordinate(ConstantVariables.HERO_NEXUS_ROW_IDX, laneIdx * ConstantVariables.DEFAULT_LANE_WIDTH);
        }else if(b.getEntry(ConstantVariables.HERO_NEXUS_ROW_IDX, laneIdx * ConstantVariables.DEFAULT_LANE_WIDTH + 1).canEnter(this)){
            nc = new Coordinate(ConstantVariables.HERO_NEXUS_ROW_IDX, laneIdx * ConstantVariables.DEFAULT_LANE_WIDTH);
        }
        
        // current lane is full, re-spwan to another lane
        for(int i = 0; i < (1 + ConstantVariables.DEFAULT_BOARD_WIDTH / ConstantVariables.DEFAULT_BOARD_WIDTH) && nc == null; i++){
            if(b.getEntry(ConstantVariables.HERO_NEXUS_ROW_IDX, i * ConstantVariables.DEFAULT_LANE_WIDTH).canEnter(this)){
                nc = new Coordinate(ConstantVariables.HERO_NEXUS_ROW_IDX, i * ConstantVariables.DEFAULT_LANE_WIDTH);
            }else if(b.getEntry(ConstantVariables.HERO_NEXUS_ROW_IDX, i * ConstantVariables.DEFAULT_LANE_WIDTH + 1).canEnter(this)){
                nc = new Coordinate(ConstantVariables.HERO_NEXUS_ROW_IDX, i * ConstantVariables.DEFAULT_LANE_WIDTH + 1);
            }
        }

        if(nc != null){
            enter(nc, b.getEntry(nc));
            return true;
        }else{
            return false;
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
    
    public static void printSingleHero(Hero h){
        System.out.println(HERO_INFO_SEPERATOR);
        System.out.println(HERO_INFO_HEADER);
        System.out.println(HERO_INFO_SEPERATOR);

        HeroAttribute a = h.getAttribute();
        System.out.format(HERO_INFO_FORMAT, 1, h.getName(), h.getType(), a.getLevel(), a.getCurHp(), a.getMana(), a.getMoney(), a.getCurExp(), a.getMaxExp(), a.getStrength(), a.getDexterity(), a.getAgility());
        System.out.println(HERO_INFO_SEPERATOR);
    }
    

    // compute damage in battle
    public int getDamage(){
        return (int)((attribute.getStrength(getCell()) + getWeaponDamage()) * ConstantVariables.heroDamageRate);
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
    public int getDodgeChance(){
        return (int)(ConstantVariables.heroDogeRate * attribute.getAgility(getCell()));
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
