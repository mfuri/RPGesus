package com.cop4656.rpgesus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class welcomeFragment extends Fragment {

    private com.cop4656.rpgesus.CharacterViewModel mViewModel;
    //private Boolean darkMode = false;
    public welcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        Button newButton = (Button) view.findViewById(R.id.newButton); //this button will send the user to the creation screen for a character
        Button editButton = (Button) view.findViewById(R.id.editButton);
        Switch switchDarkMode = (Switch) view.findViewById(R.id.switchDarkMode);
        mViewModel =  new ViewModelProvider(requireActivity()).get(com.cop4656.rpgesus.CharacterViewModel.class);

        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchDarkMode.isChecked()){
                    mViewModel.setDarkMode(true);
                    view.setBackgroundColor(getResources().getColor(R.color.darkmode));
                    switchDarkMode.setTextColor(getResources().getColor(R.color.rpg_white));
                }
                else if (!switchDarkMode.isChecked())
                {
                    mViewModel.setDarkMode(false);
                    view.setBackgroundColor(getResources().getColor(R.color.rpg_white));
                    switchDarkMode.setTextColor(getResources().getColor(R.color.darkmode));
                }
            }
        });

        newButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment creationFragment = new CharacterCreationFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, creationFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); //going to the character_creation fragment
            }
        });

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment listFragment = new ListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, listFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); //going to the character_creation fragment
            }
        });

        return view;
    }
}