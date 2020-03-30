import java.util.*;

/**
 * a class represents the Market in Quest
 */
public class Market {

    public static final String MARKET_BUY_SALE_MESSAGE = ConstantVariables.ANSI_YELLOW
            + "Input a number. 0 - return, 1 - buy, 2 - sell." + ConstantVariables.ANSI_RESET;

    public static final String MARKET_INDEX_INFO = ConstantVariables.ANSI_YELLOW
            + "0 - return, 1 - weapon, 2 - armor, 3 - spell, 4 - potion."
            + ConstantVariables.ANSI_RESET;

    public static final String BUY_HELP_INFO = ConstantVariables.ANSI_YELLOW
            + "Input the index of the weapon you want to buy. Index might change after a buy."
            + ConstantVariables.ANSI_RESET;
    
    public static final String NOT_ENOUGH_MONEY = ConstantVariables.ANSI_RED + "Buy Fail. Do not have enough money. Press enter to continue." + ConstantVariables.ANSI_RESET;
    public static final String SUCCESS_INFO = ConstantVariables.ANSI_GREEN + "Success! Press enter to continue." + ConstantVariables.ANSI_RESET;
    public static final String EMPTY_LIST_MESSAGE = ConstantVariables.ANSI_RED
            + "This hero do not have this kind item. Press enter to return." + ConstantVariables.ANSI_RESET;

    List<Hero> heros;
    List<Weapon> weapons;
    List<Armor> armors;
    List<Spell> spells;
    List<Potion> potions;

    Market(List<Hero> heros) {
        this.heros = heros;
        weapons = Infos.getAllWeapons();
        armors = Infos.getAllArmors();
        spells = Infos.getAllSpells();
        potions = Infos.getAllPotions();
    }

    // choose hero
    // choose buy or sell
    // show inventory and operate. need four sub-function to handle
    public void start() {
        Hero h = chooseHero();

        while(h != null){

            System.out.println(MARKET_BUY_SALE_MESSAGE);
            int idx = InputTools.getAnInteger();
            while(idx < 0 || idx > 2){
                System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
                System.out.println(MARKET_BUY_SALE_MESSAGE);

                idx = InputTools.getAnInteger();
            }

            switch (idx) {
                case 0: h = chooseHero(); break;
                case 1: buyStart(h); break;
                case 2: sellStart(h); break;
                default:
                    break;
            }
        }
    }

    public Hero chooseHero() {
        Hero.printHeroList(heros);
        System.out.println(ConstantVariables.ANSI_YELLOW + "Welcome to market. Input 0 to exit market.input index of the hero to choose which hero to buy or sell." + ConstantVariables.ANSI_RESET);
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

    private void buyStart(Hero h) {
        boolean exit = false;
        do {
            System.out.println("What do you want to buy: " + MARKET_INDEX_INFO);
            int idx = InputTools.getAnInteger();

            while (idx < 0 || idx > 4) {
                System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
                System.out.println(MARKET_INDEX_INFO);

                idx = InputTools.getAnInteger();
            }

            // 1 - weapon, 2 - armor, 3 - spell, 4 - potion
            switch (idx) {
                case 0: exit = true; break;
                case 1: buyWeapon(h); break;
                case 2: buyArmor(h); break;
                case 3: buySpell(h); break;
                case 4: buyPotion(h); break;
            }
        } while (!exit);
    }

    private void printBuyHelpInfo(Hero h){
        System.out.println(ConstantVariables.ANSI_GREEN + "This hero have money: " + h.getAttribute().getMoney() + ConstantVariables.ANSI_RESET);
        System.out.println(InputTools.INPUT_INDEX_MESSAGE);
        System.out.println(ConstantVariables.ANSI_YELLOW + "Input 0 to return." + ConstantVariables.ANSI_RESET);
    }

    private void buyWeapon(Hero h) {
        boolean exit = false;

        do{
            Weapon.printWeaponList(weapons);
            printBuyHelpInfo(h);

            int idx = getBuyValidIndex(weapons.size(), h);
            if(idx == 0) 
                exit = true;
            else{
                idx = idx - 1; // change to 0-based index
                if(checkPrice(h, weapons.get(idx))){
                    HeroAttribute ha = h.getAttribute();

                    h.getWeapons().add(weapons.get(idx));
                    Weapon.printWeaponList(h.getWeapons());
                    ha.addMoney(-weapons.get(idx).getPrice());

                    System.out.format(SUCCESS_INFO, ha.getMoney());
                }else{
                    System.out.println(NOT_ENOUGH_MONEY);
                }
                InputTools.getLine();
            }
        }while(!exit);
        System.out.println(ConstantVariables.ANSI_YELLOW + "Return to upper menu now." + ConstantVariables.ANSI_RESET);
    }
    private void buyArmor(Hero h) {
        boolean exit = false;

        do{
            Armor.printArmorList(armors);
            printBuyHelpInfo(h);

            int idx = getBuyValidIndex(armors.size(), h);
            if(idx == 0) 
                exit = true;
            else{
                idx = idx - 1; // change to 0-based index
                if(checkPrice(h, armors.get(idx))){
                    HeroAttribute ha = h.getAttribute();

                    h.getArmors().add(armors.get(idx)); // add item to backpack
                    ha.addMoney(-armors.get(idx).getPrice()); // decrease money

                    System.out.format(SUCCESS_INFO, ha.getMoney());
                }else{
                    System.out.println(NOT_ENOUGH_MONEY);
                }
                InputTools.getLine();
            }
        }while(!exit);
        System.out.println(ConstantVariables.ANSI_YELLOW + "Return to upper menu now." + ConstantVariables.ANSI_RESET);
    }
    private void buyPotion(Hero h) {
        boolean exit = false;

        do{
            Potion.printPotionList(potions);
            printBuyHelpInfo(h);

            int idx = getBuyValidIndex(potions.size(), h);
            if(idx == 0) 
                exit = true;
            else{
                idx = idx - 1; // change to 0-based index
                if(checkPrice(h, potions.get(idx))){
                    HeroAttribute ha = h.getAttribute();

                    h.getPotions().add(potions.get(idx)); // add item to backpack
                    ha.addMoney(-potions.get(idx).getPrice()); // decrease money

                    System.out.format(SUCCESS_INFO, ha.getMoney());
                }else{
                    System.out.println(NOT_ENOUGH_MONEY);
                }
                InputTools.getLine();
            }
        }while(!exit);
        System.out.println(ConstantVariables.ANSI_YELLOW + "Return to upper menu now." + ConstantVariables.ANSI_RESET);
    }
    private void buySpell(Hero h) {
        boolean exit = false;

        do{
            Spell.printSpellList(spells);
            printBuyHelpInfo(h);

            int idx = getBuyValidIndex(spells.size(), h);
            if(idx == 0) 
                exit = true;
            else{
                idx = idx - 1; // change to 0-based index
                if(checkPrice(h, spells.get(idx))){
                    HeroAttribute ha = h.getAttribute();

                    h.getSpells().add(spells.get(idx)); // add item to backpack
                    ha.addMoney(-spells.get(idx).getPrice()); // decrease money

                    System.out.format(SUCCESS_INFO, ha.getMoney());
                }else{
                    System.out.println(NOT_ENOUGH_MONEY);
                }
                InputTools.getLine();
            }
        }while(!exit);
        System.out.println(ConstantVariables.ANSI_YELLOW + "Return to upper menu now." + ConstantVariables.ANSI_RESET);
    }
    private int getBuyValidIndex(int size, Hero h){
        int idx = InputTools.getAnInteger();
        // remeber, here is 1-based idx
        while(idx < 0 || idx > size){
            System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
            printBuyHelpInfo(h);

            idx = InputTools.getAnInteger();
        }
        return idx;
    }
    public static int getSellValidIndex(int size){
        int idx = InputTools.getAnInteger();
        // remeber, here is 1-based idx
        while(idx < 0 || idx > size){
            System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
            printSellHelpInfo();

            idx = InputTools.getAnInteger();
        }
        return idx;
    }
    public static void printSellHelpInfo(){
        System.out.println(InputTools.INPUT_INDEX_MESSAGE);
        System.out.println(ConstantVariables.ANSI_YELLOW + "Input 0 to return." + ConstantVariables.ANSI_RESET);
    }

    private boolean checkPrice(Hero h, Item item){
        return h.getAttribute().getMoney() >= item.getPrice();
    }

    private void sellStart(Hero h) {
        boolean exit = false;
        do {
            System.out.println("What do you want to sell: " + MARKET_INDEX_INFO);
            int idx = InputTools.getAnInteger();

            while (idx < 0 || idx > 4) {
                System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
                System.out.println(MARKET_INDEX_INFO);

                idx = InputTools.getAnInteger();
            }

            // 1 - weapon, 2 - armor, 3 - spell, 4 - potion
            switch (idx) {
                case 0: exit = true; break;
                case 1: sellWeapon(h); break;
                case 2: sellArmor(h); break;
                case 3: sellSpell(h); break;
                case 4: sellPotion(h); break;
            }
        } while (!exit);
    }

    // show inventory
    // choose idx
    private void sellWeapon(Hero h) {

        boolean exit = false;
        List<Weapon> list = h.getWeapons();
        if(list.isEmpty()){
            System.out.println(EMPTY_LIST_MESSAGE);
            InputTools.getLine();
            return ;
        }

        do{
            Weapon.printWeaponList(list);
            printSellHelpInfo();

            int idx = getSellValidIndex(list.size());
            if(idx == 0){
                exit = true;
            }else{
                idx -= 1;

                HeroAttribute ha = h.getAttribute();
                ha.addMoney(list.get(idx).getPrice() / 2);
                list.remove(idx);

                System.out.println(ConstantVariables.ANSI_GREEN + "This hero now have money: " + h.getAttribute().getMoney() + ConstantVariables.ANSI_RESET);
                System.out.println(SUCCESS_INFO);
                InputTools.getLine();
            }
            if(list.isEmpty()) System.out.println(EMPTY_LIST_MESSAGE);
        }while(!exit && !list.isEmpty());

        System.out.println(ConstantVariables.ANSI_YELLOW + "Return to upper menu now." + ConstantVariables.ANSI_RESET);
    }

    private void sellArmor(Hero h) {
        boolean exit = false;
        List<Armor> list = h.getArmors();
        if(list.isEmpty()){
            System.out.println(EMPTY_LIST_MESSAGE);
            InputTools.getLine();
            return ;
        }

        do{
            Armor.printArmorList(list);
            printSellHelpInfo();

            int idx = getSellValidIndex(list.size());
            if(idx == 0){
                exit = true;
            }else{
                idx -= 1;

                HeroAttribute ha = h.getAttribute();
                ha.addMoney(list.get(idx).getPrice() / 2);
                list.remove(idx);

                System.out.println(ConstantVariables.ANSI_GREEN + "This hero now have money: " + h.getAttribute().getMoney() + ConstantVariables.ANSI_RESET);
                System.out.println(SUCCESS_INFO);
                InputTools.getLine();
            }
            if(list.isEmpty()) System.out.println(EMPTY_LIST_MESSAGE);
        }while(!exit && !list.isEmpty());

        System.out.println(ConstantVariables.ANSI_YELLOW + "Return to upper menu now." + ConstantVariables.ANSI_RESET);
    }

    private void sellPotion(Hero h) {
        boolean exit = false;
        List<Potion> list = h.getPotions();
        if(list.isEmpty()){
            System.out.println(EMPTY_LIST_MESSAGE);
            InputTools.getLine();
            return ;
        }

        do{
            Potion.printPotionList(list);
            printSellHelpInfo();

            int idx = getSellValidIndex(list.size());
            if(idx == 0){
                exit = true;
            }else{
                idx -= 1;

                HeroAttribute ha = h.getAttribute();
                ha.addMoney(list.get(idx).getPrice() / 2);
                list.remove(idx);

                System.out.println(ConstantVariables.ANSI_GREEN + "This hero now have money: " + h.getAttribute().getMoney() + ConstantVariables.ANSI_RESET);
                System.out.println(SUCCESS_INFO);
                InputTools.getLine();
            }
            if(list.isEmpty()) System.out.println(EMPTY_LIST_MESSAGE);
        }while(!exit && !list.isEmpty());

        System.out.println(ConstantVariables.ANSI_YELLOW + "Return to upper menu now." + ConstantVariables.ANSI_RESET);
    }

    private void sellSpell(Hero h) {
        boolean exit = false;
        List<Spell> list = h.getSpells();

        if(list.isEmpty()){
            System.out.println(EMPTY_LIST_MESSAGE);
            InputTools.getLine();
            return ;
        }

        do{
            Spell.printSpellList(list);
            printSellHelpInfo();

            int idx = getSellValidIndex(list.size());
            if(idx == 0){
                exit = true;
            }else{
                idx -= 1;

                HeroAttribute ha = h.getAttribute();
                ha.addMoney(list.get(idx).getPrice() / 2);
                list.remove(idx);

                System.out.println(ConstantVariables.ANSI_GREEN + "This hero now have money: " + h.getAttribute().getMoney() + ConstantVariables.ANSI_RESET);
                System.out.println(SUCCESS_INFO);
                InputTools.getLine();
            }
            if(list.isEmpty()) System.out.println(EMPTY_LIST_MESSAGE);
        }while(!exit && !list.isEmpty());

        System.out.println(ConstantVariables.ANSI_YELLOW + "Return to upper menu now." + ConstantVariables.ANSI_RESET);
    }
}