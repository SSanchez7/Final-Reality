package com.github.ssanchez7.finalreality.model.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.IPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * containing the tests for weapons.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 * @see IWeapon
 */
class WeaponTest {

  private static final String AXE_NAME = "Test Axe";
  private static final String STAFF_NAME = "Test Staff";
  private static final String SWORD_NAME = "Test Sword";
  private static final String BOW_NAME = "Test Bow";
  private static final String KNIFE_NAME = "Test Knife";
  private static final int DAMAGE = 15;
  private static final int WEIGHT = 10;
  private static final int MAGIC_DAMAGE = 12;

  private List<IWeapon> testWeapons;

  @BeforeEach
  void setUp() {
    testWeapons = new ArrayList<>();
    testWeapons.add(new Axes(AXE_NAME, DAMAGE, WEIGHT));
    testWeapons.add(new Staffs(STAFF_NAME, DAMAGE, WEIGHT, MAGIC_DAMAGE));
    testWeapons.add(new Swords(SWORD_NAME, DAMAGE, WEIGHT));
    testWeapons.add(new Bows(BOW_NAME, DAMAGE, WEIGHT));
    testWeapons.add(new Knives(KNIFE_NAME, DAMAGE, WEIGHT));
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    List<IWeapon> expectedWeapons = new ArrayList<>();

    expectedWeapons.add(new Axes(AXE_NAME, DAMAGE, WEIGHT));
    expectedWeapons.add(new Staffs(STAFF_NAME, DAMAGE, WEIGHT, MAGIC_DAMAGE));
    expectedWeapons.add(new Swords(SWORD_NAME, DAMAGE, WEIGHT));
    expectedWeapons.add(new Bows(BOW_NAME, DAMAGE, WEIGHT));
    expectedWeapons.add(new Knives(KNIFE_NAME, DAMAGE, WEIGHT));

    for(int i=0;i<5;i++) {
      IWeapon testSingleWeapon = testWeapons.get(i);
      IWeapon expectedSingleWeapon = expectedWeapons.get(i);

      assertEquals(testSingleWeapon, testSingleWeapon);
      assertEquals(testSingleWeapon.hashCode(), testSingleWeapon.hashCode());

      assertEquals(expectedSingleWeapon, testSingleWeapon);
      assertEquals(expectedSingleWeapon.hashCode(), testSingleWeapon.hashCode());

      IWeapon unexpectedSingleWeapon = (i<4)? expectedWeapons.get(i+1) : expectedWeapons.get(0);
      assertNotEquals(unexpectedSingleWeapon, testSingleWeapon);
      assertNotEquals(unexpectedSingleWeapon.hashCode(), testSingleWeapon.hashCode());
    }

    //expectWeapons = [(5),...]
    expectedWeapons.add(new Axes(AXE_NAME+"_DIFF", DAMAGE, WEIGHT));
    expectedWeapons.add(new Staffs(STAFF_NAME+"_DIFF", DAMAGE, WEIGHT, MAGIC_DAMAGE));
    expectedWeapons.add(new Swords(SWORD_NAME+"_DIFF", DAMAGE, WEIGHT));
    expectedWeapons.add(new Bows(BOW_NAME+"_DIFF", DAMAGE, WEIGHT));
    expectedWeapons.add(new Knives(KNIFE_NAME+"_DIFF", DAMAGE, WEIGHT));

    //expectWeapons = [(10),...]
    expectedWeapons.add(new Axes(AXE_NAME, DAMAGE+10, WEIGHT));
    expectedWeapons.add(new Staffs(STAFF_NAME, DAMAGE+10, WEIGHT, MAGIC_DAMAGE));
    expectedWeapons.add(new Swords(SWORD_NAME, DAMAGE+10, WEIGHT));
    expectedWeapons.add(new Bows(BOW_NAME, DAMAGE+10, WEIGHT));
    expectedWeapons.add(new Knives(KNIFE_NAME, DAMAGE+10, WEIGHT));

    //expectWeapons = [(15),...]
    expectedWeapons.add(new Axes(AXE_NAME, DAMAGE, WEIGHT+10));
    expectedWeapons.add(new Staffs(STAFF_NAME, DAMAGE, WEIGHT+10, MAGIC_DAMAGE));
    expectedWeapons.add(new Swords(SWORD_NAME, DAMAGE, WEIGHT+10));
    expectedWeapons.add(new Bows(BOW_NAME, DAMAGE, WEIGHT+10));
    expectedWeapons.add(new Knives(KNIFE_NAME, DAMAGE, WEIGHT+10));

    for(int i=0;i<5;i++) {
      IWeapon testSingleWeapon = testWeapons.get(i);
      IWeapon unexpectedSingleWeapon = expectedWeapons.get(5+i);
      assertNotEquals(unexpectedSingleWeapon, testSingleWeapon);
      assertNotEquals(unexpectedSingleWeapon.hashCode(), testSingleWeapon.hashCode());

      IWeapon unexpectedSingleWeapon2 = expectedWeapons.get(10+i);
      assertNotEquals(unexpectedSingleWeapon2, testSingleWeapon);
      assertNotEquals(unexpectedSingleWeapon2.hashCode(), testSingleWeapon.hashCode());

      IWeapon unexpectedSingleWeapon3 = expectedWeapons.get(15+i);
      assertNotEquals(unexpectedSingleWeapon3, testSingleWeapon);
      assertNotEquals(unexpectedSingleWeapon3.hashCode(), testSingleWeapon.hashCode());
    }

    //expectWeapons = [(20),...]
    expectedWeapons.add(new Staffs(STAFF_NAME, DAMAGE, WEIGHT, MAGIC_DAMAGE+10));
    assertNotEquals(expectedWeapons.get(20), testWeapons.get(1));
    assertNotEquals(expectedWeapons.get(20).hashCode(), testWeapons.get(1).hashCode());

  }
}