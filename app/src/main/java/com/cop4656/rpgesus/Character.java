package com.cop4656.rpgesus;

import java.lang.*;

public class Character {
    private String name;
    private String avatarURI;
    private int level;
    private String race;
    private int strength;
    private int charisma;
    private int intelligence;
    private int vitality;
    private int luck;

    public String getName(){
        return name;
    }

    public String getAvatarURI(){
        return avatarURI;
    }

    public int getLevel() {
        return level;
    }

    public String getRace(){
        return race;
    }

    public int getStrength() {
        return strength;
    }

    public int getLuck() {
        return luck;
    }

    public int getVitality() {
        return vitality;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAvatarURI(String pictureURI) {
        this.avatarURI = avatarURI;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

}
