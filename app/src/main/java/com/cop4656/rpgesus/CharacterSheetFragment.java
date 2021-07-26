package com.cop4656.rpgesus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharacterSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterSheetFragment extends Fragment {
    private String avatarURI;
    private int currentUnallocatedPoints = 0;
    private TextView points;
    private int playerLevel;
    private Bitmap bitmap;
    private Bitmap exportbmp;
    private Boolean darkMode;
    private CharacterViewModel mViewModel;

    private int playerStrength;
    private int playerIntel;
    private int playerCharisma;
    private int playerVitality;
    private int playerLuck;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CharacterSheetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharacterSheetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharacterSheetFragment newInstance(String param1, String param2) {
        CharacterSheetFragment fragment = new CharacterSheetFragment();
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
        View view = inflater.inflate(R.layout.fragment_character_sheet, container, false);

        mViewModel =  new ViewModelProvider(requireActivity()).get(com.cop4656.rpgesus.CharacterViewModel.class);
        playerLevel = mViewModel.getCurrentCharacter().getValue().getLevel();
        Character character = mViewModel.getCurrentCharacter().getValue();

        playerStrength = character.getStrength();
        playerIntel = character.getIntelligence();
        playerCharisma = character.getCharisma();
        playerVitality = character.getVitality();
        playerLuck = character.getLuck();

        //Getting views
        ImageView avatar = (ImageView) view.findViewById(R.id.charSheetAvatar);
        TextView name = (EditText) view.findViewById(R.id.charSheetName);
        TextView race = (TextView) view.findViewById(R.id.charSheetRace);
        TextView levelView = (TextView) view.findViewById(R.id.charSheetLevelView);
        TextView strengthView = (TextView) view.findViewById(R.id.charSheetStrengthView);
        TextView intelView = (TextView) view.findViewById(R.id.charSheetIntelView);
        TextView charismaView = (TextView) view.findViewById(R.id.charSheetCharisma);
        TextView vitalityView = (TextView) view.findViewById(R.id.charSheetVitalityView);
        TextView luckView = (TextView) view.findViewById(R.id.charSheetLuckView);
        TextView perkLabel = (TextView) view.findViewById(R.id.currentPerksLabel);
        TextView modifiersLabel = (TextView) view.findViewById(R.id.currentModifiersLabel);
        TextView perkView = (TextView) view.findViewById(R.id.currentPerksView);
        TextView modifiersView = (TextView) view.findViewById(R.id.modifiersView);


        TextView level = (TextView) view.findViewById(R.id.charSheetLevel);
        TextView strength = (TextView) view.findViewById(R.id.charSheetStrength);
        TextView intel = (TextView) view.findViewById(R.id.charSheetIntel);
        TextView charisma = (TextView) view.findViewById(R.id.charSheetCharisma);
        TextView vitality = (TextView) view.findViewById(R.id.charSheetVitality);
        TextView luck = (TextView) view.findViewById(R.id.charSheetLuck);

        Button goHome = (Button) view.findViewById(R.id.charSheetHomeButton);

        goHome.setOnClickListener(new View.OnClickListener() {
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

        Button perkButton = (Button) view.findViewById(R.id.viewPerksButton);

        perkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment perkSelectionFragment = new PerkSelectionFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, perkSelectionFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); //going to the character_creation fragment
            }
        });

        /*Button levelUpButton = (Button) view.findViewById(R.id.levelUpButton);

        levelUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerLevel != 30){
                    Toast.makeText(getContext(), "Congrats! You have now leveled up.", Toast.LENGTH_LONG).show();
                    playerLevel++;
                    character.setLevel(playerLevel);
                    level.setText(String.valueOf(playerLevel));
                }
                else{
                    Toast.makeText(getContext(), "You are already max level. You can not level up further", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        Button exportButton = (Button) view.findViewById(R.id.exportButton);

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF(v);
            }
        });


        avatar.setImageBitmap(StringToBitMap(mViewModel.getCurrentCharacter().getValue().getAvatar()));
        name.setText(character.getName());
        race.setText(character.getRace());
        level.setText(String.valueOf(playerLevel));
        strength.setText(String.valueOf(playerStrength));
        intel.setText(String.valueOf(playerIntel));
        charisma.setText(String.valueOf(playerCharisma));
        vitality.setText(String.valueOf(playerVitality));
        luck.setText(String.valueOf(playerLuck));

        perkView.setText("");
        modifiersView.setText("");

        if(character.getWizard()){
            perkView.setText("You're a wizard! - +1 I, + 1 V");
            modifiersView.setText("+5% Magic Damage");
        }
        if(character.getGambler()){
            perkView.setText(perkView.getText() + "\nThe Gambler - +1 L");
            modifiersView.setText(modifiersView.getText() + "\n+15% Critical Hit Chance\n+5% Critical Hit Damage");
        }
        if(character.getUncivilized()){
            perkView.setText(perkView.getText() + "\nSo Uncivilized - +2 S, +1 V,\n -1 C");
        }
        if(character.getHeartbreaker()){
            perkView.setText(perkView.getText() + "\nHeartbreaker - +1 C");
            modifiersView.setText(modifiersView.getText() + "\n+10% damage against\n the opposite sex\n+10% persuasion against \nthe opposite sex");
        }
        if(character.getMarathon()){
            perkView.setText(perkView.getText() + "\nMarathon Runner - +1 V");
            modifiersView.setText(modifiersView.getText() + "\n+1 Combat Move Opportunity");
        }
        if(character.getBook()){
            perkView.setText(perkView.getText() + "\nBook Worm - +1 I");
            modifiersView.setText(modifiersView.getText() + "\n+10% XP Gain");
        }
        if(character.getTreasure()){
            perkView.setText(perkView.getText() + "\nTreasure Hunter - +1 L");
            modifiersView.setText(modifiersView.getText() + "\n+15% Gold Gain");
        }
        if(character.getIron()){
            perkView.setText(perkView.getText() + "\nIron Bones - +1 S, + 1 V");
        }
        if(character.getPolitician()){
            perkView.setText(perkView.getText() + "\nPolitician - +1 C");
            modifiersView.setText(modifiersView.getText() + "\n+1 Follower");
        }

        if(mViewModel.isDarkMode()){
            view.setBackgroundColor(getResources().getColor(R.color.darkmode));
            name.setTextColor(getResources().getColor(R.color.rpg_white));
            race.setTextColor(getResources().getColor(R.color.rpg_white));
            levelView.setTextColor(getResources().getColor(R.color.rpg_white));
            strengthView.setTextColor(getResources().getColor(R.color.rpg_white));
            intelView.setTextColor(getResources().getColor(R.color.rpg_white));
            charismaView.setTextColor(getResources().getColor(R.color.rpg_white));
            vitalityView.setTextColor(getResources().getColor(R.color.rpg_white));
            luckView.setTextColor(getResources().getColor(R.color.rpg_white));
            level.setTextColor(getResources().getColor(R.color.rpg_white));
            strength.setTextColor(getResources().getColor(R.color.rpg_white));
            intel.setTextColor(getResources().getColor(R.color.rpg_white));
            charisma.setTextColor(getResources().getColor(R.color.rpg_white));
            vitality.setTextColor(getResources().getColor(R.color.rpg_white));
            luck.setTextColor(getResources().getColor(R.color.rpg_white));
            perkLabel.setTextColor(getResources().getColor(R.color.rpg_white));
            modifiersLabel.setTextColor(getResources().getColor(R.color.rpg_white));
            perkView.setTextColor(getResources().getColor(R.color.rpg_white));
            modifiersView.setTextColor(getResources().getColor(R.color.rpg_white));
        }
        else if(!mViewModel.isDarkMode()){
            view.setBackgroundColor(getResources().getColor(R.color.rpg_white));
            name.setTextColor(getResources().getColor(R.color.darkmode));
            race.setTextColor(getResources().getColor(R.color.darkmode));
            levelView.setTextColor(getResources().getColor(R.color.darkmode));
            strengthView.setTextColor(getResources().getColor(R.color.darkmode));
            intelView.setTextColor(getResources().getColor(R.color.darkmode));
            charismaView.setTextColor(getResources().getColor(R.color.darkmode));
            vitalityView.setTextColor(getResources().getColor(R.color.darkmode));
            luckView.setTextColor(getResources().getColor(R.color.darkmode));
            level.setTextColor(getResources().getColor(R.color.darkmode));
            strength.setTextColor(getResources().getColor(R.color.darkmode));
            intel.setTextColor(getResources().getColor(R.color.darkmode));
            charisma.setTextColor(getResources().getColor(R.color.darkmode));
            vitality.setTextColor(getResources().getColor(R.color.darkmode));
            luck.setTextColor(getResources().getColor(R.color.darkmode));
            perkLabel.setTextColor(getResources().getColor(R.color.darkmode));
            modifiersLabel.setTextColor(getResources().getColor(R.color.darkmode));
            perkView.setTextColor(getResources().getColor(R.color.darkmode));
            modifiersView.setTextColor(getResources().getColor(R.color.darkmode));
        }

        return view;
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

    private void createPDF(View v) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "CharacterSheets");
        if(!dir.exists()) {
            dir.mkdir();
        }
        View screenView = v.getRootView();
        screenView.setDrawingCacheEnabled(true);
        exportbmp = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);

        /*LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout root = (FrameLayout) inflater.inflate(R.layout.fragment_character_sheet, null);
        root.setDrawingCacheEnabled(true);
        exportbmp = getBitmapFromView(getActivity().getWindow().findViewById(R.id.sheetFrameLayout));*/

        File file = new File(dir, mViewModel.getCurrentCharacter().getValue().getName() +
                "_CharacterSheet.pdf");

        try {
            Document doc = new Document();
            doc.setMargins(0,0,0,0);
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            Rectangle rect = new Rectangle(width, height);
            doc.setPageSize(rect);
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            exportbmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            addImage(doc, byteArray);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.scale(752, 1120);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }*/

    private void addImage(Document doc, byte[] byteArray) {
        Image image = null;
        try {
            image = Image.getInstance(byteArray);
            image.setAlignment(Image.ALIGN_CENTER);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            doc.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}