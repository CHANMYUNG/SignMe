package com.signme.signme.Forget_Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.signme.signme.R;
import com.signme.signme.server.APIInterface;

/**
 * Created by dsm2016 on 2017-07-10.
 */

public class ForgetidFragment extends Fragment {
    public static Activity fogetidActivity;
    FrameLayout fl;
    AQuery aquery;
    private APIInterface APIInterface;
    public static final String url = "http://13.124.15.202:80/";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_forgetid, container, false);
        initforgetid(view);


        return view;
    }

    public void initforgetid(View view) {
        fl=(FrameLayout)view.findViewById(R.id.forgetid_fl);
        final Spinner spinner = (Spinner)view. findViewById(R.id.email2);
        final EditText tv = (EditText) view.findViewById(R.id.email3);
        final EditText namefield=(EditText)view.findViewById(R.id.forgetid_name);
        final TextView whelk_tv=(TextView)view.findViewById(R.id.email_whelk);
        final EditText emailfield=(EditText)view.findViewById(R.id.forgetid_email);
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
        Button check=(Button)view.findViewById(R.id.fogetid_check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=namefield.getText().toString();
                String email1=emailfield.getText().toString();
                String email2=whelk_tv.getText().toString();
                String email3=tv.getText().toString();
                String email=email1+email2+email3;
                Log.d("이메일",email);

                if(name.trim().equals("")||email1.trim().equals("")||email3.trim().equals("")||email3.trim().equals("--이메일 입력--")||email2.trim().equals("직접입력")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    Dialog dialog = builder.setMessage("이름과 이메일을 반드시 입력해주세요.").setPositiveButton("OK", null).create();
                    dialog.show();
                }
                else if(name.trim().equals(" ")||email1.equals(" ")||email3.trim().equals(" ")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    Dialog dialog = builder.setMessage("이름과 이메일에는 공백이 포함될 수 없습니다").setPositiveButton("OK", null).create();
                    dialog.show();
                }
                else {
                    ///레트로핏



                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    Dialog dialog = builder
                            .setTitle("아이디를 찾았습니다.")
                            .setMessage("아이디는"+/*아이디*/"입니다.")
                            .setPositiveButton("OK", null).create();
                    dialog.show();
                }

            }
        });
    }
}
