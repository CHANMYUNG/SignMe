package com.signme.signme.tutoreal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.signme.signme.R;
import com.signme.signme.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm2016 on 2017-07-08.
 */

public class TutorMainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mPager;
    private ImageButton tuto1,tuto2,tuto3,tuto4,tuto5,tuto6,tuto7,tuto8;
    private FrameLayout r1;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoreal_main);
        initLayout();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tutoreal1_button:
                setCurrentItem(0);
                break;
            case R.id.tutoreal2_button:
                setCurrentItem(1);
                break;
            case R.id.tutoreal3_button:
                setCurrentItem(2);
                break;
            case R.id.tutoreal4_button:
                setCurrentItem(3);
                break;
            case R.id.tutoreal5_button:
                setCurrentItem(4);
                break;
            case R.id.tutoreal6_button:
                setCurrentItem(5);
                break;
            case R.id.tutoreal7_button:
                setCurrentItem(6);
                break;
            case R.id.tutoreal8_button:
                setCurrentItem(7);
                break;
        }

    }
    private void setCurrentItem(int index){
        if(index==0){
            mPager.setCurrentItem(0);
        }
        else if(index==1){
            mPager.setCurrentItem(1);
        }
        else if(index==2){
            mPager.setCurrentItem(2);
        }
        else if(index==3){
            mPager.setCurrentItem(3);
        }
        else if(index==4){
            mPager.setCurrentItem(4);
        }
        else if(index==5){
            mPager.setCurrentItem(5);
        }
        else if(index==6){
            mPager.setCurrentItem(6);
        }
        else if(index==7){
            mPager.setCurrentItem(7);
        }
    }
    private void initLayout(){
        tuto1=(ImageButton)findViewById(R.id.tutoreal1_button);
        tuto2=(ImageButton)findViewById(R.id.tutoreal2_button);
        tuto3=(ImageButton)findViewById(R.id.tutoreal3_button);
        tuto4=(ImageButton)findViewById(R.id.tutoreal4_button);
        tuto5=(ImageButton)findViewById(R.id.tutoreal5_button);
        tuto6=(ImageButton)findViewById(R.id.tutoreal6_button);
        tuto7=(ImageButton)findViewById(R.id.tutoreal7_button);
        tuto8=(ImageButton)findViewById(R.id.tutoreal8_button);

        tuto1.setOnClickListener(this);
        tuto2.setOnClickListener(this);
        tuto3.setOnClickListener(this);
        tuto4.setOnClickListener(this);
        tuto5.setOnClickListener(this);
        tuto6.setOnClickListener(this);
        tuto7.setOnClickListener(this);
        tuto8.setOnClickListener(this);

        mPager=(ViewPager)findViewById(R.id.ViewPager_tutoreal);
        mPager.setAdapter(new ViewPagerAdapter(this));



    }
    private class ViewPagerAdapter extends PagerAdapter{
      private LayoutInflater mLayoutInflater;
        public ViewPagerAdapter(Context context){
            super();
            mLayoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public Object instantiateItem(View pager, int index) {
           View view=null;
            if(index==0){
                view=mLayoutInflater.inflate(R.layout.tutoreal1,null);
                inittutoreal1(view);
            }
            else if(index==1){
                view=mLayoutInflater.inflate(R.layout.tutoreal2,null);
                inittutoreal2(view);
            }
            else if(index==2){
                view=mLayoutInflater.inflate(R.layout.tutoreal3,null);
                inittutoreal3(view);
            }
            else if(index==3){
                view=mLayoutInflater.inflate(R.layout.tutoreal4,null);
                inittutoreal4(view);
            }
            else if(index==4){
                view=mLayoutInflater.inflate(R.layout.tutoreal5,null);
                inittutoreal5(view);
            }
            else if(index==5){
                view=mLayoutInflater.inflate(R.layout.tutoreal6,null);
                inittutoreal6(view);
            }
            else if(index==6){
                view=mLayoutInflater.inflate(R.layout.tutoreal7,null);
                inittutoreal7(view);
            }
            else if(index==7){
                view=mLayoutInflater.inflate(R.layout.tutoreal8,null);
                inittutoreal8(view);
            }
            ((ViewPager)pager).addView(view,0);
            return view;
        }
        public void inittutoreal1(View view){
            r1=(FrameLayout)view.findViewById(R.id.tutoreal1_fl);
            Button skip1=(Button)view.findViewById(R.id.skip1);
            skip1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TutorMainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }
        public void inittutoreal2(View view){
            r1=(FrameLayout)view.findViewById(R.id.tutoreal2_fl);
            Button skip1=(Button)view.findViewById(R.id.skip2);
            skip1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TutorMainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }
        public void inittutoreal3(View view){
            r1=(FrameLayout)view.findViewById(R.id.tutoreal3_fl);
            Button skip1=(Button)view.findViewById(R.id.skip3);
            skip1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TutorMainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }
        public void inittutoreal4(View view){
            r1=(FrameLayout)view.findViewById(R.id.tutoreal4_fl);
            Button skip1=(Button)view.findViewById(R.id.skip4);
            skip1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TutorMainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }
        public void inittutoreal5(View view){
            r1=(FrameLayout)view.findViewById(R.id.tutoreal5_fl);
            Button skip1=(Button)view.findViewById(R.id.skip5);
            skip1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TutorMainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }
        public void inittutoreal6(View view){
            r1=(FrameLayout)view.findViewById(R.id.tutoreal6_fl);
            Button skip6=(Button)view.findViewById(R.id.skip6);
            skip6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TutorMainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }
        public void inittutoreal7(View view){
            r1=(FrameLayout)view.findViewById(R.id.tutoreal7_fl);
            Button skip7=(Button)view.findViewById(R.id.skip7);
            skip7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TutorMainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }
        public void inittutoreal8(View view){
            r1=(FrameLayout)view.findViewById(R.id.tutoreal8_fl);
            Button skip8=(Button)view.findViewById(R.id.tuto_finish);
            skip8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TutorMainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }


        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager)pager).removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj) {
            return pager==obj;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {}

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {}

        @Override
        public void finishUpdate(View arg0) {}
    }
}
