package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * A class that holds the information of a knight, a common playable characters in the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class Knights extends AbstractPlayerCharacter {

    /**
     * Creates a new knight
     * @param name
     *      the name of this knight.
     * @param turnsQueue
     *      the queue with the characters waiting for their turn
     * @param hpMax
     *      the initial health points of this knight.
     * @param defensePoints
     *      the defense points of this knight.
     */
    public Knights(@NotNull String name, @NotNull BlockingQueue<ICharacter> turnsQueue, int hpMax, int defensePoints){
        super(name, turnsQueue, hpMax, defensePoints);
    }

    @Override
    public void equip(IWeapon weapon){
        if(weapon.equipOnKnight(this) && this.getHp()>0){
            this.equippedWeapon = weapon;
        }
    }
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Knights)) {
            return false;
        }
        final Knights character = (Knights) o;
        return getDefensePoints() == character.getDefensePoints() &&
                getHpMax() == character.getHpMax() &&
                getName().equals(character.getName());
    }

    @Override
    public int hashCode() { return Objects.hash(Knights.class, super.hashCode()); }
}
