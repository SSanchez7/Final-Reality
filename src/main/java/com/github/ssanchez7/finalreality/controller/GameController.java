package com.github.ssanchez7.finalreality.controller;

import com.github.ssanchez7.finalreality.controller.exceptions.InvalidEquipmentException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidSelectionPlayerException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidTransitionException;
import com.github.ssanchez7.finalreality.controller.handlers.*;
import com.github.ssanchez7.finalreality.controller.phases.DrawTitlePhase;
import com.github.ssanchez7.finalreality.controller.phases.Phase;
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

    /**
     * Manage the turn List changes
     */
    final TurnList turnList = new TurnList();

    /**
     * Handlers that receives notifies from a change in TurnList, defeatedPlayer and DefeatedEnemy respectively
     */
    private final TurnListHandler handlerList = new TurnListHandler(this);
    private final DefeatedPlayerHandler handlerPlayer = new DefeatedPlayerHandler(this);
    private final DefeatedEnemyHandler handlerEnemy = new DefeatedEnemyHandler(this);

    /**
     * team of players of the user, their copy and their number.
     */
    private final List<IPlayer> party = new ArrayList<>();
    private final List<IPlayer> party_copy = new ArrayList<>();
    private final int nMaxParty;
    private int nParty = 0;

    /**
     * Equip of enemies, their number and the max number possible respectively.
     */
    private final List<Enemy> enemies = new ArrayList<>();
    private int nEnemies = 0;
    private final int nMaxEnemies;
    private final BlockingQueue<ICharacter> turnsQueue = new LinkedBlockingQueue<>();

    /**
     * Phase of the game.
     */
    private Phase phase;

    /**
     * Number of Enemies and Players defeated respectively.
     */
    private int nDefeatedEnemies = 0;
    private int nDefeatedPlayers = 0;

    private ICharacter currentCharacter = new NullCharacter();

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

    /**
     * Returns the current character from the turn list.
     */
    public ICharacter getCurrentCharacter() {
        return currentCharacter;
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
    }
    /**
     * Set a new number of players defeated and set the game state to "lose" if applicable
     */
    public void setnDefeatedPlayers(int n){
        nDefeatedPlayers = n;
    }

    /**
     * Returns the boolean value if the the user is in game, won or lost respectively
     */
    public boolean isInGame(){
        return !(isWin() || isLose());
    }
    public boolean isWin(){
        return nDefeatedEnemies==nEnemies;
    }
    public boolean isLose(){
        return nDefeatedPlayers==nParty;
    }

    /**
     * initialize a new game with a random number (max 8) of enemies
     */
    public GameController(int nMaxParty){
        nMaxEnemies = new Random().nextInt(8)+1;
        this.nMaxParty = nMaxParty;
        turnList.addListener(handlerList);
        this.setPhase(new DrawTitlePhase());
    }

    /**
     * Initialize a new game with a random number from a specific seed, of enemies
     */
    public GameController(Random rng, int nMaxParty){
        nMaxEnemies = rng.nextInt(8)+1;
        this.nMaxParty = nMaxParty;
        turnList.addListener(handlerList);
    }

    /**
     * Set the phase of the game
     */
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
     * Return the max number of enemies and players possible for the game respectively.
     */
    public int getnMaxEnemies() { return nMaxEnemies; }
    public int getnMaxParty() { return nMaxParty; }

    /**
     * Selects and creates a player from the list of stats available.
     */
    public IPlayer selectionPlayer(int indexPlayer, String name) throws InvalidSelectionPlayerException {
        if(getnParty()<20) {
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
            if (partyPlayer == null) {
                throw new InvalidSelectionPlayerException("Incorrect player");
            }
            partyPlayer.addListener(handlerPlayer);
            playersStat.remove(indexPlayer);
            nPlayers -= 1;
            nParty += 1;
            party.add(partyPlayer);
            party_copy.add(partyPlayer);
            return partyPlayer;
        }else{
            throw new InvalidSelectionPlayerException("maximum number of players reached");
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

    /**
     * Try to make an attack
     * @param attacker:
     *                character that attacks.
     * @param defender:
     *                character that recives the attack.
     * @throws InvalidMovementException when the attack fails.
     */
    public void tryAttack(ICharacter attacker, ICharacter defender) throws InvalidMovementException{
        phase.attack(attacker, defender);
    }

    /**
     * attack randomly to a character from the party
     * @param attacker:
     *                Enemy that make the attack.
     * @throws InvalidMovementException when the attack fails.
     */
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
     * starts the character's turn by performing an action
     * deriving to a respective phase according to the type of the character
     * @param character:
     *                 character with the turn.
     */
    public void characterTurn(ICharacter character){
        try {
            this.currentCharacter = character;
            phase.toCharacterTurn(character);
        }catch (InvalidMovementException e){
            e.printStackTrace();
        }
    }

    /**
     * get values from an specific item and returns a list with them.
     */
    public List<String> getValues(Iitem item){ return item.getValues(); }

    /**
     * get the values of a weapon from a specific player
     */
    public List<String> getWeaponValues(IPlayer player){
        return player.getEquippedWeapon().getValues();
    }

    /**
     * Transition to a Selection Weapon Phase
     */
    public void toSelectionWeaponPhase(){
        try {
            phase.toSelectionWeaponPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    /**
     * Transition to a Turns Phase.
     */
    public void toTurnsPhase() {
        try{
            phase.toTurnsPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    /**
     * Transition to a Player Turn Phase
     */
    public void toPlayerTurnPhase(){
        try{
            phase.toPlayerTurnPhase();
        } catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    /**
     * Transition to a Selection Player Phase
     */
    public void toSelectionPlayerPhase() {
        try{
            phase.toSelectionPlayerPhase();
        } catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    /**
     * Transition to a Enemy Turn Phase.
     */
    public void toEnemyTurnPhase() {
        try{
            phase.toEnemyTurnPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    /**
     * Transition to a Selection Attack Phase.
     */
    public void toSelectionAttackPhase() {
        try{
            phase.toSelectionAttackPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    /**
     * Transition to a End Phase.
     */
    public void toEndPhase() {
        try{
            phase.toEndPhase();
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns of the game is in a specific phase respectively.
     */
    public boolean isInSelectionPlayerPhase(){return phase.isInSelectionPlayerPhase();}
    public boolean isInSelectionWeaponPhase(){return phase.isInSelectionWeaponPhase();}
    public boolean isInTurnsPhase() {return phase.isInTurnsPhase();}
    public boolean isInPlayerTurnPhase() {return phase.isInPlayerTurnPhase();}
    public boolean isInEnemyTurnPhase() {return phase.isInEnemyTurnPhase();}
    public boolean isInSelectionAttackPhase() {return phase.isInSelectionAttackPhase();}
    public boolean isInEndPhase(){return phase.isInEndPhase();}
    public boolean isInDrawTitlePhase(){return phase.isInDrawTitlePhase();}


    /**
     * derive phase change to "SelectionWeaponPhase" depending on whether or not a weapon can be equipped
     */
    public void canChooseAWeapon() {
        try {
            phase.canChooseAWeapon();
        }catch (InvalidMovementException e){
            e.printStackTrace();
        }
    }
    /**
     * derive phase change to "SelectionPlayerPhase" depending on whether or not a player can be selected
     */
    public void canChooseAPlayer() {
        try {
            phase.canChooseAPlayer();
        } catch (InvalidMovementException e) {
            e.printStackTrace();
        }
    }
    /**
     * derive phase change to "SelectionAttackPhase" depending on whether or not the character can attack.
     */
    public void canAttack() {
        try{
            phase.canAttack();
        }catch (InvalidMovementException e){
            e.printStackTrace();
        }
    }

    /**
     * Remove a player from the copy of the party.
     */
    public void removePlayerCopy(IPlayer player) {
        party_copy.remove(player);
    }

    /**
     * Returns the phase
     */
    public Phase getPhase(){return phase;}



}
