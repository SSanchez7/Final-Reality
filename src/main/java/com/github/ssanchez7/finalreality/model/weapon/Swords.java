package com.github.ssanchez7.finalreality.model.weapon;

/**
 * A class that holds information of a Sword.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra.
 */
public class Swords extends AbstractWeapon{

    /**
     * Creates a sword with a name, a base damage and weight.
     */
    public Swords(String name, int damage, int weight) {
        super(name, damage, weight, "sword");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Swords)) {
            return false;
        }
        final Swords weapon = (Swords) o;
        return getDamage() == weapon.getDamage() &&
                getWeight() == weapon.getWeight() &&
                getName().equals(weapon.getName());
    }
}
