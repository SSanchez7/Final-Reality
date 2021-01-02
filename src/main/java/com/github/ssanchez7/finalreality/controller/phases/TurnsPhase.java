package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.model.character.ICharacter;

public class TurnsPhase extends Phase{

    @Override
    public void toPlayerTurnPhase(){
        this.changePhase(new PlayerTurnPhase());
    }
    @Override
    public void toEnemyTurnPhase(){
        this.changePhase(new EnemyTurnPhase());
    }
    @Override
    public void toEndPhase(){ this.changePhase(new EndPhase());}

    @Override
    public boolean isInTurnsPhase(){return true;}

    @Override
    public void toCharacterTurn(ICharacter character){
        if(character.isEnemy()){
            toEnemyTurnPhase();
        }else{
            toPlayerTurnPhase();
        }
    }



}
