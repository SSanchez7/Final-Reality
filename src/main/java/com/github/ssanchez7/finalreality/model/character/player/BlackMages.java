package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

public class BlackMages extends AbstractMagicCharacter {

    public BlackMages(@NotNull String name, @NotNull BlockingQueue<ICharacter> turnsQueue, int mana,
                      int hp, int defense){
        super(name, turnsQueue,"black mage", mana, hp, defense);
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
        if (!(o instanceof BlackMages)) {
            return false;
        }
        final BlackMages character = (BlackMages) o;
        return getDefense() == character.getDefense() &&
                getCharacterClass() == character.getCharacterClass() &&
                getName().equals(character.getName());
    }
}
