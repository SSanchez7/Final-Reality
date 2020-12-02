package com.github.ssanchez7.finalreality.controller;

import com.github.ssanchez7.finalreality.model.Iitem;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.*;
import com.github.ssanchez7.finalreality.model.weapon.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.Arrays.asList;

public class GameController {

    private IPlayer[] party = new IPlayer[4];
    private Enemy[] enemies;
    private BlockingQueue<ICharacter> turnsQueue = new LinkedBlockingQueue<>();
    private Scanner entry = new Scanner(System.in);

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
        super();
    }

    public void playGame(){
        selectionPlayer();
        selectionEnemies();
        waitTurns();
    }

    /**
     * Creates and adds stats of different playable character to a list of players available
     */
    public void createKnightStat(int hp, int defp){
        playersStat.add(new int[]{1, hp, defp, -1});
        nPlayers+=1;
    }
    public void createThiefStat(int hp, int defp){
        playersStat.add(new int[]{2, hp, defp, -1});
        nPlayers+=1;
    }
    public void createBlackMageStat(int hp, int defp, int mana){
        playersStat.add(new int[]{3, hp, defp, mana});
        nPlayers+=1;
    }
    public void createWhiteMageStat(int hp, int defp, int mana){
        playersStat.add(new int[]{4, hp, defp, mana});
        nPlayers+=1;
    }
    public void createEngineerStat(int hp, int defp){
        playersStat.add(new int[]{5, hp, defp, -1});
        nPlayers+=1;
    }

    /**
     *  Creates and adds weapons to inventory
     */
    public void createSword(String name, int dmg, int wgt){
        inventory.add(new Swords(name, dmg, wgt));
        nInventory+=1;
    }
    public void createStaff(String name, int dmg, int wgt, int mdmg){
        inventory.add(new Staffs(name, dmg, wgt, mdmg));
        nInventory+=1;
    }
    public void createKnife(String name, int dmg, int wgt){
        inventory.add(new Knives(name, dmg, wgt));
        nInventory+=1;
    }
    public void createBow(String name, int dmg, int wgt){
        inventory.add(new Bows(name, dmg, wgt));
        nInventory+=1;
    }
    public void createAxe(String name, int dmg, int wgt){
        inventory.add(new Axes(name, dmg, wgt));
        nInventory+=1;
    }

    /**
     *  Creates and adds enemies' names and stats to a different list of available.
     */
    public void createEnemyStat(int hp, int attk, int def, int wgt){
        enemiesStat.add(new int[]{hp, attk, def, wgt});
        nEnemiesStat+=1;
    }
    public void createEnemyName(String name){
        enemiesName.add(name);
        nEnemiesNames+=1;
    }

    public void selectionPlayer(){
        for(int i=0; i<4; i++){
            showPlayers();
            System.out.print("Choose the "+(i+1)+"° player: ");
            int indexPlayers = Integer.parseInt(entry.nextLine()) - 1;
            int [] player = playersStat.get(indexPlayers);
            System.out.print("What is her/his name?: ");
            String name = entry.nextLine();
            int playerClass = player[0];
            switch(playerClass) {
                case 1: //Knight
                    party[i] = new Knights(name, turnsQueue, player[1], player[2]);
                    break;
                case 2: //Thief
                    party[i] = new Thieves(name, turnsQueue, player[1], player[2]);
                    break;
                case 3: //BlackMage
                    party[i] = new BlackMages(name, turnsQueue, player[1], player[2], player[3]);
                    break;
                case 4: //WhiteMage
                    party[i] = new WhiteMages(name, turnsQueue, player[1], player[2], player[3]);
                    break;
                case 5: //Engineer
                    party[i] = new Engineers(name, turnsQueue, player[1], player[2]);
                    break;
            }
            playersStat.remove(indexPlayers);
            nPlayers-=1;
            equipWeapon(party[i]);
        }
    }

    public void selectionEnemies(){
        int n = new Random().nextInt(8)+1;
        enemies = new Enemy[n];
        for(int i=0; i<n; i++){
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
    public void equipWeapon(IPlayer player) {
        showInventory();
        System.out.format("Choose the weapon for %s: ", player.getName());
        int indexInventory = Integer.parseInt(entry.nextLine()) - 1;
        IWeapon chosenWeapon = inventory.get(indexInventory);
        IWeapon droppedWeapon = player.getEquippedWeapon();
        if (player.equip(chosenWeapon)) { //Successful weapon equipment
            if (droppedWeapon != null) {
                inventory.add(droppedWeapon);
                nInventory+=1;
            }
            inventory.remove(indexInventory);
            nInventory-=1;
            System.out.println("*** Success ***\n");
        }else{
            //Exceptions
            System.out.println("--- Error ---\n");
        }
    }

    public void waitTurns(){
        for(IPlayer player: party){
            player.waitTurn();
        }
        for (Enemy enemy : enemies) {
            enemy.waitTurn();
        }
        System.out.println(turnsQueue.poll());
    }

    /**
     * Shows in a table the features of the team's inventory
     */
    public void showInventory(){
        int nCol = 4;
        String div = "|----"+("|"+("-".repeat(16))).repeat(nCol)+"|";
        System.out.println(div);
        System.out.format("| %-3s"+"| %-15s".repeat(nCol)+"|\n",
                "Id","Name","Damage","Weight","MagicDamage");
        System.out.println(div);
        for(int i=0; i<nInventory; i++){
            IWeapon item = inventory.get(i);
            System.out.format("| %-3d|",(i+1));
            showLine(item, nCol);
        }
        System.out.println(div);
    }

    /**
     * Shows in a table the features of the enemy team
     */
    public void showEnemies(){
        int nCol = 5;
        String div = "|----"+("|"+("-".repeat(16))).repeat(nCol)+"|";
        System.out.println(div);
        System.out.format("| %-3s"+"| %-15s".repeat(nCol)+"|\n",
                "Id","Name","Hp","AttackPoints","DefensePoints","Weight");
        System.out.println(div);
        for(int i=0; i<enemies.length; i++){
            Enemy item = enemies[i];
            System.out.format("| %-3d|",(i+1));
            showLine(item, nCol);
        }
        System.out.println(div);
    }

    /**
     * Shows in a table the members of the party and their equipment
     */
    public void showParty(){
        int nCol = 5;
        String div = "|----"+("|"+("-".repeat(16))).repeat(nCol)+"|";
        System.out.println(div);
        System.out.format("| %-3s"+"| %-15s".repeat(nCol)+"|\n",
                "Id","Name","Hp","DefensePoints","NameWeapon","Mana");
        System.out.println(div);
        for(int i=0; i<party.length; i++){
            IPlayer item = party[i];
            System.out.format("| %-3d|",(i+1));
            showLine(item, nCol);
        }
        System.out.println(div);
    }

    /**
     * Prints a single line of a table.
     */
    public void showLine(Iitem item, int nCol){
        int c = 0;
        for(String s : item.getValues()){
            System.out.format(" %-15s|",s);
            c+=1;
        }
        if(c<nCol){
            System.out.format(" %-15s|","");
        }
        System.out.println();
    }

    /**
     * Shows the different playable characters available.
     */
    public void showPlayers(){
        int nCol = 4;
        String div = "|----"+("|"+("-".repeat(16))).repeat(nCol)+"|";
        System.out.println(div);
        System.out.format("| %-3s"+"| %-15s".repeat(nCol)+"|\n",
                "Id","Class","Hp","DefensePoints","Mana");
        System.out.println(div);
        for(int i=0; i<nPlayers; i++){
            int[] item = playersStat.get(i);
            String playerClass = (item[0]==1)? "Knight": (item[0]==2)? "Thief": (item[0]==3)? "BlackMage": (item[0]==4)? "WhiteMage": "Engineer" ;
            System.out.format("| %-3d|",(i+1));
            System.out.format(" %-15s|".repeat(4)+"\n",
                    playerClass,item[1], item[2], ((item[3]==-1)? "" : item[3]));
        }
        System.out.println(div);
    }

    public static void main(String[] args) {
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
        gm.showPlayers();
        gm.showInventory();
        gm.selectionEnemies();
        gm.showEnemies();
        gm.selectionPlayer();
        gm.showParty();
        gm.party[0].attack(gm.party[1]);
        gm.showParty();
        System.out.println(gm.party[1].isKO());
    }

}
