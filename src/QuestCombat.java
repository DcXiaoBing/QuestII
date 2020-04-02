import java.util.Formatter;
import java.util.Random;

/**
 * a class handle the battle related function
 */
public class QuestCombat{
    public static final String DAMGE_MESSAGE_FORMAT = "%s deal %d damage to %s";
    public static final String DOGE_MESSAGE_FORMAT = "%s attacked, but %s doged.";
    public static final String WIN_SURVIVE_MESSAGE = "%s killed a monster, get %d exp and %d gold";
    public static final String WIN_DEAD_MESSAGE = "%s revived, now have %d hp";
    public static final String LOOSE_DEAD_MESSAGE = "%s loosed, now have %d hp and have %d money";

    private static Random ran = new Random();
    public static Formatter f = new Formatter();

    /**
     * a function to handle whether doge happens! Attack and spell can both be dodged
     * @param dogeChance the possiblilty of doging an attack
     * @return whether doge happens
     */
    public static boolean dodge(int dogeChance) {
        return 1 + ran.nextInt(100) <= dogeChance;
    }

    /**
     * a function to print dodge log
     * @param attacker the character who do the attack
     * @param target the target been attack
     */
    public static void printDodgeInfo(GameCharacter attacker, GameCharacter target){
        f = new Formatter();

        f.format(DOGE_MESSAGE_FORMAT, attacker.getName(), target.getName());
        if(attacker instanceof Monster){
            OutputTools.printYellowString("Monster " + f.toString());
        }else if(attacker instanceof Hero){
            OutputTools.printYellowString("Hero " + f.toString());
        }
    }

    /**
     * a function to print damage log
     * @param attacker the character who do the attack
     * @param target the target been attck
     */
    public static void printDamageInfo(int damage, GameCharacter attacker, GameCharacter target){
        f = new Formatter();

        f.format(DAMGE_MESSAGE_FORMAT, attacker.getName(), damage, target.getName());
        if(attacker instanceof Monster){
            OutputTools.printYellowString("Monster " + f.toString());
        }else if(attacker instanceof Hero){
            OutputTools.printYellowString("Hero " + f.toString());
        }
    }

    public static void printKillMessage(Hero h, int gold, int exp){
        f = new Formatter();
        f.format(WIN_SURVIVE_MESSAGE, h.getName(), exp, gold);
        OutputTools.printGreenString(f.toString());
    }
}