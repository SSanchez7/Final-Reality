package com.github.ssanchez7.finalreality.controller;

import com.github.ssanchez7.finalreality.model.character.ICharacter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A class that receives notifies from TurnList class when the list was empty.
 *
 * @author Ignacio Slater Muñoz.
 * @author Samuel Sanchez Parra
 */
public class TurnListHandler implements PropertyChangeListener {
    private final GameController controller;
    public TurnListHandler(GameController c){
        this.controller = c;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        controller.characterTurn((ICharacter) evt.getNewValue());
    }
}
