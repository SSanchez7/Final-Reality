package com.github.ssanchez7.finalreality.gui;

import com.github.ssanchez7.finalreality.controller.GameController;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidEquipmentException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
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
 * <Complete here with the details of the implemented application>
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class FinalReality extends Application {

    private final GameController controller = new GameController();
    private final Group root = new Group();

    private List<RadioButton> buttonListPlayer = new ArrayList<>();
    private List<RadioButton> buttonListWeapon = new ArrayList<>();
    private List<RadioButton> buttonListEnemy = new ArrayList<>();
    private List<Label> labelPlayerList = new ArrayList<>();
    private List<Label> labelEnemyList = new ArrayList<>();

    private final ToggleGroup playerListGroup = new ToggleGroup();
    private final ToggleGroup weaponListGroup = new ToggleGroup();
    private final ToggleGroup enemyListGroup = new ToggleGroup();

    private final Group selectionPlayer = new Group();
    private final Group selectionWeapon = new Group();
    private final Group selectionEnemy = new Group();
    private final Group turns = new Group();
    private final Group title = new Group();

    private final TextField name = createTextField(selectionPlayer,80, controller.getnPlayers()*30+40);
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

    private Scene createScene() {
        Scene scene = new Scene(root, 1280, 720);

        root.getChildren().add(title);
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


    private void startAnimatorSelectionPlayer(){
        List<String[]> players = controller.getPlayersStat();
        for (int i = 0; i < controller.getnPlayers(); i++) {
            RadioButton button = buttonListPlayer.get(i);
            String[] item = players.get(i);
            String str = String.format("%-3d|" + (" %-15s|".repeat(4)),
                    (i + 1), item[1], item[2], item[3], ((item[4] == null) ? "" : item[4]));
            button.setText(str);
        }
        if(controller.getnParty()==5){
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
    private void startAnimatorTurns(){
        attack.setDisable(true);
        changeWeapon.setDisable(true);
        if(controller.isInGame()) {
            controller.nextTurn(0);
            ICharacter character = controller.getActualCharacter();
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
    private void actualWeapon(IPlayer character){
        List<String> item = controller.getWeaponValues(character);
        String str = "|";
        for (String s : item) {
            str += String.format(" %-15s|", s);
        }
        infoWeapon.setText(str);
    }

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
        choosePlayer.setOnAction(event -> {
            chooseAPlayer();
        });
    }

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
        enemyLabel.setText(String.format("ENEMIES_\n"+"%-3s" + "| %-15s".repeat(5) + "|\n",
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
        }catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }

    private void chooseAWeapon(IPlayer player, Group group) throws InvalidEquipmentException{
        int k = Character.getNumericValue((((RadioButton) weaponListGroup.getSelectedToggle()).getText()).charAt(0)) - 1;
        if(controller.equipWeapon(player, k)) {
            selectionWeapon.getChildren().remove(buttonListWeapon.get(controller.getnInventory() - 1 + 1));
        }
        root.getChildren().remove(selectionWeapon);
        root.getChildren().add(group);
    }

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

    private Label createLabel(Group group, int xPos, int yPos) {
        Label label = new Label();
        label.setLayoutX(xPos);
        label.setLayoutY(yPos);
        group.getChildren().add(label);
        return label;
    }

    private TextField createTextField(Group group, int xPos, int yPos) {
        TextField text = new TextField();
        text.setLayoutX(xPos);
        text.setLayoutY(yPos);
        group.getChildren().add(text);
        return text;
    }

    private Button createButton(Group group, int xPos, int yPos) {
        Button button = new Button();
        button.setLayoutX(xPos);
        button.setLayoutY(yPos);
        group.getChildren().add(button);
        return button;
    }

    private RadioButton createRadioButton(Group group, ToggleGroup toggleGroup, int xPos, int yPos) {
        RadioButton radioButton = new RadioButton();
        radioButton.setLayoutX(xPos);
        radioButton.setLayoutY(yPos);
        radioButton.setToggleGroup(toggleGroup);
        group.getChildren().add(radioButton);
        return radioButton;
    }

}


