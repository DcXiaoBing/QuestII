import java.util.List;

/**
 * a class represents weapon
 */
public class Weapon extends Item{
    private int damage;
    private boolean singleHand;



    Weapon(String _name, int _price, int _minimumLevel, int _damage, boolean _singleHand){
        super(_name, _price, _minimumLevel);
        damage = _damage;
        singleHand = _singleHand;
    }

    // name price minlevel damage sigleHand
    //  15    7      8       8        9
    private static final String WEAPON_INFO_SEPERATOR = "|-----|---------------|-------|--------|--------|----------|";
    private static final String WEAPON_INFO_HEADER = "|index|  Weapon Name  | Pirce |minLevel| damage |singleHand|";
    private static final String WEAPON_INFO_FORMAT = "|%-5d|%-15s|%-7d|%-8d|%-8d|%-10s|%n";
    
    public static void printWeaponList(List<Weapon> list){
        if(OutputTools.emptyList(list)) return;

        System.out.println(WEAPON_INFO_SEPERATOR);
        System.out.println(WEAPON_INFO_HEADER);
        System.out.println(WEAPON_INFO_SEPERATOR);

        int counter = 1;
        for(Weapon w : list){
            System.out.format(WEAPON_INFO_FORMAT, counter, w.getName(), w.getPrice(), w.getMimumLevel(), w.getDamage(), w.isSingleHand());
            System.out.println(WEAPON_INFO_SEPERATOR);
            counter++;
        }
    }
    public int getDamage() {return this.damage;}
    public void setDamage(int damage) { this.damage = damage;}
    public boolean isSingleHand() { return this.singleHand;}
    public void setSingleHand(boolean singleHand) { this.singleHand = singleHand;}
}