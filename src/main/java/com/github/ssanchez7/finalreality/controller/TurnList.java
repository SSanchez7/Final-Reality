package com.github.ssanchez7.finalreality.controller;

import com.github.ssanchez7.finalreality.model.character.ICharacter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.BlockingQueue;

/**
 * A class that holds the information for notifies when a BlockingQueue is empty.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class TurnList {
    private final PropertyChangeSupport p = new PropertyChangeSupport(this);

    public void addListener(PropertyChangeListener listenerWaitTurn){
        p.addPropertyChangeListener(listenerWaitTurn);
    }

    /**
     * Notifies listeners when the turn list is empty and the first character is not defeated.
     */
    public void turnIsNotEmpty(BlockingQueue<ICharacter> turns){
        if (!turns.isEmpty()) {
            ICharacter character = turns.poll();
            if(!character.isKO()){
                p.firePropertyChange("WAIT_TURN", null, character);
            }
        }
    }

}
