package com.cop4656.rpgesus;

import android.graphics.Bitmap;
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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerkSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerkSelectionFragment extends Fragment {

    private com.cop4656.rpgesus.CharacterViewModel mViewModel;
    private int currentPerkPoints;
    private int level = 1;
    private TextView points;
    private Bitmap bitmap;
    private byte [] byteArray;
    private Boolean darkMode;
    private Character character;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerkSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerkSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerkSelectionFragment newInstance(String param1, String param2) {
        PerkSelectionFragment fragment = new PerkSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perk_selection, container, false);
        mViewModel =  new ViewModelProvider(requireActivity()).get(com.cop4656.rpgesus.CharacterViewModel.class);

        mViewModel =  new ViewModelProvider(requireActivity()).get(com.cop4656.rpgesus.CharacterViewModel.class);
        character = mViewModel.getCurrentCharacter().getValue();
        level = character.getLevel();

        //GETTING ALL VIEWS FOR PERKS
        TextView headerPerkView = (TextView) view.findViewById(R.id.headerPerkView);
        TextView unallocated1 = (TextView) view.findViewById(R.id.unallocated1View);
        TextView unallocated2 = (TextView) view.findViewById(R.id.unallocated2View);
        TextView unallocatedPerks = (TextView) view.findViewById(R.id.unallocatedPerkPoints);
        TextView level10View = (TextView) view.findViewById(R.id.level10PerkView);
        TextView wizardView = (TextView) view.findViewById(R.id.wizardView);
        TextView wizardDescView = (TextView) view.findViewById(R.id.wizardDescView);
        TextView gamblerView = (TextView) view.findViewById(R.id.gamblerView);
        TextView gamblerDescView = (TextView) view.findViewById(R.id.gamblerDescView);
        TextView uncivilizedView = (TextView) view.findViewById(R.id.uncivilizedView);
        TextView uncivilizedDescView = (TextView) view.findViewById(R.id.uncivilizedDescView);
        TextView level20View = (TextView) view.findViewById(R.id.level20PerkView);
        TextView heartbreakerView = (TextView) view.findViewById(R.id.heartBreakerView);
        TextView heartbreakerDescView = (TextView) view.findViewById(R.id.heartbreakerDescView);
        TextView marathonView = (TextView) view.findViewById(R.id.martathonView);
        TextView marathonDescView = (TextView) view.findViewById(R.id.martathonView);
        TextView bookView = (TextView) view.findViewById(R.id.bookView);
        TextView bookDescView = (TextView) view.findViewById(R.id.bookDescView);
        TextView level30View = (TextView) view.findViewById(R.id.level20PerkView);
        TextView treasureView = (TextView) view.findViewById(R.id.treasureView);
        TextView treasureDescView = (TextView) view.findViewById(R.id.treasureDescView);
        TextView ironView = (TextView) view.findViewById(R.id.ironView);
        TextView ironDesc = (TextView) view.findViewById(R.id.ironDescView);
        TextView politicianView = (TextView) view.findViewById(R.id.politicianView);
        TextView politicianDescView = (TextView) view.findViewById(R.id.politicianDescView);

        //BUTTONS AND SWITCHES
        Switch wizardSwitch = (Switch) view.findViewById(R.id.wizardSwitch);

        wizardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked && level >= 10) {
                    currentPerkPoints++;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                    character.setWizard(false);
                }
                if (isChecked && level < 10 || currentPerkPoints == 0) {
                    Toast.makeText(getContext(), "You are not high enough level for this perk or do not have enough perk points. Level up and come back!", Toast.LENGTH_LONG).show();
                    wizardSwitch.setChecked(false);
                }
                if (isChecked && level >= 10 && currentPerkPoints > 0) {
                    character.setWizard(true);
                    currentPerkPoints--;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                }
            }
        });

        Switch gamblerSwitch = (Switch) view.findViewById(R.id.gamblerSwitch);

        gamblerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked && level >= 10){
                    currentPerkPoints++;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                    character.setWizard(false);
                }
                if(isChecked && level < 10 || currentPerkPoints == 0) {
                    Toast.makeText(getContext(), "You are not high enough level for this perk or do not have enough perk points. Level up and come back!",Toast.LENGTH_LONG).show();
                    gamblerSwitch.setChecked(false);
                }
                if(isChecked && level >= 10 && currentPerkPoints > 0){
                    character.setGambler(true);
                    currentPerkPoints--;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                }
            }
        });

        Switch uncivilizedSwitch = (Switch) view.findViewById(R.id.uncivilizedSwitch);

        uncivilizedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked && level >= 10){
                    currentPerkPoints++;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                    character.setUncivilized(false);
                }
                if(isChecked && level < 10 || currentPerkPoints == 0) {
                    Toast.makeText(getContext(), "You are not high enough level for this perk or do not have enough perk points. Level up and come back!",Toast.LENGTH_LONG).show();
                    uncivilizedSwitch.setChecked(false);
                }
                if(isChecked && level >= 10 && currentPerkPoints > 0){
                    character.setUncivilized(true);
                    currentPerkPoints--;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                }
            }
        });

        Switch heartbreakerSwitch = (Switch) view.findViewById(R.id.heartbreakerSwitch);

        heartbreakerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked && level >= 20){
                    currentPerkPoints++;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                    character.setHeartbreaker(false);
                }
                if(isChecked && level < 20 || currentPerkPoints == 0) {
                    Toast.makeText(getContext(), "You are not high enough level for this perk or do not have enough perk points. Level up and come back!",Toast.LENGTH_LONG).show();
                    heartbreakerSwitch.setChecked(false);
                }
                if(isChecked && level >= 20 && currentPerkPoints > 0){
                    character.setHeartbreaker(true);
                    currentPerkPoints--;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                }
            }
        });
        Switch marathonSwitch = (Switch) view.findViewById(R.id.marathonSwitch);

        marathonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked && level >= 20){
                    currentPerkPoints++;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                    character.setMarathon(false);
                }
                if(isChecked && level < 20 || currentPerkPoints == 0) {
                    Toast.makeText(getContext(), "You are not high enough level for this perk or do not have enough perk points. Level up and come back!",Toast.LENGTH_LONG).show();
                    marathonSwitch.setChecked(false);
                }
                if(isChecked && level >= 20 && currentPerkPoints > 0){
                    character.setMarathon(true);
                    currentPerkPoints--;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                }
            }
        });

        Switch bookSwitch = (Switch) view.findViewById(R.id.bookSwitch);

        bookSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked && level >= 20){
                    currentPerkPoints++;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                    character.setBook(false);
                }
                if(isChecked && level < 20 || currentPerkPoints == 0) {
                    Toast.makeText(getContext(), "You are not high enough level for this perk or do not have enough perk points. Level up and come back!",Toast.LENGTH_LONG).show();
                    bookSwitch.setChecked(false);
                }
                if(isChecked && level >= 20 && currentPerkPoints > 0){
                    character.setBook(true);
                    currentPerkPoints--;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                }
            }
        });

        Switch treasureSwitch = (Switch) view.findViewById(R.id.treasureSwitch);

        treasureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked && level >= 30){
                    currentPerkPoints++;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                    character.setTreasure(false);
                }
                if(isChecked && level < 30 || currentPerkPoints == 0) {
                    Toast.makeText(getContext(), "You are not high enough level for this perk or do not have enough perk points. Level up and come back!",Toast.LENGTH_LONG).show();
                    treasureSwitch.setChecked(false);
                }
                if(isChecked && level >= 30 && currentPerkPoints > 0){
                    character.setTreasure(true);
                    currentPerkPoints--;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                }
            }
        });

        Switch ironSwitch = (Switch) view.findViewById(R.id.ironSwitch);

        ironSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked && level >= 30){
                    currentPerkPoints++;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                    character.setIron(false);
                }
                if(isChecked && level < 30 || currentPerkPoints == 0) {
                    Toast.makeText(getContext(), "You are not high enough level for this perk or do not have enough perk points. Level up and come back!",Toast.LENGTH_LONG).show();
                    ironSwitch.setChecked(false);
                }
                if(isChecked && level >= 30 && currentPerkPoints > 0){
                    character.setIron(true);
                    currentPerkPoints--;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                }
            }
        });

        Switch politicianSwitch = (Switch) view.findViewById(R.id.politicianSwitch);

        politicianSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked && level >= 30){
                    currentPerkPoints++;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                    character.setPolitician(false);
                }
                if(isChecked && level < 30 || currentPerkPoints == 0) {
                    Toast.makeText(getContext(), "You are not high enough level for this perk or do not have enough perk points. Level up and come back!",Toast.LENGTH_LONG).show();
                    politicianSwitch.setChecked(false);
                }
                if(isChecked && level >= 30 && currentPerkPoints > 0){
                    character.setPolitician(true);
                    currentPerkPoints--;
                    unallocatedPerks.setText(String.valueOf(currentPerkPoints));
                }
            }
        });

        Button goBackButton = (Button) view.findViewById(R.id.perkBackButton);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment welcomeFrag = new welcomeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, welcomeFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); //going to the character_creation fragment
            }
        });

        Button characterButton = (Button) view.findViewById(R.id.perkCharSheetButton);

        characterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment CharacterSheet = new CharacterSheetFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, CharacterSheet);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); //going to the character_creation fragment
            }
        });

        if(mViewModel.isDarkMode()){
            view.setBackgroundColor(getResources().getColor(R.color.darkmode));
            headerPerkView.setTextColor(getResources().getColor(R.color.rpg_white));
            unallocated1.setTextColor(getResources().getColor(R.color.rpg_white));
            unallocated2.setTextColor(getResources().getColor(R.color.rpg_white));
            unallocatedPerks.setTextColor(getResources().getColor(R.color.rpg_white));
            level10View.setTextColor(getResources().getColor(R.color.rpg_white));
            wizardView.setTextColor(getResources().getColor(R.color.rpg_white));
            wizardDescView.setTextColor(getResources().getColor(R.color.rpg_white));
            gamblerView.setTextColor(getResources().getColor(R.color.rpg_white));
            gamblerDescView.setTextColor(getResources().getColor(R.color.rpg_white));
            uncivilizedView.setTextColor(getResources().getColor(R.color.rpg_white));
            uncivilizedDescView.setTextColor(getResources().getColor(R.color.rpg_white));
            level20View.setTextColor(getResources().getColor(R.color.rpg_white));
            heartbreakerView.setTextColor(getResources().getColor(R.color.rpg_white));
            heartbreakerDescView.setTextColor(getResources().getColor(R.color.rpg_white));
            marathonView.setTextColor(getResources().getColor(R.color.rpg_white));
            marathonDescView.setTextColor(getResources().getColor(R.color.rpg_white));
            bookView.setTextColor(getResources().getColor(R.color.rpg_white));
            bookDescView.setTextColor(getResources().getColor(R.color.rpg_white));
            level30View.setTextColor(getResources().getColor(R.color.rpg_white));
            level30View.setTextColor(getResources().getColor(R.color.rpg_white));
            treasureView.setTextColor(getResources().getColor(R.color.rpg_white));
            treasureDescView.setTextColor(getResources().getColor(R.color.rpg_white));
            ironView.setTextColor(getResources().getColor(R.color.rpg_white));
            ironDesc.setTextColor(getResources().getColor(R.color.rpg_white));
            politicianView.setTextColor(getResources().getColor(R.color.rpg_white));
            politicianDescView.setTextColor(getResources().getColor(R.color.rpg_white));
        }



        if(level == 30) {
            currentPerkPoints = 3;
        }
        else if(level < 30 && level >= 20) {
            currentPerkPoints = 2;
        }
        else if(level < 20 && level >= 10){
            currentPerkPoints = 1;
        }
        else{
            currentPerkPoints = 0;
        }

        unallocatedPerks.setText(String.valueOf(currentPerkPoints));

        if(character.getWizard()){
            wizardSwitch.setChecked(true);
        }
        if(character.getGambler()){
            gamblerSwitch.setChecked(true);
        }
        if(character.getUncivilized()){
            uncivilizedSwitch.setChecked(true);
        }
        if(character.getHeartbreaker()){
            heartbreakerSwitch.setChecked(true);
        }
        if(character.getMarathon()){
            marathonSwitch.setChecked(true);
        }
        if(character.getBook()){
            bookSwitch.setChecked(true);
        }
        if(character.getTreasure()){
            treasureSwitch.setChecked(true);
        }
        if(character.getIron()){
            ironSwitch.setChecked(true);
        }
        if(character.getPolitician()){
            politicianSwitch.setChecked(true);
        }

        return view;
    }

}