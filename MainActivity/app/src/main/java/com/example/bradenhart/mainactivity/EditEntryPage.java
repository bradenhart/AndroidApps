package com.example.bradenhart.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class EditEntryPage extends ActionBarActivity implements AdapterView.OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entry_page);



        addOptionsToPaperCodeAutoComplete();
        addOptionsToRoomCodeAutoComplete();
        addItemsToScheduleTypeSpinner();

        addListenerToPaperCodeAutoComplete();
        addListenerToRoomCodeAutoComplete();
        addListenerToScheduleTypeSpinner();

        Button preview = (Button) findViewById(R.id.preview_button);


    }



    public void addOptionsToPaperCodeAutoComplete() {
        AutoCompleteTextView paperCode = (AutoCompleteTextView) findViewById(R.id.paper_code_complete);
        ArrayAdapter<CharSequence> paperCodeAdapter = ArrayAdapter.createFromResource(this,
                R.array.paper_codes, android.R.layout.simple_dropdown_item_1line);
        paperCode.setAdapter(paperCodeAdapter);

    }

    public void addListenerToPaperCodeAutoComplete() {
        AutoCompleteTextView paperCode = (AutoCompleteTextView) findViewById(R.id.paper_code_complete);

    }

    public void addOptionsToRoomCodeAutoComplete() {
        AutoCompleteTextView roomCode = (AutoCompleteTextView) findViewById(R.id.room_code_complete);
        ArrayAdapter<CharSequence> roomCodeAdapter = ArrayAdapter.createFromResource(this,
                R.array.room_codes, android.R.layout.simple_dropdown_item_1line);
        roomCode.setAdapter(roomCodeAdapter);
    }

    public void addListenerToRoomCodeAutoComplete() {
        AutoCompleteTextView roomCode = (AutoCompleteTextView) findViewById(R.id.room_code_complete);

    }

    public void addItemsToScheduleTypeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.schedule_type);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.schedule_type, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);
    }

    public void addListenerToScheduleTypeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.schedule_type);

    }

    /*public void previewButtonPressed(final String paper, final String room, final String schedule) {
        Button previewButton = (Button) findViewById(R.id.preview_button);
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView previewBox = (TextView) findViewById(R.id.preview_new_entry);
                previewBox.setText(String.format("%s\n%s\n%s", paper, room, schedule));
            }
        });
    }*/

    public void onClickSaveEntry(View view) {
        Intent saveAndGoBack = new Intent();
        setResult(RESULT_OK, saveAndGoBack);
        finish();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void onClickPreviewEntry(View view) {

        Intent previewEntryIntent = new Intent(this,
                NewEntryPreview.class);
        startActivity(previewEntryIntent);

    }

}

 /*
        spinner = (Spinner) findViewById(R.id.schedule_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.schedule_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        paperCode = (AutoCompleteTextView) findViewById(R.id.paper_code_complete);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.paper_codes, android.R.layout.simple_dropdown_item_1line);
        paperCode.setAdapter(adapter1);

        roomCode = (AutoCompleteTextView) findViewById(R.id.room_code_complete);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.room_codes, android.R.layout.simple_dropdown_item_1line);
        roomCode.setAdapter(adapter2);

        TextView previewEntryString = (TextView) findViewById(R.id.preview_new_entry);
        previewEntryString.setText("Hello");
        */