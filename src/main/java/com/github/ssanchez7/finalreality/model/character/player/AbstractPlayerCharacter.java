package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.AbstractCharacter;
import com.github.ssanchez7.finalreality.model.character.ICharacter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

/**
 * A class that holds all the information of a single character of the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public abstract class AbstractPlayerCharacter extends AbstractCharacter implements IPlayer{

  private final PropertyChangeSupport changesPlayer = new PropertyChangeSupport(this);

  protected IWeapon equippedWeapon = null;

  /**
   * Initialize a new playable character.
   *
   * @param name
   *     the character's name
   * @param turnsQueue
   *     the queue with the characters waiting for their turn
   * @param hpMax
   *     the initial hp of this character
   * @param defensePoints
   *     the defense of this character
   */
  public AbstractPlayerCharacter(@NotNull String name,
                                 @NotNull BlockingQueue<ICharacter> turnsQueue,
                                 int hpMax, int defensePoints) {
    super(name, turnsQueue, hpMax, defensePoints);
  }

  @Override
  public IWeapon getEquippedWeapon() {
    return equippedWeapon;
  }

  @Override
  public void waitTurn() {
    scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutor.schedule(this::addToQueue, equippedWeapon.getWeight() / 10, TimeUnit.SECONDS);
  }


  @Override
  public void attack(ICharacter attacked) {
    if (!this.isKO() && this.getEquippedWeapon()!=null) {
      int baseDamage=this.getEquippedWeapon().getDamage();
      attacked.beAttacked(baseDamage);
    }
  }

  @Override
  public List<String> getValues(){
    List<String> str = super.getValues();
    str.add(getEquippedWeapon().getName());
    return str;
  }

  @Override
  public void addListener(PropertyChangeListener listener){
    changesPlayer.addPropertyChangeListener(listener);
  }

  @Override
  public void isDefeatedCharacter(){
    if(this.isKO()) {
      changesPlayer.firePropertyChange("DEFEATED_PLAYER", null, this);
    }
  }

  @Override
  public boolean isEnemy(){return false;}


}
