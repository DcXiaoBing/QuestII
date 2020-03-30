import java.util.List;

/**
 * a class with all output related functions
 */
public class OutputTools{
    private static final String WELCOME_MESSAGE = "Welcom to Quest";
    private static final String EMPTY_LIST_MESSAGE = ConstantVariables.ANSI_RED + "list is empty" + ConstantVariables.ANSI_RESET;

    public static void printWelCome(){
        System.out.println(WELCOME_MESSAGE);
    }
    public static boolean emptyList(List list){
        if(list == null || list.size() == 0){
            System.out.println(EMPTY_LIST_MESSAGE);
            return true;
        }
        return false;
    }
    
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