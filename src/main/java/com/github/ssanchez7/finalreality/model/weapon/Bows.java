package com.github.ssanchez7.finalreality.model.weapon;

import com.github.ssanchez7.finalreality.model.character.player.IPlayer;

import java.util.Objects;

/**
 * A class that holds information of a Bow.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra.
 */
public class Bows extends AbstractWeapon{

    /**
     * Creates a bow with a name, a base damage and weight.
     */
    public Bows(String name, int damage, int weight) {
        super(name, damage, weight);
    }

    @Override
    public boolean equipOnEngineer(IPlayer player){ return true; }
    @Override
    public boolean equipOnThief(IPlayer player){ return true; }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bows)) {
            return false;
        }
        final Bows weapon = (Bows) o;
        return getDamage() == weapon.getDamage() &&
                getWeight() == weapon.getWeight() &&
                getName().equals(weapon.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Bows.class, getName(), getDamage(), getWeight());
    }

}
