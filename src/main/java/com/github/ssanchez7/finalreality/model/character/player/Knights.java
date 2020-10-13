package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

public class Knights extends AbstractCommonCharacter {

    public Knights(@NotNull String name, @NotNull BlockingQueue<ICharacter> turnsQueue, int hp, int defense){
        super(name, turnsQueue, "knight", hp, defense);
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
        if (!(o instanceof Knights)) {
            return false;
        }
        final Knights character = (Knights) o;
        return getDefense() == character.getDefense() &&
                getCharacterClass() == character.getCharacterClass() &&
                getName().equals(character.getName());
    }
}
