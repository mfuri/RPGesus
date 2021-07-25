package com.cop4656.rpgesus;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class CharacterContentProvider extends ContentProvider {
    public final static String DBNAME = "CharacterDB";

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    };

    public final static String TABLE_NAMESTABLE = "Characters";
    public final static String COLUMN_NAME = "Name";
    public final static String COLUMN_LEVEL = "Level";
    public final static String COLUMN_RACE = "Race";
    public final static String COLUMN_STRENGTH= "Strength";
    public final static String COLUMN_INTELLIGENCE = "Intelligence";
    public final static String COLUMN_CHARISMA = "Charisma";
    public final static String COLUMN_VITALITY = "Vitality";
    public final static String COLUMN_LUCK = "Luck";
    public final static String COLUMN_AVATAR = "Avatar";
    //THE FOLLOWING PERK COLUMNS WILL BE STRINGS BUT WILL ONLY EVER BE "TRUE" OR "FALSE"
    public final static String COLUMN_WIZARD = "Wizard";
    public final static String COLUMN_GAMBLER = "Gambler";
    public final static String COLUMN_UNCIVILIZED = "Uncivilized";
    public final static String COLUMN_HEARTBREAKER = "Heartbreaker";
    public final static String COLUMN_MARATHON = "Marathon";
    public final static String COLUMN_BOOK = "Book";
    public final static String COLUMN_TREASURE = "Treasure";
    public final static String COLUMN_IRON = "Iron";
    public final static String COLUMN_POLITICIAN = "Politican";



    public static final String AUTHORITY = "com.cop4656.rpgesus";
    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY +"/" + TABLE_NAMESTABLE);

    private MainDatabaseHelper mOpenHelper;

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            TABLE_NAMESTABLE +  // Table's name
            "(" +               // The columns in the table
            " _id INTEGER PRIMARY KEY, " +
            COLUMN_NAME +
            " TEXT," +
            COLUMN_RACE +
            " TEXT,"+
            COLUMN_STRENGTH +
            " TEXT,"+
            COLUMN_INTELLIGENCE +
            " TEXT,"+
            COLUMN_CHARISMA +
            " TEXT,"+
            COLUMN_VITALITY +
            " TEXT,"+
            COLUMN_LEVEL +
            " TEXT,"+
            COLUMN_AVATAR +
            " TEXT,"+
            COLUMN_LUCK +
            " TEXT,"+
            COLUMN_WIZARD +
            " TEXT,"+
            COLUMN_GAMBLER +
            " TEXT,"+
            COLUMN_UNCIVILIZED +
            " TEXT,"+
            COLUMN_HEARTBREAKER +
            " TEXT,"+
            COLUMN_BOOK +
            " TEXT,"+
            COLUMN_TREASURE +
            " TEXT,"+
            COLUMN_IRON +
            " TEXT,"+
            COLUMN_POLITICIAN +
            " TEXT)";

    public CharacterContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().delete(TABLE_NAMESTABLE, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String name = values.getAsString(COLUMN_NAME).trim();
        String race = values.getAsString(COLUMN_RACE).trim();
        //String avatar = null; //values.getAsString(COLUMN_AVATAR).trim();
        String strength = values.getAsString(COLUMN_STRENGTH).trim();
        String intel = values.getAsString(COLUMN_INTELLIGENCE).trim();
        String charisma = values.getAsString(COLUMN_CHARISMA).trim();
        String vitality = values.getAsString(COLUMN_VITALITY).trim();
        String luck = values.getAsString(COLUMN_LUCK).trim();
        String level = values.getAsString(COLUMN_LEVEL).trim();
        //PERKS
        String wizard = values.getAsString(COLUMN_WIZARD).trim();
        String gambler = values.getAsString(COLUMN_GAMBLER).trim();
        String uncivilized = values.getAsString(COLUMN_UNCIVILIZED).trim();
        String heartbreaker = values.getAsString(COLUMN_HEARTBREAKER).trim();
        String marathon = values.getAsString(COLUMN_MARATHON).trim();
        String book = values.getAsString(COLUMN_BOOK).trim();
        String treasure = values.getAsString(COLUMN_TREASURE).trim();
        String iron = values.getAsString(COLUMN_IRON).trim();
        String politician = values.getAsString(COLUMN_POLITICIAN).trim();


        if(name.equals("")){ return null;}
        if(race.equals("")){ return null;}
        //if(avatar.equals("")){ return null; }
        if(strength.equals("")){ return null;}
        if(intel.equals("")){ return null;}
        if(charisma.equals("")){ return null;}
        if(vitality.equals("")){ return null;}
        if(luck.equals("")){ return null;}
        if (level.equals("")) { return null;}
        if(wizard.equals("")){ return null;}
        if(gambler.equals("")){ return null;}
        if(uncivilized.equals("")){ return null;}
        if(heartbreaker.equals("")){ return null;}
        if(marathon.equals("")){ return null;}
        if(book.equals("")){ return null;}
        if(treasure.equals("")){ return null;}
        if(iron.equals("")){ return null;}
        if(politician.equals("")){ return null;}

        long id = mOpenHelper.getWritableDatabase().insert(TABLE_NAMESTABLE, null, values);

        return Uri.withAppendedPath(CONTENT_URI, ""+"");
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper((getContext()));
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(TABLE_NAMESTABLE, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().update(TABLE_NAMESTABLE, values, selection, selectionArgs);
    }
}