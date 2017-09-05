package com.signme.signme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm2016 on 2017-07-08.
 */

public class TutorMainActivity extends AppCompatActivity implements ViewFlipperAction.ViewFlipperCallback {
    ViewFlipper flipper;
    List<ImageView> indexes;
    public static Activity tutorMainActivity;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoreal_main);
        tutorMainActivity = this;
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        ImageView index1 = (ImageView) findViewById(R.id.imgIndex1);
        ImageView index2 = (ImageView) findViewById(R.id.imgIndex2);
        ImageView index3 = (ImageView) findViewById(R.id.imgIndex3);
        ImageView index4 = (ImageView) findViewById(R.id.imgIndex4);
        ImageView index5 = (ImageView) findViewById(R.id.imgIndex5);
        ImageView index6 = (ImageView) findViewById(R.id.imgIndex6);
        ImageView index7 = (ImageView) findViewById(R.id.imgIndex7);
        ImageView index8 = (ImageView) findViewById(R.id.imgIndex8);

        indexes = new ArrayList<>();
        indexes.add(index1);
        indexes.add(index2);
        indexes.add(index3);
        indexes.add(index4);
        indexes.add(index5);
        indexes.add(index6);
        indexes.add(index7);
        indexes.add(index8);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.tutoreal1, flipper, false);
        View view2 = inflater.inflate(R.layout.tutoreal2, flipper, false);
        View view3 = inflater.inflate(R.layout.tutoreal3, flipper, false);
        View view4 = inflater.inflate(R.layout.tutoreal4, flipper, false);
        View view5 = inflater.inflate(R.layout.tutoreal5, flipper, false);
        View view6 = inflater.inflate(R.layout.tutoreal6, flipper, false);
        View view7 = inflater.inflate(R.layout.tutoreal7, flipper, false);
        View view8 = inflater.inflate(R.layout.tutoreal8, flipper, false);

        flipper.addView(view1);
        flipper.addView(view2);
        flipper.addView(view3);
        flipper.addView(view4);
        flipper.addView(view5);
        flipper.addView(view6);
        flipper.addView(view7);
        flipper.addView(view8);

        flipper.setOnTouchListener(new ViewFlipperAction(this, flipper));

        Button btnSkip = (Button) view1.findViewById(R.id.skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("dd", "dd");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                TutorMainActivity.tutorMainActivity.finish();
                finish();

            }
        });
        Button btnEnd = (Button) view8.findViewById(R.id.End);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("dd", "dd");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                TutorMainActivity.tutorMainActivity.finish();
                finish();
            }
        });

    }

    @Override
    public void onFlipperActionCallback(int position) {
        Log.d("ddd", "" + position);
        for (int i = 0; i < indexes.size(); i++) {
            ImageView index = indexes.get(i);
            if (i == position) {
                index.setImageResource(R.drawable.blue);
            } else {
                index.setImageResource(R.drawable.white);
            }
        }
    }
}
