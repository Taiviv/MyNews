package com.chartier.virginie.mynews.controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.chartier.virginie.mynews.R;
import com.chartier.virginie.mynews.view.PageAdapter;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //FOR DESIGN

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure all views

        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureToolbar();
        this.configureViewPagerAndTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu,menu);
        return true;
    }


    // This method handle back click to close menu
    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START))
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }



    // This methods which start of a new activity
    private void launchSearchActivity() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        this.startActivity(intent);
    }

    private void launchNotificationsActivity() {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        this.startActivity(intent);
    }


    /* This method allow to choose between the different option offered in a menu
     * Ever item perform a new action
    * */
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //Open NotificationActivity
            case R.id.param_notifications:
                launchNotificationsActivity();
                return true;
            //Open SearchActivity
            case R.id.menu_activity_main_search:
                launchSearchActivity();
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /* This method allow to choose between the different option offered in a menu
     * Ever item perform a new action
     **/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_news:
                launchSearchActivity();
                break;
            case R.id.activity_main_drawer_notifications:
                launchNotificationsActivity();
                break;
            default:
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    // This method configure the tabLayout and his adapter
    private void configureViewPagerAndTabs() {
        // Get viewpager from layout
        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        // Set Adapter ViewPageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()) {
        });
        // Tab
        TabLayout tabLayout = findViewById(R.id.activity_main_tab);
        // Glue Tab et viewpager together
        tabLayout.setupWithViewPager(pager);
        // Handle the tab's width and mobility
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }



    // ---------------------
    // CONFIGURATION
    // ---------------------


    // This method handle the toolbar layout and display it in the actionbar
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    // This method handle the hamburger icon of the menu drawer
    private void configureDrawerLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                 toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    // This method handle the horizontal slide of the menu drawer
    private void configureNavigationView() {
        this.mNavigationView = findViewById(R.id.nav_view);
        assert mNavigationView != null;
        this.mNavigationView.setNavigationItemSelectedListener(this);
    }
}
