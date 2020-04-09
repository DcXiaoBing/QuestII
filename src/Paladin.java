/**
* A sub-class of Hero that specializes in Strength and Dexterity.
*/

public class Paladin extends Hero {

    Paladin(String _name, HeroAttribute _attribute){
        super(_name, _attribute, HeroType.Paladins);
    }
    
    Paladin(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, HeroAttribute _attribute) {
        super(_name, _alias, _coord, _cell, _attribute, HeroType.Paladins);
    }

}
