package com.github.ssanchez7.finalreality.model.weapon;

import com.github.ssanchez7.finalreality.model.character.player.BlackMages;
import com.github.ssanchez7.finalreality.model.character.player.IPlayer;
import com.github.ssanchez7.finalreality.model.character.player.WhiteMages;

import java.util.List;
import java.util.Objects;

/**
 * A class that holds information of a Staff.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra.
 */
public class Staffs extends AbstractWeapon{

    private final int magicDamage;

    /**
     * Creates a staff with a name, a base damage and weight.
     */
    public Staffs(String name, int damage, int weight, int magicDamage){
        super(name, damage, weight);
        this.magicDamage = magicDamage;
    }

    @Override
    public boolean equipOnBlackMage(BlackMages player){ return true; }
    @Override
    public boolean equipOnWhiteMage(WhiteMages player){ return true; }

    public int getMagicDamage(){
        return this.magicDamage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Staffs)) {
            return false;
        }
        final Staffs weapon = (Staffs) o;
        return getDamage() == weapon.getDamage() &&
                getWeight() == weapon.getWeight() &&
                getName().equals(weapon.getName()) &&
                getMagicDamage() == weapon.getMagicDamage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(Staffs.class, super.hashCode(), getMagicDamage());
    }

    @Override
    public List<String> getValues(){
        List<String> str = super.getValues();
        str.add(String.valueOf((getMagicDamage())));
        return str;
    }
}
