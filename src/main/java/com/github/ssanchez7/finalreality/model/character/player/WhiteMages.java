package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * A class that holds the information of a white mage, a magic playable characters in the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class WhiteMages extends AbstractMagicCharacter {

    /**
     * Creates a new white mage
     * @param name
     *      the name of this white mage.
     * @param turnsQueue
     *      the queue with the characters waiting for their turn
     * @param hpMax
     *      the initial health points of this white mage.
     * @param defensePoints
     *      the defense points of this white mage.
     * @param manaMax
     *      the initial magic points of this white mage.
     */
    public WhiteMages(@NotNull String name, @NotNull BlockingQueue<ICharacter> turnsQueue,
                      int hpMax, int defensePoints, int manaMax){
        super(name, turnsQueue, hpMax, defensePoints, manaMax);
    }

    @Override
    public void equip(IWeapon weapon){
        if(weapon.equipOnWhiteMage(this) && this.getHp()>0){
            this.equippedWeapon = weapon;
        }
    }
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhiteMages)) {
            return false;
        }
        final WhiteMages character = (WhiteMages) o;
        return getDefensePoints() == character.getDefensePoints() &&
                getHpMax() == character.getHpMax() &&
                getManaMax() == character.getManaMax() &&
                getName().equals(character.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(WhiteMages.class, getName(), getHpMax(), getManaMax(), getDefensePoints());
    }
}
