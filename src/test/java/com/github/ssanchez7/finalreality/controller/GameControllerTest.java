package com.github.ssanchez7.finalreality.controller;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.BlackMages;
import com.github.ssanchez7.finalreality.model.character.player.IPlayer;
import com.github.ssanchez7.finalreality.model.character.player.Knights;
import com.github.ssanchez7.finalreality.model.character.player.Thieves;
import com.github.ssanchez7.finalreality.model.weapon.*;
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
    private static BlockingQueue<ICharacter> turns;

    private static final int ENEMY_ATTACK_POINTS = 30;
    private static final int ENEMY_DEFENSE_POINTS = 10;
    private static final int ENEMY_WEIGHT = 10;
    private static final String ENEMY_NAME = "enemy";

    private static final int WEAPON_DAMAGE = 70;
    private static final int WEAPON_WEIGHT = 7;
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
        turns = new LinkedBlockingQueue<>();
    }

    @RepeatedTest(64*2)
    void randomEnemyTest(){
        assertTrue(gm_seed.getnMaxEnemies()>=1 && gm_seed.getnMaxEnemies()<=8);
        assertTrue(gm_seed.getEnemies().isEmpty());
    }

    /**
     * Checks that the controller selects a player correctly.
     */
    @Test
    void selectionPlayerTest(){
        assertTrue(gm.getParty().isEmpty());
        //player stats
        for(int i=0; i<4; i++){
            gm.createKnightStat(HP_MAX, DEFENSE_POINTS);
            gm.createThiefStat(HP_MAX, DEFENSE_POINTS);
            gm.createBlackMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
            gm.createEngineerStat(HP_MAX, DEFENSE_POINTS);
            gm.createWhiteMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
        }
        //inventory
        for(int i=0; i<4; i++) {
            gm.createSword("Sword", WEAPON_DAMAGE, WEAPON_WEIGHT);
            gm.createBow("Bow", WEAPON_DAMAGE, WEAPON_WEIGHT);
            gm.createKnife("Knife", WEAPON_DAMAGE, WEAPON_WEIGHT);
            gm.createAxe("Axe", WEAPON_DAMAGE, WEAPON_WEIGHT);
            gm.createStaff("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);
        }

        gm.createKnightStat(HP_MAX, DEFENSE_POINTS);
        gm.createSword("Sword", WEAPON_DAMAGE, WEAPON_WEIGHT);
        for (int i=0; i<20; i++){
            gm.selectionPlayer(new BufferedReader(new StringReader("1\nplayer"+(i+1)+"\n1\n\n")));
            assertEquals(i+1,gm.getnParty());
        }
        gm.selectionPlayer(new BufferedReader(new StringReader("1\nplayer21\n1\n\n")));
        assertEquals(20,gm.getnParty());
        assertNotEquals(21,gm.getnParty());
    }

    /**
     * Checks that the controller selects a enemy correctly.
     */
    @Test
    public void selectionEnemiesTest(){
        assertTrue(gm_seed.getEnemies().isEmpty());
        String[] names = {"enemy1", "enemy2", "enemy3"};
        int[][] stats = {{100,100,100,100},{200,200,200,200},{300,300,300,300}};
        for(String name : names){
            gm.createEnemyName(name);
        }
        for(int[] stat : stats){
            gm.createEnemyStat(stat[0],stat[1],stat[2],stat[3]);
        }
        for(int i=0; i<20; i++) {
            gm.selectionEnemy();
            assertEquals(i+1, gm.getnEnemies());
        }
        gm.selectionEnemy();
        assertEquals(20, gm.getnEnemies());
        assertNotEquals(21, gm.getnEnemies());
        for(Enemy e : gm.getEnemies()){
            String rName = e.getName();
            assertTrue(rName.equals(names[0]) || rName.equals(names[1]) || rName.equals(names[2]));
            int[] rStat = {e.getHp(), e.getDefensePoints(), e.getAttackPoints(), e.getWeight()};
            boolean v = false;
            for(int[] i : stats) {
                v = v || (rStat[0]==i[0] && rStat[1]==i[1] && rStat[2]==i[2]);
            }
            assertTrue(v);
        }
    }

    /**
     * Checks that the controller creates players stats correctly.
     */
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

    /**
     * Checks that the controller creates enemies stats correctly.
     */
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

    /**
     * Checks that the controller creates weapons and adds them to inventory correctly.
     */
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

    /**
     * check the obtaining of values of an item correctly
     */
    @Test
    public void getValuesTest(){
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

    /**
     * Checks that the controller equips weapons on a player correctly.
     */
    @Test
    public void equipWeaponTest(){
        //inventory
        gm.createSword("Sword", WEAPON_DAMAGE, WEAPON_WEIGHT);
        gm.createAxe("Axe", WEAPON_DAMAGE, WEAPON_WEIGHT);
        gm.createStaff("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);

        IPlayer player = new Knights("Knight", turns, HP_MAX, DEFENSE_POINTS);

        //Success first equipment (dropped a "null")
        assertNull(player.getEquippedWeapon());
        assertEquals(3, gm.getnInventory());
        IWeapon swordInventory = gm.getInventory().get(0);
        gm.equipWeapon(player, new BufferedReader(new StringReader(1+"\n\n")));
        assertEquals(2, gm.getnInventory());
        assertEquals(swordInventory, player.getEquippedWeapon());
        assertEquals(gm.getInventory().get(0), new Axes("Axe", WEAPON_DAMAGE, WEAPON_WEIGHT));

        //Success second equipment (dropped a weapon)
        IWeapon AxeInventory = gm.getInventory().get(0);
        gm.equipWeapon(player, new BufferedReader(new StringReader(1+"\n\n")));
        assertNotEquals(1, gm.getnInventory());
        assertEquals(2, gm.getnInventory());
        assertEquals(AxeInventory, player.getEquippedWeapon());
        assertEquals(gm.getInventory().get(0), new Staffs("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE));
        assertEquals(gm.getInventory().get(1), swordInventory);

        //Failed equipment
        IWeapon staffInventory = gm.getInventory().get(0);
        gm.equipWeapon(player, new BufferedReader(new StringReader(1+"\n\n")));
        assertEquals(2, gm.getnInventory());
        assertNotEquals(staffInventory, player.getEquippedWeapon());
        assertEquals(AxeInventory, player.getEquippedWeapon());
        assertEquals(gm.getInventory().get(0), new Staffs("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE));
    }

    /**
     * checks a correct operation of turns.
     */
    @RepeatedTest(3)
    public void waitTurnsTest(){
        String knightName = "knight";
        String thiefName = "thief";
        int knightWeight = 10;
        int enemyWeight = 20;
        int thiefWeight = 30;
        //player stats
        gm.createKnightStat(HP_MAX, DEFENSE_POINTS);
        gm.createThiefStat(HP_MAX, DEFENSE_POINTS);
        //enemies stats
        gm.createEnemyStat(HP_MAX, 0, ENEMY_ATTACK_POINTS, enemyWeight);
        gm.createEnemyName(ENEMY_NAME);
        //inventory
        gm.createSword("Sword", HP_MAX, knightWeight);
        gm.createKnife("Knife", HP_MAX, thiefWeight);
        gm.createKnife("Knife", HP_MAX, 1);
        //Selections
        gm.selectionPlayer(new BufferedReader(new StringReader("1\n"+knightName+"\n1\n\n")));
        gm.selectionPlayer(new BufferedReader(new StringReader("1\n"+thiefName+"\n1\n\n")));
        gm.selectionEnemy();

        int maxWeight = Math.max(knightWeight, Math.max(thiefWeight, enemyWeight));

        assertTrue(gm.getTurnsQueue().isEmpty());
        gm.initialWaitTurns(((maxWeight/10)+1)*1000);
        assertEquals(3, gm.getTurnsQueue().size());

        IPlayer knight = new Knights(knightName, turns, HP_MAX, DEFENSE_POINTS);
        IPlayer thief = new Thieves(thiefName, turns, HP_MAX, DEFENSE_POINTS);
        Enemy enemy = new Enemy(ENEMY_NAME, turns, HP_MAX, 0, ENEMY_ATTACK_POINTS, 20);

        // o: enter to the list, x: exit from the list
        // Knight o x         o    x         o?   x?        o?   x?
        // Enemy  o      x                   ox?                 o?
        // Thief  o           x                             ox?
        //          |----.----|----.----|----.----|----.----|----.----|
        //          0         1         2         3         4         5  Sec
        // iter    (1)  (2)  (3)  (4)  (5)  (6)  (7)  (8)  (9)  (10)
        // peek()   K    E    T    K   null  ?    ?   ?     ?    ?

        ICharacter[] characterList = {knight, enemy, thief, knight, null};
        for(ICharacter character : characterList) {
            assertEquals(character, gm.getTurnsQueue().peek());
            gm.turnIsNotEmpty(500);
        }
        assertEquals(0, gm.getState());
        assertTrue(gm.getTurnsQueue().peek().equals(knight) || gm.getTurnsQueue().peek().equals(enemy));
        if(gm.getTurnsQueue().peek().equals(enemy)){
            gm.turnIsNotEmpty(500);
            assertEquals(knight, gm.getTurnsQueue().peek());
            // turns change with a equipment change
            gm.equipWeapon((IPlayer) gm.getTurnsQueue().peek(), new BufferedReader(new StringReader("1\n\n")));
            gm.turnIsNotEmpty(500);

            assertNotEquals(null, gm.getTurnsQueue().peek());
            assertEquals(knight, gm.getTurnsQueue().peek());
            gm.turnIsNotEmpty(500);
        }
        assertEquals(0, gm.getState());
        try{
            //assertEquals(enemy, gm.getTurnsQueue().peek());
            gm.attack(gm.getParty().get(0), gm.getEnemies().get(0));
            assertTrue(gm.getEnemies().get(0).isKO());
            assertEquals(1,gm.getnDefeatedEnemies());
            for(int i=0; i<6; i++){
                gm.turnIsNotEmpty(500);
            }
            Thread.sleep(3000);
            assertEquals(2, gm.getTurnsQueue().size());
            assertEquals(1, gm.getState());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * check that state is "win" when all enemies are defeated
     */
    @Test
    public void stateWinTest(){
        gm.createKnightStat(HP_MAX, DEFENSE_POINTS);
        gm.createEnemyStat(HP_MAX, 0, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
        gm.createEnemyName(ENEMY_NAME);
        gm.createSword("Sword", HP_MAX, WEAPON_WEIGHT);
        gm.selectionPlayer(new BufferedReader(new StringReader("1\nKnight\n1\n\n")));
        gm.selectionEnemy();
        assertTrue(gm.getTurnsQueue().isEmpty());
        gm.initialWaitTurns(2000);
        assertEquals(0, gm.getState());
        try{
            gm.attack(gm.getParty().get(0), gm.getEnemies().get(0));
            assertTrue(gm.getEnemies().get(0).isKO());
            assertEquals(1,gm.getnDefeatedEnemies());
            for(int i=0; i<4; i++){
                gm.turnIsNotEmpty(500);
            }
            Thread.sleep(1000);
            assertEquals(1, gm.getState());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * check that state is "lose" when all players are defeated
     */
    @Test
    public void stateLoseTest(){
        gm.createKnightStat(HP_MAX, 0);
        gm.createEnemyStat(HP_MAX, ENEMY_DEFENSE_POINTS, HP_MAX, ENEMY_WEIGHT);
        gm.createEnemyName(ENEMY_NAME);
        gm.createSword("Sword", HP_MAX, WEAPON_WEIGHT);
        gm.selectionPlayer(new BufferedReader(new StringReader("1\nKnight\n1\n\n")));
        gm.selectionEnemy();
        assertTrue(gm.getTurnsQueue().isEmpty());
        gm.initialWaitTurns(2000);
        assertEquals(0, gm.getState());
        try{
            gm.attack(gm.getEnemies().get(0), gm.getParty().get(0));
            assertTrue(gm.getParty().get(0).isKO());
            assertEquals(1,gm.getnDefeatedPlayers());
            for(int i=0; i<4; i++){
                gm.turnIsNotEmpty(500);
            }
            Thread.sleep(1000);
            assertEquals(2, gm.getState());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * check that the controller makes attacks between two characters
     */
    @Test
    public void attackTest(){
        IPlayer player1 = new Knights("Knight", turns, HP_MAX, DEFENSE_POINTS);
        IPlayer player2 = new Thieves("Thief", turns, HP_MAX, DEFENSE_POINTS);
        player1.equip(new Swords("sword", WEAPON_DAMAGE, WEAPON_WEIGHT));
        gm.attack(player1, player2);
        assertEquals(270, player2.getHp());
    }

    /**
     * checks a correct operation of defeated characters when they receives attacks.
     */
    @Test
    public void defeatedCharacterTest(){
        //player stats
        gm.createKnightStat(HP_MAX, 0);
        gm.createThiefStat(HP_MAX, 0);
        //enemies stats
        gm.createEnemyStat(HP_MAX, 0, HP_MAX, ENEMY_WEIGHT);
        gm.createEnemyName(ENEMY_NAME);
        //inventory
        gm.createSword("Sword", HP_MAX, WEAPON_WEIGHT);
        gm.createKnife("Knife", HP_MAX, WEAPON_WEIGHT);
        gm.createKnife("Knife", HP_MAX, WEAPON_WEIGHT);

        gm.selectionPlayer(new BufferedReader(new StringReader("1\nknight\n1\n\n")));
        gm.selectionPlayer(new BufferedReader(new StringReader("1\nthief\n1\n\n")));
        gm.selectionEnemy();
        gm.selectionEnemy();
        gm.selectionEnemy();

        IPlayer player1 = gm.getParty().get(0);
        IPlayer player2 = gm.getParty().get(1);
        Enemy enemy1 = gm.getEnemies().get(0);
        Enemy enemy2 = gm.getEnemies().get(1);
        Enemy enemy3 = gm.getEnemies().get(2);

        //Player doesn't defeat enemy
        IPlayer player3 = new Knights("weak", turns, HP_MAX, DEFENSE_POINTS);
        player3.equip(new Swords("stick", 0, WEAPON_WEIGHT));
        gm.attack(player3, player1);
        assertFalse(player1.isKO());
        assertEquals(0, gm.getnDefeatedPlayers());
        //Enemy doesn't defeat player
        gm.attack(player3, enemy1);
        assertFalse(enemy1.isKO());
        assertEquals(0, gm.getnDefeatedEnemies());

        //Player defeats enemy
        gm.attack(player1, enemy2);
        assertTrue(enemy2.isKO());
        assertEquals(1, gm.getnDefeatedEnemies());
        //Player defeats player
        gm.attack(player1, player2);
        assertTrue(player2.isKO());
        assertEquals(1, gm.getnDefeatedPlayers());
        //Enemy defeats enemy
        gm.attack(enemy1, enemy3);
        assertTrue(enemy3.isKO());
        assertEquals(2, gm.getnDefeatedEnemies());
        //Enemy defeats player
        gm.attack(enemy1, player1);
        assertTrue(player1.isKO());
        assertEquals(2, gm.getnDefeatedPlayers());

        //Character attack defeated character
        gm.attack(enemy1, player1);
        assertTrue(player1.isKO());
        assertEquals(2, gm.getnDefeatedPlayers());
    }


}