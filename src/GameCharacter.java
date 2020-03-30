/**
 * a class represents all character in Quest
 * hero and monser should be sub-class of this class
 */
public abstract class GameCharacter{
    private Name name;
    private Coordinate coord;

    GameCharacter(String _name){
        name = new Name(_name);
        coord = null;
    }
    GameCharacter(String _name, Coordinate _position){
        name = new Name(_name);
        coord = _position;
    }
    
    public abstract boolean isAlive();

    // cell's influence is shown in attribute getter
    // this two attribute do not exist, needs to compute when need
    public abstract int getDamage();
    public abstract int getDefense();

    public String getName() {
        return this.name.getFirstName();
    }

    public void setName(String name) {
        this.name = new Name(name);
    }

    public Coordinate getPosition() {
        return this.coord;
    }

    public void setPosition(Coordinate position) {
        this.coord = position;
    }
}