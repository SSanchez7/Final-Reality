package com.github.ssanchez7.finalreality.model.character;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class NullCharacter implements ICharacter{

    @Override
    public void waitTurn(){
    }

    @Override
    public String getName(){
        return "";
    }

    @Override
    public int getHp(){
        return -1;
    }

    @Override
    public void setHp(int hp){

    }

    @Override
    public int getDefensePoints(){
        return -1;
    }

    @Override
    public int getHpMax(){
        return -1;
    }

    @Override
    public void attack(ICharacter attacked){

    }

    @Override
    public void beAttacked(int baseDamage){

    }

    @Override
    public boolean isKO(){
        return false;
    }

    @Override
    public void addListener(PropertyChangeListener listener){

    }

    @Override
    public void isDefeatedCharacter(){

    }

    @Override
    public boolean isEnemy(){
        return false;
    }

    @Override
    public List<String> getValues() {
        List<String> str = new ArrayList<>();
        str.add("");
        return str;
    }
}
