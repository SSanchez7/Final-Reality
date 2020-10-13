package com.github.ssanchez7.finalreality.model.weapon;

/**
 * A class that holds information of a Axe.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra.
 */
public class Axes extends AbstractWeapon{

    /**
     * Creates a axe with a name, a base damage and weight.
     */
    public Axes(String name, int damage, int weight) {
        super(name, damage, weight, "axe");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Axes)) {
            return false;
        }
        final Axes weapon = (Axes) o;
        return getDamage() == weapon.getDamage() &&
                getWeight() == weapon.getWeight() &&
                getName().equals(weapon.getName());
    }
}
