package com.github.ssanchez7.finalreality.model.character.player;

import com.github.ssanchez7.finalreality.model.character.AbstractCharacter;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

/**
 * A class that holds all the information of a single character of the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author <Your name>
 */
public abstract class AbstractPlayerCharacter extends AbstractCharacter implements IPlayer{

  protected IWeapon equippedWeapon = null;
  private final String characterClass;

  /**
   * Creates a new character.
   *
   * @param name
   *     the character's name
   * @param turnsQueue
   *     the queue with the characters waiting for their turn
   * @param characterClass
   *     the class of this character
   * @param hp
   *     the hp of this character
   * @param defense
   *     the defense of this character
   */
  public AbstractPlayerCharacter(@NotNull String name,
                                 @NotNull BlockingQueue<ICharacter> turnsQueue,
                                 String characterClass, int hp, int defense) {
    super(name, turnsQueue, hp, defense);
    this.characterClass = characterClass;
  }

  @Override
  public IWeapon getEquippedWeapon() {
    return equippedWeapon;
  }

  public abstract void equip(IWeapon weapon);

  @Override
  public String getCharacterClass() {
    return characterClass;
  }

  @Override
  public void waitTurn() {
    scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutor.schedule(this::addToQueue, equippedWeapon.getWeight() / 10, TimeUnit.SECONDS);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCharacterClass());
  }

}
