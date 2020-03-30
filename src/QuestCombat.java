import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Random;

/**
 * a class handle the battle rule
 */
public class QuestCombat implements TurnBasedCombat {
    public static final String DAMGE_MESSAGE_FORMAT = "%s deal %d damage to %s";
    public static final String DOGE_MESSAGE_FORMAT = "%s attacked, but %s doged.";
    private static final String COMBAT_INSTRUCTION_MESSAGE = "Input 1 to attack, 2 to use item, 3 to display hero info, 4 to display monster info";
    public static final String WIN_SURVIVE_MESSAGE = "%s survived, get %d exp and %d gold";
    public static final String WIN_DEAD_MESSAGE = "%s revived, now have %d hp";
    public static final String LOOSE_DEAD_MESSAGE = "%s loosed, now have %d hp and have %d money";

    private List<Hero> heros;
    private List<Monster> monsters;
    private Random ran = new Random();
    private Formatter f = new Formatter();
    private int monsterIdx = 0, heroIdx = 0;

    QuestCombat(List<Hero> heros) {
        this.heros = heros;
        creatMonsters();
        OutputTools.printRedString("You are attaced by some monster!");
        Monster.printMonsterList(monsters);
    }

    /**
     * creat monster according to level of hero
     */
    private void creatMonsters() {
        monsters = new ArrayList<>();
        int count = heros.size();
        int maxLevel = 0;
        for (Hero h : heros)
            maxLevel = Math.max(maxLevel, h.getAttribute().getLevel());

        List<Monster> temp = new ArrayList<>();
        for (Monster m : Infos.getAllMonster()) {
            if (m.getAttribute().getLevel() == maxLevel)
                temp.add(m);
        }

        // randomly choose monster from list
        // do not check repeat
        Random ran = new Random();
        for (int i = 0; i < count; i++) {
            int idx = ran.nextInt(temp.size()); // it has at least one
            monsters.add(temp.get(idx));
        }
    }

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
            // get target
            Monster m = (Monster)c;
            Hero h = heros.get(getTarget(monsterIdx, heros));
            if (doge(h.getDodgeChange())) {
                f = new Formatter();
                OutputTools.printGreenString(
                        f.format("Monster " + DOGE_MESSAGE_FORMAT, m.getName(), h.getName()).toString());
            } else {
                int damage = Math.max(0, m.getDamage() - h.getDefense());
                h.getAttribute().addCurHp(-damage);
                f = new Formatter();
                OutputTools.printGreenString(
                        f.format("Monster " + DAMGE_MESSAGE_FORMAT, m.getName(), damage, h.getName()).toString());
            }
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
                        if (doge(m.getAttribute().getDogeChance())) {
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

    private boolean doge(int dogeChance) {
        return 1 + ran.nextInt(100) <= dogeChance;
    }

    /**
     * a function return the target of attack.
     * 
     * @param curIdx
     * @param enemy
     * @return
     */
    private <E extends GameCharacter> int getTarget(int curIdx, List<E> enemy) {
        // choose the one with same index if it is alive
        // otherwise, choose the alive one with min idx

        // tranfer idx
        if (curIdx == 0)
            curIdx = enemy.size() - 1;
        else
            curIdx -= 1;

        if (enemy.get(curIdx).isAlive())
            return curIdx;
        for (int i = 0; i < enemy.size(); i++) {
            if (enemy.get(i).isAlive())
                return i;
        }

        return 0;
    }

    /**
     * handle the logic when one round end
     */
    private void roundEnd() {
        for (Hero h : heros) {
            if (h.isAlive()) {
                HeroAttribute ha = h.getAttribute();
                ha.addCurHp((int) (ha.getCurHp() * ConstantVariables.heroHpRegainRate));
                ha.addMana((int) (ha.getMana() * ConstantVariables.heroManaRegainRate));
            }
        }
    }

    // The fight ends only when the hp of either all the monsters or all the heroes
    // is zeroed.
    /**
     * a function to judge whether game ends
     * 
     * @param lastMove the one who make last attack. can be hero or monster.
     * @return whether this game end. 0 - not end, 1 - hero win, 2 - monster win
     */
    public int isEnd(boolean isMonster) {
        int res = 0;
        if (!isMonster) { // hero move, check monster
            res = 1;
            for (Monster m : monsters) {
                if (m.isAlive()) {
                    res = 0;
                    break;
                }
            }
        } else if (isMonster) {
            res = 2;
            for (Hero h : heros) {
                if (h.isAlive()) {
                    res = 0;
                    break;
                }
            }
        }

        return res;
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