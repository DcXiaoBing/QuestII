/**
* A sub-class of Monster that specializes in Damage.
*/

public class Dragon extends Monster {

    Dragon(String _name, MonsterAttribute _attribute){
        super(_name, _attribute, MonsterType.Dragons);
    }
    
    Dragon(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, MonsterAttribute _attribute) {
        super(_name, _alias, _coord, _cell, _attribute, MonsterType.Dragons);
    }

}
