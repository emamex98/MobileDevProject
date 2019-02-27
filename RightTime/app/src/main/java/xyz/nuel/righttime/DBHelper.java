package xyz.nuel.righttime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_FILE = "Users2.db";
    private static final String TABLE = "USER";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_PASSCODE = "passcode";
    private static final String FIELD_LEVEL = "level";
    private static final String FIELD_SCORE = "score";
    //private static final String FIELD_REWARDS = "rewards";
    //private static final String FIELD_PURCHASES = "purchases";


    public DBHelper(Context context){
        super(context, DB_FILE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE + "(" +
                FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_NAME +  " TEXT, " +
                FIELD_EMAIL +  " TEXT, " +
                FIELD_PASSCODE +  " INTEGER, " +
                FIELD_LEVEL +  " INTEGER, " +
                FIELD_SCORE +  " INTEGER)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // prepared statements
        String query = "DROP TABLE IF EXISTS ?";
        String[] params = {TABLE};
        db.execSQL(query, params);

        onCreate(db);
    }

    public void addUser(String name, String email, int passcode){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, name);
        values.put(FIELD_EMAIL, email);
        values.put(FIELD_PASSCODE, passcode);
        values.put(FIELD_LEVEL, 1);
        values.put(FIELD_SCORE, 0);

        db.insert(TABLE, null, values);
    }

    /*public int delete(int id){

        SQLiteDatabase db = getWritableDatabase();

        // clause - clausula - condition for this query to happen
        String clause = FIELD_ID + " = ?";
        String[] args = {id + ""};

        return db.delete(TABLE, clause, args);
    }*/

    public int logIn(String email, int passcode){

        SQLiteDatabase db = getReadableDatabase();
        String clause = FIELD_EMAIL + " = ?";
        String[] args = {email};

        Cursor c = db.query(TABLE, null, clause, args, null, null, null);

        if(c.moveToFirst()){
            if(passcode == c.getInt(3))
                return 1;
        }

        return -1;
    }

    public String getName(String email){

        SQLiteDatabase db = getReadableDatabase();
        String clause = FIELD_EMAIL + " = ?";
        String[] args = {email};

        Cursor c = db.query(TABLE, null, clause, args, null, null, null);

        if(c.moveToFirst())
            return c.getString(1);

        return "friend";
    }

    public int getLevel(String email){

        SQLiteDatabase db = getReadableDatabase();
        String clause = FIELD_EMAIL + " = ?";
        String[] args = {email};

        Cursor c = db.query(TABLE, null, clause, args, null, null, null);

        if(c.moveToFirst())
            return c.getInt(4);

        return 0;
    }

    public int getScore(String email){

        SQLiteDatabase db = getReadableDatabase();
        String clause = FIELD_EMAIL + " = ?";
        String[] args = {email};

        Cursor c = db.query(TABLE, null, clause, args, null, null, null);

        if(c.moveToFirst())
            return c.getInt(5);

        return 0;
    }

    /*public void updateScore(String email){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        db.update(TABLE, values, "email = " + email, null);
    }*/
}

