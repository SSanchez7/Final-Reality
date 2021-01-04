package com.github.ssanchez7.finalreality.gui;

import com.github.ssanchez7.finalreality.controller.GameController;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidEquipmentException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidSelectionPlayerException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidTransitionException;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.IPlayer;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for the application.
 * <p>
 * GUI for the game FINAL REALITY, that shows all information for a correct gameplay
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class FinalReality extends Application {

    private final GameController controller = new GameController(5);
    private final Group root = new Group();

    /**
     *  List of Radio Button for player, weapon and enemies and theirs ToggleGroup respectively.
     */
    private List<RadioButton> buttonListPlayer = new ArrayList<>();
    private List<RadioButton> buttonListWeapon = new ArrayList<>();
    private List<RadioButton> buttonListEnemy = new ArrayList<>();
    private final ToggleGroup playerListGroup = new ToggleGroup();
    private final ToggleGroup weaponListGroup = new ToggleGroup();
    private final ToggleGroup enemyListGroup = new ToggleGroup();

    /**
     * List of players of the party and enemies for the battle "scene"
     */
    private List<Label> labelPlayerList = new ArrayList<>();
    private List<Label> labelEnemyList = new ArrayList<>();

    /**
     * Groups that holds items for selection player, selection weapon, selection enemy, battle, and title "scenes" respectively.
     */
    private final Group selectionPlayer = new Group();
    private final Group selectionWeapon = new Group();
    private final Group selectionEnemy = new Group();
    private final Group turns = new Group();
    private final Group title = new Group();

    /**
     * Buttons, Labels and Text Field that need to be known throughout the class
     */
    private final TextField name = new TextField();
    private final Button chooseWeapon = new Button(); //Button in selectionWeapon
    private final Button chooseEnemy = new Button(); //Button in selectionEnemy
    private final Button changeWeapon = new Button(); //Button in turns
    private final Button attack = new Button();
    private final Label playing = createLabel(turns, 10,280);
    private final Label finalMsg = createLabel(turns, 500,500);
    private final Label infoWeapon = createLabel(turns, 10,250);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Final reality");
        primaryStage.setResizable(false);
        Scene scene = createScene();
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Create the scene and shows the info for a "title phase"
     */
    private Scene createScene() {
        Scene scene = new Scene(root, 1280, 720);
        setPredefinedStats();
        root.getChildren().add(title);
        Label titleLabel = createLabel(title, 250,100);
        titleLabel.setFont(Font.font("consolas",100));
        titleLabel.setText("Final Reality");
        Button start = createButton(title, 500,500);
        start.setText("START");
        start.setOnAction(event -> {
            showSelectionPlayer();
            showSelectionWeapon(selectionWeapon);
            root.getChildren().remove(title);
            root.getChildren().add(selectionPlayer);
            controller.toSelectionPlayerPhase();
            startAnimator();
        });
        return scene;
    }

    /**
     * Starts the Animator with the information depending on the phase
     */
    private void startAnimator() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(controller.isInSelectionPlayerPhase()){
                    startAnimatorSelectionPlayer();
                }
                if(controller.isInSelectionWeaponPhase()){
                    startAnimatorSelectionWeapon();
                }
                if(controller.isInTurnsPhase()){
                    startAnimatorBattle();
                    startAnimatorTurns();
                }
                if(controller.isInSelectionAttackPhase()){
                    startAnimatorSelectionEnemy();
                }
            }
        };
        timer.start();
    }

    /**
     * Animator for a Selection Player phase, update information from the list of available players stats.
     */
    private void startAnimatorSelectionPlayer(){
        List<String[]> players = controller.getPlayersStat();
        for (int i = 0; i < controller.getnPlayers(); i++) {
            RadioButton button = buttonListPlayer.get(i);
            String[] item = players.get(i);
            String str = String.format("%-3d|" + (" %-15s|".repeat(4)),
                    (i + 1), item[1], item[2], item[3], ((item[4] == null) ? "" : item[4]));
            button.setText(str);
        }
        if(controller.getnParty()==controller.getnMaxParty()){
            for(int i=0; i<controller.getnMaxEnemies(); i++) {
                controller.selectionEnemy();
            }
            controller.initialWaitTurns(400);
            showTurns();
            showSelectionEnemy();
            root.getChildren().remove(selectionPlayer);
            root.getChildren().add(turns);
            controller.toTurnsPhase();
        }
    }

    /**
     * Animator for a Selection Weapon phase, update information from the list of weapons from the inventory.
     */
    private void startAnimatorSelectionWeapon(){
        for(int i=0; i<controller.getnInventory();i++){
            RadioButton button = buttonListWeapon.get(i);
            List<String> item = controller.getValues(controller.getInventory().get(i));
            String str = String.format("%-3d|",(i+1));
            for(String s : item){
                str += String.format(" %-15s|",s);
            }
            button.setText(str);
        }
    }

    /**
     * Animator for a turns phase, update information from the list of players of the party an enemies.
     */
    private void startAnimatorBattle() {
        for (int i = 0; i < controller.getnParty(); i++) {
            Label label = labelPlayerList.get(i);
            List<String> item = controller.getValues(controller.getParty().get(i));
            String str = String.format("%-3d|", (i + 1));
            if (item.get(1).equals("0")) {
                label.setDisable(true);
            }
            for (String s : item) {
                str += String.format(" %-15s|", s);
            }
            label.setText(str);
        }
        for (int i = 0; i < controller.getnEnemies(); i++) {
            Label label = labelEnemyList.get(i);
            List<String> item = controller.getValues(controller.getEnemies().get(i));
            String str = String.format("%-3d|", (i + 1));
            if (item.get(1).equals("0")) {
                label.setDisable(true);
            }
            for (String s : item) {
                str += String.format(" %-15s|", s);
            }
            label.setText(str);
        }
    }

    /**
     * Animator for a turns phase, update information of actual character turn.
     */
    private void startAnimatorTurns(){
        attack.setDisable(true);
        changeWeapon.setDisable(true);
        if(controller.isInGame()) {
            controller.nextTurn(0);
            ICharacter character = controller.getCurrentCharacter();
            playing.setText("Playing... " + character.getValues().get(0));


            if(controller.isInPlayerTurnPhase()) {
                attack.setDisable(false);
                changeWeapon.setDisable(false);
                actualWeapon((IPlayer) character);
            }
            changeWeapon.setOnAction(event -> {
                root.getChildren().remove(turns);
                root.getChildren().add(selectionWeapon);
                controller.canChooseAWeapon();
                chooseWeapon.setOnAction(event2 -> {
                    try {
                        chooseAWeapon((IPlayer) character, turns);
                        controller.toPlayerTurnPhase();
                        startAnimatorBattle();
                        actualWeapon((IPlayer) character);
                    }catch (InvalidEquipmentException e){
                        e.printStackTrace();
                    }
                });
            });
            attack.setOnAction(event -> {
                root.getChildren().remove(turns);
                root.getChildren().add(selectionEnemy);
                controller.canAttack();
                chooseEnemy.setOnAction(event2 -> {
                    chooseAnEnemy((IPlayer) character);
                });
            });
            if (controller.isInEnemyTurnPhase()) {
                try {
                    controller.canAttack();
                    controller.randomEnemyAttack(character);

                } catch (InvalidMovementException e) {
                    e.printStackTrace();
                }
            }
        }else{
            controller.toEndPhase();
            attack.setDisable(true);
            changeWeapon.setDisable(true);
            finalMsg.setFont(Font.font("consolas",50));
            if(controller.isWin()) {
                finalMsg.setLayoutX(200);
                finalMsg.setText("WINNER WINNER, CHICKEN DINNER");
            }
            if(controller.isLose()){
                finalMsg.setText("YOU DIED");
            }
        }
    }

    /**
     * shows info of the character equipped weapon in the turns phase.
     */
    private void actualWeapon(IPlayer character){
        List<String> item = controller.getWeaponValues(character);
        String str = "|";
        for (String s : item) {
            str += String.format(" %-15s|", s);
        }
        infoWeapon.setText(str);
    }

    /**
     * Animator for a Selection Attack phase, update information from the list of enemies.
     */
    private void startAnimatorSelectionEnemy() {
        for(int i=0; i<controller.getnEnemies();i++){
            RadioButton button = buttonListEnemy.get(i);
            List<String> item = controller.getValues(controller.getEnemies().get(i));
            if(item.get(1).equals("0")){
                button.setDisable(true);
            }
            String str = String.format("%-3d|",(i+1));
            for(String s : item){
                str += String.format(" %-15s|",s);
            }
            button.setText(str);
        }
    }

    /**
     * Shows information of the player selection (list of players, button and textField for the player name)
     */
    private void showSelectionPlayer(){
        Label titleLabel = createLabel(selectionPlayer,30, 10);
        titleLabel.setText(String.format("%-3s" + "| %-15s".repeat(4) + "|\n",
                "Id", "Class", "Hp", "DefensePoints", "Mana"));
        titleLabel.setFont(Font.font("consolas"));
        for (int i = 0; i < controller.getnPlayers(); i++) {
            RadioButton button = createRadioButton(selectionPlayer, playerListGroup, 10, 40 + 30 * i);
            button.setFont(Font.font("consolas"));
            buttonListPlayer.add(button);
        }
        buttonListPlayer.get(0).setSelected(true);
        Button choosePlayer = createButton(selectionPlayer, 400, controller.getnPlayers()*30+40);
        choosePlayer.setText("Choose Player");
        Label insertName = createLabel(selectionPlayer,10,controller.getnPlayers()*30+40);
        insertName.setText("Insert Name");

        name.setLayoutX(80);
        name.setLayoutY(controller.getnPlayers()*30+40);
        selectionPlayer.getChildren().add(name);
        choosePlayer.setOnAction(event -> {
            chooseAPlayer();
        });
    }

    /**
     * Shows information of the weapon selection (list of weapons and a button to choose it)
     */
    private void showSelectionWeapon(Group group){
        Label titleLabel = createLabel(group,30, 10);
        titleLabel.setText(String.format("%-3s"+"| %-15s".repeat(4)+"|\n",
                "Id","Name","Damage","Weight","MagicDamage"));
        titleLabel.setFont(Font.font("consolas"));
        for (int i = 0; i < controller.getnInventory(); i++) {
            RadioButton button = createRadioButton(group, weaponListGroup, 10, 40 + 30 * i);
            button.setFont(Font.font("consolas"));
            buttonListWeapon.add(button);
        }
        buttonListWeapon.get(0).setSelected(true);

        chooseWeapon.setText("Choose Weapon");
        chooseWeapon.setLayoutX(400);
        chooseWeapon.setLayoutY(controller.getnInventory()*30+40);
        selectionWeapon.getChildren().add(chooseWeapon);
    }


    /**
     * Shows information of the battle (list of players of the party, list of enemies and buttons for change the weapon and attack)
     */
    private void showTurns(){
        Label playerLabel = createLabel(turns,10, 10);
        playerLabel.setText(String.format("PARTY:\n"+"%-3s" + "| %-15s".repeat(5) + "|\n",
                "Id","Name","Hp","DefensePoints","NameWeapon","Mana"));
        playerLabel.setFont(Font.font("consolas"));
        for (int i = 0; i < controller.getnParty(); i++) {
            Label label = createLabel(turns, 10, 40 + 30 * i);
            label.setFont(Font.font("consolas"));
            labelPlayerList.add(label);
        }

        Label enemyLabel = createLabel(turns,650, 10);
        enemyLabel.setText(String.format("ENEMIES:\n"+"%-3s" + "| %-15s".repeat(5) + "|\n",
                "Id","Name","Hp","DefensePoints","AttackPoints","Weight"));
        enemyLabel.setFont(Font.font("consolas"));
        System.out.println(controller.getnEnemies());
        for (int i = 0; i < controller.getnEnemies(); i++) {
            Label label = createLabel(turns, 650, 40 + 30 * i);
            label.setFont(Font.font("consolas"));
            labelEnemyList.add(label);
        }

        Label titleLabel = createLabel(turns,10, 220);
        titleLabel.setText(String.format("ACTUAL EQUIPPED WEAPON:\n"+"| %-15s".repeat(4)+"|\n",
                "Name","Damage","Weight","MagicDamage"));
        titleLabel.setFont(Font.font("consolas"));
        infoWeapon.setFont(Font.font("consolas"));

        changeWeapon.setText("Change Weapon");
        changeWeapon.setLayoutX(300);
        changeWeapon.setLayoutY(controller.getnParty()*30+40);
        turns.getChildren().add(changeWeapon);

        attack.setText("Attack");
        attack.setLayoutX(500);
        attack.setLayoutY(controller.getnParty()*30+40);
        turns.getChildren().add(attack);
        playing.setFont(Font.font("consolas",20));
    }

    /**
     * Shows information of the enemy selection for attack (list of enemies and a button for choose it)
     */
    private void showSelectionEnemy(){
        Label enemyLabel = createLabel(selectionEnemy,30, 10);
        enemyLabel.setText(String.format("%-3s" + "| %-15s".repeat(5) + "|\n",
                "Id","Name","Hp","DefensePoints","AttackPoints","Weight"));
        enemyLabel.setFont(Font.font("consolas"));
        System.out.println(controller.getnEnemies());
        for (int i = 0; i < controller.getnEnemies(); i++) {
            RadioButton button = createRadioButton(selectionEnemy, enemyListGroup, 10, 40 + 30 * i);
            button.setFont(Font.font("consolas"));
            buttonListEnemy.add(button);
        }
        buttonListEnemy.get(0).setSelected(true);
        chooseEnemy.setText("Choose Enemy");
        chooseEnemy.setLayoutX(400);
        chooseEnemy.setLayoutY(controller.getnEnemies()*30+40);
        selectionEnemy.getChildren().add(chooseEnemy);
    }

    /**
     * selects the chosen player and change to the selectionWeapon "scene"
     */
    private void chooseAPlayer() {
        try {
            int k = Character.getNumericValue((((RadioButton) playerListGroup.getSelectedToggle()).getText()).charAt(0)) - 1;
            IPlayer player = controller.selectionPlayer(k, name.getText());
            selectionPlayer.getChildren().remove(buttonListPlayer.get(controller.getnPlayers() - 1 + 1));
            name.setText("");
            chooseWeapon.setOnAction(event -> {
                try {
                    chooseAWeapon(player, selectionPlayer);
                    controller.canChooseAPlayer();
                }catch (InvalidEquipmentException e){
                    e.printStackTrace();
                }
            });
            root.getChildren().remove(selectionPlayer);
            root.getChildren().add(selectionWeapon);
            controller.canChooseAWeapon();
        }catch (InvalidSelectionPlayerException e){
            e.printStackTrace();
        }
    }

    /**
     * equips the chosen weapon and change to the "group" "scene"
     */
    private void chooseAWeapon(IPlayer player, Group group) throws InvalidEquipmentException{
        int k = Character.getNumericValue((((RadioButton) weaponListGroup.getSelectedToggle()).getText()).charAt(0)) - 1;
        if(controller.equipWeapon(player, k)) {
            selectionWeapon.getChildren().remove(buttonListWeapon.get(controller.getnInventory() - 1 + 1));
        }
        root.getChildren().remove(selectionWeapon);
        root.getChildren().add(group);
    }

    /**
     * attacks the chosen enemy and change to the turns "scene"
     */
    private void chooseAnEnemy(IPlayer player){
        try {
            int k = Character.getNumericValue((((RadioButton) enemyListGroup.getSelectedToggle()).getText()).charAt(0)) - 1;
            controller.tryAttack(player, controller.getEnemies().get(k));
            root.getChildren().remove(selectionEnemy);
            root.getChildren().add(turns);
        }catch (InvalidMovementException e){
            e.printStackTrace();
        }
    }

    /**
     * create a label
     * @param group:
     *             group to which it belongs
     * @param xPos:
     *            position in the X axis
     * @param yPos:
     *            position in the Y axis
     * @return the label created.
     */
    private Label createLabel(Group group, int xPos, int yPos) {
        Label label = new Label();
        label.setLayoutX(xPos);
        label.setLayoutY(yPos);
        group.getChildren().add(label);
        return label;
    }

    /**
     * create a button
     * @param group:
     *             group to which it belongs
     * @param xPos:
     *            position in the X axis
     * @param yPos:
     *            position in the Y axis
     * @return the button created.
     */
    private Button createButton(Group group, int xPos, int yPos) {
        Button button = new Button();
        button.setLayoutX(xPos);
        button.setLayoutY(yPos);
        group.getChildren().add(button);
        return button;
    }

    /**
     * create a radio button
     * @param group:
     *             group to which it belongs
     * @param toggleGroup:
     *             group of radio buttons to which it belongs
     * @param xPos:
     *            position in the X axis
     * @param yPos:
     *            position in the Y axis
     * @return the radio button created.
     */
    private RadioButton createRadioButton(Group group, ToggleGroup toggleGroup, int xPos, int yPos) {
        RadioButton radioButton = new RadioButton();
        radioButton.setLayoutX(xPos);
        radioButton.setLayoutY(yPos);
        radioButton.setToggleGroup(toggleGroup);
        group.getChildren().add(radioButton);
        return radioButton;
    }

    /**
     * set predefined characters, weapons and enemies to choose from
     */
    public void setPredefinedStats(){
        controller.createKnightStat(30, 100);
        controller.createThiefStat(20, 140);
        controller.createBlackMageStat(25, 130, 30);
        controller.createEngineerStat(15, 60);
        controller.createWhiteMageStat(10, 40, 20);
        controller.createWhiteMageStat(10, 40, 20);
        controller.createThiefStat(20, 140);
        controller.createBlackMageStat(25, 130, 30);
        controller.createWhiteMageStat(10, 40, 20);
        controller.createThiefStat(20, 140);
        controller.createBlackMageStat(25, 130, 30);

        controller.createAxe("AXE", 130,40);
        controller.createKnife("KNIFE", 75,12);
        controller.createStaff("STAFF1", 664, 21,98);
        controller.createAxe("AXE", 140, 20);
        controller.createStaff("STAFF2", 664, 21,98);
        controller.createSword("SWORD", 123,40);
        controller.createBow("BOW", 123,67);
        controller.createStaff("STAFF2", 664, 21,98);
        controller.createBow("BOW", 123,10);

        controller.createEnemyName("Goblin");
        controller.createEnemyName("Spider");
        controller.createEnemyName("Ghost");
        controller.createEnemyName("Skeleton");
        controller.createEnemyName("Zombie");

        controller.createEnemyStat(10,130,120,50);
        controller.createEnemyStat(50,100,100,56);
        controller.createEnemyStat(187,40,160,23);
        controller.createEnemyStat(123,90,60,80);
        controller.createEnemyStat(53,70,190,20);
        controller.createEnemyStat(90,127,40,40);
    }

}


