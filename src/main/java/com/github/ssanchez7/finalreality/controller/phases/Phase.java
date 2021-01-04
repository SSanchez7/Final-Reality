package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.controller.GameController;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidTransitionException;
import com.github.ssanchez7.finalreality.model.character.ICharacter;

/**
 * hold all information from the phases from the game Final Reality
 */
public class Phase {
    protected GameController controller;

    protected boolean canChooseWeapon = false;
    protected boolean canChoosePlayer = false;
    protected boolean canAttack = false;

    /**
     * set the context of the phase (controller)
     */
    public void setController(GameController controller){
        this.controller = controller;
    }

    /**
     * change the phase
     */
    public void changePhase(Phase phase){
        controller.setPhase(phase);
    }

    /**
     * transitions to title phase
     */
    public void toDrawTitlePhase() throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to draw title phase");
    }
    /**
     * transitions to a turn of a player phase
     */
    public void toPlayerTurnPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to player turn phase");
    }
    /**
     * transitions to a turn of a enemy phase
     */
    public void toEnemyTurnPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to enemy turn phase");
    }
    /**
     * transitions to Selection attack phase
     */
    public void toSelectionAttackPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to selection attack phase");
    }
    /**
     * transitions to Selection player phase
     */
    public void toSelectionPlayerPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to selection player phase");
    }
    /**
     * transitions to Selection weapon phase
     */
    public void toSelectionWeaponPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to selection weapon phase");
    }
    /**
     * transitions to turns phase
     */
    public void toTurnsPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to turns phase");
    }
    /**
     * transitions to End phase.
     */
    public void toEndPhase() throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to end phase");
    }

    /**
     * Returns if the game is in a respectively phase.
     */
    public boolean isInDrawTitlePhase(){return false;}
    public boolean isInSelectionPlayerPhase(){return false;}
    public boolean isInSelectionWeaponPhase(){return false;}
    public boolean isInSelectionAttackPhase(){return false;}
    public boolean isInPlayerTurnPhase(){return false;}
    public boolean isInEnemyTurnPhase(){return false;}
    public boolean isInTurnsPhase(){return false;}
    public boolean isInEndPhase(){return false;}


    /**
     * change to "SelectionWeaponPhase" depending on whether or not a weapon can be equipped
     */
    public void canChooseAWeapon() throws InvalidMovementException {
        if (!canChooseWeapon) {
            throw new InvalidMovementException("You can't choose a weapon now");
        }
    }

    /**
     * change to "SelectionPlayerPhase" depending on whether or not a player can be selected
     */
    public void canChooseAPlayer() throws InvalidMovementException{
        if (!canChoosePlayer) {
            throw new InvalidMovementException("You can't choose a player now");
        }
    }

    /**
     * change to "SelectionAttackPhase" depending on whether or not the character can attack.
     */
    public void canAttack() throws InvalidMovementException{
        if (!canAttack) {
            throw new InvalidMovementException("You can't attack now");
        }
    }

    /**
     * derives a specific phase depending on the character type.
     */
    public void toCharacterTurn(ICharacter character) throws InvalidMovementException{
        throw new InvalidMovementException(character+" isn't a possible character");
    }

    /**
     * makes an attack depending on the phase of the game.
     */
    public void attack(ICharacter attacker, ICharacter defender) throws InvalidMovementException{
        throw new InvalidMovementException("You cant attack now");
    }


}
