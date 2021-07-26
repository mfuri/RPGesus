package com.cop4656.rpgesus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class welcomeFragment extends Fragment {
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference darkMode = db.getReference().child("DarkMode");




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

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mViewModel.setDarkMode(dataSnapshot.getValue(Boolean.class));
                if(mViewModel.isDarkMode())
                {
                    darkMode.setValue(true);
                    switchDarkMode.setChecked(true);
                    view.setBackgroundColor(getResources().getColor(R.color.darkmode));
                    switchDarkMode.setTextColor(getResources().getColor(R.color.rpg_white));
                }
                else if (!mViewModel.isDarkMode())
                {
                    darkMode.setValue(false);
                    switchDarkMode.setChecked(false);
                    view.setBackgroundColor(getResources().getColor(R.color.rpg_white));
                    switchDarkMode.setTextColor(getResources().getColor(R.color.darkmode));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage());
            }
        };

        darkMode.addListenerForSingleValueEvent(valueEventListener);



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