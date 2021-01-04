package com.github.ssanchez7.finalreality.controller;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidEquipmentException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidSelectionPlayerException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidTransitionException;
import com.github.ssanchez7.finalreality.controller.phases.*;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.BlackMages;
import com.github.ssanchez7.finalreality.model.character.player.IPlayer;
import com.github.ssanchez7.finalreality.model.character.player.Knights;
import com.github.ssanchez7.finalreality.model.character.player.Thieves;
import com.github.ssanchez7.finalreality.model.weapon.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * containing the tests for the controller of the game "Final reality".
 *
 * @author Samuel Sanchez Parra
 */
class GameControllerTest {

    private GameController gc4;
    private GameController gc2;
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
        gm_seed = new GameController(new Random(seed),4);
        gc4 = new GameController(4);
        gc2 = new GameController(2);
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
    void selectionPlayerTest() throws InvalidEquipmentException, InvalidSelectionPlayerException {
        assertTrue(gc4.getParty().isEmpty());
        //player stats
        for(int i=0; i<4; i++){
            gc4.createKnightStat(HP_MAX, DEFENSE_POINTS);
            gc4.createThiefStat(HP_MAX, DEFENSE_POINTS);
            gc4.createBlackMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
            gc4.createEngineerStat(HP_MAX, DEFENSE_POINTS);
            gc4.createWhiteMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
        }
        //inventory
        for(int i=0; i<4; i++) {
            gc4.createSword("Sword", WEAPON_DAMAGE, WEAPON_WEIGHT);
            gc4.createBow("Bow", WEAPON_DAMAGE, WEAPON_WEIGHT);
            gc4.createKnife("Knife", WEAPON_DAMAGE, WEAPON_WEIGHT);
            gc4.createAxe("Axe", WEAPON_DAMAGE, WEAPON_WEIGHT);
            gc4.createStaff("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);
        }

        gc4.createKnightStat(HP_MAX, DEFENSE_POINTS);
        gc4.createSword("Sword", WEAPON_DAMAGE, WEAPON_WEIGHT);
        for (int i=0; i<20; i++){
            IPlayer player = gc4.selectionPlayer(0,"player"+(i+1));
            gc4.equipWeapon(player,0);
            assertEquals(i+1, gc4.getnParty());
        }
        assertThrows(InvalidSelectionPlayerException.class,() -> {
            IPlayer player = gc4.selectionPlayer(0,"player21");
            gc4.equipWeapon(player,0);
        });

        gc4.setPhase(new EndPhase());
        assertThrows(InvalidMovementException.class,() -> {
            gc4.getPhase().canChooseAPlayer();
        });
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
            gc4.createEnemyName(name);
        }
        for(int[] stat : stats){
            gc4.createEnemyStat(stat[0],stat[1],stat[2],stat[3]);
        }
        for(int i=0; i<20; i++) {
            gc4.selectionEnemy();
            assertEquals(i+1, gc4.getnEnemies());
        }
        gc4.selectionEnemy();
        assertEquals(20, gc4.getnEnemies());
        assertNotEquals(21, gc4.getnEnemies());
        for(Enemy e : gc4.getEnemies()){
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
        assertTrue(gc4.getPlayersStat().isEmpty());
        for (int i=0; i<10; i++){
            gc4.createKnightStat(HP_MAX, DEFENSE_POINTS);
            assertTrue(gc4.getnPlayers()== gc4.getPlayersStat().size() && gc4.getnPlayers()==(5*i+1));
            gc4.createBlackMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
            assertTrue(gc4.getnPlayers()== gc4.getPlayersStat().size() && gc4.getnPlayers()==(5*i+2));
            gc4.createEngineerStat(HP_MAX, DEFENSE_POINTS);
            assertTrue(gc4.getnPlayers()== gc4.getPlayersStat().size() && gc4.getnPlayers()==(5*i+3));
            gc4.createThiefStat(HP_MAX, DEFENSE_POINTS);
            assertTrue(gc4.getnPlayers()== gc4.getPlayersStat().size() && gc4.getnPlayers()==(5*i+4));
            gc4.createWhiteMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
            assertTrue(gc4.getnPlayers()== gc4.getPlayersStat().size() && gc4.getnPlayers()==(5*i+5));
        }
        gc4.createKnightStat(HP_MAX, DEFENSE_POINTS);
        assertEquals(gc4.getnPlayers(), gc4.getPlayersStat().size());
        assertNotEquals(51, gc4.getnPlayers());
        assertEquals(50, gc4.getnPlayers());

        gc4.createWhiteMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
        assertEquals(gc4.getnPlayers(), gc4.getPlayersStat().size());
        assertNotEquals(51, gc4.getnPlayers());
        assertEquals(50, gc4.getnPlayers());

        gc4.createThiefStat(HP_MAX, DEFENSE_POINTS);
        assertEquals(gc4.getnPlayers(), gc4.getPlayersStat().size());
        assertNotEquals(51, gc4.getnPlayers());
        assertEquals(50, gc4.getnPlayers());

        gc4.createEngineerStat(HP_MAX, DEFENSE_POINTS);
        assertEquals(gc4.getnPlayers(), gc4.getPlayersStat().size());
        assertNotEquals(51, gc4.getnPlayers());
        assertEquals(50, gc4.getnPlayers());

        gc4.createBlackMageStat(HP_MAX, DEFENSE_POINTS, MANA_MAX);
        assertEquals(gc4.getnPlayers(), gc4.getPlayersStat().size());
        assertNotEquals(51, gc4.getnPlayers());
        assertEquals(50, gc4.getnPlayers());
    }

    /**
     * Checks that the controller creates enemies stats correctly.
     */
    @Test
    public void createEnemiesTest() {
        assertTrue(gc4.getEnemiesName().isEmpty());
        assertTrue(gc4.getEnemiesStat().isEmpty());
        for(int i=0; i<50; i++){
            gc4.createEnemyStat(HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
            assertTrue(gc4.getnEnemiesStat()== gc4.getEnemiesStat().size() && gc4.getnEnemiesStat()==i+1);
            gc4.createEnemyName(ENEMY_NAME+i);
            assertTrue(gc4.getnEnemiesNames()== gc4.getEnemiesName().size() && gc4.getnEnemiesNames()==i+1);
        }
        gc4.createEnemyStat(HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
        assertEquals(gc4.getnEnemiesStat(), gc4.getEnemiesStat().size());
        assertNotEquals(51, gc4.getnEnemiesStat());
        assertEquals(50, gc4.getnEnemiesStat());
        gc4.createEnemyName(ENEMY_NAME+51);
        assertEquals(gc4.getnEnemiesNames(), gc4.getEnemiesName().size());
        assertNotEquals(51, gc4.getnEnemiesNames());
        assertEquals(50, gc4.getnEnemiesNames());
    }

    /**
     * Checks that the controller creates weapons and adds them to inventory correctly.
     */
    @Test
    public void createInventoryTest(){
        assertTrue(gc4.getInventory().isEmpty());
        for (int i=0; i<6; i++){
            gc4.createAxe("Axe"+i, WEAPON_DAMAGE, WEAPON_WEIGHT);
            assertTrue(gc4.getnInventory()== gc4.getInventory().size() && gc4.getnInventory()==(5*i+1));
            gc4.createBow("Bow"+i, WEAPON_DAMAGE, WEAPON_WEIGHT);
            assertTrue(gc4.getnInventory()== gc4.getInventory().size() && gc4.getnInventory()==(5*i+2));
            gc4.createKnife("Knife"+i, WEAPON_DAMAGE, WEAPON_WEIGHT);
            assertTrue(gc4.getnInventory()== gc4.getInventory().size() && gc4.getnInventory()==(5*i+3));
            gc4.createStaff("Staff"+i, WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);
            assertTrue(gc4.getnInventory()== gc4.getInventory().size() && gc4.getnInventory()==(5*i+4));
            gc4.createSword("Sword"+i, WEAPON_DAMAGE, WEAPON_WEIGHT);
            assertTrue(gc4.getnInventory()== gc4.getInventory().size() && gc4.getnInventory()==(5*i+5));
        }
        gc4.createAxe("Axe"+31, WEAPON_DAMAGE, WEAPON_WEIGHT);
        assertEquals(gc4.getnInventory(), gc4.getInventory().size());
        assertNotEquals(31, gc4.getnInventory());
        assertEquals(30, gc4.getnInventory());

        gc4.createBow("Bow"+31, WEAPON_DAMAGE, WEAPON_WEIGHT);
        assertEquals(gc4.getnInventory(), gc4.getInventory().size());
        assertNotEquals(31, gc4.getnInventory());
        assertEquals(30, gc4.getnInventory());

        gc4.createKnife("Knife"+31, WEAPON_DAMAGE, WEAPON_WEIGHT);
        assertEquals(gc4.getnInventory(), gc4.getInventory().size());
        assertNotEquals(31, gc4.getnInventory());
        assertEquals(30, gc4.getnInventory());

        gc4.createStaff("Staff"+31, WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);
        assertEquals(gc4.getnInventory(), gc4.getInventory().size());
        assertNotEquals(31, gc4.getnInventory());
        assertEquals(30, gc4.getnInventory());

        gc4.createSword("Sword"+31, WEAPON_DAMAGE, WEAPON_WEIGHT);
        assertEquals(gc4.getnInventory(), gc4.getInventory().size());
        assertNotEquals(31, gc4.getnInventory());
        assertEquals(30, gc4.getnInventory());
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

        assertEquals(staffValues, gc2.getValues(weapon));
        assertEquals(enemyValues, gc2.getValues(enemy));
        assertEquals(blackMageValues, gc2.getValues(player));

        assertEquals(staffValues, gc2.getWeaponValues(player));
    }

    /**
     * Checks that the controller equips weapons on a player correctly.
     */
    @Test
    public void equipWeaponTest() throws InvalidEquipmentException {
        //inventory
        gc4.createSword("Sword", WEAPON_DAMAGE, WEAPON_WEIGHT);
        gc4.createAxe("Axe", WEAPON_DAMAGE, WEAPON_WEIGHT);
        gc4.createStaff("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);

        IPlayer player = new Knights("Knight", turns, HP_MAX, DEFENSE_POINTS);

        //Success first equipment (dropped a "null")
        assertNull(player.getEquippedWeapon());
        assertEquals(3, gc4.getnInventory());
        IWeapon swordInventory = gc4.getInventory().get(0);
        gc4.equipWeapon(player,0);
        assertEquals(2, gc4.getnInventory());
        assertEquals(swordInventory, player.getEquippedWeapon());
        assertEquals(gc4.getInventory().get(0), new Axes("Axe", WEAPON_DAMAGE, WEAPON_WEIGHT));

        //Success second equipment (dropped a weapon)
        IWeapon AxeInventory = gc4.getInventory().get(0);
        gc4.equipWeapon(player,0);
        assertNotEquals(1, gc4.getnInventory());
        assertEquals(2, gc4.getnInventory());
        assertEquals(AxeInventory, player.getEquippedWeapon());
        assertEquals(gc4.getInventory().get(0), new Staffs("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE));
        assertEquals(gc4.getInventory().get(1), swordInventory);

        //Failed equipment
        assertThrows(InvalidEquipmentException.class,() -> {
            gc4.equipWeapon(player,0);
        });
        assertThrows(InvalidMovementException.class,() -> {
            gc4.getPhase().canChooseAWeapon();
        });


    }

    /**
     * checks a correct operation of turns.
     */
    @RepeatedTest(3)
    public void waitTurnsTest() throws InvalidEquipmentException, InterruptedException, InvalidSelectionPlayerException {
        gc2.toSelectionPlayerPhase();
        String knightName = "knight";
        String thiefName = "thief";
        int knightWeight = 10;
        int enemyWeight = 20;
        int thiefWeight = 30;
        //player stats
        gc2.createKnightStat(HP_MAX, DEFENSE_POINTS);
        gc2.createThiefStat(HP_MAX, DEFENSE_POINTS);
        //enemies stats
        gc2.createEnemyStat(HP_MAX, 0, ENEMY_ATTACK_POINTS, enemyWeight);
        gc2.createEnemyName(ENEMY_NAME);
        //inventory
        gc2.createSword("Sword", HP_MAX, knightWeight);
        gc2.createKnife("Knife", HP_MAX, thiefWeight);
        gc2.createKnife("Knife", HP_MAX, 1);
        //Selections

        IPlayer player = gc2.selectionPlayer(0, knightName);
        gc2.canChooseAWeapon();

        gc2.equipWeapon(player, 0);
        gc2.canChooseAPlayer();

        IPlayer player2 = gc2.selectionPlayer(0,thiefName);
        gc2.canChooseAWeapon();

        gc2.equipWeapon(player2,0);
        gc2.canChooseAPlayer();

        gc2.selectionEnemy();

        gc2.toTurnsPhase();

        int maxWeight = Math.max(knightWeight, Math.max(thiefWeight, enemyWeight));

        assertTrue(gc2.getTurnsQueue().isEmpty());
        gc2.initialWaitTurns(((maxWeight/10)+1)*1000);
        assertEquals(3, gc2.getTurnsQueue().size());

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

        ICharacter[] characterList = {knight, enemy, thief, knight};
        for(ICharacter character : characterList) {
            autoNextTurn(500, gc2);
            assertEquals(character, gc2.getCurrentCharacter());
        }
        assertTrue(gc2.isInGame());
        assertNull(gc2.getTurnsQueue().peek());
        gc2.turnList.turnIsNotEmpty(gc2.getTurnsQueue());
        Thread.sleep(maxWeight*100);
        assertNotNull(gc2.getTurnsQueue().peek());
        autoNextTurn(500,gc2);
        assertTrue(gc2.getCurrentCharacter().equals(knight) || gc2.getCurrentCharacter().equals(enemy));
        if(gc2.getCurrentCharacter().equals(enemy)){
            gc2.nextTurn(500);
            assertEquals(knight, gc2.getCurrentCharacter());
            // turns change with a equipment change
            gc2.canChooseAWeapon();
            gc2.equipWeapon((IPlayer) gc2.getCurrentCharacter(),0);
            gc2.toPlayerTurnPhase();
            gc2.canAttack();
            gc2.getCurrentCharacter().waitTurn();
            gc2.toTurnsPhase();

            autoNextTurn(500, gc2);
            assertEquals(knight, gc2.getTurnsQueue().peek());
            autoNextTurn(500, gc2);
        }
        assertTrue(gc2.isInGame());

        gc2.attack(gc2.getParty().get(0), gc2.getEnemies().get(0));
        assertTrue(gc2.getEnemies().get(0).isKO());
        assertEquals(1, gc2.getnDefeatedEnemies());
        assertTrue(gc2.isWin());

        gc2.setPhase(new EndPhase());
        assertThrows(InvalidMovementException.class,() -> {
            gc2.getPhase().toCharacterTurn(player);
        });

    }

    /**
     * holds all for an "automatic" next turn
     */
    private void autoNextTurn(int millis, GameController gc){
        gc.nextTurn(millis);
        gc.canAttack();
        gc.getCurrentCharacter().waitTurn();
        gc.toTurnsPhase();
    }

    /**
     * check that the controller makes attacks between two characters correctly
     */
    @Test
    public void attackTest() throws InvalidMovementException {
        IPlayer player1 = new Knights("Knight", turns, HP_MAX, DEFENSE_POINTS);
        IPlayer player2 = new Thieves("Thief", turns, HP_MAX, DEFENSE_POINTS);
        gc2.setPhase(new SelectionWeaponPhase());
        player1.equip(new Swords("sword", WEAPON_DAMAGE, WEAPON_WEIGHT));
        gc2.setPhase(new SelectionAttackPhase());
        gc2.tryAttack(player1, player2);
        assertEquals(270, player2.getHp());

        gc2.setPhase(new EndPhase());
        assertThrows(InvalidMovementException.class,() -> {
            gc2.tryAttack(player1, player2);
        });
        assertEquals(270, player2.getHp());
        assertThrows(InvalidMovementException.class,() -> {
            gc2.getPhase().canAttack();
        });

    }

    /**
     * check that the controller makes random attacks correctly
     */
    @RepeatedTest(10)
    public void randomAttackTest() throws InvalidSelectionPlayerException, InvalidEquipmentException, InvalidMovementException {
        //player stats
        gc2.createKnightStat(HP_MAX, 0);
        gc2.createThiefStat(HP_MAX, 0);
        //enemies stats
        gc2.createEnemyStat(HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT);
        gc2.createEnemyName(ENEMY_NAME);
        //inventory
        gc2.createSword("Sword", HP_MAX, WEAPON_WEIGHT);
        gc2.createKnife("Knife", HP_MAX, WEAPON_WEIGHT);

        gc2.canChooseAPlayer();
        for(int i=0; i<gc2.getnMaxParty(); i++){
            IPlayer p = gc2.selectionPlayer(0,"player"+(i+1));
            gc2.canChooseAWeapon();
            gc2.equipWeapon(p, 0);
            gc2.canChooseAPlayer();
        }
        gc2.selectionEnemy();
        gc2.setPhase(new SelectionAttackPhase());
        Enemy enemy = gc2.getEnemies().get(0);

        gc2.randomEnemyAttack(enemy);

        IPlayer p1 = gc2.getParty().get(0);
        IPlayer p2 = gc2.getParty().get(1);

        assertTrue(p1.getHp()<HP_MAX || p2.getHp()<HP_MAX);

    }

    /**
     * checks a correct operation of defeated characters when they receives attacks.
     */
    @Test
    public void defeatedCharacterTest() throws  InvalidEquipmentException, InvalidSelectionPlayerException {
        //player stats
        gc4.createKnightStat(HP_MAX, 0);
        gc4.createThiefStat(HP_MAX, 0);
        //enemies stats
        gc4.createEnemyStat(HP_MAX, 0, HP_MAX, ENEMY_WEIGHT);
        gc4.createEnemyName(ENEMY_NAME);
        //inventory
        gc4.createSword("Sword", HP_MAX, WEAPON_WEIGHT);
        gc4.createKnife("Knife", HP_MAX, WEAPON_WEIGHT);
        gc4.createKnife("Knife", HP_MAX, WEAPON_WEIGHT);

        IPlayer playerchoose1 = gc4.selectionPlayer(0,"knight");
        gc4.equipWeapon(playerchoose1,0);
        IPlayer playerchoose2 = gc4.selectionPlayer(0,"thief");
        gc4.equipWeapon(playerchoose2,0);
        gc4.selectionEnemy();
        gc4.selectionEnemy();
        gc4.selectionEnemy();

        IPlayer player1 = gc4.getParty().get(0);
        IPlayer player2 = gc4.getParty().get(1);
        Enemy enemy1 = gc4.getEnemies().get(0);
        Enemy enemy2 = gc4.getEnemies().get(1);
        Enemy enemy3 = gc4.getEnemies().get(2);

        //Player doesn't defeat enemy
        IPlayer player3 = new Knights("weak", turns, HP_MAX, DEFENSE_POINTS);
        player3.equip(new Swords("stick", 0, WEAPON_WEIGHT));
        gc4.attack(player3, player1);
        assertFalse(player1.isKO());
        assertEquals(0, gc4.getnDefeatedPlayers());
        //Enemy doesn't defeat player
        gc4.attack(player3, enemy1);
        assertFalse(enemy1.isKO());
        assertEquals(0, gc4.getnDefeatedEnemies());

        //Player defeats enemy
        gc4.attack(player1, enemy2);
        assertTrue(enemy2.isKO());
        assertEquals(1, gc4.getnDefeatedEnemies());
        //Player defeats player
        gc4.attack(player1, player2);
        assertTrue(player2.isKO());
        assertEquals(1, gc4.getnDefeatedPlayers());
        //Enemy defeats enemy
        gc4.attack(enemy1, enemy3);
        assertTrue(enemy3.isKO());
        assertEquals(2, gc4.getnDefeatedEnemies());
        //Enemy defeats player
        gc4.attack(enemy1, player1);
        assertTrue(player1.isKO());
        assertEquals(2, gc4.getnDefeatedPlayers());

        //Character attack defeated character
        gc4.attack(enemy1, player1);
        assertTrue(player1.isKO());
        assertEquals(2, gc4.getnDefeatedPlayers());
    }


}