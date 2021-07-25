package com.cop4656.rpgesus;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cop4656.rpgesus.Character;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class CharacterViewModel extends ViewModel {
    private MutableLiveData<LinkedList<Character>> characters;
    private MutableLiveData<Character> currentCharacter;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dbDarkMode = db.getReference().child("DarkMode");


    private Boolean darkMode = false;


    public LiveData<LinkedList<Character>> getCharacters() {
        if (characters == null) {
            characters = new MutableLiveData<>();
        }
        return characters;
    }

    public LiveData<Character> getCurrentCharacter() {
        if (currentCharacter == null) {
            currentCharacter = new MutableLiveData<>();
            currentCharacter.postValue(new Character());
        }
        return currentCharacter;
    }

    public void setCurrentCharacter(Character newChar) {
        if (currentCharacter == null) {
            currentCharacter = new MutableLiveData<Character>();
        }
        currentCharacter.setValue(newChar);
    }

    public void setCharacters(LinkedList<Character> newCharacters) {
        if (characters == null) {
            characters = new MutableLiveData<>();
        }
        characters.setValue(newCharacters);
    }

    public void addCharacter(Character t){
        LinkedList<Character> tempList = characters.getValue();
        assert tempList != null;
        tempList.add(t);
        characters.setValue(tempList);
    }

    public void deleteCharacter(Character t){
        LinkedList<Character> tempList = characters.getValue();
        assert tempList != null;
        tempList.remove(t);
        characters.setValue(tempList);
    }

    public Boolean isDarkMode(){
        return darkMode;
    }

    public void setDarkMode(Boolean darkMode){
        dbDarkMode.setValue(darkMode);
        this.darkMode = darkMode;
    }
}