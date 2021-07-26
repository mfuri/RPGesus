package com.cop4656.rpgesus;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pub.devrel.easypermissions.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lowagie.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharacterCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterEditFragment extends Fragment implements View.OnClickListener{

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Characters");

    private com.cop4656.rpgesus.CharacterViewModel mViewModel;

    private String avatarURI;
    private int currentUnallocatedPoints = 0;
    private TextView points;
    private int level;
    private Bitmap bitmap;
    private Boolean darkMode;

    private Button strengthPlus;
    private Button strengthMinus;
    private Button intelPlus;
    private Button intelMinus;
    private Button charismaPlus;
    private Button charismaMinus;
    private Button vitalityPlus;
    private Button vitalityMinus;
    private Button luckPlus;
    private Button luckMinus;
    private Button goBackButton;
    private Button continueButton;
    private ImageView avatarView;
    private Button avatarButton;


    private EditText strength;
    private EditText intel;
    private EditText charisma;
    private EditText vitality;
    private EditText luck;

    private TextView viewStrength;
    private TextView viewIntel;
    private TextView viewCharisma;
    private TextView viewVitality;
    private TextView viewLuck;
    private TextView pointsLabel;

    private EditText Name;
    private Spinner Race;
    private TextView setRace;

    private Character character;

    public static final int IMAGE_CODE = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CharacterEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharacterCreationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharacterEditFragment newInstance(String param1, String param2) {
        CharacterEditFragment fragment = new CharacterEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_character_edit, container, false);

        mViewModel =  new ViewModelProvider(requireActivity()).get(com.cop4656.rpgesus.CharacterViewModel.class);
        level = mViewModel.getCurrentCharacter().getValue().getLevel();
        character = mViewModel.getCurrentCharacter().getValue();
        //Getting all buttons

        strengthPlus = (Button) view.findViewById(R.id.strengthPlusButton);
        strengthMinus = (Button) view.findViewById(R.id.strengthMinusButton);
        intelPlus = (Button) view.findViewById(R.id.intelPlusButton);
        intelMinus = (Button) view.findViewById(R.id.intelMinusButton);
        charismaPlus = (Button) view.findViewById(R.id.charismaPlusButton);
        charismaMinus = (Button) view.findViewById(R.id.charismaMinusButton);
        vitalityPlus = (Button) view.findViewById(R.id.vitalityPlusButton);
        vitalityMinus = (Button) view.findViewById(R.id.vitalityMinusButton);
        luckPlus = (Button) view.findViewById(R.id.luckPlusButton);
        luckMinus = (Button) view.findViewById(R.id.luckMinusButton);
        goBackButton = (Button) view.findViewById(R.id.goBackButton);
        continueButton = (Button) view.findViewById(R.id.continueButton);
        avatarView = (ImageView) view.findViewById(R.id.characterAvatarView);
        avatarButton = (Button) view.findViewById(R.id.avatarUploadButton);

        //Adding onClickListenrs to current Buttons

        strengthPlus.setOnClickListener(this);
        strengthMinus.setOnClickListener(this);
        intelPlus.setOnClickListener(this);
        intelMinus.setOnClickListener(this);
        charismaPlus.setOnClickListener(this);
        charismaMinus.setOnClickListener(this);
        vitalityPlus.setOnClickListener(this);
        vitalityMinus.setOnClickListener(this);
        luckPlus.setOnClickListener(this);
        luckMinus.setOnClickListener(this);
        continueButton.setOnClickListener(this);
        goBackButton.setOnClickListener(this);
        avatarButton.setOnClickListener(this);

        viewStrength = (TextView) view.findViewById(R.id.strengthLabel);
        viewCharisma = (TextView) view.findViewById(R.id.charismaLabel);
        viewIntel = (TextView) view.findViewById(R.id.intelligenceLabel);
        viewVitality = (TextView) view.findViewById(R.id.vitalityLabel);
        viewLuck = (TextView) view.findViewById(R.id.luckLabel);

        //Getting TextViews to display current skill point values

        strength = (EditText) view.findViewById(R.id.strengthPointsEditText);
        intel = (EditText) view.findViewById(R.id.intelligencePointsEditText);
        charisma = (EditText) view.findViewById(R.id.charismaPointsEditText);
        vitality = (EditText) view.findViewById(R.id.vitalityPointsEditText);
        luck = (EditText) view.findViewById(R.id.luckPointsEditText);

        strength.setText(String.valueOf(AddPoint(mViewModel.getCurrentCharacter().getValue().getStrength())));
        intel.setText(String.valueOf(AddPoint(mViewModel.getCurrentCharacter().getValue().getIntelligence())));
        charisma.setText(String.valueOf(AddPoint(mViewModel.getCurrentCharacter().getValue().getCharisma())));
        vitality.setText(String.valueOf(AddPoint(mViewModel.getCurrentCharacter().getValue().getVitality())));
        luck.setText(String.valueOf(AddPoint(mViewModel.getCurrentCharacter().getValue().getLuck())));

        TextView levelLabel = view.findViewById(R.id.EditLevelLabel);
        TextView levelView = view.findViewById(R.id.editLevel);

        levelView.setText(String.valueOf(level));

        Button levelUpButton = (Button) view.findViewById(R.id.levelUpButton);

        levelUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level != 30){
                    Toast.makeText(getContext(), "Congrats! You have now leveled up.", Toast.LENGTH_LONG).show();
                    level++;
                    character.setLevel(level);
                    levelView.setText(String.valueOf(level));
                    currentUnallocatedPoints++;
                    points.setText(String.valueOf(currentUnallocatedPoints));
                }
                else{
                    Toast.makeText(getContext(), "You are already max level. You can not level up further", Toast.LENGTH_LONG).show();
                }
            }
        });



        //Getting points that are unallocated to the user

        points = (TextView) view.findViewById(R.id.remainingSkillPointsView);
        pointsLabel = (TextView) view.findViewById(R.id.pointsLabel);
        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Set Character Level");

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Integer.parseInt(input.getText().toString()) < 1 || Integer.parseInt(input.getText().toString()) > 70)
                            Toast.makeText(getContext(), "Invalid level",Toast.LENGTH_LONG).show();
                        else {
                            level = Integer.parseInt(input.getText().toString());
                            points.setText(String.valueOf(level));
                            currentUnallocatedPoints = level;
                            strength.setText(String.valueOf(0));
                            charisma.setText(String.valueOf(0));
                            luck.setText(String.valueOf(0));
                            vitality.setText(String.valueOf(0));
                            intel.setText(String.valueOf(0));
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        currentUnallocatedPoints = Integer.parseInt(points.getText().toString());

        //Getting name and race selection
        Name = (EditText) view.findViewById(R.id.characterNameEditText);
        Race = (Spinner) view.findViewById(R.id.raceSpinner) ;
        setRace = (TextView) view.findViewById(R.id.setRaceTextView);

        Name.setText(mViewModel.getCurrentCharacter().getValue().getName());
        Race.setSelection(getIndex(Race, mViewModel.getCurrentCharacter().getValue().getRace()));

        bitmap = StringToBitMap(mViewModel.getCurrentCharacter().getValue().getAvatar());

        avatarView.setImageBitmap(bitmap);

        List<String> races = Arrays.asList(getResources().getStringArray(R.array.races));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, races);
        Race.setAdapter(adapter);

        if (mViewModel.isDarkMode())
        {
            view.setBackgroundColor(getResources().getColor(R.color.darkmode));
            Name.setTextColor(getResources().getColor(R.color.rpg_white));
            Name.setBackgroundTintList(getResources().getColorStateList(R.color.rpg_white));
            strength.setTextColor(getResources().getColor(R.color.rpg_white));
            intel.setTextColor(getResources().getColor(R.color.rpg_white));
            vitality.setTextColor(getResources().getColor(R.color.rpg_white));
            charisma.setTextColor(getResources().getColor(R.color.rpg_white));
            luck.setTextColor(getResources().getColor(R.color.rpg_white));
            viewLuck.setTextColor(getResources().getColor(R.color.rpg_white));
            viewStrength.setTextColor(getResources().getColor(R.color.rpg_white));
            viewVitality.setTextColor(getResources().getColor(R.color.rpg_white));
            viewCharisma.setTextColor(getResources().getColor(R.color.rpg_white));
            viewIntel.setTextColor(getResources().getColor(R.color.rpg_white));
            setRace.setTextColor(getResources().getColor(R.color.rpg_white));
            strength.setBackgroundTintList(getResources().getColorStateList(R.color.rpg_white));
            charisma.setBackgroundTintList(getResources().getColorStateList(R.color.rpg_white));
            vitality.setBackgroundTintList(getResources().getColorStateList(R.color.rpg_white));
            luck.setBackgroundTintList(getResources().getColorStateList(R.color.rpg_white));
            intel.setBackgroundTintList(getResources().getColorStateList(R.color.rpg_white));
            pointsLabel.setTextColor(getResources().getColorStateList(R.color.rpg_white));
            points.setTextColor(getResources().getColorStateList(R.color.rpg_white));
            levelLabel.setTextColor(getResources().getColorStateList(R.color.rpg_white));
            levelView.setTextColor(getResources().getColorStateList(R.color.rpg_white));
        }
        if (!mViewModel.isDarkMode())
        {
            view.setBackgroundColor(getResources().getColor(R.color.rpg_white));
            Name.setTextColor(getResources().getColor(R.color.darkmode));
            Name.setBackgroundTintList(getResources().getColorStateList(R.color.darkmode));
            strength.setTextColor(getResources().getColor(R.color.darkmode));
            intel.setTextColor(getResources().getColor(R.color.darkmode));
            vitality.setTextColor(getResources().getColor(R.color.darkmode));
            charisma.setTextColor(getResources().getColor(R.color.darkmode));
            luck.setTextColor(getResources().getColor(R.color.darkmode));
            viewLuck.setTextColor(getResources().getColor(R.color.darkmode));
            viewStrength.setTextColor(getResources().getColor(R.color.darkmode));
            viewVitality.setTextColor(getResources().getColor(R.color.darkmode));
            viewCharisma.setTextColor(getResources().getColor(R.color.darkmode));
            viewIntel.setTextColor(getResources().getColor(R.color.darkmode));
            setRace.setTextColor(getResources().getColor(R.color.darkmode));

            strength.setBackgroundTintList(getResources().getColorStateList(R.color.darkmode));
            charisma.setBackgroundTintList(getResources().getColorStateList(R.color.darkmode));
            vitality.setBackgroundTintList(getResources().getColorStateList(R.color.darkmode));
            luck.setBackgroundTintList(getResources().getColorStateList(R.color.darkmode));
            intel.setBackgroundTintList(getResources().getColorStateList(R.color.darkmode));
            pointsLabel.setTextColor(getResources().getColorStateList(R.color.darkmode));
            points.setTextColor(getResources().getColorStateList(R.color.darkmode));
        }


        return view;
    }


    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    @Override
    public void onClick(View v) { //Custom onClick method

        if(v.getId() == R.id.strengthPlusButton){
            strength.setText(String.valueOf(AddPoint(Integer.parseInt(strength.getText().toString()))));
            /*
            We are getting the current value held in the EditText value and parsing it into an int and passing it into the
            add point method.
            We are setting the strength's text to the value that is returned from the AddPoint method.
            Strength is the textview that holds the point value for the strength stat.
            The rest of the methods are the same structure except for "Go Back" and "Continue Buttons"
            */
        }
        if(v.getId() == R.id.strengthMinusButton){
            strength.setText(String.valueOf(RemovePoint(Integer.parseInt(strength.getText().toString()))));
        }
        if(v.getId() == R.id.intelPlusButton){
            intel.setText(String.valueOf(AddPoint(Integer.parseInt(intel.getText().toString()))));
        }
        if(v.getId() == R.id.intelMinusButton){
            intel.setText(String.valueOf(RemovePoint(Integer.parseInt(intel.getText().toString()))));
        }
        if(v.getId() == R.id.charismaPlusButton){
            charisma.setText(String.valueOf(AddPoint(Integer.parseInt(charisma.getText().toString()))));
        }
        if(v.getId() == R.id.charismaMinusButton){
            charisma.setText(String.valueOf(RemovePoint(Integer.parseInt(charisma.getText().toString()))));
        }
        if(v.getId() == R.id.vitalityPlusButton){
            vitality.setText(String.valueOf(AddPoint(Integer.parseInt(vitality.getText().toString()))));
        }
        if(v.getId() == R.id.vitalityMinusButton){
            vitality.setText(String.valueOf(RemovePoint(Integer.parseInt(vitality.getText().toString()))));
        }
        if(v.getId() == R.id.luckPlusButton){
            luck.setText(String.valueOf(AddPoint(Integer.parseInt(luck.getText().toString()))));
        }
        if(v.getId() == R.id.luckMinusButton){
            luck.setText(String.valueOf(RemovePoint(Integer.parseInt(luck.getText().toString()))));
        }
        if(v.getId() == R.id.continueButton){ //saving character to database
            if(currentUnallocatedPoints == 0 && bitmap != null){
                
                mViewModel.getCurrentCharacter().getValue().setName(Name.getText().toString().trim());
                mViewModel.getCurrentCharacter().getValue().setRace(Race.getSelectedItem().toString().trim());
                mViewModel.getCurrentCharacter().getValue().setLevel(level);
                mViewModel.getCurrentCharacter().getValue().setCharisma(Integer.parseInt(charisma.getText().toString()));
                mViewModel.getCurrentCharacter().getValue().setVitality(Integer.parseInt(vitality.getText().toString()));
                mViewModel.getCurrentCharacter().getValue().setStrength(Integer.parseInt(strength.getText().toString()));
                mViewModel.getCurrentCharacter().getValue().setLuck(Integer.parseInt(luck.getText().toString()));
                mViewModel.getCurrentCharacter().getValue().setIntelligence(Integer.parseInt(intel.getText().toString()));
                mViewModel.getCurrentCharacter().getValue().setAvatar(BitMapToString(bitmap));
                mViewModel.setCurrentCharacter(mViewModel.getCurrentCharacter().getValue());

                root.setValue(mViewModel.getCharacters().getValue());


                Toast.makeText(getContext(), "The following information was edited",Toast.LENGTH_LONG).show();

                ContentValues contentValues = new ContentValues();

                contentValues.put(CharacterContentProvider.COLUMN_NAME, Name.getText().toString().trim());
                contentValues.put(CharacterContentProvider.COLUMN_RACE, Race.getSelectedItem().toString());
                contentValues.put(CharacterContentProvider.COLUMN_STRENGTH, strength.getText().toString().trim());
                contentValues.put(CharacterContentProvider.COLUMN_INTELLIGENCE, intel.getText().toString().trim());
                contentValues.put(CharacterContentProvider.COLUMN_CHARISMA, charisma.getText().toString().trim());
                contentValues.put(CharacterContentProvider.COLUMN_VITALITY, vitality.getText().toString().trim());
                contentValues.put(CharacterContentProvider.COLUMN_LUCK, luck.getText().toString().trim());
                contentValues.put(CharacterContentProvider.COLUMN_LEVEL, String.valueOf(level));
                //contentValues.put(CharacterContentProvider.COLUMN_AVATAR, avatarURI);

               // getActivity().getApplicationContext().getContentResolver().insert(CharacterContentProvider.CONTENT_URI, contentValues);

                Fragment perkSelectionFragment = new PerkSelectionFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, perkSelectionFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); //going to the character_creation fragment
            }
            else{
                Toast.makeText(getContext(), "Please allocate all points and upload an avatar before continuing",Toast.LENGTH_LONG).show();
            }

        }
        if(v.getId() == R.id.goBackButton){
            Fragment welcomeFrag = new welcomeFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.list_fragment, welcomeFrag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit(); //going to the character_Edit fragment
        }
        if(v.getId() == R.id.avatarUploadButton){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_CODE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_CODE) {
            avatarView.setImageURI(data.getData());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData());
                int dimension = getSquareCropDimensionForBitmap(bitmap);
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
                bitmap = Bitmap.createScaledBitmap(bitmap,200,200, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private int AddPoint(int currentPoints){ //currentPoints is the point value in current stat
        if(currentPoints == 10 || currentUnallocatedPoints == 0){
            return currentPoints;
        }
        currentPoints++;
        points.setText(String.valueOf(--currentUnallocatedPoints));
        return currentPoints;
    }

    private int RemovePoint(int currentPoints){
        if(currentPoints == 0){
            return currentPoints;
        }
        currentPoints--;
        points.setText(String.valueOf(++currentUnallocatedPoints));
        return currentPoints;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public int getSquareCropDimensionForBitmap(Bitmap bitmap)
    {
        //use the smallest dimension of the image to crop to
        return Math.min(bitmap.getWidth(), bitmap.getHeight());
    }


}