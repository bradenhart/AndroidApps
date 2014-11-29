package com.example.bradenhart.myunitimetable;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class EditTextViewLayout extends Activity implements AdapterView.OnItemClickListener {

    AutoCompleteTextView paperCode, roomCode;
    Button downButton;
    Spinner daySelect, entryType, startTime, endTime;
    ArrayList<TimetableEntry> Entries = new ArrayList<TimetableEntry>(); //if this works then change to List<T> name = ArrayList<T>()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_textview);

        daySelect = (Spinner) findViewById(R.id.day_select_spinner);
        paperCode = (AutoCompleteTextView) findViewById(R.id.paper_code_complete);
        roomCode = (AutoCompleteTextView) findViewById(R.id.room_code_complete);
        entryType = (Spinner) findViewById(R.id.schedule_type_spinner);
        startTime = (Spinner) findViewById(R.id.startTimeSpinner);
        endTime = (Spinner) findViewById(R.id.endTimeSpinner);

        //Force typed input to be uppercase
        paperCode.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        roomCode.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        /* ADD OPTIONS TO DROPDOWN MENUS */
        addOptionsToDaySpinner();
        addOptionsToPaperCodeAutoComplete();
        addOptionsToRoomCodeAutoComplete();
        addOptionsToScheduleTypeSpinner();
        addOptionsToStartTimeSpinner();
        addOptionsToEndTimeSpinner();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paper = String.valueOf(paperCode.getText());
                String room = String.valueOf(roomCode.getText());
                String type = String.valueOf(entryType.getSelectedItem());
                int the_day = determineDay(daySelect);
                int start_time = determineStartTime(startTime);
                int end_time = determineEndTime(endTime);
                int the_duration = determineDuration(start_time, end_time);

                TimetableEntry newEntry = new TimetableEntry(the_day, paper, room, type, start_time, the_duration);

                if (!entryExists(newEntry)) { //timetable has entries, newEntry already exists in timetable
                    if (timeClash(newEntry) == null) {
                        Entries.add(newEntry);
                    } else {
                        Toast.makeText(getApplicationContext(), "There is already an entry in the timetable that clashes with the " + timeClash(newEntry), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "This entry is already in the timetable. Add a different entry or press DONE if you are finished.", Toast.LENGTH_LONG).show();
                }

                clearTextViews(paperCode, roomCode);
                // ArrayList has been updated with the entries that the user wants added to their timetable.
                // Now the onClick method for DONE can pass the arraylist with the Intent
            }
        });

        Button doneCheckButton = (Button) findViewById(R.id.done_checkButton);
        doneCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //ENTRIES arraylist is never null
                Intent doneIntent = new Intent(EditTextViewLayout.this, MainActivity.class);
                if (Entries.size() == 0) {
                    Toast.makeText(getApplicationContext(), "size is 0", Toast.LENGTH_SHORT).show();
                } else if (Entries.size() > 0) {
                    int s = Entries.size();
                    String str = Entries.get(s-1).getFullString();
                    Toast.makeText(getApplicationContext(), String.format("size is %d: %s", Entries.size(), str), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }

                if (Entries.size() > 0) {
                    doneIntent.putParcelableArrayListExtra("entries", Entries);
                }
                startActivity(doneIntent);
            }
        });

    }

    /* To check whether an entry exists already (same day, paper, room, type, start, end time) */
    public boolean entryExists(TimetableEntry entry) {
        String newEntry = entry.getAllEntryInfo();
        for (int i = 0; i < Entries.size(); i++) {
            if (newEntry.compareToIgnoreCase(Entries.get(i).getAllEntryInfo()) == 0) return true;
        }
        return false;
    }

    /* To make sure an entry doesn't get added at the same start time or end time as another, check, then Toast "an entry already exists at this time" "... clash"  */
    public String timeClash(TimetableEntry newEntry) {
        int startTime = newEntry.getStartTime();
        int endTime = newEntry.getEndTime();
        String message = null;

        for (int i = 0; i < Entries.size(); i++) {
            if (Entries.get(i).getStartTime() == startTime && Entries.get(i).getEndTime() == endTime) { message = "start and end times"; }
            else if (Entries.get(i).getStartTime() == startTime) { message = "start time"; }
            else if (Entries.get(i).getEndTime() == endTime) { message = "end time"; }
        }

        return message;
    }

    private int getPixelsFromDP(int dipValue) {
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
        return px;
    }

    private int getPixelsFromSP(int spValue) {
        Resources r = getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, r.getDisplayMetrics());
        return px;
    }

    private int determineDay(Spinner day) {
        String chosenDay = String.valueOf(day.getSelectedItem());
        int x;

        if (chosenDay.equals("Monday")) {
            x = 45;

        } else if (chosenDay.equals("Tuesday")) {
            x = 118;

        } else if (chosenDay.equals("Wednesday")) {
            x = 191;

        } else if (chosenDay.equals("Thurday")) {
            x = 264;

        } else if (chosenDay.equals("Friday")) {
            x = 337;

        } else {
            x = -1;

        }
        return x;
    }

    private int determineStartTime(Spinner start) {
        String startChoice = String.valueOf(start.getSelectedItem());
        int y;
        if (startChoice.equals("8am")) {
            y = 0;
        } else if (startChoice.equals("9am")) {
            y = 100;
        } else if (startChoice.equals("10am")) {
            y = 200;
        } else if (startChoice.equals("11am")) {
            y = 300;
        } else if (startChoice.equals("12pm")) {
            y = 400;
        } else if (startChoice.equals("1pm")) {
            y = 500;
        } else if (startChoice.equals("2pm")) {
            y = 600;
        } else if (startChoice.equals("3pm")) {
            y = 700;
        } else if (startChoice.equals("4pm")) {
            y = 800;
        } else if (startChoice.equals("5pm")) {
            y = 900;
        } else if (startChoice.equals("6pm")) {
            y = 1000;
        } else if (startChoice.equals("7pm")) {
            y = 1100;
        } else {
            y = -1;
        }
        return y;
    }

    private int determineEndTime(Spinner end) {
        String endChoice = String.valueOf(end.getSelectedItem());
        int y;
        if (endChoice.equals("9am")) {
            y = 100;
        } else if (endChoice.equals("10am")) {
            y = 200;
        } else if (endChoice.equals("11am")) {
            y = 300;
        } else if (endChoice.equals("12pm")) {
            y = 400;
        } else if (endChoice.equals("1pm")) {
            y = 500;
        } else if (endChoice.equals("2pm")) {
            y = 600;
        } else if (endChoice.equals("3pm")) {
            y = 700;
        } else if (endChoice.equals("4pm")) {
            y = 800;
        } else if (endChoice.equals("5pm")) {
            y = 900;
        } else if (endChoice.equals("6pm")) {
            y = 1000;
        } else if (endChoice.equals("7pm")) {
            y = 1100;
        } else if (endChoice.equals("8pm")) {
            y = 1200;
        } else {
            y = -1;
        }
        return y;
    }

    private int determineDuration(int start, int end) {
        int duration = end - start;
        return duration;
    }

    /*****************************************************************/
    /** Methods to add options to Spinners and AutoComplete widgets **/
    /*****************************************************************/
    public void addOptionsToDaySpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.day_select_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.weekday_full, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    public void addOptionsToPaperCodeAutoComplete() {
        AutoCompleteTextView paperCode = (AutoCompleteTextView) findViewById(R.id.paper_code_complete);
        ArrayAdapter<CharSequence> paperCodeAdapter = ArrayAdapter.createFromResource(this,
                R.array.paper_codes, android.R.layout.simple_dropdown_item_1line);
        paperCode.setAdapter(paperCodeAdapter);

    }

    public void addOptionsToRoomCodeAutoComplete() {
        AutoCompleteTextView roomCode = (AutoCompleteTextView) findViewById(R.id.room_code_complete);
        ArrayAdapter<CharSequence> roomCodeAdapter = ArrayAdapter.createFromResource(this,
                R.array.room_codes, android.R.layout.simple_dropdown_item_1line);
        roomCode.setAdapter(roomCodeAdapter);
    }

    public void addOptionsToScheduleTypeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.schedule_type_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.schedule_type, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    public void addOptionsToStartTimeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.startTimeSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.start_time_12hrs, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    public void addOptionsToEndTimeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.endTimeSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.end_time_12hrs, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    public void clearTextViews(AutoCompleteTextView paperCode, AutoCompleteTextView roomCode) {
        paperCode.setText("");
        roomCode.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

}




