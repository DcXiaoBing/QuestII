import java.util.List;

/**
 * a class represents potion
 */
public class Potion extends Item implements Sellable{
    private HeroAttribute attribute;
    private int effectAmount; // only for display info
    private boolean canBeSold = true;


    Potion(String _name, int _price, int _minimumLevel, HeroAttribute _attribute, int _effectAmount){
        super(_name, _price, _minimumLevel);
        attribute = _attribute;
        effectAmount = _effectAmount;
    }

    public void usePotion(Hero user){
        HeroAttribute curAttr = user.getAttribute();

        curAttr.addMana(attribute.getMana());
        curAttr.addStrength(attribute.getStrength());
        curAttr.addDexterity(attribute.getDexterity());
        curAttr.addAgility(attribute.getAgility());
        curAttr.addCurExp(attribute.getCurExp());

        OutputTools.printGreenString("Use potion success");
    }

    // name price minlevel effect amount
    //  15    7      8          15     
    private static final String POTION_INFO_SEPERATOR = "|-----|-----------------|-------|--------|---------------|";
    private static final String POTION_INFO_HEADER = "|index|   Potion Name   | Price |minLevel| Effect Amount |";
    private static final String POTION_INFO_FORMAT = "|%-5d|%-17s|%-7d|%-8d|%-15d|%n";

    public static void printPotionList(List<Potion> list){
        if(OutputTools.emptyList(list)) return;

        System.out.println(POTION_INFO_SEPERATOR);
        System.out.println(POTION_INFO_HEADER);
        System.out.println(POTION_INFO_SEPERATOR);

        int counter = 1;
        for(Potion p : list){
            System.out.format(POTION_INFO_FORMAT, counter, p.getName(), p.getPrice(), p.getMimumLevel(), p.getEffectAmount());

            counter++;
            System.out.println(POTION_INFO_SEPERATOR);
        }
    }

    public HeroAttribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(HeroAttribute attribute) {
        this.attribute = attribute;
    }

    public int getEffectAmount() {
        return this.effectAmount;
    }

    public void setEffectAmount(int effectAmount) {
        this.effectAmount = effectAmount;
    }

    @Override
    public int getHalfPrice() {
        return (int)(0.5 * getPrice());
    }

    @Override
    public boolean canSoldToMarket() {
        return canBeSold;
    }
}
