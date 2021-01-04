package com.github.ssanchez7.finalreality.controller.handlers;

import com.github.ssanchez7.finalreality.controller.GameController;
import com.github.ssanchez7.finalreality.model.character.Enemy;
import com.github.ssanchez7.finalreality.model.character.player.IPlayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A class that receives notifies from AbstractPLayerCharacter class when the player was defeat.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class DefeatedPlayerHandler implements PropertyChangeListener {
    private final GameController controller;
    public DefeatedPlayerHandler(GameController c){
        this.controller = c;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        controller.setnDefeatedPlayers(controller.getnDefeatedPlayers()+1);
        controller.removePlayerCopy((IPlayer)evt.getNewValue());
    }
}
