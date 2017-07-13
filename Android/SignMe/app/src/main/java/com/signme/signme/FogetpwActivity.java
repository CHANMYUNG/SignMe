package com.signme.signme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by dsm2016 on 2017-07-12.
 */

public class FogetpwActivity extends AppCompatActivity {
    public static Activity fogetpwActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fogetpwActivity = this;
        setContentView(R.layout.activity_forgetpw);
        final Spinner spinner = (Spinner) findViewById(R.id.email2);
        final EditText tv = (EditText) findViewById(R.id.email3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(spinner.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void closeonClick(View view) {
        FogetpwActivity.fogetpwActivity.finish();
        finish();
    }

    public void fogetidonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), FogetidActivity.class);
        startActivity(intent);
        FogetpwActivity.fogetpwActivity.finish();
        finish();
    }
}
