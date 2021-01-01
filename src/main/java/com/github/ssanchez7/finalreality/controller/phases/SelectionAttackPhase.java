package com.github.ssanchez7.finalreality.controller.phases;

public class SelectionAttackPhase extends Phase{
    @Override
    public void toTurnsPhase(){
        this.changePhase(new TurnsPhase());}
}
