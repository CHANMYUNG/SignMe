package com.signme.signme.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.signme.signme.FCM.FirebaseInstanceIDService;
import com.signme.signme.R;
import com.signme.signme.fragment.HomeFragment;
import com.signme.signme.fragment.HomeViewPagerAdapter;

/**
 * Created by NooHeat on 28/09/2017.
 */

public class HomeActivity extends AppCompatActivity {
    AHBottomNavigation bottomNavigation;
    AHBottomNavigationViewPager viewPager;
    HomeFragment currentFragment = null;
    HomeViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN", "onCreate: " + FirebaseInstanceId.getInstance().getToken());

        FirebaseInstanceIDService.sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken(), getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null).toString());

        this.createNavItems();


    }

    private void initUI() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        viewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Letters", R.drawable.ic_chat_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Tasks", R.drawable.ic_event_note_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("MyPage", R.drawable.main_mypage, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setTranslucentNavigationEnabled(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (currentFragment == null) currentFragment = adapter.getCurrentFragment();

                if (wasSelected && currentFragment != null) {
                    currentFragment.refresh();
                    return true;
                }
                if (currentFragment != null) currentFragment.willBeHidden();

                viewPager.setCurrentItem(position, false);

                if (currentFragment == null) return true;

                currentFragment = adapter.getCurrentFragment();
                currentFragment.willBeDisplayed();

                return true;
            }
        });

        viewPager.setOffscreenPageLimit(4);
        adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();
    }


    public void createNavItems() {


        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);

        bottomNavigation.setCurrentItem(0);
    }
}
