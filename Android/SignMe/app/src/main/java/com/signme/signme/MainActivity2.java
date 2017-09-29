package com.signme.signme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.signme.signme.fragment.LetterListFragment;
import com.signme.signme.fragment.TaskFragment;

/**
 * Created by NooHeat on 28/09/2017.
 */

public class MainActivity2 extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ver2);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();


    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        Log.d("position :: ", position + "");
        if (position == 0) {
            LetterListFragment letterListFragment = new LetterListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main, letterListFragment).commit();
        }
        if (position == 1) {
            TaskFragment taskFragment = new TaskFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main, taskFragment).commit();
        }
        return true;
    }

    public void createNavItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("first", R.drawable.ic_chat_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("second", R.drawable.ic_chat_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("third", R.drawable.ic_chat_black_24dp, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setCurrentItem(0);
    }
}
