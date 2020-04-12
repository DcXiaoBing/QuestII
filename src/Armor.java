import java.util.List;

public class Armor extends Item implements Sellable{
    private int defense;
    private boolean canBeSold = true;

    Armor(String _name, int _price, int _minimumLevel, int _defense){
        super(_name, _price, _minimumLevel);

        defense = _defense;
    }

    // name price minlevel damage sigleHand
    //  15    7      8       8        9
    private static final String ARMOR_INFO_SEPERATOR = "|-----|---------------|-------|--------|--------|";
    private static final String ARMOR_INFO_HEADER = "|index|  Armor  Name  | Price |minLevel| damage |";
    private static final String ARMOR_INFO_FORMAT = "|%-5d|%-15s|%-7d|%-8d|%-8d|%n";
    public static void printArmorList(List<Armor> list){
        if(OutputTools.emptyList(list)) return;

        System.out.println(ARMOR_INFO_SEPERATOR);
        System.out.println(ARMOR_INFO_HEADER);
        System.out.println(ARMOR_INFO_SEPERATOR);

        int counter = 1;
        for(Armor a : list){
            System.out.format(ARMOR_INFO_FORMAT, counter, a.getName(), a.getPrice(), a.getMimumLevel(), a.getDefense());
            System.out.println(ARMOR_INFO_SEPERATOR);
            counter++;
        }
    }
    @Override
    public int getHalfPrice() {
        return (int)(0.5 * getPrice());
    }

    @Override
    public boolean canSoldToMarket() {
        return canBeSold;
    }

    public int getDefense(){return this.defense;}
    public void setDefense(int defense){this.defense = defense;}
}
