/**
* A sub-class of Monster that specializes in Dodging.
*/

public class Spirit extends Monster {

    Spirit(String _name, MonsterAttribute _attribute){
        super(_name, _attribute, MonsterType.Spirits);
    }
    
    Spirit(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, MonsterAttribute _attribute) {
        super(_name, _alias, _coord, _cell, _attribute, MonsterType.Spirits);
    }

}
