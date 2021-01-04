package com.github.ssanchez7.finalreality.controller;

import com.github.ssanchez7.finalreality.controller.exceptions.InvalidEquipmentException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidSelectionPlayerException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidTransitionException;
import com.github.ssanchez7.finalreality.controller.phases.*;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.ICharacter;
import com.github.ssanchez7.finalreality.model.character.player.IPlayer;
import com.github.ssanchez7.finalreality.model.character.player.Knights;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * containing the tests for the phases of the game "Final reality".
 *
 * @author Samuel Sanchez Parra
 */
public class PhasesTest {

    private GameController gc2;

    private static final int HP_MAX = 300;
    private static final int WEAPON_WEIGHT = 7;

    private static final int ENEMY_ATTACK_POINTS = 30;
    private static final int ENEMY_DEFENSE_POINTS = 10;
    private static final int ENEMY_WEIGHT = 10;
    private static final String ENEMY_NAME = "enemy";

    private static BlockingQueue<ICharacter> turns;

    @BeforeEach
    void setUp() {
        gc2 = new GameController(2);
        turns = new LinkedBlockingQueue<>();
    }

    /**
     * check a correct transition of the phases from "Draw title phase"
     */
    @Test
    public void drawTitlePhaseTest() {
        gc2.setPhase(new DrawTitlePhase());
        assertTrue(gc2.isInDrawTitlePhase());
        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionWeaponPhase();
        });assertFalse(gc2.isInSelectionWeaponPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toTurnsPhase();
        });assertFalse(gc2.isInTurnsPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionAttackPhase();
        });assertFalse(gc2.isInSelectionAttackPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toPlayerTurnPhase();
        });assertFalse(gc2.isInPlayerTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEnemyTurnPhase();
        });assertFalse(gc2.isInEnemyTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEndPhase();
        });assertFalse(gc2.isInEndPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toDrawTitlePhase();
        });//assertFalse(gc2.isInDrawTitlePhase());

        gc2.canChooseAPlayer();
        assertTrue(gc2.isInSelectionPlayerPhase());

    }

    /**
     * check a correct transition of the phases from "Selection Player Phase"
     */
    @Test
    public void selectionPlayerPhaseTest() throws InvalidSelectionPlayerException, InvalidEquipmentException {
        gc2.setPhase(new SelectionPlayerPhase());
        assertTrue(gc2.isInSelectionPlayerPhase());
        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionPlayerPhase();
        });//assertFalse(gc2.isInSelectionPlayerPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionAttackPhase();
        });assertFalse(gc2.isInSelectionAttackPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toPlayerTurnPhase();
        });assertFalse(gc2.isInPlayerTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEnemyTurnPhase();
        });assertFalse(gc2.isInEnemyTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEndPhase();
        });assertFalse(gc2.isInEndPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toDrawTitlePhase();
        });assertFalse(gc2.isInDrawTitlePhase());

        gc2.toSelectionWeaponPhase();
        assertTrue(gc2.isInSelectionWeaponPhase());

        gc2.setPhase(new SelectionPlayerPhase());
        assertTrue(gc2.isInSelectionPlayerPhase());
        gc2.toTurnsPhase();
        assertFalse(gc2.isInTurnsPhase());

        gc2.createKnightStat(HP_MAX, 0);
        gc2.createThiefStat(HP_MAX, 0);
        gc2.createSword("Sword", HP_MAX, WEAPON_WEIGHT);
        gc2.createKnife("Knife", HP_MAX, WEAPON_WEIGHT);
        for(int i=0; i<gc2.getnMaxParty(); i++){
            IPlayer player = gc2.selectionPlayer(0,"player"+(i+1));
            gc2.canChooseAWeapon();

            gc2.equipWeapon(player, 0);
            gc2.canChooseAPlayer();
        }
        gc2.toTurnsPhase();
        assertTrue(gc2.isInTurnsPhase());
    }

    /**
     * check a correct transition of the phases from "Selection Weapon Phase"
     */
    @Test
    public void selectionWeaponPhase() {
        gc2.setPhase(new SelectionWeaponPhase());
        assertTrue(gc2.isInSelectionWeaponPhase());
        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionWeaponPhase();
        });//assertFalse(gc2.isInSelectionWeaponPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toTurnsPhase();
        });assertFalse(gc2.isInTurnsPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionAttackPhase();
        });assertFalse(gc2.isInSelectionAttackPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEnemyTurnPhase();
        });assertFalse(gc2.isInEnemyTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEndPhase();
        });assertFalse(gc2.isInEndPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toDrawTitlePhase();
        });assertFalse(gc2.isInDrawTitlePhase());

        gc2.toSelectionPlayerPhase();
        assertTrue(gc2.isInSelectionPlayerPhase());

        gc2.setPhase(new SelectionWeaponPhase());
        assertTrue(gc2.isInSelectionWeaponPhase());
        gc2.toPlayerTurnPhase();
        assertTrue(gc2.isInPlayerTurnPhase());
    }

    /**
     * check a correct transition of the phases from "turn phase"
     */
    @Test
    public void turnsPhaseTest() {
        gc2.setPhase(new TurnsPhase());
        assertTrue(gc2.isInTurnsPhase());
        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionWeaponPhase();
        });assertFalse(gc2.isInSelectionWeaponPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toTurnsPhase();
        });//assertFalse(gc2.isInTurnsPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionAttackPhase();
        });assertFalse(gc2.isInSelectionAttackPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionPlayerPhase();
        });assertFalse(gc2.isInSelectionPlayerPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toDrawTitlePhase();
        });assertFalse(gc2.isInDrawTitlePhase());

        gc2.characterTurn(new Enemy(ENEMY_NAME, turns, HP_MAX, ENEMY_DEFENSE_POINTS, ENEMY_ATTACK_POINTS, ENEMY_WEIGHT));
        assertTrue(gc2.isInEnemyTurnPhase());

        gc2.setPhase(new TurnsPhase());
        assertTrue(gc2.isInTurnsPhase());
        gc2.characterTurn(new Knights("Knight", turns, HP_MAX, ENEMY_DEFENSE_POINTS));
        assertTrue(gc2.isInPlayerTurnPhase());

        gc2.setPhase(new TurnsPhase());
        assertTrue(gc2.isInTurnsPhase());
        gc2.toEndPhase();
        assertTrue(gc2.isInEndPhase());
    }

    /**
     * check a correct transition of the phases from "Player turn phase"
     */
    @Test
    public void playerTurnPhaseTest() {
        gc2.setPhase(new PlayerTurnPhase());
        assertTrue(gc2.isInPlayerTurnPhase());
        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionPlayerPhase();
        });assertFalse(gc2.isInSelectionPlayerPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toTurnsPhase();
        });assertFalse(gc2.isInTurnsPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEndPhase();
        });assertFalse(gc2.isInEndPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEnemyTurnPhase();
        });assertFalse(gc2.isInEnemyTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toDrawTitlePhase();
        });assertFalse(gc2.isInDrawTitlePhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toPlayerTurnPhase();
        });//assertFalse(gc2.isInPlayerTurnPhase());

        gc2.canChooseAWeapon();
        assertTrue(gc2.isInSelectionWeaponPhase());

        gc2.setPhase(new PlayerTurnPhase());
        assertTrue(gc2.isInPlayerTurnPhase());
        gc2.canAttack();
        assertTrue(gc2.isInSelectionAttackPhase());
    }

    /**
     * check a correct transition of the phases from "Enemy Turn Phase"
     */
    @Test
    public void enemyTurnPhaseTest() {
        gc2.setPhase(new EnemyTurnPhase());
        assertTrue(gc2.isInEnemyTurnPhase());
        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionPlayerPhase();
        });assertFalse(gc2.isInSelectionPlayerPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionWeaponPhase();
        });assertFalse(gc2.isInSelectionWeaponPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toTurnsPhase();
        });assertFalse(gc2.isInTurnsPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEndPhase();
        });assertFalse(gc2.isInEndPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEnemyTurnPhase();
        });//assertFalse(gc2.isInEnemyTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toDrawTitlePhase();
        });assertFalse(gc2.isInDrawTitlePhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toPlayerTurnPhase();
        });assertFalse(gc2.isInPlayerTurnPhase());

        gc2.canAttack();
        assertTrue(gc2.isInSelectionAttackPhase());
    }

    /**
     * check a correct transition of the phases from "Selection Attack Phase"
     */
    @Test
    public void selectionAttackPhaseTest() {
        gc2.setPhase(new SelectionAttackPhase());
        assertTrue(gc2.isInSelectionAttackPhase());
        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionPlayerPhase();
        });assertFalse(gc2.isInSelectionPlayerPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionWeaponPhase();
        });assertFalse(gc2.isInSelectionWeaponPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionAttackPhase();
        });//assertFalse(gc2.isInSelectionAttackPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEndPhase();
        });assertFalse(gc2.isInEndPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEnemyTurnPhase();
        });assertFalse(gc2.isInEnemyTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toDrawTitlePhase();
        });assertFalse(gc2.isInDrawTitlePhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toPlayerTurnPhase();
        });assertFalse(gc2.isInPlayerTurnPhase());

        gc2.toTurnsPhase();
        assertTrue(gc2.isInTurnsPhase());
    }

    /**
     * check a correct transition of the phases from "End Phase"
     */
    @Test
    public void endPhaseTest() {
        gc2.setPhase(new EndPhase());
        assertTrue(gc2.isInEndPhase());
        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionWeaponPhase();
        });assertFalse(gc2.isInSelectionWeaponPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toTurnsPhase();
        });assertFalse(gc2.isInTurnsPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionAttackPhase();
        });assertFalse(gc2.isInSelectionAttackPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toPlayerTurnPhase();
        });assertFalse(gc2.isInPlayerTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEnemyTurnPhase();
        });assertFalse(gc2.isInEnemyTurnPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toEndPhase();
        });//assertFalse(gc2.isInEndPhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toDrawTitlePhase();
        });assertFalse(gc2.isInDrawTitlePhase());

        assertThrows(InvalidTransitionException.class, () -> {
            gc2.getPhase().toSelectionPlayerPhase();
        });assertFalse(gc2.isInSelectionPlayerPhase());
    }

}
