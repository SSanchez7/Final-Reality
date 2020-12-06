package com.github.ssanchez7.finalreality.model.weapon;

import com.github.ssanchez7.finalreality.model.Iitem;
import com.github.ssanchez7.finalreality.model.character.player.*;

import java.util.List;

/**
 * This represents a weapon from the game.
 * A item can be used by a character according to the type.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public interface IWeapon extends Iitem {

    /**
     * Returns the weapon's name.
     */
    String getName();

    /**
     * Returns the weapon's damage.
     */
    int getDamage();

    /**
     * Returns the weapon's weight.
     */
    int getWeight();

    /**
     * Returns logical value from: if the weapon can be equipped on the player.
     */
    boolean equipOnKnight(Knights player);
    boolean equipOnThief(Thieves player);
    boolean equipOnEngineer(Engineers player);
    boolean equipOnBlackMage(BlackMages player);
    boolean equipOnWhiteMage(WhiteMages player);

}
