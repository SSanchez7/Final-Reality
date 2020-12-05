package com.github.ssanchez7.finalreality.controller;

import com.github.ssanchez7.finalreality.gui.ProvisorialGui;
import com.github.ssanchez7.finalreality.model.Iitem;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.*;
import com.github.ssanchez7.finalreality.model.weapon.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.Arrays.asList;

public class GameController {

    private ProvisorialGui view = new ProvisorialGui();
    private List<IPlayer> party = new ArrayList<>();// IPlayer[4];
    private Enemy[] enemies;
    private BlockingQueue<ICharacter> turnsQueue = new LinkedBlockingQueue<>();

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
        enemies = new Enemy[new Random().nextInt(8)+1];
    }
    public GameController(Random rng){
        enemies = new Enemy[rng.nextInt(8)+1];
    }

    /**
     * Run the game.
     */
    public void playGame(){
        for(int i=0; i<4; i++){
            selectionPlayer(new BufferedReader(new InputStreamReader(System.in)));
        }
        selectionEnemies();
        waitTurns();
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
    public Enemy[] getEnemies() { return enemies; }
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

    /**
     * Selects and creates four players from the list of stats available.
     */
    public void selectionPlayer(BufferedReader in){
        view.showPlayers(playersStat, nPlayers);
        String[] s = view.getIndexPlayerUser(in);
        int indexPlayers = Integer.parseInt(s[0])-1;
        String name = s[1];
        int[] player = playersStat.get(indexPlayers);
        IPlayer partyPlayer = null;
        switch(player[0]) {
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
        playersStat.remove(indexPlayers);
        nPlayers-=1;
        party.add(partyPlayer);

    }


    /**
     * Selects and creates a random number of enemies, with random stats and names from the list of available.
     */
    public void selectionEnemies(){
        for(int i=0; i<enemies.length; i++){
            int[] stat = enemiesStat.get(new Random().nextInt(nEnemiesStat));
            String name = enemiesName.get(new Random().nextInt(nEnemiesNames));
            enemies[i] = new Enemy(name,turnsQueue,stat[0],stat[1],stat[2],stat[3]);
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
            //System.out.println("*** Success ***\n");
        }else{
            //Exceptions
            //System.out.println("--- Error ---\n");
        }
    }

    /**
     * Returns the list of weapon of the inventory.
     */
    public void waitTurns(){
        for(IPlayer player: party){
            player.waitTurn();
        }
        for (Enemy enemy : enemies){
            enemy.waitTurn();
        }
    }

    /*public static void main(String[] args) {
        GameController gm = new GameController();
        gm.createEnemyName("DonPepe");
        gm.createEnemyName("ElDramas");
        gm.createEnemyStat(10,20,30,40);
        gm.createEnemyStat(60,10,1,1);
        gm.createKnightStat(100,10);
        gm.createKnightStat(100,10);
        gm.createKnightStat(100,10);
        gm.createKnightStat(100,10);
        gm.createAxe("The",123,1);
        gm.createAxe("The",123,1);
        gm.createAxe("The",123,1);
        gm.createAxe("The",123,1);
        //gm.playGame();

    }*/

}
