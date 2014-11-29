package com.example.bradenhart.myunitimetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bradenhart on 28/11/14.
 */
public class TestScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_screen);

        Button backToMainButton = (Button) findViewById(R.id.testToMainButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnToMainIntent = new Intent(TestScreen.this, MainActivity.class);
                startActivity(returnToMainIntent);
            }
        });

        final TextView testTextView = (TextView) findViewById(R.id.testTextView);
        testTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                testTextView.setBackgroundResource(R.drawable.longclick_border);
                Toast.makeText(getApplicationContext(), "Long click worked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }


}
