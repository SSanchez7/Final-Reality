package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.model.character.ICharacter;

public class PlayerTurnPhase extends Phase {

    @Override
    public void toSelectionAttackPhase(){ this.changePhase(new SelectionAttackPhase()); }

    @Override
    public void toSelectionWeaponPhase(){ this.changePhase(new SelectionWeaponPhase()); }

    @Override
    public boolean isInPlayerTurnPhase(){return true;}

    @Override
    public void characterTurn(ICharacter character){
        System.out.println("Playeeeeer");
    }
}
