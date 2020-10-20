package com.github.ssanchez7.finalreality.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.github.ssanchez7.finalreality.model.character.ICharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.ssanchez7.finalreality.model.weapon.Axes;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import org.junit.jupiter.api.Test;

/**
 * Abstract class containing the common tests for all the types of characters.
 *
 * @author Ignacio Slater Muñoz.
 * @author <Your name>
 * @see ICharacter
 */
public abstract class AbstractCharacterTest {

  protected BlockingQueue<ICharacter> turns;

  /**
   * Checks that the character waits the appropriate amount of time for it's turn.
   */
  @Test
  abstract void waitTurnTest();

  protected void checkConstruction(final ICharacter expectedCharacter, final ICharacter testEqualCharacter) {
    assertEquals(expectedCharacter, testEqualCharacter);
    assertEquals(expectedCharacter.hashCode(), testEqualCharacter.hashCode());
  }

  protected void basicSetUp() {
    turns = new LinkedBlockingQueue<>();
  }

}
