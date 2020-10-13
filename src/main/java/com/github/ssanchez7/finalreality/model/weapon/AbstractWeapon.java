package com.github.ssanchez7.finalreality.model.weapon;

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
    private final String type;

    /**
     * Creates a weapon with a name, a base damage, weight and it's type.
     */
    protected AbstractWeapon(final String name, final int damage, final int weight,
                  final String type) {
        this.name = name;
        this.damage = damage;
        this.weight = weight;
        this.type = type;
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
    public String getType() {
        return type;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDamage(), getWeight(), getType());
    }
}
