package com.cop4656.rpgesus;

import android.graphics.Bitmap;

import java.lang.*;

public class Character {
    private String name;
    private String avatar;
    private int level;
    private String race;
    private int strength;
    private int charisma;
    private int intelligence;
    private int vitality;
    private int luck;
    private boolean wizard;
    private boolean gambler;
    private boolean uncivilized;
    private boolean heartbreaker;
    private boolean marathon;
    private boolean book;
    private boolean treasure;
    private boolean iron;
    private boolean politician;

    public String getName(){
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public boolean getWizard(){return wizard;}

    public boolean getGambler(){return gambler;}

    public boolean getUncivilized(){return uncivilized;}

    public boolean getHeartbreaker(){return heartbreaker;}

    public boolean getMarathon(){return marathon;}

    public boolean getBook(){return book;}

    public boolean getTreasure(){return treasure;}

    public boolean getIron(){return iron;}

    public boolean getPolitician(){return politician;}


    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public void setWizard(boolean value){wizard = value;}

    public void setGambler(boolean value){gambler = value;}

    public void setUncivilized(boolean value){ uncivilized = value;}

    public void setHeartbreaker(boolean value){ heartbreaker = value;}

    public void setMarathon(boolean value){ marathon = value;}

    public void setBook(boolean value){ book = value;}

    public void setTreasure(boolean value){ treasure = value;}

    public void setIron(boolean value){ iron = value;}

    public void setPolitician(boolean value){ politician = value;}

}
