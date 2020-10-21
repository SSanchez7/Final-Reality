package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * A class that holds the information of a engineer, a common playable characters in the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class Engineers extends AbstractPlayerCharacter {

    /**
     * Creates a new engineer.
     * @param name
     *      the name of this engineer.
     * @param turnsQueue
     *      the queue with the characters waiting for their turn.
     * @param hpMax
     *      the initial health points of this engineer.
     * @param defensePoints
     *      the defense points of this engineer.
     */
    public Engineers(@NotNull String name, @NotNull BlockingQueue<ICharacter> turnsQueue, int hpMax, int defensePoints){
        super(name, turnsQueue, hpMax, defensePoints);
    }

    @Override
    public void equip(IWeapon weapon){
        this.equippedWeapon=weapon;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Engineers)) {
            return false;
        }
        final Engineers character = (Engineers) o;
        return getDefensePoints() == character.getDefensePoints() &&
                getHpMax() == character.getHpMax() &&
                getName().equals(character.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Engineers.class, getName(), getHpMax(), getDefensePoints());
    }
}
