package pl.ofalwoz.crimeapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CrimeActivity extends AppCompatActivity {

    Snackbar info;
    public TextView Title;
    public int Id;
    public TextView idView;
    public boolean solveView;
    Switch simpleSwitch;
    public Date newDate = new Date();
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button timeButton;
    int hour, minute;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        Title = findViewById(R.id.title);
        idView = findViewById(R.id.idView);
        simpleSwitch = (Switch) findViewById(R.id.solved);
        initDatePicker();
        dateButton = findViewById(R.id.date);
        dateButton.setText("Set date");

        timeButton = findViewById(R.id.time);
        timeButton.setText("Set time");

        Title.setText(getIntent().getStringExtra("title"));
        Id = getIntent().getIntExtra("id", R.id.idView);
        solveView = getIntent().getExtras().getBoolean("solve");

        simpleSwitch.setChecked(solveView);
        idView.setText("Id: "+Id);
    }
    
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                newDate.setDate(day);
                newDate.setMonth(month);
                newDate.setYear(year - 1900);
            }
        };
        Calendar cel = Calendar.getInstance();
        int year = cel.get(Calendar.YEAR);
        int month = cel.get(Calendar.MONTH);
        int day = cel.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "." + month + "." + year;
    }

    public void date(View view) {
        datePickerDialog.show();
    }

    public void time(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minute = minute;
                newDate.setHours(hour);
                newDate.setMinutes(minute);
                timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style, onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
    }

    public void setCrime(View view) {
        info= Snackbar.make(idView, "Chenges have been sent!", Snackbar.LENGTH_SHORT);
        info.show();
        CrimeLab.get(this).updateCrime(Id, Title.getText().toString(), simpleSwitch.isChecked(), newDate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CrimeLab.get(this).updateCrime(Id,Title.getText().toString(),simpleSwitch.isChecked(), newDate);
    }

    public void delCrime(View view) {
        CrimeLab.get(this).deleteCrime(Id);
        finish();
    }
}