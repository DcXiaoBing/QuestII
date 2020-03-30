/**
 * a class represents common atrribute for all character in RPG game
 */
public class Attribute{
    private int level;
    private int curHp; // hp is not capped

    Attribute(){
        level = ConstantVariables.defaultInitalLevel;
        setHpByLevel();
    }
    Attribute(int _level){
        level = _level;
        setHpByLevel();
    }

    Attribute(int _level, int _curHp){
        level = _level;
        curHp = _curHp;
    }

    public void setHpByLevel(){
        curHp = level * ConstantVariables.heroHpRate;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void levelUp(){
        level += 1;
    }

    public int getCurHp() {
        return this.curHp;
    }

    public void setCurHp(int curHp) {
        this.curHp = curHp;
    }

    public void addCurHp(int incre){
        curHp += incre;
    }
}