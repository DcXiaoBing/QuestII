/**
* A sub-class of Monster that specializes in Defense.
*/

public class Exoskeleton extends Monster {

    Exoskeleton(String _name, MonsterAttribute _attribute){
        super(_name, _attribute, MonsterType.Exoskeletons);
    }
    
    Exoskeleton(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, MonsterAttribute _attribute) {
        super(_name, _alias, _coord, _cell, _attribute, MonsterType.Exoskeletons);
    }

}
