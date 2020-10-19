package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

/**
 * An abstract class that holds the common behaviour of all the magic playable characters in the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public abstract class AbstractMagicCharacter extends AbstractPlayerCharacter {

    private final int manaMax;
    private int mana;

    /**
     * Initialize a new Magic playable character.
     *
     * @param name
     *      Character's name.
     * @param turnsQueue
     *      the queue with the characters waiting for their turn
     * @param hpMax
     *      Character's initial health points.
     * @param defensePoints
     *      Character's defense points.
     * @param manaMax
     *      Character's initial magic points
     */
    protected AbstractMagicCharacter(@NotNull String name,
             @NotNull BlockingQueue<ICharacter> turnsQueue,
             int hpMax, int defensePoints, int manaMax){
        super(name, turnsQueue, hpMax, defensePoints);
        this.mana = manaMax;
        this.manaMax = manaMax;
    }

    /**
     * Set this character's actual magic points.
     */
    protected void setMana(int mana){
        this.mana = mana;
    }

    /**
     * Returns this character's actual magic points.
     */
    protected int getMana(){
        return this.mana;
    }

    /**
     * Returns this character's initial magic points.
     */
    protected int getManaMax(){
        return this.manaMax;
    }


}
