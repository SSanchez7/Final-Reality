package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.weapon.IWeapon;


public interface IPlayer {
    /**
     * Returns the character's equipped weapon.
     */
    IWeapon getEquippedWeapon();

    /**
     * Returns the character's class.
     */
    String getCharacterClass();

    /**
     * Equip the character's weapon
     */
    void equip(IWeapon weapon);

}
