package pl.ofalwoz.crimeapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "pl.edu.uwr.pum.recyclerviewwordlistjava.MESSAGE";
    private RecyclerView recyclerView;
    private WordListAdapter crimeListAdapter;
    public List<Crime> crimeList = CrimeLab.get(this).getCrimes();

    private CrimeLab mCrimeLab;
    private DBHandler DbHandler;
    private SearchView svCrimeSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHandler = new DBHandler(this);
        mCrimeLab.get(this);
        initialize();

        recyclerView = findViewById(R.id.recyclerView);
        crimeListAdapter = new WordListAdapter(this, crimeList);
        recyclerView.setAdapter(crimeListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        svCrimeSearcher = findViewById(R.id.search);

        svCrimeSearcher.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                crimeListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        svCrimeSearcher.setOnCloseListener(() -> {
            initialize();
            crimeListAdapter.notifyDataSetChanged();
            return false;
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        crimeListAdapter.notifyDataSetChanged();
    }

    public void add_crime(View view) {
        Crime crime = new Crime();
        DbHandler.addCrime(crime);
        crimeListAdapter.notifyDataSetChanged();
    }

    private void initialize() {
        Cursor c = DbHandler.getCrimes();
        crimeList.clear();

        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                Crime crime = new Crime();
                crime.setId(Integer.parseInt(c.getString(1)));
                crime.setTitle(c.getString(2));
                crime.setDate(new Date(c.getString(3)));
                int solved = c.getInt(4);
                boolean s = solved > 0 ? true : false;
                crime.setSolved(s);
                crimeList.add(crime);
            }
        }
    }
}