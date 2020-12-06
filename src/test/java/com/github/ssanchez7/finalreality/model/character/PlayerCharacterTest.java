package com.github.ssanchez7.finalreality.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.github.ssanchez7.finalreality.model.character.player.*;

import java.util.ArrayList;
import java.util.List;

import com.github.ssanchez7.finalreality.model.weapon.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * containing the tests for playable characters.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 * @see IPlayer
 */
class PlayerCharacterTest extends AbstractCharacterTest {

  protected List<IPlayer> testPlayerCharacters;
  protected List<IWeapon> testWeapons;
  protected ICharacter enemy;

  private static final String BLACK_MAGE_NAME = "Vivi";
  private static final String KNIGHT_NAME = "Adelbert";
  private static final String WHITE_MAGE_NAME = "Eiko";
  private static final String ENGINEER_NAME = "Cid";
  private static final String THIEF_NAME = "Zidane";

  private static final int HP_MAX = 300;
  private static final int MANA_MAX = 100;
  private static final int DEFENSE_POINTS = 40;

  private static final int ENEMY_ATTACK_POINTS = 30;
  private static final int ENEMY_DEFENSE_POINTS = 10;
  private static final int ENEMY_WEIGHT = 40;

  private static final int WEAPON_DAMAGE = 15;
  private static final int WEAPON_WEIGHT = 10;
  private static final int WEAPON_MAGIC_DAMAGE = 20;



  @BeforeEach
  void setUp() {
    super.basicSetUp();

    testWeapons = new ArrayList<>();
    testWeapons.add(new Axes("Axe", WEAPON_DAMAGE,  WEAPON_WEIGHT));
    testWeapons.add(new Bows("Bow", WEAPON_DAMAGE,  WEAPON_WEIGHT));
    testWeapons.add(new Knives("Knife", WEAPON_DAMAGE,  WEAPON_WEIGHT));
    testWeapons.add(new Staffs("Staff", WEAPON_DAMAGE,  WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE));
    testWeapons.add(new Swords("Sword", WEAPON_DAMAGE,  WEAPON_WEIGHT));

    testPlayerCharacters = new ArrayList<>();
    testPlayerCharacters.add(new BlackMages(BLACK_MAGE_NAME, turns,  HP_MAX, DEFENSE_POINTS, MANA_MAX));
    testPlayerCharacters.add(new Knights(KNIGHT_NAME, turns, HP_MAX, DEFENSE_POINTS));
    testPlayerCharacters.add(new WhiteMages(WHITE_MAGE_NAME, turns, HP_MAX, DEFENSE_POINTS, MANA_MAX));
    testPlayerCharacters.add(new Engineers(ENGINEER_NAME, turns, HP_MAX, DEFENSE_POINTS));
    testPlayerCharacters.add(new Thieves(THIEF_NAME, turns, HP_MAX, DEFENSE_POINTS));

    enemy = new Enemy("Enemy", turns, HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {

    List<IPlayer> expectedCharacters = new ArrayList<>();
    expectedCharacters.add(new BlackMages(BLACK_MAGE_NAME, turns, HP_MAX, DEFENSE_POINTS, MANA_MAX));
    expectedCharacters.add(new Knights(KNIGHT_NAME, turns, HP_MAX, DEFENSE_POINTS));
    expectedCharacters.add(new WhiteMages(WHITE_MAGE_NAME, turns, HP_MAX, DEFENSE_POINTS, MANA_MAX));
    expectedCharacters.add(new Engineers(ENGINEER_NAME, turns, HP_MAX, DEFENSE_POINTS));
    expectedCharacters.add(new Thieves(THIEF_NAME, turns, HP_MAX, DEFENSE_POINTS));

    for (int i = 0; i < 5; i++) {
      checkConstruction(expectedCharacters.get(i), testPlayerCharacters.get(i));
      checkConstruction(testPlayerCharacters.get(i), testPlayerCharacters.get(i));

      IPlayer unexpectedCharacters = (i<4)? expectedCharacters.get(i+1) : expectedCharacters.get(0);
      assertNotEquals(unexpectedCharacters, testPlayerCharacters.get(i));

      assertNotEquals(enemy,testPlayerCharacters.get(i));
    }

    //expectWeapons = [(5),...]
    expectedCharacters.add(new BlackMages(BLACK_MAGE_NAME+"DIFF", turns, HP_MAX, DEFENSE_POINTS, MANA_MAX));
    expectedCharacters.add(new Knights(KNIGHT_NAME+"DIFF", turns, HP_MAX, DEFENSE_POINTS));
    expectedCharacters.add(new WhiteMages(WHITE_MAGE_NAME+"DIFF", turns, HP_MAX, DEFENSE_POINTS, MANA_MAX));
    expectedCharacters.add(new Engineers(ENGINEER_NAME+"DIFF", turns, HP_MAX, DEFENSE_POINTS));
    expectedCharacters.add(new Thieves(THIEF_NAME+"DIFF", turns, HP_MAX, DEFENSE_POINTS));

    //expectWeapons = [(10),...]
    expectedCharacters.add(new BlackMages(BLACK_MAGE_NAME, turns,HP_MAX+10, DEFENSE_POINTS, MANA_MAX));
    expectedCharacters.add(new Knights(KNIGHT_NAME, turns, HP_MAX+10, DEFENSE_POINTS));
    expectedCharacters.add(new WhiteMages(WHITE_MAGE_NAME, turns,HP_MAX+10, DEFENSE_POINTS, MANA_MAX));
    expectedCharacters.add(new Engineers(ENGINEER_NAME, turns, HP_MAX+10, DEFENSE_POINTS));
    expectedCharacters.add(new Thieves(THIEF_NAME, turns, HP_MAX+10, DEFENSE_POINTS));

    //expectWeapons = [(15),...]
    expectedCharacters.add(new BlackMages(BLACK_MAGE_NAME, turns, HP_MAX, DEFENSE_POINTS+10, MANA_MAX));
    expectedCharacters.add(new Knights(KNIGHT_NAME, turns, HP_MAX, DEFENSE_POINTS+10));
    expectedCharacters.add(new WhiteMages(WHITE_MAGE_NAME, turns, HP_MAX, DEFENSE_POINTS+10, MANA_MAX));
    expectedCharacters.add(new Engineers(ENGINEER_NAME, turns, HP_MAX, DEFENSE_POINTS+10));
    expectedCharacters.add(new Thieves(THIEF_NAME, turns, HP_MAX, DEFENSE_POINTS+10));

    for(int i=0;i<5;i++) {
      IPlayer testSingleCharacter = testPlayerCharacters.get(i);
      IPlayer unexpectedSingleCharacter = expectedCharacters.get(5+i);
      assertNotEquals(unexpectedSingleCharacter, testSingleCharacter);
      assertNotEquals(unexpectedSingleCharacter.hashCode(), testSingleCharacter.hashCode());

      IPlayer unexpectedSingleWeapon2 = expectedCharacters.get(10+i);
      assertNotEquals(unexpectedSingleWeapon2, testSingleCharacter);
      assertNotEquals(unexpectedSingleWeapon2.hashCode(), testSingleCharacter.hashCode());

      IPlayer unexpectedSingleWeapon3 = expectedCharacters.get(15+i);
      assertNotEquals(unexpectedSingleWeapon3, testSingleCharacter);
      assertNotEquals(unexpectedSingleWeapon3.hashCode(), testSingleCharacter.hashCode());
    }

    //expectWeapons = [(20),...]
    expectedCharacters.add(new BlackMages(BLACK_MAGE_NAME, turns, HP_MAX, DEFENSE_POINTS, MANA_MAX+10));
    expectedCharacters.add(new WhiteMages(WHITE_MAGE_NAME, turns, HP_MAX, DEFENSE_POINTS, MANA_MAX+10));
    assertNotEquals(expectedCharacters.get(20), testPlayerCharacters.get(0));
    assertNotEquals(expectedCharacters.get(20).hashCode(), testPlayerCharacters.get(0).hashCode());
    assertNotEquals(expectedCharacters.get(21), testPlayerCharacters.get(2));
    assertNotEquals(expectedCharacters.get(21).hashCode(), testPlayerCharacters.get(2).hashCode());

  }

  @Test @Override
  void waitTurnTest(){
      Assertions.assertTrue(turns.isEmpty());
      tryToEquip(testPlayerCharacters.get(0));
      (testPlayerCharacters.get(0)).waitTurn();
      try {
        // Thread.sleep is not accurate so this values may be changed to adjust the
        // acceptable error margin.
        // We're testing that the character waits approximately 1 second.
        Thread.sleep(900);
        Assertions.assertEquals(0, turns.size());
        Thread.sleep(200);
        Assertions.assertEquals(1, turns.size());
        Assertions.assertEquals(testPlayerCharacters.get(0), turns.peek());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
  }

  /**
   * Checks that the character equips a Axe correctly.
   */
  @Test
  void equipAxeTest(){
    IWeapon testWeapon = testWeapons.get(0);
    for(int i=0; i<5;i++){
      IPlayer character = testPlayerCharacters.get(i);
      assertEquals(null, character.getEquippedWeapon());
      character.equip(testWeapon);
    }
    assertEquals(null, testPlayerCharacters.get(0).getEquippedWeapon());
    assertEquals(testWeapon, testPlayerCharacters.get(1).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(2).getEquippedWeapon());
    assertEquals(testWeapon, testPlayerCharacters.get(3).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(4).getEquippedWeapon());
  }

  /**
   * Checks that the character equips a Bow correctly.
   */
  @Test
  void equipBowTest(){
    IWeapon testWeapon = testWeapons.get(1);
    for(int i=0; i<5;i++){
      IPlayer character = testPlayerCharacters.get(i);
      assertEquals(null, character.getEquippedWeapon());
      character.equip(testWeapon);
    }
    assertEquals(null, testPlayerCharacters.get(0).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(1).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(2).getEquippedWeapon());
    assertEquals(testWeapon, testPlayerCharacters.get(3).getEquippedWeapon());
    assertEquals(testWeapon, testPlayerCharacters.get(4).getEquippedWeapon());
  }

  /**
   * Checks that the character equips a Knife correctly.
   */
  @Test
  void equipKnifeTest(){
    IWeapon testWeapon = testWeapons.get(2);
    for(int i=0; i<5;i++){
      IPlayer character = testPlayerCharacters.get(i);
      assertEquals(null, character.getEquippedWeapon());
      character.equip(testWeapon);
    }
    assertEquals(testWeapon, testPlayerCharacters.get(0).getEquippedWeapon());
    assertEquals(testWeapon, testPlayerCharacters.get(1).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(2).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(3).getEquippedWeapon());
    assertEquals(testWeapon, testPlayerCharacters.get(4).getEquippedWeapon());
  }

  /**
   * Checks that the character equips a Staff correctly.
   */
  @Test
  void equipStaffTest(){
    IWeapon testWeapon = testWeapons.get(3);
    for(int i=0; i<5;i++){
      IPlayer character = testPlayerCharacters.get(i);
      assertEquals(null, character.getEquippedWeapon());
      character.equip(testWeapon);
    }
    assertEquals(testWeapon, testPlayerCharacters.get(0).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(1).getEquippedWeapon());
    assertEquals(testWeapon, testPlayerCharacters.get(2).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(3).getEquippedWeapon());
    assertEquals(null , testPlayerCharacters.get(4).getEquippedWeapon());
  }

  /**
   * Checks that the character equips a Sword correctly.
   */
  @Test
  void equipSwordTest(){
    IWeapon testWeapon = testWeapons.get(4);
    for(int i=0; i<5;i++){
      IPlayer character = testPlayerCharacters.get(i);
      assertEquals(null, character.getEquippedWeapon());
      character.equip(testWeapon);
    }
    assertEquals(null, testPlayerCharacters.get(0).getEquippedWeapon());
    assertEquals(testWeapon, testPlayerCharacters.get(1).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(2).getEquippedWeapon());
    assertEquals(null, testPlayerCharacters.get(3).getEquippedWeapon());
    assertEquals(testWeapon, testPlayerCharacters.get(4).getEquippedWeapon());
  }

  /**
   * Checks that a defeated character cannot equip a weapon
   */
  @Test
  void defeatedCharacterEquipTest(){
    List<IPlayer> defeatedCharacters = new ArrayList<>();
    defeatedCharacters.add(new BlackMages(BLACK_MAGE_NAME, turns, 0, DEFENSE_POINTS, MANA_MAX));
    defeatedCharacters.add(new Knights(KNIGHT_NAME, turns, 0, DEFENSE_POINTS));
    defeatedCharacters.add(new WhiteMages(WHITE_MAGE_NAME, turns, 0, DEFENSE_POINTS, MANA_MAX));
    defeatedCharacters.add(new Engineers(ENGINEER_NAME, turns, 0, DEFENSE_POINTS));
    defeatedCharacters.add(new Thieves(THIEF_NAME, turns, 0, DEFENSE_POINTS));

    // equip test
    for (int i=0;i<5;i++){
      for (int j=0;j<5;j++){
        defeatedCharacters.get(i).equip(testWeapons.get(j));
        assertNull(defeatedCharacters.get(i).getEquippedWeapon());
      }
    }
  }

  /**
   * Checks the get and set from Mana.
   */
  @Test
  void manaTest(){
    BlackMages mage = (BlackMages) testPlayerCharacters.get(0);
    assertEquals(MANA_MAX, mage.getMana());
    assertEquals(mage.getManaMax(), mage.getMana());
    mage.setMana(mage.getMana()+10);
    assertEquals(MANA_MAX+10,mage.getMana());
    assertNotEquals(mage.getManaMax(), mage.getMana());
  }

  /**
   * Checks the get and set from Hp.
   */
  @Test
  void hpTest(){
    BlackMages mage = (BlackMages) testPlayerCharacters.get(0);
    assertEquals(HP_MAX, mage.getHp());
    assertEquals(mage.getHpMax(), mage.getHp());
    mage.setHp(mage.getHp()+10);
    assertEquals(HP_MAX+10,mage.getHp());
    assertNotEquals(mage.getHpMax(), mage.getHp());
  }

  @Test @Override
  void attackTest(){
    IPlayer attackerChar = testPlayerCharacters.get(1); // attacker
    IPlayer attackedChar = testPlayerCharacters.get(3); // attacked (defensePoints: 40)
    IPlayer lowDefenseChar = new Knights("Weaker", turns, HP_MAX, 10);
    IPlayer zeroDefenseChar = new Knights("TheWeakest", turns, HP_MAX, 0);
    IWeapon weapon = testWeapons.get(4); // weapon (Damage: 15)

    assertEquals(HP_MAX, attackedChar.getHp());
    assertEquals(HP_MAX, zeroDefenseChar.getHp());

    // attack with a weapon
    attackerChar.equip(weapon);

    attackerChar.attack(attackedChar); // weapon damage < defense points
    assertEquals(HP_MAX,attackedChar.getHp());
    attackerChar.attack(lowDefenseChar); // weapon damage > defense points
    assertEquals(HP_MAX-(WEAPON_DAMAGE-10),lowDefenseChar.getHp());
    attackerChar.attack(zeroDefenseChar); // weapon damage > defense points
    assertEquals((HP_MAX)-(WEAPON_DAMAGE),zeroDefenseChar.getHp());

    // Defeated character attacks
    IPlayer defeatedChar = new Knights("defeatedKnight", turns, 0, DEFENSE_POINTS);
    assertEquals(HP_MAX,attackedChar.getHp());
    defeatedChar.attack(attackedChar); // failed attack
    assertEquals(HP_MAX,attackedChar.getHp());

    // Attack to a defeated character
    assertEquals(0,defeatedChar.getHp());
    attackerChar.attack(defeatedChar); // failed attack
    assertEquals(0,defeatedChar.getHp());

    // Attack to a Enemy
    assertEquals(HP_MAX,enemy.getHp());
    attackerChar.attack(enemy);
    assertEquals(HP_MAX-(WEAPON_DAMAGE-ENEMY_DEFENSE_POINTS),enemy.getHp());

    // instaKill
    IWeapon instaKill = new Swords("instaKill", HP_MAX, WEAPON_WEIGHT);
    attackerChar.equip(instaKill);
    attackerChar.attack(zeroDefenseChar);
    assertEquals(0,zeroDefenseChar.getHp());
  }

  /**
   * Check that a character is defeated if has 0 life points and is alive if not
   */
  @Test
  void isKOTest(){
    IPlayer character = testPlayerCharacters.get(0);
    assertEquals(false,character.isKO());
    character.setHp(0);
    assertEquals(true, character.isKO());
  }


  /**
   * Auxiliary function that tries to equip a weapon on a character
   */
  protected void tryToEquip(IPlayer character) {
    character.equip(testWeapons.get(3));
  }



}
