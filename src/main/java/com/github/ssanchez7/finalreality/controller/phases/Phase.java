package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.controller.GameController;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidTransitionException;
import com.github.ssanchez7.finalreality.model.character.ICharacter;

public class Phase {
    protected GameController controller;
    protected boolean canChooseWeapon = false;
    protected boolean canChoosePlayer = false;

    public void setController(GameController controller){
        this.controller = controller;
    }

    public void changePhase(Phase phase){
        controller.setPhase(phase);
    }


    public void toPlayerTurnPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to player turn phase");
    }
    public void toEnemyTurnPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to enemy turn phase");
    }
    public void toDrawTitlePhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to title phase");
    }
    public void toSelectionAttackPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to selection attack phase");
    }
    public void toSelectionPlayerPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to selection player phase");
    }
    public void toSelectionWeaponPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to selection weapon phase");
    }
    public void toTurnsPhase()throws InvalidTransitionException{
        throw new InvalidTransitionException("Cant change to turns phase");
    }

    public boolean isInSelectionPlayerPhase(){return false;}
    public boolean isInSelectionWeaponPhase(){return false;}
    public boolean isInPlayerTurnPhase(){return false;}
    public boolean isInEnemyTurnPhase(){return false;}
    public boolean isInTurnsPhase(){return false;}


    public void canChooseAWeapon() throws InvalidMovementException {
        if (!canChooseWeapon) {
            throw new InvalidMovementException("You can't choose a weapon now");
        }
    }

    public void canChooseAPlayer() throws InvalidMovementException{
        if (!canChoosePlayer) {
            throw new InvalidMovementException("You can't choose a player now");
        }
    }

    public void characterTurn(ICharacter character) throws  InvalidMovementException{
        throw new InvalidMovementException(character+" isn't a possible character");
    }
}
