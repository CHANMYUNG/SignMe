package com.signme.signme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dsm2016 on 2017-07-10.
 */

public class FogetidActivity extends AppCompatActivity {
    public static Activity fogetidActivity;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetid);
        fogetidActivity = this;
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

       /* String text=spinner.getSelectedItem().toString();
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/

    }

    public void closeonClick(View view) {
        FogetidActivity.fogetidActivity.finish();
        finish();
    }

    public void fogetpwonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), FogetpwActivity.class);
        startActivity(intent);
        FogetidActivity.fogetidActivity.finish();
        finish();
    }
}

    /*private class MyOnltemSelectdListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           Toast.makeText(
                    parent.getContext(),
            "The planet is"+parent.getItemAtPosition(position).toString(),
            Toast.LENGTH_LONG).show();

            Log.d("gkgk","gkgk");

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}*/
