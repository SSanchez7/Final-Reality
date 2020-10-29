package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * A class that holds the information of a black mage, a magic playable characters in the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class BlackMages extends AbstractMagicCharacter {

    /**
     * Creates a new black mage
     * @param name
     *      the name of this black mage.
     * @param turnsQueue
     *      the queue with the characters waiting for their turn
     * @param hpMax
     *      the initial health points of this black mage.
     * @param defensePoints
     *      the defense points of this black mage.
     * @param manaMax
     *      the initial magic points of this black mage.
     */
    public BlackMages(@NotNull String name, @NotNull BlockingQueue<ICharacter> turnsQueue,
                      int hpMax, int defensePoints, int manaMax){
        super(name, turnsQueue, hpMax, defensePoints, manaMax);
    }

    @Override
    public void equip(IWeapon weapon){
        if(weapon.equipOnBlackMage(this)){
            this.equippedWeapon = weapon;
        }
    }
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlackMages)) {
            return false;
        }
        final BlackMages character = (BlackMages) o;
        return getDefensePoints() == character.getDefensePoints() &&
                getHpMax() == character.getHpMax() &&
                getManaMax() == character.getManaMax() &&
                getName().equals(character.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(BlackMages.class, getName(), getHpMax(), getManaMax(), getDefensePoints());
    }
}
