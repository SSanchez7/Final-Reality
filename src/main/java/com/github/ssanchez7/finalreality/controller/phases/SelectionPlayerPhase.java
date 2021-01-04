package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.controller.exceptions.InvalidTransitionException;
import com.github.ssanchez7.finalreality.model.character.ICharacter;

public class SelectionPlayerPhase extends Phase{

    public SelectionPlayerPhase(){
        canChooseWeapon = true;
    }

    @Override
    public void toSelectionWeaponPhase(){
        this.changePhase(new SelectionWeaponPhase());
    }

    @Override
    public void toTurnsPhase(){
        if(controller.getnParty()==controller.getnMaxParty()) {
            this.changePhase(new TurnsPhase());
        }
    }

    @Override
    public boolean isInSelectionPlayerPhase(){return true;}

    @Override
    public void canChooseAWeapon() throws InvalidMovementException {
        super.canChooseAWeapon();
        toSelectionWeaponPhase();
    }

}
