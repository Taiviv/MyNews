package com.chartier.virginie.mynews.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chartier.virginie.mynews.fragments.ArticleFragment;

/**
 * Created by Virginie Chartier alias Taiviv on 12/09/2018.
 */
public class PageAdapter extends FragmentStatePagerAdapter {


    private String tabTitle[] = {"TOP STORIES", "MOST POPULAR", "BUSINESS",};
    public static final String[] topStoriesSection = {"home", "", "business"};


    // Default Constructor
    protected PageAdapter(FragmentManager mgr) {
        super(mgr);
    }


    @Override
    public int getCount() {
        return 3;
    }


    // Depend the position we can switch the fragment placeholder
    @Override
    public Fragment getItem(int position) {
        return ArticleFragment.newInstance(position);
    }

    // Define the Tab title
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}

