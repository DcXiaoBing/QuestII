import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Random;

/**
 * a class handle the battle related function
 */
public class QuestCombat{
    public static final String DAMGE_MESSAGE_FORMAT = "%s deal %d damage to %s";
    public static final String DOGE_MESSAGE_FORMAT = "%s attacked, but %s doged.";
    private static final String COMBAT_INSTRUCTION_MESSAGE = "Input 1 to attack, 2 to use item, 3 to display hero info, 4 to display monster info";
    public static final String WIN_SURVIVE_MESSAGE = "%s survived, get %d exp and %d gold";
    public static final String WIN_DEAD_MESSAGE = "%s revived, now have %d hp";
    public static final String LOOSE_DEAD_MESSAGE = "%s loosed, now have %d hp and have %d money";

    private static Random ran = new Random();
    public static Formatter f = new Formatter();

    /**
     * a function hadle the combat.
     */
    public void run() {
        int isEnd = 0;
        boolean isMonster = false; // hero move first

        while (isEnd == 0) {
            if (isMonster) { // monster move

                // find an alive one
                Monster m = monsters.get(monsterIdx++);
                if (monsterIdx == monsters.size())
                    monsterIdx = 0;
                while (!m.isAlive()) {
                    m = monsters.get(monsterIdx++);
                    if (monsterIdx == monsters.size())
                        monsterIdx = 0;
                }

                OutputTools.printYellowString("It's " + m.getName() + "'s trun to move.");

                makeMove(m);
            } else { // hero move
                     // use item, attack

                // find an alive hero
                Hero h = heros.get(heroIdx++);
                if (heroIdx == heros.size())
                    heroIdx = 0;
                while (!h.isAlive()) {
                    h = heros.get(heroIdx++);
                    if (heroIdx == heros.size())
                        heroIdx = 0;
                }
                OutputTools.printYellowString("It's " + h.getName() + "'s trun to move.");

                makeMove(h);
            }

            roundEnd();
            isEnd = isEnd(isMonster);
            isMonster = !isMonster;
        }
        combatEnd(isEnd);
    }

    public void makeMove(GameCharacter c) {
        if (c instanceof Monster) {
            
        } else if (c instanceof Hero) {
            boolean exit = false;
            while (!exit) {
                OutputTools.printYellowString(COMBAT_INSTRUCTION_MESSAGE);
                int idx = InputTools.getAnInteger();
                while (idx < 1 || idx > 4) {
                    System.out.println(InputTools.ILLEGAL_VALUE_ERROR);
                    OutputTools.printYellowString(COMBAT_INSTRUCTION_MESSAGE);

                    idx = InputTools.getAnInteger();
                }

                Monster m = monsters.get(getTarget(heroIdx, monsters));
                Hero h = (Hero)c;
                switch (idx) {
                    case 1:
                        // get target
                        exit = true;
                        if (dodge(m.getAttribute().getDogeChance())) {
                            f = new Formatter();
                            OutputTools.printGreenString(
                                    f.format("Hero " + DOGE_MESSAGE_FORMAT, h.getName(), m.getName()).toString());
                        } else {
                            int damage = Math.max(0, h.getDamage() - m.getDefense());
                            m.getAttribute().addCurHp(-damage);
                            f = new Formatter();
                            OutputTools.printGreenString(
                                    f.format("Hero " + DAMGE_MESSAGE_FORMAT, h.getName(), damage, m.getName())
                                            .toString());
                        }
                        break;
                    case 2:
                        exit = h.useItem(m);
                        break;
                    case 3:
                        Hero.printHeroList(heros);
                        break;
                    case 4:
                        Monster.printMonsterList(monsters);
                        break;
                }
            }
        }
    }

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

    /**
     * handle the logic when combat end
     */
    public void combatEnd(int state) {
        if (state == 1) {
            OutputTools.printGreenString("You Win! Survived hero will get exp and money.");
            for (Hero h : heros) { // only need to check survived hero
                HeroAttribute ha = h.getAttribute();
                if (h.isAlive()) {
                    ha.addCurExp(ConstantVariables.heroCombatExpRate);
                    ha.addMoney(ConstantVariables.heroCombatGoldRate * monsters.get(0).getAttribute().getLevel()); // 100 * level
                    f = new Formatter();
                    OutputTools.printGreenString(f
                            .format(WIN_SURVIVE_MESSAGE, h.getName(), ConstantVariables.heroCombatExpRate,
                                    ConstantVariables.heroCombatGoldRate * monsters.get(0).getAttribute().getLevel())
                            .toString());
                } else {
                    ha.setHpByLevel();
                    ha.addCurHp(-(int) (0.5 * ha.getCurHp()));
                    f = new Formatter();
                    OutputTools.printGreenString(f.format(WIN_DEAD_MESSAGE, h.getName(), ha.getCurHp()).toString());
                }
            }
        } else if (state == 2) { // monster win, loose money
            OutputTools.printGreenString("You Loose, you will lose some money!");
            for (Hero h : heros) {
                HeroAttribute ha = h.getAttribute();
                ha.setHpByLevel();
                ha.addCurHp(-(int) (ConstantVariables.heroReviveHpRate * ha.getCurHp()));
                ha.addMoney(-(int) (ConstantVariables.heroLooseMoneyRate * ha.getMoney()));
                f = new Formatter();
                OutputTools.printGreenString(f.format(LOOSE_DEAD_MESSAGE, h.getName(), ha.getCurHp(), ha.getMoney()).toString());
            }
        }
        OutputTools.printGreenString("Press enter to continue.");
        InputTools.getLine();
    }
}