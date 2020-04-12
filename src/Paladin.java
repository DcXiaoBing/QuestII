/**
 * A sub-class of Hero that specializes in Strength and Dexterity.
 */

public class Paladin extends Hero{

    Paladin(String _name, HeroAttribute _attribute) {
        super(_name, _attribute, HeroType.Paladins);
    }

    Paladin(String _name, String _alias, Coordinate _coord, RPGBoardEntry _cell, HeroAttribute _attribute) {
        super(_name, _alias, _coord, _cell, _attribute, HeroType.Paladins);
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
        attribute.addStrength((int) (attribute.getStrength() * ConstantVariables.paladinStrengthUpgradeRate));
        attribute.addDexterity((int) (attribute.getDexterity() * ConstantVariables.paladinStrengthUpgradeRate));
        attribute.addAgility((int) (attribute.getAgility() * ConstantVariables.paladinStrengthUpgradeRate));
    }
}
