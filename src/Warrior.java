/**
* A sub-class of Hero that specializes in Strength and Agility.
*/

public class Warrior extends Hero {

    Warrior(String _name, HeroAttribute _attribute){
        super(_name, _attribute, HeroType.Warriors);
    }
    
    Warrior(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, HeroAttribute _attribute) {
        super(_name, _alias, _coord, _cell, _attribute, HeroType.Warriors);
    }

}
