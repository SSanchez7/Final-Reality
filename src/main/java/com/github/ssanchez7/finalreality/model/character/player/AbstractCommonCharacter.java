package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

public abstract class AbstractCommonCharacter extends AbstractPlayerCharacter {

    protected AbstractCommonCharacter(@NotNull String name,
          @NotNull BlockingQueue<ICharacter> turnsQueue, String characterClass, int hp, int defense){
        super(name, turnsQueue, characterClass, hp, defense);

    }
}
