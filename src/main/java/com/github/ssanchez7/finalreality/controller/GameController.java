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
    private final String[] colParty = {"Name","Hp","DefensePoints","NameWeapon","Mana"};
    private final String[] colInventory = {"Name","Damage","Weight","MagicDamage"};
    private final String[] colEnemies = {"Name","Hp","AttackPoints","DefensePoints","Weight"};


    //Possible Players
    private List<int[]> playersStat = new ArrayList<>();
    private int nPlayers;

    //Possible Enemies
    private int[][] enemiesStat = {
            {600,200,40,40},{400,200,50,50},{100,400,20,20},{400,200,10,20},{300,300,20,30},
            {150,600,70,30},{700,50,30,10},{300,700,40,50},{200,300,50,30},{500,300,60,40},{700,200,20,60}};
    private String[] enemiesName = {"Goblin","Slime","Gnomo","Blaze","Ender","Zombie","Skeleton","Wither"};

    //Inventory
    private List<IWeapon> inventory = new ArrayList<>();
    private int nInventory;

    public GameController(){
        setInventory();
        setPlayersStat();
    }

    public void playGame(){
        selectionPlayer();
        selectionEnemies();
        waitTurns();
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
            int[] stat = enemiesStat[new Random().nextInt(enemiesStat.length)];
            String name = enemiesName[new Random().nextInt(enemiesName.length)];
            enemies[i] = new Enemy(name,turnsQueue,stat[0],stat[1],stat[2],stat[3]);
        }
    }

    /**
     * Set the initials weapons in the inventory
     */
    public void setInventory(){
        inventory.add(new Swords("Sword1",70,60));
        inventory.add(new Swords("Sword2", 100, 100));
        inventory.add(new Staffs("Staff1", 50, 40, 70));
        inventory.add(new Staffs("Staff2", 30, 20, 60));
        inventory.add(new Axes("Axe1", 90, 80));
        inventory.add(new Axes("Axe2", 120, 12));
        inventory.add(new Bows("Bow1", 70, 80));
        inventory.add(new Bows("Bow2", 90, 100));
        inventory.add(new Knives("Knife1", 40, 20));
        inventory.add(new Knives("Knife2", 50, 30));

        this.nInventory = 10;
    }

    /**
     * Set the initials stats available of players
     */
    public void setPlayersStat(){
        playersStat.add(new int[]{1,400,150,-1});
        playersStat.add(new int[]{1,300,250,-1});
        playersStat.add(new int[]{2,500,50,-1});
        playersStat.add(new int[]{2,400,150,-1});
        playersStat.add(new int[]{3,250,250,200});
        playersStat.add(new int[]{3,300,300,100});
        playersStat.add(new int[]{4,400,150,150});
        playersStat.add(new int[]{4,300,240,160});
        playersStat.add(new int[]{5,200,350,-1});
        playersStat.add(new int[]{5,350,200,-1});

        this.nPlayers = 10;
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

    /*public String tableFormat(int nRow, int nCol){
        String div = "|----"+("|"+("-".repeat(16))).repeat(nCol)+"|";
        String table = div+"\n| %-3s"+"| %-15s".repeat(nCol)+"|\n"+div+"\n";
        table+=("| %-3d|"+" %-15s|".repeat(nCol)+"\n").repeat(nRow)+div+"\n";
        return table;
    }*/


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
        gm.playGame();
    }

}
