package com.github.ssanchez7.finalreality.controller;

import com.github.ssanchez7.finalreality.controller.exceptions.InvalidEquipmentException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidTransitionException;
import com.github.ssanchez7.finalreality.controller.handlers.*;
import com.github.ssanchez7.finalreality.controller.phases.Phase;
import com.github.ssanchez7.finalreality.controller.phases.SelectionPlayerPhase;
import com.github.ssanchez7.finalreality.gui.FinalReality;
import com.github.ssanchez7.finalreality.gui.ProvisionalGui;
import com.github.ssanchez7.finalreality.model.Iitem;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.NullCharacter;
import com.github.ssanchez7.finalreality.model.character.player.*;
import com.github.ssanchez7.finalreality.model.weapon.*;

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

    private final List<IPlayer> party = new ArrayList<>();
    private final List<IPlayer> party_copy = new ArrayList<>();
    private int nParty = 0;
    private final List<Enemy> enemies = new ArrayList<>();
    private int nEnemies = 0;
    private final int nMaxEnemies;
    private final BlockingQueue<ICharacter> turnsQueue = new LinkedBlockingQueue<>();

    private Phase phase;

    private int nDefeatedEnemies = 0;
    private int nDefeatedPlayers = 0;
    private int state = 0;

    private ICharacter actualCharacter = new NullCharacter();

    public ICharacter getActualCharacter() {
        return actualCharacter;
    }

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

    public boolean isInGame(){
        return !(isWin() || isLose());
    }
    public boolean isWin(){
        return nDefeatedEnemies==nEnemies;
    }
    public boolean isLose(){
        return nDefeatedPlayers==nParty;
    }


    //Possible Players
    private List<String[]> playersStat = new ArrayList<>();
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
        this.setPhase(new SelectionPlayerPhase());

        createKnightStat(30, 100);
        createThiefStat(20, 140);
        createBlackMageStat(25, 130, 30);
        createEngineerStat(15, 60);
        createWhiteMageStat(10, 40, 20);
        createWhiteMageStat(10, 40, 20);
        createThiefStat(20, 140);
        createBlackMageStat(25, 130, 30);
        createWhiteMageStat(10, 40, 20);
        createThiefStat(20, 140);
        createBlackMageStat(25, 130, 30);

        createAxe("AXE", 130,40);
        createKnife("KNIFE", 75,12);
        createStaff("STAFF1", 664, 21,98);
        createAxe("AXE", 140, 20);
        createStaff("STAFF2", 664, 21,98);
        createSword("SWORD", 123,40);
        createBow("BOW", 123,67);
        createStaff("STAFF2", 664, 21,98);
        createBow("BOW", 123,10);

        createEnemyName("Goblin");
        createEnemyName("Spider");
        createEnemyName("Ghost");
        createEnemyName("Skeleton");
        createEnemyName("Zombie");

        createEnemyStat(10,130,120,50);
        createEnemyStat(50,100,100,56);
        createEnemyStat(187,40,160,23);
        createEnemyStat(123,90,60,80);
        createEnemyStat(53,70,190,20);
        createEnemyStat(90,127,40,40);



    }
    public GameController(Random rng){
        nMaxEnemies = rng.nextInt(8)+1;
        turnList.addListener(handlerList);
    }

    public void setPhase(Phase phase){
        this.phase = phase;
        phase.setController(this);
    }

    /**
     * Creates and adds stats of different playable character to a list of players available
     */
    public void createKnightStat(int hp, int def){
        if(nPlayers<50){
            playersStat.add(new String[]{"1", "Knight", String.valueOf(hp), String.valueOf(def), null});
            nPlayers += 1;
        }
    }
    public void createThiefStat(int hp, int def){
        if(nPlayers<50){
            playersStat.add(new String[]{"2", "Thief", String.valueOf(hp), String.valueOf(def), null});
            nPlayers += 1;
        }
    }
    public void createBlackMageStat(int hp, int def, int mana){
        if(nPlayers<50){
            playersStat.add(new String[]{"3", "BlackMage", String.valueOf(hp), String.valueOf(def), String.valueOf(mana)});
            nPlayers += 1;
        }
    }
    public void createWhiteMageStat(int hp, int def, int mana){
        if(nPlayers<50){
            playersStat.add(new String[]{"4", "WhiteMage", String.valueOf(hp), String.valueOf(def), String.valueOf(mana)});
            nPlayers += 1;
        }
    }
    public void createEngineerStat(int hp, int def){
        if(nPlayers<50){
            playersStat.add(new String[]{"5", "Engineer", String.valueOf(hp), String.valueOf(def), null});
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
    public List<String[]> getPlayersStat() { return List.copyOf(playersStat); }
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
    public IPlayer selectionPlayer(int indexPlayer, String name) throws InvalidTransitionException {
        String[] player = playersStat.get(indexPlayer);
        IPlayer partyPlayer = null;
        switch (Integer.parseInt(player[0])) {
            case 1: //Knight
                partyPlayer = new Knights(name, turnsQueue, Integer.parseInt(player[2]), Integer.parseInt(player[3]));
                break;
            case 2: //Thief
                partyPlayer = new Thieves(name, turnsQueue, Integer.parseInt(player[2]), Integer.parseInt(player[3]));
                break;
            case 3: //BlackMage
                partyPlayer = new BlackMages(name, turnsQueue, Integer.parseInt(player[2]), Integer.parseInt(player[3]), Integer.parseInt(player[4]));
                break;
            case 4: //WhiteMage
                partyPlayer = new WhiteMages(name, turnsQueue, Integer.parseInt(player[2]), Integer.parseInt(player[3]), Integer.parseInt(player[4]));
                break;
            case 5: //Engineer
                partyPlayer = new Engineers(name, turnsQueue, Integer.parseInt(player[2]), Integer.parseInt(player[3]));
                break;
        }
        if(partyPlayer==null){
            throw new InvalidTransitionException("Incorrect player");
        }
        partyPlayer.addListener(handlerPlayer);
        playersStat.remove(indexPlayer);
        nPlayers -= 1;
        nParty += 1;
        party.add(partyPlayer);
        party_copy.add(partyPlayer);
        return partyPlayer;
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
     * Equips a user selected weapon on a playable character ans returns true if the dropped weapon is null
     * @param player:
     *       playable character who receives the weapon.
     */
    public boolean equipWeapon(IPlayer player, int indexInventory) throws InvalidEquipmentException{
        boolean v = true;
        IWeapon chosenWeapon = inventory.get(indexInventory);
        IWeapon droppedWeapon = player.getEquippedWeapon();
        if (player.equip(chosenWeapon)) { //Successful weapon equipment
            if (droppedWeapon != null) {
                inventory.add(droppedWeapon);
                nInventory+=1;
                v = false;
            }
            inventory.remove(indexInventory);
            nInventory-=1;
            return v;
        }else {
            throw new InvalidEquipmentException("The weapon '"+chosenWeapon.getName()+"' can't be equipped on '"+player.getName()+"'");
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
            defender.isDefeatedCharacter();
        }
    }

    public void tryAttack(ICharacter attacker, ICharacter defender) throws InvalidMovementException{
        phase.attack(attacker, defender);
    }

    public void randomEnemyAttack(ICharacter attacker) throws InvalidMovementException{
        IPlayer defender = party_copy.get((new Random()).nextInt(party_copy.size()));
        tryAttack(attacker, defender);
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
    public void nextTurn(int mSecSleep){
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
        try {
            this.actualCharacter = character;
            phase.toCharacterTurn(character);
        }catch (InvalidMovementException e){
            e.printStackTrace();
        }
    }

    public List<String> getValues(Iitem item){ return item.getValues(); }
    public boolean isEnemy(ICharacter character){return character.isEnemy();}

    public void toSelectionWeaponPhase(){
        try {
            phase.toSelectionWeaponPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    public void toTurnsPhase() {
        try{
            phase.toTurnsPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    public void toPlayerTurnPhase(){
        try{
            phase.toPlayerTurnPhase();
        } catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    public void toSelectionPlayerPhase() {
        try{
            phase.toSelectionPlayerPhase();
        } catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    public void toEnemyTurnPhase() {
        try{
            phase.toEnemyTurnPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    public void toSelectionAttackPhase() {
        try{
            phase.toSelectionAttackPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    public void toEndPhase() {
        try{
            phase.toEndPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    public boolean isInSelectionPlayerPhase(){return phase.isInSelectionPlayerPhase();}
    public boolean isInSelectionWeaponPhase(){return phase.isInSelectionWeaponPhase();}
    public boolean isInTurnsPhase() {return phase.isInTurnsPhase();}
    public boolean isInPlayerTurnPhase() {return phase.isInPlayerTurnPhase();}
    public boolean isInEnemyTurnPhase() {return phase.isInEnemyTurnPhase();}
    public boolean isInSelectionAttackPhase() {return phase.isInSelectionAttackPhase();}


    public void canChooseAWeapon() {
        try {
            phase.canChooseAWeapon();
        }catch (InvalidMovementException e){
            e.printStackTrace();
        }
    }

    public void canChooseAPlayer() {
        try{
            phase.canChooseAPlayer();
        }catch (InvalidMovementException e){
            e.printStackTrace();
        }
    }

    public void canAttack() {
        try{
            phase.canAttack();
        }catch (InvalidMovementException e){
            e.printStackTrace();
        }
    }

    public void removePlayerCopy(IPlayer player) {
        party_copy.remove(player);
    }

    public Phase getPhase() {
        return phase;
    }
}
