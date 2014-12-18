package com.bradenhart.navigation;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditFragment extends Fragment {

    Spinner daySelect, entryType, startTime, endTime;
    AutoCompleteTextView paperCode, roomCode;
    ArrayList<TimetableEntry> Entries = new ArrayList<>();
    Button addButton, doneButton;

    // Default constructor
    public EditFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        /* Get the Spinners and Views */
        getWidgets(v);
        /* Force typed input to be uppercase */
        forceInput();
        /* Add options to dropdown menus */
        addOptions();
        /* Add some imeOptions */
        addIMEOptions();
        /* Dealing with button presses */
        setUpButtons();

        return v;
    }

    /* To check whether an entry exists already (same day, paper, room, type, start, end time) */
    public boolean entryExists(TimetableEntry entry) {
        String newEntry = entry.getAllEntryInfo();
        for (int i = 0; i < Entries.size(); i++) {
            if (newEntry.compareToIgnoreCase(Entries.get(i).getAllEntryInfo()) == 0) return true;
        }
        return false;
    }

    /* ----------------------------------------------------------- */
    /* Methods to add options to Spinners and AutoComplete widgets */
    /* ----------------------------------------------------------- */
    private void addOptionsToDaySpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.weekday_full, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void addOptionsToPaperCodeAutoComplete(AutoCompleteTextView paperCode) {
        ArrayAdapter<CharSequence> paperCodeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.paper_codes, android.R.layout.simple_dropdown_item_1line);
        paperCode.setAdapter(paperCodeAdapter);

    }

    private void addOptionsToRoomCodeAutoComplete(AutoCompleteTextView roomCode) {
        ArrayAdapter<CharSequence> roomCodeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.room_codes, android.R.layout.simple_dropdown_item_1line);
        roomCode.setAdapter(roomCodeAdapter);
    }

    private void addOptionsToScheduleTypeSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.schedule_type, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void addOptionsToStartTimeSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.start_time_12hrs, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void addOptionsToEndTimeSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.end_time_12hrs, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void clearTextViews(AutoCompleteTextView paperCode, AutoCompleteTextView roomCode) {
        paperCode.setText("");
        roomCode.setText("");
    }

    private void resetSpinners(Spinner daySelect, Spinner entryType, Spinner startTime, Spinner endTime) {
        daySelect.setSelection(0, true);
        startTime.setSelection(0, true);
        entryType.setSelection(0, true);
        endTime.setSelection(0, true);
    }

    private void getWidgets(View v) {
        daySelect = (Spinner) v.findViewById(R.id.day_select_spinner);
        paperCode = (AutoCompleteTextView) v.findViewById(R.id.paper_code_complete);
        roomCode = (AutoCompleteTextView) v.findViewById(R.id.room_code_complete);
        entryType = (Spinner) v.findViewById(R.id.schedule_type_spinner);
        startTime = (Spinner) v.findViewById(R.id.startTimeSpinner);
        endTime = (Spinner) v.findViewById(R.id.endTimeSpinner);
        addButton = (Button) v.findViewById(R.id.addButton);
        doneButton = (Button) v.findViewById(R.id.doneButton);
    }

    private void addOptions() {
        addOptionsToDaySpinner(daySelect);
        addOptionsToPaperCodeAutoComplete(paperCode);
        addOptionsToRoomCodeAutoComplete(roomCode);
        addOptionsToScheduleTypeSpinner(entryType);
        addOptionsToStartTimeSpinner(startTime);
        addOptionsToEndTimeSpinner(endTime);
    }

    private void forceInput() {
        paperCode.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        roomCode.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }


    private void addIMEOptions() {
        paperCode.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        roomCode.setImeOptions(EditorInfo.IME_ACTION_PREVIOUS);
    }

    private void setUpButtons() {
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
                    Entries.add(newEntry);
                    Toast.makeText(getActivity(), "Entry successfully added", Toast.LENGTH_LONG).show();
                    /*if (timeClash(newEntry) == null) { // no clash was found
                        Entries.add(newEntry);
                        Toast.makeText(getActivity(), "Entry successfully added", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), timeClash(newEntry), Toast.LENGTH_LONG).show();
                    }*/
                } else {
                    Toast.makeText(getActivity(), "This entry is already in the timetable. Add a different entry or press DONE if you are finished.", Toast.LENGTH_LONG).show();
                }

                clearTextViews(paperCode, roomCode);
                resetSpinners(daySelect, entryType,startTime, endTime);
                // ArrayList has been updated with the entries that the user wants added to their timetable.
                // Now the onClick method for DONE can pass the arraylist with the Intent
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //ENTRIES arraylist is never null
                Fragment ttFragment = new TimetableFragment();
                Bundle bundle = new Bundle();

                // Intent doneIntent = new Intent(EditFragment.this, MainActivity.class);
                if (Entries.size() == 0) {
                    Toast.makeText(getActivity(), "size is 0", Toast.LENGTH_SHORT).show();
                }
                if (Entries.size() > 0) {
                    bundle.putParcelableArrayList("entries", Entries);
                    //doneIntent.putParcelableArrayListExtra("entries", Entries);
                }
                ttFragment.setArguments(bundle);

                // create a transaction, replace the current fragment with the home fragment, add to back stack and commit.
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, ttFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    private int determineDay(Spinner day) {
        String chosenDay = String.valueOf(day.getSelectedItem());
        // int col_width = (int) (getResources().getDimension(R.dimen.column_width) / getResources().getDisplayMetrics().density);
        int dayVal;

        switch (chosenDay) {
            case "Monday":
                dayVal = 1;
                break;
            case "Tuesday":
                dayVal = 2;
                break;
            case "Wednesday":
                dayVal = 3;
                break;
            case "Thursday":
                dayVal = 4;
                break;
            case "Friday":
                dayVal = 5;
                break;
            default:
                dayVal = -1;
                break;
        }
        return dayVal;
    }

    private int determineStartTime(Spinner start) {
        String startChoice = String.valueOf(start.getSelectedItem());
        int y;
        switch (startChoice) {
            case "8am":
                y = 0;
                break;
            case "9am":
                y = 60;
                break;
            case "10am":
                y = 120;
                break;
            case "11am":
                y = 180;
                break;
            case "12pm":
                y = 240;
                break;
            case "1pm":
                y = 300;
                break;
            case "2pm":
                y = 360;
                break;
            case "3pm":
                y = 420;
                break;
            case "4pm":
                y = 480;
                break;
            case "5pm":
                y = 540;
                break;
            case "6pm":
                y = 600;
                break;
            case "7pm":
                y = 660;
                break;
            default:
                y = -1;
                break;
        }
        return y;
    }

    private int determineEndTime(Spinner end) {
        String endChoice = String.valueOf(end.getSelectedItem());
        int y;
        switch (endChoice) {
            case "9am":
                y = 60;
                break;
            case "10am":
                y = 120;
                break;
            case "11am":
                y = 180;
                break;
            case "12pm":
                y = 240;
                break;
            case "1pm":
                y = 300;
                break;
            case "2pm":
                y = 360;
                break;
            case "3pm":
                y = 420;
                break;
            case "4pm":
                y = 480;
                break;
            case "5pm":
                y = 540;
                break;
            case "6pm":
                y = 600;
                break;
            case "7pm":
                y = 660;
                break;
            case "8pm":
                y = 720;
                break;
            default:
                y = -1;
                break;
        }
        return y;
    }

    private int determineDuration(int start, int end) {
        return end - start;
    }

}
