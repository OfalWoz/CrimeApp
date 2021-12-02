package pl.ofalwoz.crimeapp;

import java.util.Date;

public class Crime {
    private int mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public void setTitle(String s) { this.mTitle = s; }

    public String getTitle() { return mTitle; }

    public void setSolved(boolean b) { this.mSolved = b; }

    public boolean getSolved() { return mSolved; }

    public int getId() { return mId; }

    public void setId(int mId) { this.mId = mId; }

    public void setDate(Date date) { this.mDate = date; }

    public Date getDate() { return mDate; }
}