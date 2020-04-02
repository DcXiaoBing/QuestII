import java.util.Scanner;

/**
 * a class assembles Input functions used in this program
 */
public class InputTools{
    public static String ILLEGAL_VALUE_ERROR = ConstantVariables.ANSI_RED + "Illeagle input, please retry" + ConstantVariables.ANSI_RESET;
    public static String INPUT_INDEX_MESSAGE = ConstantVariables.ANSI_YELLOW + "Please input the index. Index might change." + ConstantVariables.ANSI_RESET;
    public static String CHOOSE_HERO_MESSAGE = "Input the index of the hero you want. Index might change.";
    
    private static Scanner in = new Scanner(System.in);

    public static int[] getCoord() {
        System.out.println("input the number like x,y");
        
        String line = null; // use to temperaly store line
        int[] res = null; // use to store result

        while (res == null) {
            line = getLine().trim();
            String[] temp = line.split(",");

            // wrong length
            if (temp.length != 2) {
                System.out.println(ILLEGAL_VALUE_ERROR);
                continue;
            }

            int[] tempI = new int[2];
            // try to parse 1st number
            try {
                tempI[0] = Integer.valueOf(temp[0]);
            } catch (Exception e) {
                System.out.println(ILLEGAL_VALUE_ERROR);
                continue;
            }
            // try to parse 2nd number
            try {
                tempI[1] = Integer.valueOf(temp[1]);
            } catch (Exception e) {
                System.out.println(ILLEGAL_VALUE_ERROR);
                continue;
            }

            // reach here, get a pair of corrd
            // do not check value's range here
            
            res = tempI;
        }
        System.out.println("------------------------------------------");
        return res;
    }

    public static char getSigleChar() {
        System.out.println("please input a single char");
        String line = null;
    
        Character res = null;
        
        while(res == null){
            line = getLine();

            // 1 means user only input one char
            if(line.length() == 1) 
                res = line.charAt(0);
            else
                System.out.println(ILLEGAL_VALUE_ERROR);
        }

        
        System.out.println("------------------------------------------");
        return res;
    }

    public static int getAnInteger(){
        Integer res = null;

        while(res == null){
            System.out.println("Please enter a number");
            String temp = getLine();
            
            Integer tempI = null;
            try{
                tempI = Integer.valueOf(temp);
            }catch(Exception e){
                System.out.println(ILLEGAL_VALUE_ERROR);
                continue;
            }
            res = tempI;
        }
        System.out.println("------------------------------------------");
        return res;
    }

    /**
     * get an index in the bound
     * @param instruction the instructoin for inputing index
     * @param min minimum value, included
     * @param max maximum value, included
     * @return the index user input
     */
    public static int getAnIndex(String instruction, int min, int max){
        OutputTools.printYellowString(instruction);
        int res = getAnInteger();
        while(res < min || res > max){
            OutputTools.printYellowString(ILLEGAL_VALUE_ERROR);
            OutputTools.printYellowString(instruction);

            res = getAnInteger();
        }

        return res;
    }

    /**
     * a function realize the press key to continue function
     */
    public static void pressKeyToContinue(){
        OutputTools.printYellowString("Press any key to continue");
        getLine();
    }

    // use this function to get line
    // so that we do not need to insert checkQuit everywhere
    public static String getLine(){
        String temp = in.nextLine();
        checkQuit(temp);

        return temp;
    }
    private static void checkQuit(String s){
        s = s.toUpperCase();
        if(s.equals("QUIT")) System.exit(0);
    }
}