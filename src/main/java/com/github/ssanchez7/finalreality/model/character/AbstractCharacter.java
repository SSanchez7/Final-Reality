package com.github.ssanchez7.finalreality.model.character;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

import com.github.ssanchez7.finalreality.model.character.player.Thieves;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract class that holds the common behaviour of all the characters in the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public abstract class AbstractCharacter implements ICharacter {

  protected ScheduledExecutorService scheduledExecutor;

  protected final BlockingQueue<ICharacter> turnsQueue;
  protected final String name;
  protected final int hpMax;
  private final int defensePoints;
  private int hp;

  /**
   * Initialize a new Character
   *
   * @param name
   *    Character's name
   * @param turnsQueue
   *    Queue with the characters ready to play
   * @param hpMax
   *    Character's initial health points
   * @param defensePoints
   *    Character's defense points
   */
  protected AbstractCharacter(@NotNull String name,
                              @NotNull BlockingQueue<ICharacter> turnsQueue,
                              int hpMax, int defensePoints) {
    this.turnsQueue = turnsQueue;
    this.name = name;
    this.hp = hpMax;
    this.hpMax = hpMax;
    this.defensePoints = defensePoints;
  }

  /**
   * Adds this character to the turns queue.
   */
  protected void addToQueue() {
    turnsQueue.add(this);
    scheduledExecutor.shutdown();
  }

  @Override
  public void setHp(int hp){
    this.hp = hp;
  }

  @Override
  public int getHp(){
    return this.hp;
  }

  @Override
  public int getHpMax() { return this.hpMax;}

  @Override
  public int getDefensePoints(){
    return this.defensePoints;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void beAttacked(int baseDamage){
    if (this.getHp()>0) {
      int damage;
      damage = (baseDamage > this.getDefensePoints()) ? baseDamage - this.getDefensePoints() : 0;
      damage = (damage > this.getHp()) ? this.getHp() : damage;
      this.setHp(this.getHp() - damage);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getHpMax(), getDefensePoints());
  }



}
