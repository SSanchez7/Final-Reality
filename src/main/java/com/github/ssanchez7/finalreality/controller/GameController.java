package com.github.ssanchez7.finalreality.controller;

import com.github.ssanchez7.finalreality.gui.ProvisionalGui;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.*;
import com.github.ssanchez7.finalreality.model.weapon.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A class that holds all the information for driven the game. The Controller class.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class GameController {
    private final TurnList turnList = new TurnList();
    private final TurnListHandler handlerList = new TurnListHandler(this);
    private final DefeatedPlayerHandler handlerPlayer = new DefeatedPlayerHandler(this);
    private final DefeatedEnemyHandler handlerEnemy = new DefeatedEnemyHandler(this);

    private ProvisionalGui view = new ProvisionalGui();
    private List<IPlayer> party = new ArrayList<>();
    private int nParty = 0;
    private List<Enemy> enemies = new ArrayList<>();
    private int nEnemies = 0;
    private final int nMaxEnemies;
    private BlockingQueue<ICharacter> turnsQueue = new LinkedBlockingQueue<>();
    private int nDefeatedEnemies = 0;
    private int nDefeatedPlayers = 0;
    private int state = 0;

    /**
     * Returns the number of defeated enemies and defeated players respectively.
     */
    public int getnDefeatedEnemies(){return nDefeatedEnemies;}
    public int getnDefeatedPlayers(){return nDefeatedPlayers;}

    /**
     * Set a new number of enemies defeated and set the game state to "win" if applicable
     */
    public void setnDefeatedEnemies(int n){
        nDefeatedEnemies = n;
        if(n==nEnemies){
            state = 1;
        }
    }
    /**
     * Set a new number of players defeated and set the game state to "lose" if applicable
     */
    public void setnDefeatedPlayers(int n){
        nDefeatedPlayers = n;
        if(n==nParty){
            state = 2;
        }
    }

    //Possible Players
    private List<int[]> playersStat = new ArrayList<>();
    private int nPlayers=0;

    //Possible Enemies
    private List<int[]> enemiesStat = new ArrayList<>();
    private List<String> enemiesName = new ArrayList<>();
    private int nEnemiesStat=0;
    private int nEnemiesNames=0;

    //Inventory
    private List<IWeapon> inventory = new ArrayList<>();
    private int nInventory=0;


    public GameController(){
        nMaxEnemies = new Random().nextInt(8)+1;
        turnList.addListener(handlerList);
    }
    public GameController(Random rng){
        nMaxEnemies = rng.nextInt(8)+1;
        turnList.addListener(handlerList);
    }

    /**
     * Run the game.
     */
    public void playGame(){
        for(int i=0; i<4; i++){
            selectionPlayer(new BufferedReader(new InputStreamReader(System.in)));
        }
        for(int i=0; i<nMaxEnemies; i++){
            selectionEnemy();
        }
        initialWaitTurns(1000);
        while(!(nDefeatedPlayers==nParty || nDefeatedEnemies==nEnemies)) {
            turnIsNotEmpty(500);
        }
        state = (nDefeatedPlayers==4)?2:1;
        //System.out.println((state==2)?"-------\nYOU DIED":(state==1)?"-------\nYOU WIN!!":"-------\nERROR");
    }

    /**
     * Creates and adds stats of different playable character to a list of players available
     */
    public void createKnightStat(int hp, int def){
        if(nPlayers<50){
            playersStat.add(new int[]{1, hp, def, -1});
            nPlayers += 1;
        }
    }
    public void createThiefStat(int hp, int def){
        if(nPlayers<50){
            playersStat.add(new int[]{2, hp, def, -1});
            nPlayers += 1;
        }
    }
    public void createBlackMageStat(int hp, int def, int mana){
        if(nPlayers<50){
            playersStat.add(new int[]{3, hp, def, mana});
            nPlayers += 1;
        }
    }
    public void createWhiteMageStat(int hp, int def, int mana){
        if(nPlayers<50){
            playersStat.add(new int[]{4, hp, def, mana});
            nPlayers += 1;
        }
    }
    public void createEngineerStat(int hp, int def){
        if(nPlayers<50){
            playersStat.add(new int[]{5, hp, def, -1});
            nPlayers += 1;
        }
    }

    /**
     *  Creates and adds weapons to inventory
     */
    public void createSword(String name, int dmg, int wgt){
        if(nInventory<30) {
            inventory.add(new Swords(name, dmg, wgt));
            nInventory += 1;
        }
    }
    public void createStaff(String name, int dmg, int wgt, int mdmg){
        if(nInventory<30) {
            inventory.add(new Staffs(name, dmg, wgt, mdmg));
            nInventory += 1;
        }
    }
    public void createKnife(String name, int dmg, int wgt){
        if(nInventory<30) {
            inventory.add(new Knives(name, dmg, wgt));
            nInventory += 1;
        }
    }
    public void createBow(String name, int dmg, int wgt){
        if(nInventory<30) {
            inventory.add(new Bows(name, dmg, wgt));
            nInventory += 1;
        }
    }
    public void createAxe(String name, int dmg, int wgt){
        if(nInventory<30) {
            inventory.add(new Axes(name, dmg, wgt));
            nInventory += 1;
        }
    }

    /**
     *  Creates and adds enemies' names and stats to a different list of available.
     */
    public void createEnemyStat(int hp, int def, int attk, int wgt){
        if(nEnemiesStat<50) {
            enemiesStat.add(new int[]{hp, def, attk, wgt});
            nEnemiesStat += 1;
        }
    }
    public void createEnemyName(String name){
        if(nEnemiesNames<50) {
            enemiesName.add(name);
            nEnemiesNames += 1;
        }
    }

    /**
     * Returns the list of members of the party.
     */
    public List<IPlayer> getParty() { return List.copyOf(party); }
    /**
     * Returns the list of enemies.
     */
    public List<Enemy> getEnemies() { return List.copyOf(enemies); }
    /**
     * Returns the list of turns from the game.
     */
    public BlockingQueue<ICharacter> getTurnsQueue() { return turnsQueue; }
    /**
     * Returns the list of available player stats.
     */
    public List<int[]> getPlayersStat() { return List.copyOf(playersStat); }
    /**
     * Returns the list of available enemies stats.
     */
    public List<int[]> getEnemiesStat() { return List.copyOf(enemiesStat); }
    /**
     * Returns the list of available enemies names.
     */
    public List<String> getEnemiesName() { return List.copyOf(enemiesName); }
    /**
     * Returns the list of weapon of the inventory.
     */
    public List<IWeapon> getInventory() { return List.copyOf(inventory); }

    /**
     * Returns the number of elements from the different lists.
     */
    public int getnPlayers() { return nPlayers; }
    public int getnEnemiesStat() { return nEnemiesStat; }
    public int getnEnemiesNames() { return nEnemiesNames; }
    public int getnInventory() { return nInventory; }
    public int getnParty() { return nParty; }
    public int getnEnemies() { return nEnemies; }

    /**
     * Return the max number of enemies possible for the game
     */
    public int getnMaxEnemies() { return nMaxEnemies; }
    /**
     * Returns the state of the game: 0 for "in game", 1 for "victory", 2 for "defeat"
     */
    public int getState() { return state; }

    /**
     * Selects and creates a player from the list of stats available.
     */
    public void selectionPlayer(BufferedReader in){
        if(nParty<20) {
            view.showPlayers(playersStat, nPlayers);
            String[] s = view.getIndexPlayerUser(in);
            int indexPlayers = Integer.parseInt(s[0]) - 1;
            String name = s[1];
            int[] player = playersStat.get(indexPlayers);
            IPlayer partyPlayer = null;
            switch (player[0]) {
                case 1: //Knight
                    partyPlayer = new Knights(name, turnsQueue, player[1], player[2]);
                    break;
                case 2: //Thief
                    partyPlayer = new Thieves(name, turnsQueue, player[1], player[2]);
                    break;
                case 3: //BlackMage
                    partyPlayer = new BlackMages(name, turnsQueue, player[1], player[2], player[3]);
                    break;
                case 4: //WhiteMage
                    partyPlayer = new WhiteMages(name, turnsQueue, player[1], player[2], player[3]);
                    break;
                case 5: //Engineer
                    partyPlayer = new Engineers(name, turnsQueue, player[1], player[2]);
                    break;
            }
            equipWeapon(partyPlayer, in);
            partyPlayer.addListener(handlerPlayer);
            playersStat.remove(indexPlayers);
            nPlayers -= 1;
            nParty += 1;
            party.add(partyPlayer);
        }
    }


    /**
     * Selects and creates an enemy, with random stats and names from the list of available.
     */
    public void selectionEnemy(){
        if(nEnemies<20) {
            int[] stat = enemiesStat.get(new Random().nextInt(nEnemiesStat));
            String name = enemiesName.get(new Random().nextInt(nEnemiesNames));
            Enemy enemy = new Enemy(name, turnsQueue, stat[0], stat[1], stat[2], stat[3]);
            enemy.addListener(handlerEnemy);
            enemies.add(enemy);
            nEnemies += 1;
        }
    }

    /**
     * Equips a user selected weapon on a playable character
     * @param player:
     *       playable character who receives the weapon.
     */
    public void equipWeapon(IPlayer player, BufferedReader in) {
        view.showInventory(inventory, nInventory);
        String s = view.getIndexInventoryUser(player.getName(), in);
        int indexInventory = Integer.parseInt(s) - 1;
        IWeapon chosenWeapon = inventory.get(indexInventory);
        IWeapon droppedWeapon = player.getEquippedWeapon();
        if (player.equip(chosenWeapon)) { //Successful weapon equipment
            if (droppedWeapon != null) {
                inventory.add(droppedWeapon);
                nInventory+=1;
            }
            inventory.remove(indexInventory);
            nInventory-=1;
        }else{
            //Exceptions
        }
    }

    /**
     * Attacks from a character to another.
     * @param attacker:
     *                character that attacks
     * @param defender:
     *                character that receives the attack
     */
    public void attack(ICharacter attacker, ICharacter defender){
        if(!defender.isKO()) {
            attacker.attack(defender);
            defender.defeatedCharacter();
        }
    }

    /**
     * Puts all characters in the turn list
     */
    public void initialWaitTurns(int mSecSleep){
        try {
            for (IPlayer player : party) {
                player.waitTurn();
            }
            for (Enemy enemy : enemies) {
                enemy.waitTurn();
            }
            Thread.sleep(mSecSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Perform a certain action on a character's turn according to its type only
     * when the turn list contains an item.
     * @param mSecSleep:
     *                 wait time in milliseconds between turns.
     */
    public void turnIsNotEmpty(int mSecSleep){
        try {
            turnList.turnIsNotEmpty(turnsQueue);
            Thread.sleep(mSecSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * starts the character's turn by performing an action and ends
     * it by putting him back on hold for the turn list.
     * @param character:
     *                 character with the turn.
     */
    public void characterTurn(ICharacter character){
        character.action(); //Starts turn
        character.waitTurn(); //Ends turn
    }

}
