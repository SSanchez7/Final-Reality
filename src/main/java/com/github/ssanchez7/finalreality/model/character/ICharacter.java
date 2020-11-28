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

  /**
   * This character attacks another character
   * @param attacked
   * The attacked character
   */
  void attack(ICharacter attacked);

  /**
   * this character receives the base damage and discounts it of their hp
   * @param baseDamage
   * the base damage applied to this character
   */
  void beAttacked(int baseDamage);

  /**
   * Returns true if the character is alive, false else.
   */
  boolean isKO();
}
