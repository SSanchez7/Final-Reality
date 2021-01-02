package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.model.character.ICharacter;

public class EnemyTurnPhase extends Phase {

    public EnemyTurnPhase(){
        canAttack = true;
    }

    public void toSelectionAttackPhase(){
        this.changePhase(new SelectionAttackPhase());
    }

    @Override
    public boolean isInEnemyTurnPhase() {return true;}

    @Override
    public void canAttack() throws InvalidMovementException {
        super.canAttack();
        toSelectionAttackPhase();
    }

}
