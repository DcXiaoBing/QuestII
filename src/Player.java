/**
 * class represents the player that participate in any type game
 */
public class Player {
    private final Name name;
    private final int teamId;
    
    Player(String _name, int _teamId){
        name = new Name(_name);
        teamId = _teamId;
    }

    public String getName(){
        return name.getFullName();
    }
    public int getTeamId(){
        return teamId;
    }
}