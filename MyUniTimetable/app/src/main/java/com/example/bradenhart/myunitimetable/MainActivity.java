package com.example.bradenhart.myunitimetable;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout rLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        addViewsToTimePanel(rLayout1);

        // Button related methods
        final Button toEditScreenButton = (Button) findViewById(R.id.button1);
        toEditScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToEditIntent = new Intent(MainActivity.this, EditTextViewLayout.class);
                startActivity(goToEditIntent);
            }
        });

        Intent in = getIntent();
        if (in != null) {
            ArrayList<TimetableEntry> newEntries1 = in.getParcelableArrayListExtra("entries");
            if (newEntries1 != null && newEntries1.size() > 0) {
                for (int i = 0; i < newEntries1.size(); i++) {
                    addViewToTimetable(rLayout1, newEntries1.get(i));
                }
            }
        }

    // end onCreate
    }



    private int getPixelsFromDP(int dipValue) {
        Resources r = getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
        return px;
    }

    private int getPixelsFromSP(int spValue) {
        Resources r = getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, r.getDisplayMetrics());
        return px;
    }

    public void addViewsToTimePanel(RelativeLayout rl) {
        int timesMax = 20;
        int startY = getPixelsFromDP(0);
        int increment = getPixelsFromDP(100);
        int leftPadding = getPixelsFromDP(5);
        TextView newView;

        for (int i = 8; i < timesMax; i++) {
            newView = new TextView(MainActivity.this);
            newView.setHeight(getPixelsFromDP(100));
            newView.setWidth(getPixelsFromDP(45));
            newView.setX((float) getPixelsFromDP(0));
            newView.setY((float) startY);
            newView.setBackgroundResource(R.drawable.time_textview_small);
            if (i <= 9) {
                newView.setText(String.format("0%d:\n00", i));
            } else {
                newView.setText(String.format("%d:\n00", i));
            }
            newView.setPadding(leftPadding, 0, 0, 0);
            newView.setTextSize((float) getPixelsFromSP(8));
            startY += increment;
            rl.addView(newView);
        }
    }

    public void addViewToTimetable(RelativeLayout rl, TimetableEntry entry) {
        final TextView newView = new TextView(MainActivity.this);
        int width = getPixelsFromDP(73);
        int height = getPixelsFromDP(entry.getDuration());
        int x = getPixelsFromDP(entry.getDay());
        int y = getPixelsFromDP(entry.getStartTime());
        String entryText = entry.getFullString();

        newView.setHeight(height);
        newView.setWidth(width);
        newView.setX((float) x);
        newView.setY((float) y);
        newView.setText(entryText);
        newView.setTextSize((float) getPixelsFromSP(7));
        newView.setGravity(Gravity.CENTER);

        newView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                newView.setBackgroundResource(R.drawable.longclick_border);
                Toast.makeText(getApplicationContext(), "Long click worked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        rl.addView(newView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

}