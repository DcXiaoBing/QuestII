/**
 * a class represents a single entry on board for a RPG game
 */
public class RPGBoardEntry{
    private Hero h; // reference to hero in this entry
    private Monster m; // reference to monster in this entry

    private BoardEntryType type;

    RPGBoardEntry(){
        type = BoardEntryType.Regular;
    }
    RPGBoardEntry(BoardEntryType type){
        this.type = type;
    }

    public BoardEntryType getType() {
        return this.type;
    }
    public void setType(BoardEntryType type) {
        this.type = type;
    }

    // TODO: refactor printEntry
    /** 
     * @return return a string represents cell's content
     */ 
    public String[] printEntry(){
        return null;
    }

    public boolean beEnter(GameCharacter c){
        if(canEnter(c)){
            if(c instanceof Monster){
                m = (Monster)c;
            }else if(c instanceof Hero){
                h = (Hero)c;
            }
            return true;
        }
        return false;
    }

    public void beLeft(GameCharacter c){
        if(c instanceof Monster){
            m = null;
        }else if(c instanceof Hero){
            h = null;
        }
    }

    public boolean canEnter(GameCharacter c){
        if(c instanceof Monster){
            return m == null;
        }else if(c instanceof Hero){
            return h == null;
        }

        return false;
    }

    public boolean hasHero(){
        return h != null;
    }
    public boolean hasMonster(){
        return m != null;
    }

    public Hero getHero() {
        return this.h;
    }

    public void setHero(Hero h) {
        this.h = h;
    }

    public Monster getMonster() {
        return this.m;
    }

    public void setMonster(Monster m) {
        this.m = m;
    }
}