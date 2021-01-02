package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.model.character.ICharacter;

public class SelectionAttackPhase extends Phase{

    @Override
    public void toTurnsPhase(){
        this.changePhase(new TurnsPhase());
    }

    @Override
    public boolean isInSelectionAttackPhase(){
        return true;
    }

    @Override
    public void attack(ICharacter attacker, ICharacter defender){
        controller.attack(attacker, defender);
        attacker.waitTurn();
        toTurnsPhase();
    }
}
