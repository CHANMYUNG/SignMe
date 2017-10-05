package com.signme.signme.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by NooHeat on 06/10/2017.
 */

public class LetterListViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<LetterListFragment> fragments = new ArrayList<>();
    private LetterListFragment currentFragment;

    public LetterListViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(LetterListFragment.newInstance(0));
        fragments.add(LetterListFragment.newInstance(1));
        fragments.add(LetterListFragment.newInstance(2));
        fragments.add(LetterListFragment.newInstance(3));
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
        super.setPrimaryItem(container, position, object);
    }

    public LetterListFragment getCurrentFragment() {
        return currentFragment;
    }
}
