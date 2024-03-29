package sg.edu.rp.c346.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName, etTelephone, etSize, etDate, etTime;
    CheckBox checkBox;
    DatePicker datePicker;
    TimePicker timePicker;
    Button btReserve, btReset;

    int day,month, year,hour,minute;

    String dateS, timeS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Reservation");

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        etDate = findViewById(R.id.editTextDate);
        etTime = findViewById(R.id.editTextTime);

        checkBox = findViewById(R.id.checkBox);

//        datePicker = findViewById(R.id.datePicker);
//        timePicker = findViewById(R.id.timePicker);

        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);


        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                } else {
                    isSmoke = "non-smoking";
                }

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage("New Reservation"
                        +"\nName: "+etName.getText().toString()
                        +"\nSmoking: "+isSmoke
                        +"\nSize: "+etSize.getText().toString()
                        +"\nDate: "+etDate.getText().toString()
                        +"\nTime: "+etTime.getText().toString());
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                myBuilder.setNeutralButton("Cancel",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

//                Toast.makeText(MainActivity.this,
//                        "Hi, " + etName.getText().toString() + ", you have booked a "
//                                + etSize.getText().toString() + " person(s) "
//                                + isSmoke + " table on "
//                                + datePicker.getYear() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth() + " at "
//                                + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ". Your phone number is "
//                                + etTelephone.getText().toString() + ".",
//                        Toast.LENGTH_LONG).show();
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText(null);
                etTelephone.setText(null);
                etSize.setText(null);
                checkBox.setChecked(false);
//                datePicker.updateDate(year, month, day);
//                timePicker.setCurrentHour(hour);
//                timePicker.setCurrentMinute(minute);
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateS = dayOfMonth + "/" + (month + 1) + "/" + year;
                        etDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                };

                Calendar cal = Calendar.getInstance();

                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, year,month,day);
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeS = hourOfDay + ":" + minute;
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };

                Calendar cal = Calendar.getInstance();

                hour = cal.get(Calendar.HOUR);
                minute = cal.get(Calendar.MINUTE);

                TimePickerDialog myTimerDialog = new TimePickerDialog(MainActivity.this, myTimeListener, hour, minute, false );
                myTimerDialog.show();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String getName = prefs.getString("Name",null);
        String getTele = prefs.getString("Tele",null);
        String getSize = prefs.getString("Size", null);
        String getDate = prefs.getString("Date",null);
        String getTime = prefs.getString("Time", null);
        Boolean getSmoke = prefs.getBoolean("Smoke",false);


        etName.setText(getName);
        etTelephone.setText(getTele);
        etSize.setText(getSize);
        etDate.setText(getDate);
        etTime.setText(getTime);
        checkBox.setChecked(getSmoke);
    }

    @Override
    protected void onPause(){
        super.onPause();

        String shareName = etName.getText().toString();
        String shareTele = etTelephone.getText().toString();
        String shareSize = etSize.getText().toString();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("Name",shareName);
        prefEdit.putString("Tele",shareTele);
        prefEdit.putString("Size",shareSize);
        prefEdit.putString("Date",dateS);
        prefEdit.putString("Time",timeS);
        prefEdit.putBoolean("Smoke",checkBox.isChecked());

        prefEdit.commit();
    }


}