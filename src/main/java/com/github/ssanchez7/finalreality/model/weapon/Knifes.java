package com.github.ssanchez7.finalreality.model.weapon;

/**
 * A class that holds information of a Blade.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra.
 */
public class Knifes extends AbstractWeapon {

    /**
     * Creates a blade with a name, a base damage and weight.
     */
    public Knifes(String name, int damage, int weight) {
        super(name, damage, weight, "knife");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Knifes)) {
            return false;
        }
        final Knifes weapon = (Knifes) o;
        return getDamage() == weapon.getDamage() &&
                getWeight() == weapon.getWeight() &&
                getName().equals(weapon.getName());
    }
}
