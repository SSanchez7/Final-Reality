package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidTransitionException;

public class SelectionWeaponPhase extends Phase{

    public SelectionWeaponPhase(){
        canChoosePlayer = true;
    }

    @Override
    public void toSelectionPlayerPhase(){
        this.changePhase(new SelectionPlayerPhase());
    }

    @Override
    public void toSelectionAttackPhase(){
        this.changePhase(new SelectionAttackPhase());
    }

    @Override
    public boolean isInSelectionWeaponPhase(){return true;}

    public void canChooseAPlayer() throws InvalidMovementException {
        super.canChooseAPlayer();
        toSelectionPlayerPhase();
    }
}
