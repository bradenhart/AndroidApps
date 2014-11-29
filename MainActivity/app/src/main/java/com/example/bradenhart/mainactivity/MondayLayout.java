package com.example.bradenhart.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MondayLayout extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.monday_layout);


    }

    public void onClickGoBackHome(View view) {

        Intent goingBack = new Intent();
        setResult(RESULT_OK, goingBack);
        finish();

    }

    public void onClickGoToEditPage(View view) {

        Intent goToEditPage = new Intent(this,
                EditEntryPage.class);

        startActivity(goToEditPage);
    }
}
