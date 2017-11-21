package com.signme.signme.Forget_Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.signme.signme.R;

/**
 * Created by ghdth on 2017-10-14.
 */

public class ForgetMainActivity extends AppCompatActivity {
    android.app.FragmentManager manager;
    android.app.FragmentTransaction tran;
    android.app.Fragment fr;
    ImageView imageView=null;
    TextView forgetid,fogetpwd;
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetmain);
        forgetid=(TextView)findViewById(R.id.fogetid_tv);
        fogetpwd=(TextView)findViewById(R.id.fogetpwd_tv);
       imageView=(ImageView)findViewById(R.id.toolbar_iv);
        back=(ImageView)findViewById(R.id.forgetmain_back);
        manager=getFragmentManager();
        tran=manager.beginTransaction();
        tran.add(R.id.foget_main_fl,new ForgetidFragment());
        tran.commit();



        //아이디 찾기
        forgetid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.fogetid);
                fr=new ForgetidFragment();
                tran=manager.beginTransaction();
                tran.replace(R.id.foget_main_fl,fr);
                tran.commit();
            }
        });
        //비밀번호 찾기
        fogetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.fogetpw);
                fr=new ForgetpwFragment();
                tran=manager.beginTransaction();
                tran.replace(R.id.foget_main_fl,fr);
                tran.commit();
            }
        });
        //뒤로가기
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
