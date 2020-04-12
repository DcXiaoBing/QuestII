import java.util.List;

/**
 * a class represents all character in Quest hero and monser should be sub-class
 * of this class
 */
public abstract class GameCharacter{
    private Name name;
    private String alias; // for print board purpose
    private Coordinate coord;
    private RPGBoardEntry cell;

    GameCharacter(String _name){
        name = new Name(_name);
        coord = null;
        cell = null;
    }

    GameCharacter(String _name, String _alias, Coordinate _position, RPGBoardEntry _cell){
        name = new Name(_name);
        alias = _alias;
        coord = _position;
        cell = _cell;
    }
    
    public abstract boolean isAlive();

    // cell's influence is shown in attribute getter
    // this two attribute do not exist, needs to compute when need
    public abstract int getDamage();
    public abstract int getDefense();

    /**
     * a function fro enter a cell
     * @param newCoord the target cell's coordinate
     * @param newCell reference to target cell
     * @return whether this operation successed
     */
    public boolean enter(Coordinate newCoord, RPGBoardEntry newCell){
        if(!newCell.canEnter(this)) return false;

        if(cell != null) // because when initialize, hero's cell is null
            cell.beLeft(this);
        
        newCell.beEnter(this);
        coord = newCoord;
        cell = newCell;

        return true;
    }

    public boolean enter(int x, int y, RectangularRPGBoard b){
        RPGBoardEntry newCell = b.getEntry(x, y);
        Coordinate newCoord = new Coordinate(x, y);
        
        if(!newCell.canEnter(this)) return false;

        if(cell != null) // because when initialize, hero's cell is null
            cell.beLeft(this);
        
        newCell.beEnter(this);
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

    public Coordinate getCoord() {
        return this.coord;
    }

    public void setCoord(Coordinate position) {
        this.coord = position;
    }

    public RPGBoardEntry getCell() {
        return this.cell;
    }

    public void setCell(RPGBoardEntry cell) {
        this.cell = cell;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}