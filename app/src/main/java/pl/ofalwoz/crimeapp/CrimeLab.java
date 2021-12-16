package pl.ofalwoz.crimeapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrimeLab{

    private static CrimeLab sCrimeLab;

    public static List<Crime> mCrimes;
    private DBHandler mDbHandler;

    public int getSize() { return mCrimes.size(); }

    public static CrimeLab get(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
    }

    public Crime getCrime(int id) {
        for (Crime crime : mCrimes) {
            if (crime.getId() == id) {
                return crime;
            }
        }
        return null;
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public void updateCrime(int id, String title, boolean solve, Date date){
        for(Crime crime : mCrimes){
            if(crime.getId() == id){
                crime.setTitle(title);
                crime.setSolved(solve);
                crime.setDate(date);
            }
        }
    }

    public void deleteCrime(int id){
        int i = 0;
        for(Crime crime: mCrimes){
            if(crime.getId() == id) {
                mCrimes.remove(crime);
                break;
            }
        }
    }

    public void newCrime(Crime crime){
        crime.setId(mCrimes.size());
        crime.setTitle("New crime");
        crime.setSolved(false);
        crime.setDate(new Date());
        mCrimes.add(crime);
    }
}