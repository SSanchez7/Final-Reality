package com.github.ssanchez7.finalreality.model.weapon;

import com.github.ssanchez7.finalreality.model.character.player.*;

import java.util.Objects;

/**
 * A class that holds the common information of all the weapons.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra.
 */
public abstract class AbstractWeapon implements IWeapon{

    private final String name;
    private final int damage;
    private final int weight;

    /**
     * Initialize a weapon with a name, a base damage and weight.
     */
    protected AbstractWeapon(final String name, final int damage, final int weight) {
        this.name = name;
        this.damage = damage;
        this.weight = weight;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getDamage() {
        return damage;
    }
    @Override
    public int getWeight() { return weight; }

    @Override
    public boolean equipOnKnight(Knights player){ return false; }
    @Override
    public boolean equipOnThief(Thieves player){ return false; }
    @Override
    public boolean equipOnEngineer(Engineers player){ return false; }
    @Override
    public boolean equipOnBlackMage(BlackMages player){ return false; }
    @Override
    public boolean equipOnWhiteMage(WhiteMages player){ return false; }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDamage(), getWeight());
    }

}
