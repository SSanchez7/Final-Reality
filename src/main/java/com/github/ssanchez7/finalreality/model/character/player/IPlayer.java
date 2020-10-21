package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;

/**
 * This represents a playable character from the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public interface IPlayer extends ICharacter {
    /**
     * Returns the character's equipped weapon.
     */
    IWeapon getEquippedWeapon();

    /**
     * Equip the character's weapon
     */
    void equip(IWeapon weapon);

}
