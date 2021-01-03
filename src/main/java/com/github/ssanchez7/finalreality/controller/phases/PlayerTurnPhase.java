package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;

public class PlayerTurnPhase extends Phase {

    public PlayerTurnPhase(){
        canAttack = true;
        canChooseWeapon = true;
    }

    @Override
    public void toSelectionAttackPhase(){
        this.changePhase(new SelectionAttackPhase());
    }

    @Override
    public void toSelectionWeaponPhase(){
        this.changePhase(new SelectionWeaponPhase());
    }

    @Override
    public boolean isInPlayerTurnPhase(){return true;}

    @Override
    public void canAttack() throws InvalidMovementException {
        super.canAttack();
        toSelectionAttackPhase();
    }

    @Override
    public void canChooseAWeapon() throws InvalidMovementException {
        super.canChooseAWeapon();
        toSelectionWeaponPhase();
    }


}
