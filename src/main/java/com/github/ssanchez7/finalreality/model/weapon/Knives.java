package com.github.ssanchez7.finalreality.model.weapon;

import java.util.Objects;

/**
 * A class that holds information of a Knife.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra.
 */
public class Knives extends AbstractWeapon {

    /**
     * Creates a Knife with a name, a base damage and weight.
     */
    public Knives(String name, int damage, int weight) {
        super(name, damage, weight);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Knives)) {
            return false;
        }
        final Knives weapon = (Knives) o;
        return getDamage() == weapon.getDamage() &&
                getWeight() == weapon.getWeight() &&
                getName().equals(weapon.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Knives.class, getName(), getDamage(), getWeight());
    }
}
