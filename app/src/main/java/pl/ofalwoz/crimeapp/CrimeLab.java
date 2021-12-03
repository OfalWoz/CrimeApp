package pl.ofalwoz.crimeapp;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    public static List<Crime> mCrimes;

    public int getSize() { return mCrimes.size(); }

    public static CrimeLab get(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #"+i);
            crime.setSolved(i%2==0);
            crime.setId(i);
            crime.setDate(new Date());
            mCrimes.add(crime);
        }
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
        crime.setTitle("New Crime");
        crime.setSolved(false);
        crime.setId(mCrimes.size());
        crime.setDate(new Date());
        mCrimes.add(crime);
    }
}