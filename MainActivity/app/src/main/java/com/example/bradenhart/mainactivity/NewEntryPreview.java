package com.example.bradenhart.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by bradenhart on 24/08/14.
 */
public class NewEntryPreview extends Activity {

    TextView txtView;
    String newText;
    private EditText editTxt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry_preview);

        txtView = (TextView) findViewById(R.id.preview_box);
        editTxt = (EditText) findViewById(R.id.edittextthing);
        Button txtButton = (Button) findViewById(R.id.txtButton);
        newText = editTxt.getText().toString();


        txtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtView.setText("hello there");
            }
        });

        System.err.println(newText);

    }

    public void onClickAddThisEntry(View view) {

        Intent addEntryIntent = new Intent(this,
                MainActivity.class);
        startActivity(addEntryIntent);

    }

    public void onClickEditThisPreview(View view) {

        Intent editPreviewIntent = new Intent(this,
                EditEntryPage.class);
        startActivity(editPreviewIntent);

    }

}
