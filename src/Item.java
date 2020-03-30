/**
 * a class represents all item armor weapon potion spell all are items
 */
public abstract class Item{
    private String name;
    private int price;
    private int minimumLevel;

    Item(String _name, int _price, int _minimumLevel){
        name = _name;
        price = _price;
        minimumLevel = _minimumLevel;
    }

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public int getPrice() {return this.price;}
    public void setPrice(int price) {this.price = price;}
    public int getMimumLevel() {return this.minimumLevel;}
    public void setMimumLevel(int mimumLevel) {this.minimumLevel = mimumLevel;}
}