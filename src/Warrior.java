/**
 * A sub-class of Hero that specializes in Strength and Agility.
 */

public class Warrior extends Hero {

    Warrior(String _name, HeroAttribute _attribute) {
        super(_name, _attribute, HeroType.Warriors);
    }

    Warrior(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, HeroAttribute _attribute) {
        super(_name, _alias, _coord, _cell, _attribute, HeroType.Warriors);
    }

    @Override
    protected void setAttributeWhenUpgrade() {
        HeroAttribute attribute = getAttribute();

        attribute.addCurExp(-attribute.getMaxExp()); // set exp
        attribute.setMaxExpByLevel();

        // because hp is not capped, so curHp could larger than level*100
        // when this happen, add 100 to curHp
        if (attribute.getCurHp() > attribute.getLevel() * ConstantVariables.heroHpRate) {
            attribute.addCurHp(ConstantVariables.heroHpRate);
        } else {
            attribute.setHpByLevel();
        }
        attribute.addMana((int) (attribute.getMana() * ConstantVariables.manaUpgradeRate));

        // attribute growth
        attribute.addStrength((int) (attribute.getStrength() * ConstantVariables.warriorStrengthUpgradeRate));
        attribute.addDexterity((int) (attribute.getDexterity() * ConstantVariables.warriorDexterityUpgradeRate));
        attribute.addAgility((int) (attribute.getAgility() * ConstantVariables.warriorAgilityUpgradeRate));
    }
}
