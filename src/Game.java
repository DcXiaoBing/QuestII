import java.util.*;

/**
 * a class represents the general game
 * all game should be a subclass of this class
 * 
 * Support store different players in one team
 */
public abstract class Game {
    // list of player that participates in the game
    // players[i] means the i-th team
    List<Player> players[];

    Game(int countOfTeam){
        setPlayerList(countOfTeam);
    }

    public void setPlayerList(int countOfTeam){
        players = new List[countOfTeam + 1]; // index is 1-based
        for(int i = 0; i < countOfTeam + 1; i++){
            players[i] = new ArrayList<>();
        }
    }
    public void addPlayer(Player p){
        players[p.getTeamId()].add(p);
    }
    /**
     * entrence of game
     */
    public abstract void start(); 

    /**
     * to judge whether game end
     * @return the state indicates how game end. -1 - not end, 0 - not end, 1 - player win, 2 - monster win.
     */
    public abstract int isEnd();

    /**
     * print winning message or stale message.
     */
    public abstract void end();
}