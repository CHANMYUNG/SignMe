package com.signme.signme.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by NooHeat on 30/09/2017.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<HomeFragment> fragments = new ArrayList<>();
    private HomeFragment currentFragment;

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(HomeFragment.newInstance(0));
        fragments.add(HomeFragment.newInstance(1));
        fragments.add(HomeFragment.newInstance(2));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((HomeFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public HomeFragment getCurrentFragment() {
        return currentFragment;
    }
}

