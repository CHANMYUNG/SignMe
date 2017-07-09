package com.signme.signme;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by dsm2016 on 2017-07-07.
 */

public class SpinnerTest extends Activity implements AdapterView.OnItemSelectedListener {
  TextView selection;
    String[] items={"직접입력","naver.com","nate.com","gmail.com","hanmail.net"};
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_forgetid);
        selection =(TextView)findViewById(R.id.sel);

        Spinner spin=(Spinner)findViewById(R.id.eamil2);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    selection.setText(items[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    selection.setText("");
    }
}
