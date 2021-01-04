package com.github.ssanchez7.finalreality.controller.phases;

import com.github.ssanchez7.finalreality.controller.exceptions.InvalidMovementException;

public class DrawTitlePhase extends Phase{

    public DrawTitlePhase(){ canChoosePlayer = true; }
    @Override
    public void toSelectionPlayerPhase(){
        this.changePhase(new SelectionPlayerPhase());
    }

     @Override
    public boolean isInDrawTitlePhase(){ return true; }

    public void canChooseAPlayer() throws InvalidMovementException {
        super.canChooseAPlayer();
        toSelectionPlayerPhase();
    }
}
