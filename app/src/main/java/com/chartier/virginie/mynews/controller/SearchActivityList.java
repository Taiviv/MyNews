package com.chartier.virginie.mynews.controller;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.chartier.virginie.mynews.R;
import com.chartier.virginie.mynews.apis.NewYorkTimeStream;
import com.chartier.virginie.mynews.model.SearchArticle;
import com.chartier.virginie.mynews.utils.HttpRequest;
import com.chartier.virginie.mynews.utils.NavigationUtils;
import com.chartier.virginie.mynews.view.SearchActivityAdapter;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.chartier.virginie.mynews.controller.SearchActivity.SEARCH_ARTICLE_VALUES;

public class SearchActivityList extends AppCompatActivity {

    // FOR DATA

    public List<SearchArticle.Doc> mDocArrayList = new ArrayList<>();
    private Disposable mDisposable;
    private SearchActivityAdapter mSearchActivityAdapter;
    private NavigationUtils mNavigationUtils = new NavigationUtils();
    private HttpRequest mHttpRequest = new HttpRequest();

    //FOR DESIGN

    @BindView(R.id.recyclerview_layout) RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.frag_swipe_layout) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ButterKnife.bind(this);
        this.configureRecyclerView();
        this.mHttpRequest.progressBarHandler(mProgressBar, this);
        this.configureSwipeRefreshLayout();
        this.executeSearchArticleHttpRequest();
        configureToolbar();
    }


    //This method calls the toolbar layout and fixed it on the action bar then a return home function is displayed.
    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    //This method called for better performances
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }


    // -----------------
    // CONFIGURATION
    // -----------------

    //This method manages the recyclerView set up
    private void configureRecyclerView() {
        //Calling the adapter
        this.mSearchActivityAdapter = new SearchActivityAdapter(mDocArrayList, Glide.with(this));
        //Set them with natives methods
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mSearchActivityAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //When user click on an item a new activity is launched to display a webView
        this.displayWebView();

    }

    //This method used to open a web view directly in the app, not by default application
    private void displayWebView() {
        this.mSearchActivityAdapter.setOnItemClickListener(new SearchActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mNavigationUtils.openActivityAsBrowser(mDocArrayList.get(position).getWebUrl(), SearchActivityList.this, WebViewActivity.class);
            }
        });

    }


    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    //This method handle the Http rest request takes a model object as parameter and display a result
    private void executeSearchArticleHttpRequest() {
        this.mHttpRequest.updateUIWhenStartingHTTPRequest(mProgressBar);
        //Get the array content to provide query to the http request
        String[] mDataValues = getIntent().getStringArrayExtra(SEARCH_ARTICLE_VALUES);

        //mDataValues[0] == query, mDataValues[1] == new_desk, mDataValues[2] == begin_date, mDataValues[3] == endDate
        this.mDisposable = NewYorkTimeStream.streamFetchSearchArticle(mDataValues[0], "news_desk:(" + mDataValues[1] + ")", mDataValues[2], mDataValues[3])
                .subscribeWith(new DisposableObserver<SearchArticle>() {

                    @Override
                    public void onNext(SearchArticle article) {
                        Log.d("Search Article", "On Next");
                        upDateUISearchArticle(article);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Search Article", "On Error " + Log.getStackTraceString(e));
                        mHttpRequest.internetDisable(mProgressBar, getString(R.string.no_internet), getBaseContext());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Search Article", "On Complete !");
                    }
                });
    }

    //Dispose subscription
    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
    }



    // ------------------
    //  UPDATE UI
    // ------------------

    //This method executes the http request When the screen is swipe
    private void configureSwipeRefreshLayout() {
        this.mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeSearchArticleHttpRequest();
            }
        });
    }

    //This method Update the UI from the recycler view with the adapter
    private void upDateUISearchArticle(SearchArticle searchArticle) {
        this.mHttpRequest.updateUIWhenStopingHTTPRequest(mRefreshLayout, mProgressBar);
        this.mDocArrayList.clear();
        this.mDocArrayList.addAll(searchArticle.getResponse().getDocs());
        this.mSearchActivityAdapter.notifyDataSetChanged();
    }
}
