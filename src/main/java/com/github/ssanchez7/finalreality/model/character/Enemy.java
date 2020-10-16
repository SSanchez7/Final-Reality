package com.github.ssanchez7.finalreality.model.character;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;

/**
 * A class that holds all the information of a single enemy of the game.
 *
 * @author Ignacio Slater Muñoz
 * @author Samuel Sanchez Parra
 */
public class Enemy extends AbstractCharacter {

  private final int weight;
  private int attack;

  /**
   * Creates a new enemy with a name, a weight and the queue with the characters ready to
   * play.
   */
  public Enemy(@NotNull final String name,
               @NotNull final BlockingQueue<ICharacter> turnsQueue,
               int hp, int defense, int attack, int weight) {
    super(name, turnsQueue, hp, defense);
    this.weight = weight;
    this.attack = attack;
  }

  /**
   * Returns the weight of this enemy.
   */
  public int getWeight() {
    return this.weight;
  }

  /**
   * Returns the attack of this enemy.
   */
  public int getAttack() { return this.attack; }

  @Override
  public void waitTurn() {
    scheduledExecutor.schedule(this::addToQueue, this.getWeight() / 10, TimeUnit.SECONDS);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Enemy)) {
      return false;
    }
    final Enemy enemy = (Enemy) o;
    return  getWeight() == enemy.getWeight() &&
            getName().equals(enemy.getName()) &&
            getDefense() == enemy.getDefense() &&
            getAttack() == enemy.getAttack();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getWeight(), getName(), getDefense(), getAttack());
  }
}
