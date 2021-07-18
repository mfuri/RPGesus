package com.cop4656.rpgesus;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cop4656.rpgesus.Character;

import java.util.LinkedList;


public class ListFragment extends Fragment {

    private com.cop4656.rpgesus.CharacterViewModel mViewModel;
    ListView listView;

    AdapterView.OnItemClickListener characterListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Character currentCharacter = (Character) parent.getItemAtPosition(position);
            mViewModel.setCurrentCharacter(currentCharacter);
        }
    };

    public ListFragment() {
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
        View inflate = inflater.inflate(R.layout.fragment_character_list, container, false);
        listView = inflate.findViewById(R.id.listView);
        listView.setOnItemClickListener(characterListener);

        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel =  new ViewModelProvider(requireActivity()).get(com.cop4656.rpgesus.CharacterViewModel.class);

       mViewModel.getCharacters().observe(getViewLifecycleOwner(), new Observer<LinkedList<Character>>() {
            @Override
            public void onChanged(LinkedList<Character> characters) {
                ListAdapter customAdapter = new ListAdapter(getActivity(), R.layout.fragment_character_list_row, mViewModel.getCharacters().getValue());
                listView.setAdapter(customAdapter);
            }
        });
    }
}