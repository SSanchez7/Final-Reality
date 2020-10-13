package com.github.ssanchez7.finalreality.model.weapon;

/**
 * This represents a weapon from the game.
 * A item can be used by a character according to the type.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public interface IWeapon {

    /**
     * Returns the weapon's name.
     */
    String getName();

    /**
     * Returns the weapon's damage.
     */
    int getDamage();

    /**
     * Returns the wepaon's weight.
     */
    int getWeight();

    /**
     * Returns this weapon's type.
     */
    String getType();

}
