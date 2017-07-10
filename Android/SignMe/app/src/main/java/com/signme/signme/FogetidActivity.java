package com.signme.signme;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by dsm2016 on 2017-07-10.
 */

public class FogetidActivity extends AppCompatActivity{
   public static Activity fogetidActivity;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetid);
        fogetidActivity = this;
        Spinner spinner = (Spinner) findViewById(R.id.eamil2);
        String text=spinner.getSelectedItem().toString();
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnltemSelectdListener());
    }

    private class MyOnltemSelectdListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d("gkgk","gkgk");

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
