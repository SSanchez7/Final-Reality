package com.github.ssanchez7.finalreality.model.character;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
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
  private final int attackPoints;

  /**
   * Creates a new enemy.
   *
   * @param name
   *    Enemy's name
   * @param turnsQueue
   *    Queue with the characters ready to play
   * @param hpMax
   *    Enemy's initial health points
   * @param defensePoints
   *    Enemy's defense points
   * @param attackPoints
   *    Enemy's attack points
   * @param weight
   *    Enemy's weight for an attack
   */
  public Enemy(@NotNull final String name,
               @NotNull final BlockingQueue<ICharacter> turnsQueue,
               int hpMax, int defensePoints, int attackPoints, int weight) {
    super(name, turnsQueue, hpMax, defensePoints);
    this.weight = weight;
    this.attackPoints = attackPoints;
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
  public int getAttackPoints() { return this.attackPoints; }

  @Override
  public void waitTurn() {
    scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutor.schedule(this::addToQueue, (this.getWeight() / 10), TimeUnit.SECONDS);
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
            getHpMax() == enemy.getHpMax() &&
            getDefensePoints() == enemy.getDefensePoints() &&
            getAttackPoints() == enemy.getAttackPoints();
  }

  @Override
  public int hashCode() {
    return Objects.hash(Enemy.class, getWeight(), getName(), getHpMax(), getDefensePoints(), getAttackPoints());
  }

  @Override
  public void attack(ICharacter attacked) {
    if (this.getHp() > 0) {
      attacked.beAttacked(this.attackPoints);
    }
  }
}
