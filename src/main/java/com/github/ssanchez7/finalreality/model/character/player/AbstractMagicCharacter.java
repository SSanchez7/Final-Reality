package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

public abstract class AbstractMagicCharacter extends AbstractPlayerCharacter {

    private int mana;

    protected AbstractMagicCharacter(@NotNull String name,
             @NotNull BlockingQueue<ICharacter> turnsQueue, String characterClass,
             int hp, int defense, int mana){
        super(name, turnsQueue, characterClass, hp, defense);
        this.mana = mana;
    }

    int getMana(){
        return this.mana;
    }

    void setMana(int mana){
        this.mana = mana;
    }
}
