package com.rba.examennaat.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rba.examennaat.fragments.AdministracionFragment;
import com.rba.examennaat.fragments.RecargasFragment;
import com.rba.examennaat.fragments.RecaudacionFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabs;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.tabs=behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RecargasFragment();
            case 1:
                return new RecaudacionFragment();
            case 2:
                return new AdministracionFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
