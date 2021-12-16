package pl.ofalwoz.crimeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    CrimeLab mCrimeLab;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CrimeDB.db";

    public static final String TABLE_NAME = "crimes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CRIME_ID = "crime_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_SOLVED = "solved";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mCrimeLab = CrimeLab.get(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("[INFO] Initialized empty crime database.");
        String INITIALIZE_TABLE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s BOOLEAN)", TABLE_NAME, COLUMN_ID, COLUMN_CRIME_ID, COLUMN_TITLE, COLUMN_DATE, COLUMN_SOLVED
        );
        db.execSQL(INITIALIZE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(db);
    }

    public Cursor getCrimes() {
        String q = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(q, null);
    }

    public Cursor getCrime(int id) {
        String uuidString = Integer.toString(id);
        String q = String.format("SELECT * FROM %s WHERE = ?", TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(q, new String[]{uuidString});
    }

    public void addCrime(Crime crime) {
        ContentValues v = new ContentValues();
        v.put(COLUMN_CRIME_ID, CrimeLab.mCrimes.size());
        v.put(COLUMN_TITLE, "New Crime");
        v.put(COLUMN_DATE, new Date().toString());
        v.put(COLUMN_SOLVED, false);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, v);
        db.close();

        mCrimeLab.newCrime(crime);
    }

    public void deleteCrime(int id) {
        String uuidString = Integer.toString(id);
        String q = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_CRIME_ID, uuidString);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_CRIME_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();
        mCrimeLab.deleteCrime(id);
    }

    public void updateCrime(int id, String title, Date date, boolean solved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, date.toString());
        cv.put(COLUMN_SOLVED, solved);
        db.update(TABLE_NAME, cv, COLUMN_CRIME_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }
}
