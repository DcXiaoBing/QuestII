/**
 * a class represents all character in Quest
 * hero and monser should be sub-class of this class
 */
public abstract class GameCharacter{
    private Name name;
    private Coordinate coord;
    private RPGBoardEntry cell;

    GameCharacter(String _name){
        name = new Name(_name);
        coord = null;
        cell = null;
    }
    GameCharacter(String _name, Coordinate _position, RPGBoardEntry _cell){
        name = new Name(_name);
        coord = _position;
        cell = _cell;
    }
    
    public abstract boolean isAlive();

    // cell's influence is shown in attribute getter
    // this two attribute do not exist, needs to compute when need
    public abstract int getDamage();
    public abstract int getDefense();

    // enter a cell
    public boolean enter(Coordinate newCoord, RPGBoardEntry newCell){
        if(!newCell.canEnter(this)) return false;

        cell.leave(this);
        newCell.enter(this);
        coord = newCoord;
        cell = newCell;

        return true;
    }

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

    public RPGBoardEntry getCell() {
        return this.cell;
    }

    public void setCell(RPGBoardEntry cell) {
        this.cell = cell;
    }
}