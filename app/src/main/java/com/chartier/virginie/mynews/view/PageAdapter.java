package com.chartier.virginie.mynews.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chartier.virginie.mynews.fragments.FragmentBusiness;
import com.chartier.virginie.mynews.fragments.FragmentMostPopular;
import com.chartier.virginie.mynews.fragments.FragmentTopStories;


public class PageAdapter extends FragmentStatePagerAdapter {


    private String tabTitle[] = {"TOP STORIES", "MOST POPULAR", "BUSINESS",};


    // Default Constructor
    protected PageAdapter(FragmentManager mgr) {
        super(mgr);
    }


    @Override
    public int getCount() {
        return tabTitle.length;
    }


    // Depend the position we can switch the fragment placeholder
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentTopStories.newInstance(position);
            case 1:
                return  FragmentMostPopular.newInstance(position);
            case 2:
                return  FragmentBusiness.newInstance(position);
            default:
            return null;
        }
    }

    // Define the Tab title
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}

