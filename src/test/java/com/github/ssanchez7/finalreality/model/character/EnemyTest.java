package com.github.ssanchez7.finalreality.model.character;


import com.github.ssanchez7.finalreality.model.character.player.IPlayer;
import com.github.ssanchez7.finalreality.model.character.player.Knights;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class containing the tests for Enemies.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 * @see Enemy
 */
class EnemyTest extends AbstractCharacterTest {

  protected ICharacter testEnemy;

  private static final String ENEMY_NAME = "Goblin";
  private static final int ENEMY_HP_MAX = 200;
  private static final int ENEMY_DEFENSE_POINTS = 20;
  private static final int ENEMY_ATTACK_POINTS = 10;
  private static final int ENEMY_WEIGHT = 10;

  @BeforeEach
  void setUp() {
    basicSetUp();
    testEnemy = new Enemy(ENEMY_NAME, turns, ENEMY_HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    List<ICharacter> expectedEnemy = new ArrayList<>();
    expectedEnemy.add(new Enemy(ENEMY_NAME, turns, ENEMY_HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT));
    checkConstruction(expectedEnemy.get(0), testEnemy);
    checkConstruction(testEnemy, testEnemy);

    ICharacter player = new Knights("knight", turns, ENEMY_HP_MAX, ENEMY_DEFENSE_POINTS);
    assertNotEquals(player,testEnemy);

    expectedEnemy.add(new Enemy(ENEMY_NAME+"DIFF", turns, ENEMY_HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT));
    expectedEnemy.add(new Enemy(ENEMY_NAME, turns, ENEMY_HP_MAX+10, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT));
    expectedEnemy.add(new Enemy(ENEMY_NAME, turns, ENEMY_HP_MAX, ENEMY_DEFENSE_POINTS+10, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT));
    expectedEnemy.add(new Enemy(ENEMY_NAME, turns, ENEMY_HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS+10, ENEMY_WEIGHT));
    expectedEnemy.add(new Enemy(ENEMY_NAME, turns, ENEMY_HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT+10));

    for (int i=1;i<=5;i++){
      assertNotEquals(expectedEnemy.get(i),testEnemy);
    }

  }

  @Test @Override
  void waitTurnTest(){
    assertTrue(turns.isEmpty());
    testEnemy.waitTurn();
    try {
      // Thread.sleep is not accurate so this values may be changed to adjust the
      // acceptable error margin.
      // We're testing that the character waits approximately 1 second.
      Thread.sleep(900);
      assertEquals(0, turns.size());
      Thread.sleep(200);
      assertEquals(1, turns.size());
      assertEquals(testEnemy, turns.peek());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test @Override
  void attackTest(){
    ICharacter attackedEnemy = new Enemy("attackedEnemy", turns, ENEMY_HP_MAX, 5, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
    ICharacter attackedPlayer = new Knights("attackedPlayer", turns, ENEMY_HP_MAX, 5);

    // attack to another enemy
    assertEquals(ENEMY_HP_MAX,attackedEnemy.getHp());
    testEnemy.attack(attackedEnemy);
    assertEquals(ENEMY_HP_MAX-(ENEMY_ATTACK_POINTS-5),attackedEnemy.getHp());

    // attack to a player
    assertEquals(ENEMY_HP_MAX,attackedPlayer.getHp());
    testEnemy.attack(attackedPlayer);
    assertEquals(ENEMY_HP_MAX-(ENEMY_ATTACK_POINTS-5),attackedPlayer.getHp());

    // defeated enemy attacks
    ICharacter defeatedEnemy = new Enemy("defeatedEnemy", turns, 0, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
    assertEquals(ENEMY_HP_MAX,testEnemy.getHp());
    defeatedEnemy.attack(testEnemy);
    assertEquals(ENEMY_HP_MAX,testEnemy.getHp());

    // attack to a defeated enemy
    assertEquals(0,defeatedEnemy.getHp());
    testEnemy.attack(defeatedEnemy);
    assertEquals(0,defeatedEnemy.getHp());


  }
}