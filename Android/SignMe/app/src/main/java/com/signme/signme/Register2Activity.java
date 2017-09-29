package com.signme.signme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register2Activity extends AppCompatActivity {

    public EditText serialinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        serialinput = (EditText) findViewById(R.id.serialinput);
        Button serialok = (Button)findViewById(R.id.serialok);
    }

    public void serialok(View v){
        switch(v.getId()){
            case R.id.serialok:

                String a = serialinput.getText().toString();
                if(a.equals("aaa")){
                    Toast.makeText(getApplicationContext(), "인증이 완료 되었습니다", Toast.LENGTH_SHORT).show();
                    Intent serialok = new Intent(getApplication(), Register3Activity.class);
                    startActivity(serialok);
                }else{
                    Toast.makeText(getApplicationContext(), "인증 번호가 틀렸습니다. 다시 입력 해주세요", Toast.LENGTH_SHORT).show();
                    serialinput.setText(null);
                }
        }
    }
}