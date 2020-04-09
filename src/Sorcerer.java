/**
* A sub-class of Hero that specializes in Dexerity and Agility.
*/

public class Sorcerer extends Hero {

    Sorcerer(String _name, HeroAttribute _attribute){
        super(_name, _attribute, HeroType.Sorcerers);
    }
    
    Sorcerer(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, HeroAttribute _attribute) {
        super(_name, _alias, _coord, _cell, _attribute, HeroType.Sorcerers);
    }

}
