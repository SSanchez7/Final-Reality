package com.github.ssanchez7.finalreality.model.character;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

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
  private int hp;
  private final int defense;

  protected AbstractCharacter(@NotNull String name,
                              @NotNull BlockingQueue<ICharacter> turnsQueue,
                              int hp, int defense) {
    this.turnsQueue = turnsQueue;
    this.name = name;
    this.hp = hp;
    this.defense = defense;
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
  public int getDefense(){
    return this.defense;
  }

  @Override
  public String getName() {
    return name;
  }

}
