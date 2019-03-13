package com.chartier.virginie.mynews.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.chartier.virginie.mynews.R;
import com.chartier.virginie.mynews.fragments.ArticleFragment;

import com.chartier.virginie.mynews.utils.NavigationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chartier.virginie.mynews.controller.SearchActivity.SEARCH_ARTICLE_VALUES;


public class SearchActivityList extends AppCompatActivity {

    // FOR DATA

    private NavigationUtils mNavigationUtils = new NavigationUtils();
    //Declare fragment
    private ArticleFragment articleFragment;


    // FOR DESIGN

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_search_list_fragment_container) FrameLayout fragmentContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ButterKnife.bind(this);
        configureToolbar();
        // Intent
        String value = getIntent().getStringExtra(SEARCH_ARTICLE_VALUES);
        // Configure and show home fragment
        configureAndShowMainFragment();
    }

    /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString(SEARCH_ARTICLE_VALUES);*/


    // This method calls the toolbar layout and fixed it on the action bar then a return home function is displayed.
    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    // This method called for better performances
    @Override
    public void onDestroy() {
        super.onDestroy();
        //this.disposeWhenDestroy();
    }


    // --------------
    // FRAGMENTS
    // --------------

    private void configureAndShowMainFragment(){
        // A - Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        articleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_list_fragment_container);

        if (articleFragment == null) {
            // B - Create new main fragment
            articleFragment = ArticleFragment.newInstanceForResearch(ArticleFragment.SEARCH_POSITION,String.valueOf(3));
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_list_fragment_container, articleFragment)
                    .commit();
        }
    }
}
