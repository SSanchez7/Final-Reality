package com.github.ssanchez7.finalreality.model;

import java.util.List;

/**
 * This represents a element from the game.
 * can be a weapon and a character.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public interface Iitem {

    /**
     * Returns an array of String with the differentiable values from an element
     */
    List<String> getValues();
}
