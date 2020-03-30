/**
 * a interface that shows the action in a TurnBasedCombat
 */
public interface TurnBasedCombat{

    /**
     * a method to make a move int combat
     * @param c a reference of character to make move
     */
    public abstract void makeMove(GameCharacter c);

    /**
     * a method to judge whether this game ends
     * @return show who wins. 1 means hero, 2 means monster. 0 means quit by player
     */
    public abstract int isEnd(boolean isMonster);

    /**
     * a method handle the logic when ending the combat
     * @param state
     */
    public abstract void combatEnd(int state);
}