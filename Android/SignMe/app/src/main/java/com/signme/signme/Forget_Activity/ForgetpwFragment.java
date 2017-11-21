package com.signme.signme.Forget_Activity;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.signme.signme.R;

/**
 * Created by dsm2016 on 2017-07-12.
 */

public class ForgetpwFragment extends Fragment {

    public static final String url = "http://13.124.15.202:80/";
    //AQuery aquery;
    FrameLayout fl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_forgetpw,container,false);
        initforgerpwd(view);
        return view;
    }
    public void initforgerpwd(View view){
        fl=(FrameLayout)view.findViewById(R.id.forgetpwd_fl);
        final Spinner spinner = (Spinner)view.findViewById(R.id.fogetpwd_email2);
        final EditText tv = (EditText) view.findViewById(R.id.forgetpwd_email3);
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
        final EditText emailfield=(EditText)view.findViewById(R.id.forgetpwd_email);
        final TextView email2_whelk=(TextView)view.findViewById(R.id.pwdemail_whelk);
       //이메일 전송
        Button email_send=(Button)view.findViewById(R.id.emailcode_send);
        email_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1=emailfield.getText().toString();
                String email2=email2_whelk.getText().toString();
                String email3=tv.getText().toString();
                String email=email1+email2+email3;
                Log.d(email1,email3);
                Log.d("이메일",email);
                if(email1.equals("")||email3.equals("")||email3.equals("--이메일 입력--")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    Dialog dialog = builder.setMessage("이메일을 반드시 입력해주세요.").setPositiveButton("OK", null).create();
                    dialog.show();
                }
                else if(email1.equals(" ")||email3.equals(" ")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    Dialog dialog = builder.setMessage("이메일에는 공백이 포함될 수 없습니다").setPositiveButton("OK", null).create();
                    dialog.show();
                }
                else{
                    ///레트로핏


                }
            }
        });

        final EditText emailcodefield=(EditText)view.findViewById(R.id.emailcode);
        Button emailcode_check=(Button)view.findViewById(R.id.emailcode_check);
        final EditText user_idfield=(EditText)view.findViewById(R.id.user_id);
        emailcode_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailcode=emailcodefield.getText().toString();
                String user_id=user_idfield.getText().toString();
                if(emailcode.equals("")||user_id.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    Dialog dialog = builder.setMessage("아이디와 이메일코드를 반드시 입력해주세요.").setPositiveButton("OK", null).create();
                    dialog.show();
                }
                else if(emailcode.equals(" ")||user_id.equals(" ")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    Dialog dialog = builder.setMessage("아이디와 이메일에는 공백이 포함될 수 없습니다").setPositiveButton("OK", null).create();
                    dialog.show();
                }
                else {
                       /*
                        레트로핏
                       */

                     Intent intent=new Intent(getActivity(),PwchangeActivity.class);
                     startActivity(intent);
                }




            }
        });

    }
}
