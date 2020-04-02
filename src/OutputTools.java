import java.util.Formatter;
import java.util.List;

/**
 * a class with all output related functions
 */
public class OutputTools{
    
    public static Formatter f = new Formatter();
    private static final String EMPTY_LIST_MESSAGE = ConstantVariables.ANSI_RED + "list is empty" + ConstantVariables.ANSI_RESET;

    public static boolean emptyList(List list){
        if(list == null || list.size() == 0){
            System.out.println(EMPTY_LIST_MESSAGE);
            return true;
        }
        return false;
    }
    
    // public static void printItemList(List<Item> list){
    //     if(emptyList(list)) return;

    //     Item head = list.get(0);
    //     if(head instanceof Weapon){
    //         Weapon.printWeaponList(list);
    //     }else if(head instanceof Armor){
    //         Armor.printArmorList(list);
    //     }else if(head instanceof Spell){
    //         Spell.printSpellList(list);
    //     }else if(head instanceof Potion){
    //         Potion.printPotionList(list);
    //     }
    // }

    public static void printYellowString(String s){
        System.out.println(ConstantVariables.ANSI_YELLOW + s + ConstantVariables.ANSI_RESET);
    }
    public static void printGreenString(String s){
        System.out.println(ConstantVariables.ANSI_GREEN + s + ConstantVariables.ANSI_RESET);
    }
    public static void printRedString(String s){
        System.out.println(ConstantVariables.ANSI_RED + s + ConstantVariables.ANSI_RESET);
    }
}