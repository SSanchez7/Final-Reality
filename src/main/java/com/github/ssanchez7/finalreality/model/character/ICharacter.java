package com.github.ssanchez7.finalreality.model.character;

/**
 * This represents a character from the game.
 * A character can be controlled by the player or by the CPU (an enemy).
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public interface ICharacter {

  /**
   * Sets a scheduled executor to make this character (thread) wait for {@code speed / 10}
   * seconds before adding the character to the queue.
   */
  void waitTurn();

  /**
   * Returns this character's name.
   */
  String getName();

  /**
   * Returns this character's actual health points.
   */
  int getHp();

  /**
   * Set this character's actual health points.
   */
  void setHp(int hp);

  /**
   * Returns this character's defense.
   */
  int getDefensePoints();

  /**
   * Returns this character's initial health points.
   */
  int getHpMax();
}
