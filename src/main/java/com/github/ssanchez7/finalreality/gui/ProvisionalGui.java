package com.github.ssanchez7.finalreality.gui;

import com.github.ssanchez7.finalreality.model.Iitem;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.IPlayer;
import com.github.ssanchez7.finalreality.model.weapon.IWeapon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;


/**
 * A provisional class for GUI.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class ProvisionalGui {

    // Methods that make questions to the user and return the answer. Those methods will be implemented in a new gui class.
    /**
     * Gets the player's index and player's name from the user
     */
    public String[] getIndexPlayerUser(BufferedReader in) {
        try {
            BufferedReader entry = in;
            System.out.print("Choose the player: ");
            String indexPlayers = entry.readLine();
            System.out.print("What is her/his name?: ");
            String name = entry.readLine();
            return new String[]{indexPlayers, name};
        } catch (IOException e) {
        }
        return null;
    }
    /**
     * Gets the index of the inventory from the user
     */
    public String getIndexInventoryUser(String name, BufferedReader in) {
        try {
            BufferedReader entry = in;
            System.out.format("Choose the weapon for %s: ", name);
            String indexInventory = entry.readLine();
            return indexInventory;
        }catch(IOException e){
            return null;
        }

    }
    // Methods that show in a table the different lists of elements. Those methods will be implemented in a new gui class.
    /**
     * Shows in a table the features of the team's inventory
     */
    public void showInventory(List<IWeapon> inventory, int nInventory){
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
    public void showEnemies(List<Enemy> enemies, int nEnemies){
        int nCol = 5;
        String div = "|----"+("|"+("-".repeat(16))).repeat(nCol)+"|";
        System.out.println(div);
        System.out.format("| %-3s"+"| %-15s".repeat(nCol)+"|\n",
                "Id","Name","Hp","DefensePoints","AttackPoints","Weight");
        System.out.println(div);
        for(int i=0; i<nEnemies; i++){
            Enemy item = enemies.get(i);
            System.out.format("| %-3d|",(i+1));
            showLine(item, nCol);
        }
        System.out.println(div);
    }
    /**
     * Shows in a table the members of the party and their equipment
     */
    public void showParty(List<IPlayer> party, int nParty){
        int nCol = 5;
        String div = "|----"+("|"+("-".repeat(16))).repeat(nCol)+"|";
        System.out.println(div);
        System.out.format("| %-3s"+"| %-15s".repeat(nCol)+"|\n",
                "Id","Name","Hp","DefensePoints","NameWeapon","Mana");
        System.out.println(div);
        for(int i=0; i<nParty; i++){
            IPlayer item = party.get(i);
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
    public void showPlayers(List<int[]> playersStat, int nPlayers){
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
    /**
     * Shows the list of turns.
     */
    public void showTurns(BlockingQueue<ICharacter> turnsQueue){
        int nCol = 0;
        System.out.println();
        for(ICharacter item : turnsQueue){
            System.out.format(" %-1s <--",item.getName());
        }
    }
}
