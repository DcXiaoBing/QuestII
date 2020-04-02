/**
 * a class holds all configurable data
 */
public class ConstantVariables {
    public static final int defaultInitalLevel = 1;

    // map generate info
    public static final double nonAccessibleRate = 0.2;
    public static final double marketRate = 0.3;

    // upgrade exp, hp related
    public static final int expUpgradeRate = 10;
    public static final int heroHpRate = 100;
    public static final int monsterHpRate = 100;
    public static final double manaUpgradeRate = 0.1;

    // upgrade attribute rate. combine favored attribute
    public static final double warriorStrengthUpgradeRate = 0.1;
    public static final double warriorDexterityUpgradeRate = 0.05;
    public static final double warriorAgilityUpgradeRate = 0.1;

    public static final double sorcererStrengthUpgradeRate = 0.05;
    public static final double sorcererDexterityUpgradeRate = 0.1;
    public static final double sorcererAgilityUpgradeRate = 0.1;

    public static final double paladinStrengthUpgradeRate = 0.1;
    public static final double paladinDexterityUpgradeRate = 0.1;
    public static final double paladinAgilityUpgradeRate = 0.05;

    // battle rates
    public static final double heroDogeRate = 0.02;
    public static final double heroDamageRate = 0.3;
    public static final int heroSpellDamgeRate = 10000;
    public static final double heroSpellEffectRate = -0.1;

    public static final double heroHpRegainRate = 0.1;
    public static final double heroManaRegainRate = 0.1;

    public static final int heroCombatExpRate = 2;
    public static final int heroCombatGoldRate = 100;

    public static final double heroReviveHpRate = 0.5;
    public static final double heroLooseMoneyRate = 0.5;

    // game world rates
    public static final int COMBAT_POSSIBILITY = 40;
    public static final double BUSH_DEXTERITY_BOOST = 0.1;
    public static final double CAVE_AGILITY_BOOST = 0.1;
    public static final double KOULOU_STRENGTH_BOOST = 0.1;

    public static final int HERO_DIRECTION = -1, MONSTER_DIRECTION = 1;
    public static final int SPAWN_MONSTER_ROUNDS = 8, SPAWN_MONSTER_COUNT = 3;
    public static final int DEFAULT_BOARD_LENGTH = 8, DEFAULT_BOARD_WIDTH = 8;
    public static final int DEFAULT_HERO_COUNT = 3, DEFAULT_LANE_WIDTH = 3;
    public static final int HERO_NEXUS_ROW_IDX = 7, MONSTER_NEXUS_ROW_IDX = 0;

    // color string
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}