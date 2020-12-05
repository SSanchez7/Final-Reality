package com.github.ssanchez7.finalreality.controller;
import com.github.ssanchez7.finalreality.model.Iitem;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.BlackMages;
import com.github.ssanchez7.finalreality.model.character.player.IPlayer;
import com.github.ssanchez7.finalreality.model.character.player.Knights;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;
import com.github.ssanchez7.finalreality.model.weapon.Staffs;
import com.github.ssanchez7.finalreality.model.weapon.Swords;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gm;
    private GameController gm_seed;
    private long seed;

    private static final int HP_MAX = 300;
    private static final int MANA_MAX = 100;
    private static final int DEFENSE_POINTS = 40;

    private static final int ENEMY_ATTACK_POINTS = 30;
    private static final int ENEMY_DEFENSE_POINTS = 10;
    private static final int ENEMY_WEIGHT = 40;
    private static final String ENEMY_NAME = "enemy";

    private static final int WEAPON_DAMAGE = 15;
    private static final int WEAPON_WEIGHT = 10;
    private static final int WEAPON_MAGIC_DAMAGE = 20;

    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private static PrintStream originalOut = System.out;

    @BeforeAll
    public static void initialSetUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterAll
    public static void finalTearDown() {
        System.setOut(originalOut);
    }


    @BeforeEach
    void setUp() {
        seed = new Random().nextLong();
        gm_seed = new GameController(new Random(seed));
        gm = new GameController();
    }

    /**
     * Checks that the class' constructor and equals method works properly.
     */
    @Test
    void constructorTest() {

    }

    @RepeatedTest(64*2)
    void randomEnemyTest(){
        assertTrue(gm_seed.getEnemies().length>=1 && gm_seed.getEnemies().length<=8);
        for (Enemy p : gm.getEnemies()){
            assertNull(p);
        }
    }

    @Test
    void selectionPlayerTest(){
        assertTrue(gm.getParty().isEmpty());
        //player stats
        gm.createKnightStat(HP_MAX, DEFENSE_POINTS);
        gm.createThiefStat(HP_MAX, DEFENSE_POINTS);
        gm.createBlackMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
        gm.createEngineerStat(HP_MAX, DEFENSE_POINTS);
        gm.createWhiteMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
        gm.createWhiteMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);

        //inventory
        gm.createSword("Sword", WEAPON_DAMAGE, WEAPON_WEIGHT);
        gm.createBow("Bow", WEAPON_DAMAGE, WEAPON_WEIGHT);
        gm.createKnife("Knife", WEAPON_DAMAGE, WEAPON_WEIGHT);
        gm.createAxe("Axe", WEAPON_DAMAGE, WEAPON_WEIGHT);
        gm.createStaff("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);

        for (int i=0; i<5; i++){
            gm.selectionPlayer(new BufferedReader(new StringReader("1\nplayer"+(i+1)+"\n1\n\n")));
            assertEquals(i+1,gm.getParty().size());
        }
    }

    @Test
    public void createPlayerStatTest(){
        assertTrue(gm.getPlayersStat().isEmpty());
        for (int i=0; i<10; i++){
            gm.createKnightStat(HP_MAX, DEFENSE_POINTS);
            assertTrue(gm.getnPlayers()==gm.getPlayersStat().size() && gm.getnPlayers()==(5*i+1));
            gm.createBlackMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
            assertTrue(gm.getnPlayers()==gm.getPlayersStat().size() && gm.getnPlayers()==(5*i+2));
            gm.createEngineerStat(HP_MAX, DEFENSE_POINTS);
            assertTrue(gm.getnPlayers()==gm.getPlayersStat().size() && gm.getnPlayers()==(5*i+3));
            gm.createThiefStat(HP_MAX, DEFENSE_POINTS);
            assertTrue(gm.getnPlayers()==gm.getPlayersStat().size() && gm.getnPlayers()==(5*i+4));
            gm.createWhiteMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
            assertTrue(gm.getnPlayers()==gm.getPlayersStat().size() && gm.getnPlayers()==(5*i+5));
        }
        gm.createKnightStat(HP_MAX, DEFENSE_POINTS);
        assertEquals(gm.getnPlayers(),gm.getPlayersStat().size());
        assertNotEquals(51,gm.getnPlayers());
        assertEquals(50,gm.getnPlayers());

        gm.createWhiteMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
        assertEquals(gm.getnPlayers(),gm.getPlayersStat().size());
        assertNotEquals(51,gm.getnPlayers());
        assertEquals(50,gm.getnPlayers());

        gm.createThiefStat(HP_MAX, DEFENSE_POINTS);
        assertEquals(gm.getnPlayers(),gm.getPlayersStat().size());
        assertNotEquals(51,gm.getnPlayers());
        assertEquals(50,gm.getnPlayers());

        gm.createEngineerStat(HP_MAX, DEFENSE_POINTS);
        assertEquals(gm.getnPlayers(),gm.getPlayersStat().size());
        assertNotEquals(51,gm.getnPlayers());
        assertEquals(50,gm.getnPlayers());

        gm.createBlackMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
        assertEquals(gm.getnPlayers(),gm.getPlayersStat().size());
        assertNotEquals(51,gm.getnPlayers());
        assertEquals(50,gm.getnPlayers());
    }

    @Test
    public void createEnemiesTest() {
        assertTrue(gm.getEnemiesName().isEmpty());
        assertTrue(gm.getEnemiesStat().isEmpty());
        for(int i=0; i<50; i++){
            gm.createEnemyStat(HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
            assertTrue(gm.getnEnemiesStat()==gm.getEnemiesStat().size() && gm.getnEnemiesStat()==i+1);
            gm.createEnemyName(ENEMY_NAME+i);
            assertTrue(gm.getnEnemiesNames()==gm.getEnemiesName().size() && gm.getnEnemiesNames()==i+1);
        }
        gm.createEnemyStat(HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
        assertEquals(gm.getnEnemiesStat(),gm.getEnemiesStat().size());
        assertNotEquals(51,gm.getnEnemiesStat());
        assertEquals(50,gm.getnEnemiesStat());
        gm.createEnemyName(ENEMY_NAME+51);
        assertEquals(gm.getnEnemiesNames(),gm.getEnemiesName().size());
        assertNotEquals(51,gm.getnEnemiesNames());
        assertEquals(50,gm.getnEnemiesNames());
    }

    @Test
    public void createInventoryTest(){
        assertTrue(gm.getInventory().isEmpty());
        for (int i=0; i<6; i++){
            gm.createAxe("Axe"+i, WEAPON_DAMAGE, WEAPON_WEIGHT);
            assertTrue(gm.getnInventory()==gm.getInventory().size() && gm.getnInventory()==(5*i+1));
            gm.createBow("Bow"+i, WEAPON_DAMAGE, WEAPON_WEIGHT);
            assertTrue(gm.getnInventory()==gm.getInventory().size() && gm.getnInventory()==(5*i+2));
            gm.createKnife("Knife"+i, WEAPON_DAMAGE, WEAPON_WEIGHT);
            assertTrue(gm.getnInventory()==gm.getInventory().size() && gm.getnInventory()==(5*i+3));
            gm.createStaff("Staff"+i, WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);
            assertTrue(gm.getnInventory()==gm.getInventory().size() && gm.getnInventory()==(5*i+4));
            gm.createSword("Sword"+i, WEAPON_DAMAGE, WEAPON_WEIGHT);
            assertTrue(gm.getnInventory()==gm.getInventory().size() && gm.getnInventory()==(5*i+5));
        }
        gm.createAxe("Axe"+31, WEAPON_DAMAGE, WEAPON_WEIGHT);
        assertEquals(gm.getnInventory(),gm.getInventory().size());
        assertNotEquals(31,gm.getnInventory());
        assertEquals(30,gm.getnInventory());

        gm.createBow("Bow"+31, WEAPON_DAMAGE, WEAPON_WEIGHT);
        assertEquals(gm.getnInventory(),gm.getInventory().size());
        assertNotEquals(31,gm.getnInventory());
        assertEquals(30,gm.getnInventory());

        gm.createKnife("Knife"+31, WEAPON_DAMAGE, WEAPON_WEIGHT);
        assertEquals(gm.getnInventory(),gm.getInventory().size());
        assertNotEquals(31,gm.getnInventory());
        assertEquals(30,gm.getnInventory());

        gm.createStaff("Staff"+31, WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);
        assertEquals(gm.getnInventory(),gm.getInventory().size());
        assertNotEquals(31,gm.getnInventory());
        assertEquals(30,gm.getnInventory());

        gm.createSword("Sword"+31, WEAPON_DAMAGE, WEAPON_WEIGHT);
        assertEquals(gm.getnInventory(),gm.getInventory().size());
        assertNotEquals(31,gm.getnInventory());
        assertEquals(30,gm.getnInventory());
    }

    @Test
    public void getValuesTest(){
        BlockingQueue<ICharacter> turns = new LinkedBlockingQueue<>();
        IPlayer player = new BlackMages("BlackMage", turns, HP_MAX, DEFENSE_POINTS, MANA_MAX);
        IWeapon weapon = new Staffs("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);
        Enemy enemy = new Enemy(ENEMY_NAME, turns, HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
        player.equip(weapon);

        List<String> staffValues = new ArrayList<>();
        staffValues.add("Staff");
        staffValues.add(String.valueOf(WEAPON_DAMAGE));
        staffValues.add(String.valueOf(WEAPON_WEIGHT));
        staffValues.add(String.valueOf(WEAPON_MAGIC_DAMAGE));

        List<String> enemyValues = new ArrayList<>();
        enemyValues.add(ENEMY_NAME);
        enemyValues.add(String.valueOf(HP_MAX));
        enemyValues.add(String.valueOf(ENEMY_DEFENSE_POINTS));
        enemyValues.add(String.valueOf(ENEMY_ATTACK_POINTS));
        enemyValues.add(String.valueOf(ENEMY_WEIGHT));

        List<String> blackMageValues = new ArrayList<>();
        blackMageValues.add("BlackMage");
        blackMageValues.add(String.valueOf(HP_MAX));
        blackMageValues.add(String.valueOf(DEFENSE_POINTS));
        blackMageValues.add("Staff");
        blackMageValues.add(String.valueOf(MANA_MAX));

        assertEquals(staffValues, weapon.getValues());
        assertEquals(enemyValues, enemy.getValues());
        assertEquals(blackMageValues, player.getValues());
    }


}