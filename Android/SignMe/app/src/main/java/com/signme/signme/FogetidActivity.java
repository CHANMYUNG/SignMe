package com.signme.signme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.signme.signme.server.APIinterface;

/**
 * Created by dsm2016 on 2017-07-10.
 */

public class FogetidActivity extends AppCompatActivity {
    public static Activity fogetidActivity;
    AQuery aquery;
    private APIinterface apIinterface;
    public static final String url = "http://13.124.15.202:80/";
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetid);
        fogetidActivity = this;
        final Spinner spinner = (Spinner) findViewById(R.id.email2);
        final EditText tv = (EditText) findViewById(R.id.email3);
        final EditText filed_name=(EditText)findViewById(R.id.forgetid_name);
        final EditText filed_email=(EditText)findViewById(R.id.forgetid_email);

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
    public void fogetidcheck(View view){
        aquery = new AQuery(getApplicationContext());
        final EditText namefield=(EditText)findViewById(R.id.forgetid_name);
        final TextView whelk=(TextView)findViewById(R.id.email_whelk);
        final EditText emailfield1=(EditText) findViewById(R.id.email);
        final EditText emailfield2=(EditText) findViewById(R.id.email3);
        String name=namefield.getText().toString();
        String email1=emailfield1.getText().toString();
        String email2=emailfield2.getText().toString();
        if(name.trim().equals("")||email1.trim().equals("")||email2.trim().equals("")||email2.trim().equals("--이메일 입력--")||email2.trim().equals("직접입력")){
            AlertDialog.Builder builder = new AlertDialog.Builder(FogetidActivity.this);
            Dialog dialog = builder.setMessage("이름과 이메일을 반드시 입력해주세요.").setPositiveButton("OK", null).create();
            dialog.show();
        }
        else if(name.trim().equals(" ")||email1.equals(" ")||email2.trim().equals(" ")){
            AlertDialog.Builder builder = new AlertDialog.Builder(FogetidActivity.this);
            Dialog dialog = builder.setMessage("이름과 이메일에는 공백이 포함될 수 없습니다").setPositiveButton("OK", null).create();
            dialog.show();
        }




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
