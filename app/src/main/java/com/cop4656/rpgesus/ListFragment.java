package com.cop4656.rpgesus;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;


public class ListFragment extends Fragment {

    private com.cop4656.rpgesus.CharacterViewModel mViewModel;
    ListView listView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Characters");
    private Boolean darkMode;

    AdapterView.OnItemClickListener characterListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Character currentCharacter = (Character) parent.getItemAtPosition(position);
            mViewModel.setCurrentCharacter(currentCharacter);

            String [] options = {"Edit","Delete"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose an option");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0)
                    {
                        Fragment editFragment = new CharacterEditFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.list_fragment, editFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit(); //going to the character_creation fragment
                    }
                    else if (which == 1)
                    {
                        mViewModel.deleteCharacter(mViewModel.getCurrentCharacter().getValue());
                        root.setValue(mViewModel.getCharacters().getValue());
                    }
                }
            });
            builder.show();
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
            if(mViewModel.isDarkMode()){
                listView.setBackgroundColor(getResources().getColor(R.color.darkmode));
                listView.setDivider(new ColorDrawable(getResources().getColor(R.color.rpg_white)));
                listView.setDividerHeight(1);
            }
            else if(!mViewModel.isDarkMode()){
                listView.setBackgroundColor(getResources().getColor(R.color.rpg_white));
                listView.setDivider(new ColorDrawable(getResources().getColor(R.color.darkmode)));
                listView.setDividerHeight(1);
            }

       mViewModel.getCharacters().observe(getViewLifecycleOwner(), new Observer<LinkedList<Character>>() {
            @Override
            public void onChanged(LinkedList<Character> characters) {
                ListAdapter customAdapter = new ListAdapter(getActivity(), R.layout.fragment_character_list_row, mViewModel.getCharacters().getValue());
                customAdapter.setDarkMode(mViewModel.isDarkMode());
                listView.setAdapter(customAdapter);
            }
        });
    }
}