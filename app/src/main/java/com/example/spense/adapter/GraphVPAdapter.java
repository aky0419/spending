package com.example.spense.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class GraphVPAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragments;

    public GraphVPAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> mFragments) {
        super(fm, behavior);
        this.mFragments = mFragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
