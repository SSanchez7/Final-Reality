package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.model.character.ICharacter;

public class EnemyTurnPhase extends Phase {

    public void toTurnsPhase(){
        this.changePhase(new TurnsPhase());
    }

    @Override
    public boolean isInEnemyTurnPhase() {return true;}

    @Override
    public void characterTurn(ICharacter character){
        System.out.println("Enemyyyy");
    }
}
