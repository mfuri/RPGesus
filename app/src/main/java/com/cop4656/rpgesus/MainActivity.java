package com.cop4656.rpgesus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private CharacterViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);
        loadCharacters();
    }

    private void loadCharacters() {
        //SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        LinkedList<Character> tempList = new LinkedList<>();
        mViewModel.setCharacters(tempList);
    }

}