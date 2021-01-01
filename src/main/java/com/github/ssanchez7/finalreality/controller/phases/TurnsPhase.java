package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;
import com.github.ssanchez7.finalreality.model.character.ICharacter;

public class TurnsPhase extends Phase{

    public void toPlayerTurnPhase(){
        this.changePhase(new PlayerTurnPhase());
    }
    public void toEnemyTurnPhase(){
        this.changePhase(new EnemyTurnPhase());
    }

    @Override
    public boolean isInTurnsPhase(){return true;}

    @Override
    public void characterTurn(ICharacter character){
        System.out.println("Turns");
    }

}
