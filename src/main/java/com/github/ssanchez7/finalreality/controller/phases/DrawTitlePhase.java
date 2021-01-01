package com.github.ssanchez7.finalreality.controller.phases;

public class DrawTitlePhase extends Phase{

    @Override
    public void toSelectionPlayerPhase(){
        this.changePhase(new SelectionPlayerPhase());
    }
}
