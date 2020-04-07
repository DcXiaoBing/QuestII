import java.util.*;

/**
 * hard coded data for hero
 */

 public class Infos{
    public static List<Hero> getAllHeros(){
        List<Hero> res = new ArrayList<>();

        HeroAttribute attribute = null;

        // Name             mana   strength agility dexterity starting money starting experience
        // Gaerdal_Ironhand    100     700     500     600     1354    7
        // Sehanine_Monnbow    600     700     800     500     2500    8
        // Muamman_Duathall    300     900     500     750     2546    6
        // Flandal_Steelskin   200     750     650     700     2500    7
        String[] names = new String[]{"Gaerdal_Ironhand", "Sehanine_Monnbow", "Muamman_Duathall", "Flandal_Steelskin"};
        int[] manas = new int[]{100, 600, 300, 200};
        int[] strengths = new int[]{700, 700, 900, 750};
        int[] agilitys = new int[]{500, 800, 500, 650};
        int[] dexteritys = new int[]{600, 500, 750, 700};
        int[] moneys = new int[]{1354, 2500, 2546, 2500};
        int[] exps = new int[]{7, 8, 6, 7};
        for(int i = 0; i < names.length; i++){
            attribute = new HeroAttribute(manas[i], strengths[i], agilitys[i], dexteritys[i], moneys[i], exps[i]);
            res.add(new Warrior(names[i], attribute));
        }

        // Name                   mana   stren  agil dexterity money experience
        // Garl_Glittergold        700     550     600   500     2500    7
        // Rillifane_Rallathil     1300    750     450   500     2500    9
        // Segojan_Earthcaller     900     800     500   650     2500    5
        // Skoraeus_Stonebones     800     850     600   450     2500    6
        names = new String[]{"Garl_Glittergold", "Rillifane_Rallathil", "Segojan_Earthcaller", "Skoraeus_Stonebones"};
        manas = new int[]{700, 1300, 900, 800};
        strengths = new int[]{550, 750, 800, 850};
        agilitys = new int[]{600, 450, 500, 600};
        dexteritys = new int[]{500, 500, 650, 450};
        moneys = new int[]{2500, 2500, 2500, 2500};
        exps = new int[]{7, 9, 5, 6};
        for(int i = 0; i < names.length; i++){
            attribute = new HeroAttribute(manas[i], strengths[i], agilitys[i], dexteritys[i], moneys[i], exps[i]);
            res.add(new Sorcerer(names[i], attribute));
        }

        // Name                mana    stren   agil  dex   starting money starting experience
        // Solonor_Thelandira   300     750     650     700     2500    7
        // Sehanine_Moonbow     300     750     700     700     2500    7
        // Skoraeus_Stonebones  250     650     600     350     2500    4
        // Garl_Glittergold     100     600     500     400     2500    5

        names = new String[]{"Solonor_Thelandira", "Sehanine_Moonbow", "Skoraeus_Stonebones", "Garl_Glittergold"};
        manas = new int[]{300, 300, 250, 100};
        strengths = new int[]{750, 750, 650, 600};
        agilitys = new int[]{650, 700, 600, 500};
        dexteritys = new int[]{700, 700, 350, 400};
        moneys = new int[]{2500, 2500, 2500, 2500};
        exps = new int[]{7, 7, 4, 5};
        for(int i = 0; i < names.length; i++){
            attribute = new HeroAttribute(manas[i], strengths[i], agilitys[i], dexteritys[i], moneys[i], exps[i]);
            res.add(new Paladin(names[i], attribute));
        }
        
        return res;
    }

    public static List<Monster> getAllMonster(){
        List<Monster> res = new ArrayList<>();
        MonsterAttribute attribute = null;

        // Name        level   damage   defense  dodge chance
        // Desghidorrah	 3       300       400     35
        // Chrysophylax  2       200       500     20
        // BunsenBurner	 4       400       500     45
        // Natsunomeryu  1       100       200     10
        // TheScaleless	 7       700       600     75
        // Kas-Ethelinh  5       600       500     60
        // Alexstraszan	 10      1000      9000    55
        // Phaarthurnax	 6       600       700     60
        // D-Maleficent	 9       900       950     85
        // TheWeatherbe  8       800       900     80

        String[] names = new String[]{"Desghidorrah", "Chrysophylax", "BunsenBurner", "Natsunomeryu", "TheScaleless", "Kas-Ethelinh", "Alexstraszan", "Phaarthurnax", "D-Maleficent", "TheWeatherbe"};
        int[] levels = new int[]{3, 2, 4, 1, 7, 5, 10, 6, 9, 8};
        int[] damages = new int[]{300, 200, 400, 100, 700, 600, 1000, 600, 900, 800};
        int[] defenses = new int[]{400, 500, 500, 200, 600, 500, 9000, 700, 950, 900};
        int[] dogeChances = new int[]{35, 20, 45, 10, 75, 60, 55, 60, 85, 80};
        
        // System.out.println(names.length + " " + levels.length + " " + damages.length + " " + defenses.length + " " + dogeChances.length);
        for(int i = 0; i < names.length; i++){
            attribute = new MonsterAttribute(levels[i], damages[i],defenses[i], dogeChances[i]);
            res.add(new Dragon(names[i], attribute));
        }

        // Name          level    damage    defense dodge chance
        // Cyrrollalee     7       700        800     75
        // Brandobaris     3       350        450     30
        // BigBad-Wolf     1       150        250     15
        // WickedWitch     2       250        350     25
        // Aasterinian     4       400        500     45
        // Chronepsish     6       650        750     60
        // Kiaransalee     8       850        950     85
        // St-Shargaas     5       550        650     55
        // Merrshaullk     10      1000       900     55
        // St-Yeenoghu     9       950        850     90

        names = new String[]{"Cyrrollalee", "Brandobaris", "BigBad-Wolf", "WickedWitch", "Aasterinian", "Chronepsish", "Kiaransalee", "St-Shargaas", "Merrshaullk", "St-Yeenoghu"};
        levels = new int[]{7, 3, 1, 2, 4, 6, 8, 5, 10, 9};
        damages = new int[]{700, 350, 150, 250, 400, 650, 850, 550, 1000, 950};
        defenses = new int[]{800, 450, 250, 350, 500, 750, 950, 650, 900, 850};
        dogeChances = new int[]{75, 30, 15, 25, 45, 60, 85, 55, 55, 90};

        // System.out.println(names.length + " " + levels.length + " " + damages.length + " " + defenses.length + " " + dogeChances.length);
        for(int i = 0; i < names.length; i++){
            attribute = new MonsterAttribute(levels[i], damages[i],defenses[i], dogeChances[i]);
            res.add(new Exoskeleton(names[i], attribute));
        }

        // Name          level    damage   defense dodge chance
        // Andrealphus     2       600       500     40
        // Aim-Haborym     1       450       350     35
        // Andromalius     3       550       450     25
        // Chiang-shih     4       700       600     40
        // FallenAngel     5       800       700     50
        // Ereshkigall     6       950       450     35
        // Melchiresas     7       350       150     75
        // Jormunngand     8       600       900     20
        // Rakkshasass     9       550       600     35
        // Taltecuhtli     10      300       200     50
        names = new String[]{"Andrealphus", "Aim-Haborym", "Andromalius", "Chiang-shih", "FallenAngel", "Ereshkigall", "Melchiresas", "Jormunngand", "Rakkshasass", "Taltecuhtli"};
        damages = new int[]{600, 450, 550, 700, 800, 950, 350, 600, 550, 300};
        defenses = new int[]{500, 350, 450, 600, 700, 450, 150, 900, 600, 200};
        dogeChances = new int[]{40, 35, 25, 40, 50, 35, 75, 20, 35, 50};

        // System.out.println(names.length + " " + levels.length + " " + damages.length + " " + defenses.length + " " + dogeChances.length);
        for(int i = 0; i < names.length; i++){
            attribute = new MonsterAttribute(levels[i], damages[i],defenses[i], dogeChances[i]);
            res.add(new Spirit(names[i], attribute));
        }
        
        return res;
     }
     public static List<Weapon> getAllWeapons(){
        List<Weapon> res = new ArrayList<>();
        // Name/cost/level/damage/required hands
        // Sword           500     1    800    1
        // Bow             300     2    500    2
        // Scythe          1000    6    1100   2
        // Axe             550     5    850    1
        // Shield          400     1    100    1
        // TSwords         1400    8    1600   2
        // Dagger          200     1    250    1

        String[] names = new String[]{"Sword", "Bow", "Scythe", "Axe", "Shield", "TSwords", "Dagger"};
        int[] costs = new int[]{500, 300, 1000, 550, 400, 1400, 200};
        int[] levels = new int[]{1, 2, 6, 5, 1, 8, 1};
        int[] damages = new int[]{800, 500, 1100, 850, 100, 1600, 250};
        boolean[] sigleHand = new boolean[]{true, false, false, true, true, false, true};
        
        for(int i = 0; i < names.length; i++){
            res.add(new Weapon(names[i], costs[i], levels[i], damages[i], sigleHand[i]));
        }

        return res;
     }
     public static List<Armor> getAllArmors(){
        List<Armor> res = new ArrayList<>();
        //  Name/cost/required level/damage reduction
        //  Platinum_Shield       150   1   200
        //  Breastplate           350   3   600
        //  Full_Body_Armor       1000  8   1100
        //  Wizard_Shield         1200  10  1500
        //  Speed_Boots           550   4   600
        String[] names = new String[]{"Platinum_Shield", "Breastplate", "Full_Body_Armor", "Wizard_Shield", "Speed_Boots"};
        int[] costs = new int[]{150, 350, 1000, 1200, 550};
        int[] levels = new int[]{1, 3, 8, 10, 4};
        int[] defenses = new int[]{200, 600, 1100, 1500, 600};
        for(int i = 0; i < names.length; i++){
            res.add(new Armor(names[i], costs[i], levels[i], defenses[i]));
        }
        
        return res;
     }

     public static List<Spell> getAllSpells(){
         List<Spell> res = new ArrayList<>();

        //  Name/cost/required level/damage/mana cost
        //  Flame_Tornado   700     4   850     300
        //  Breath_of_Fire  350     1   450     100
        //  Heat_Wave       450     2   600     150
        //  Lava_Commet     800     7   1000    550
        //  Heat_Wave       450     2   600     150

        String[] names = new String[]{"Flame_Tornado", "Breath_of_Fire", "Heat_Wave", "Lava_Commet", "Heat_Wave"};
        int[] costs = new int[]{700, 350, 450, 800, 450};
        int[] levels = new int[]{4, 1, 2, 7, 2};
        int[] damages = new int[]{850, 450, 600, 1000, 600};
        int[] manaCosts = new int[]{300, 100, 150, 550, 150};

        for(int i = 0; i < names.length; i++){
            res.add(new Spell(names[i], costs[i], levels[i], damages[i], manaCosts[i], SpellType.Fire));
        }
        // Name/cost/required level/damage/mana cost
        // Snow_Canon      500     2   650     250
        // Ice_Blade       250     1   450     100
        // Frost_Blizzard  750     5   850     350
        // Arctic_storm    700     6   800     300
        // Ice_Blade       250     1   450     100

        names = new String[]{"Snow_Canon", "Ice_Blade", "Frost_Blizzard", "Arctic_storm", "Ice_Blade"};
        costs = new int[]{500, 250, 750, 700, 250};
        levels = new int[]{2, 1, 5, 6, 1};
        damages = new int[]{650, 450, 850, 800, 450};
        manaCosts = new int[]{250, 100, 350, 300, 100};
        for(int i = 0; i < names.length; i++){
            res.add(new Spell(names[i], costs[i], levels[i], damages[i], manaCosts[i], SpellType.Ice));
        }
        // Name/cost/required level/damage/mana cost
        // LightningDagger       400        1       500     150
        // Thunder_Blast         750        4       950     400
        // Electric_Arrows       550        5       650     200
        // Spark_Needles         500        2       600     200
        // LightningDagger       400        1       500     150
        names = new String[]{"LightningDagger", "Thunder_Blast", "Electric_Arrows", "Spark_Needles", "LightningDagger"};
        costs = new int[]{400, 750, 550, 500, 400};
        levels = new int[]{1, 4, 5, 2, 1};
        damages = new int[]{500, 950, 650, 600, 500};
        manaCosts = new int[]{150, 400, 200, 200, 150};
        for(int i = 0; i < names.length; i++){
            res.add(new Spell(names[i], costs[i], levels[i], damages[i], manaCosts[i], SpellType.Ice));
        }
        
        return res;
     }

     public static List<Potion> getAllPotions(){
        List<Potion> res = new ArrayList<>();

        String[] names = new String[]{"Healing_Potion", "Magic_Potion","Strength_Potion", "Agility_Potion", "Dexterity_Potion", "All_Attri_Potion"};
        int[] costs = new int[]{250, 350, 300, 300, 300, 1000};
        int[] levels = new int[]{1, 2, 1, 1, 1, 5};
        int[] effectAmout = new int[]{100, 100, 75, 75, 75, 80};

        int[] hps = new int[]{100, 0, 0, 0, 0, 80};
        int[] manas = new int[]{0, 100, 0, 0, 0, 80};
        int[] strengths = new int[]{0, 0, 75, 0, 0, 80};
        int[] agilitys = new int[]{0, 0, 0, 75, 0, 80};
        int[] dexteritys = new int[]{0, 0, 0, 0, 75, 80};

        for(int i = 0; i < names.length; i++){
            HeroAttribute attribute = new HeroAttribute(hps[i], manas[i], strengths[i], agilitys[i], dexteritys[i], 0, 0);

            res.add(new Potion(names[i], costs[i], levels[i], attribute, effectAmout[i]));
        }

        return res;
     }
 }
