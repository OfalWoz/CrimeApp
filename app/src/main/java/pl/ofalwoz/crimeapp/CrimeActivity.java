package pl.ofalwoz.crimeapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrimeActivity extends AppCompatActivity {

    public List<Crime> crimes =  CrimeLab.get(this).getCrimes();
    public int Id;
    public Date newDate = new Date();
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button timeButton;
    int hour, minute;
    public String date = "Set Date";
    public String time = "Set Time";

    private ViewPager2 viewPager2;
    private int crimePos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_view_pager2);
        viewPager2 = findViewById(R.id.detail_view_pager);
        CrimeActivityAdapter adapter = new CrimeActivityAdapter();
        viewPager2.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Id = getIntent().getIntExtra("id",0);
            crimePos = getIntent().getIntExtra("position",0);
        }
        viewPager2.setCurrentItem(crimePos, false);
    }

    @SuppressLint("WrongViewCast")
    public void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                //date = makeDateString(day, month, year);
                newDate.setDate(day);
                newDate.setMonth(month - 1);
                newDate.setYear(year - 1900);
            }
        };
        Calendar cel = Calendar.getInstance();
        int year = cel.get(Calendar.YEAR);
        int month = cel.get(Calendar.MONTH);
        int day = cel.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    public String makeDateString(int day, int month, int year) {
        return day + "." + month + "." + year;
    }

    public void time(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minute = minute;
                newDate.setHours(hour);
                newDate.setMinutes(minute);
                //time = String.format(Locale.getDefault(),"%02d:%02d",hour,minute);
            }
        };
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style, onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
    }

    public class CrimeActivityAdapter extends RecyclerView.Adapter<CrimeActivityAdapter.CrimeViewHolder> {

        public class CrimeViewHolder extends RecyclerView.ViewHolder {
            public Date newDate;
            private EditText Title;
            private TextView viewId;
            private Button crimeDate;
            private Button crimeTime;
            private Switch crimeSolve;

            private Button First;
            private Button Last;

            CrimeActivityAdapter adapter;

            public CrimeViewHolder(@NonNull View itemView, CrimeActivityAdapter adapter){
                super(itemView);
                Title = itemView.findViewById(R.id.title);
                crimeDate = itemView.findViewById(R.id.date);
                crimeTime = itemView.findViewById(R.id.time);
                crimeSolve = itemView.findViewById(R.id.solved);
                First = itemView.findViewById(R.id.first);
                Last = itemView.findViewById(R.id.last);
                this.adapter = adapter;

                crimeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initDatePicker();
                        crimeDate.setText("Date seted!"); //niestety pokazywanie wybranej daty i godziny dzialalo z opoznieniem dlatego ustawilem tylko zmiane napisu
                    }
                });

                crimeTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        time(view);
                        crimeTime.setText("Time seted!");
                    }
                });

            }
        }

        @NonNull
        @Override
        public CrimeActivityAdapter.CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CrimeViewHolder(
                    (LayoutInflater.from(CrimeActivity.this).inflate(
                            R.layout.activity_crime,
                            parent,
                            false
                    )),
                    this
            );
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeActivityAdapter.CrimeViewHolder holder, int position) {
            Crime current = crimes.get(position);
            holder.Title.setText(current.getTitle());
            holder.crimeDate.setText(date);
            holder.crimeTime.setText(time);
            holder.crimeSolve.setChecked(current.getSolved());
            newDate = new Date();
            Id = crimes.get(position).getId();
            crimePos = viewPager2.getCurrentItem();
            holder.Title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    current.setTitle(holder.Title.getText().toString());
                }
            });
            holder.crimeDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    holder.newDate = newDate;
                }
                @Override
                public void afterTextChanged(Editable s) {
                    current.setDate(holder.newDate);
                }
            });
            holder.crimeTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    holder.newDate = newDate;
                }

                @Override
                public void afterTextChanged(Editable s) {
                    holder.newDate = newDate;
                    current.setDate(holder.newDate);
                }
            });
            holder.crimeSolve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    current.setSolved(holder.crimeSolve.isChecked());
                }
            });
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void First(View view){
        viewPager2.setCurrentItem(0);
    }

    public void Last(View view){
        viewPager2.setCurrentItem(crimes.size());
    }

    public void delCrime(View view) {
        CrimeLab.get(this).deleteCrime(Id);
        finish();
    }
}