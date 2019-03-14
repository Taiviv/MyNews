package com.chartier.virginie.mynews.fragments;

import android.content.Intent;
import android.icu.text.StringSearch;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chartier.virginie.mynews.R;
import com.chartier.virginie.mynews.apis.NewYorkTimeStream;
import com.chartier.virginie.mynews.controller.WebViewActivity;
import com.chartier.virginie.mynews.model.Doc;
import com.chartier.virginie.mynews.model.MostPopular;
import com.chartier.virginie.mynews.model.ArticleItem;
import com.chartier.virginie.mynews.model.SearchArticle;
import com.chartier.virginie.mynews.model.TopStories;
import com.chartier.virginie.mynews.utils.HttpRequest;
import com.chartier.virginie.mynews.utils.NavigationUtils;
import com.chartier.virginie.mynews.view.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.chartier.virginie.mynews.controller.SearchActivity.SEARCH_ARTICLE_VALUES;
import static com.chartier.virginie.mynews.view.PageAdapter.topStoriesSection;


public class ArticleFragment extends Fragment {


    // FOR DATA

    // Create keys for our Bundle
    private static final String KEY_POSITION = "position";
    private static final String KEY_SEARCH = "search";
    public static final int TOP_STORIES_POSITION = 0;
    public static final int MOST_POPULAR_POSITION = 1;
    public static final int BUSINESS_POSITION = 2;
    public static final int SEARCH_POSITION = 3;
    public List<ArticleItem> mTopStoriesArray = new ArrayList<>();
    private Disposable mDisposable;
    private ArticleAdapter mAdapter;
    private NavigationUtils mNavigationUtils = new NavigationUtils();
    private HttpRequest mHttpRequest = new HttpRequest();
    


    // FOR DESIGN

    @BindView(R.id.fragment_article_textview) TextView titleTextView;
    @BindView(R.id.recyclerview_layout) RecyclerView mRecyclerView;
    @BindView(R.id.frag_swipe_layout) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.activity_main_progress_bar) ProgressBar mProgressBar;


    // Required empty public constructor
    public ArticleFragment() { }


    // This method that will create a new instance of ArticleFragment, and add data to its bundle.
    public static ArticleFragment newInstance(int position) {

        // Create new fragment
        ArticleFragment frag = new ArticleFragment();

        // Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return frag;
    }

    public static ArticleFragment newInstanceForResearch(int position, String search){
        ArticleFragment frag = newInstance(position);
        assert frag.getArguments() != null;
        frag.getArguments().putString(KEY_SEARCH, search);

        return frag;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this,view);
        // We get the index position of the viewPager
        assert getArguments() != null;
        int position = getArguments().getInt(KEY_POSITION);
        switch (position){
            case TOP_STORIES_POSITION :
                executeTopStoriesHttpRequest();
                break;
            case MOST_POPULAR_POSITION :
                executeMostPopularHttpRequest();
                break;
            case BUSINESS_POSITION :
                executeBusinessHttpRequest();
                break;
            case SEARCH_POSITION :
                executeSearchArticleHttpRequest();
                break;
        }

        // A progress bar is loaded and configured
        this.mHttpRequest.progressBarHandler(mProgressBar, getContext());
        // Call the recyclerView method
        this.configureRecyclerView();
        // It's possible to refresh the Uri api on vertical swipe from the top to the bottom
        this.configureSwipeRefreshLayout();

        return view;
    }


    // This method called for better performances
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }


    // -----------------
    // CONFIGURATION
    // -----------------

    // This method configure the recyclerView set up
    private void configureRecyclerView() {
        // Calling the adapter
        this.mAdapter = new ArticleAdapter(mTopStoriesArray,Glide.with(this));
        // Set them with natives methods
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // When user click on an item a new activity is launched to display a webView
        this.displayWebView();
    }


    // This method used to open a web view directly in the app, not by default application
    private void displayWebView() {
        this.mAdapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mNavigationUtils.openActivityAsBrowser(mTopStoriesArray.get(position).getUrl(),getContext(), WebViewActivity.class);
            }
        });
    }


    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // This methods handle the Http rest request takes a model object as parameter and display a result
    public void executeTopStoriesHttpRequest() {
        this.mHttpRequest.updateUIWhenStartingHTTPRequest(mProgressBar);

        this.mDisposable = NewYorkTimeStream.streamFetchTopStories(topStoriesSection[0])
                .subscribeWith(new DisposableObserver<TopStories>() {

                    @Override
                    public void onNext(TopStories topStoriesItems) {
                        Log.d("TopStories Tag", "On Next");
                        updateRecyclerUI(topStoriesItems.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TopStories Tag", "On Error" + Log.getStackTraceString(e).toUpperCase());
                        mHttpRequest.internetDisable(mProgressBar, getString(R.string.no_internet), getContext());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TopStories Tag", "On Complete !");
                    }
                });
    }

    private void executeMostPopularHttpRequest() {
        this.mHttpRequest.updateUIWhenStartingHTTPRequest(mProgressBar);

        this.mDisposable = NewYorkTimeStream.streamFetchMostPopular()
                .subscribeWith(new DisposableObserver<MostPopular>() {

                    @Override
                    public void onNext(MostPopular mostPopularItems) {
                        Log.d("Most Popular", "On Next" + mostPopularItems.getResults().size());
                        updateRecyclerUI(mostPopularItems.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Most Popular", "On Error" + Log.getStackTraceString(e));
                        mHttpRequest.internetDisable(mProgressBar, getString(R.string.no_internet), getContext());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Most Popular", "On Complete !");
                    }
                });
    }


    public void executeBusinessHttpRequest() {
        this.mHttpRequest.updateUIWhenStartingHTTPRequest(mProgressBar);

        this.mDisposable = NewYorkTimeStream.streamFetchTopStories(topStoriesSection[2])
                .subscribeWith(new DisposableObserver<TopStories>() {


                    @Override
                    public void onNext(TopStories topStoriesItems) {
                        Log.d("Business Tag", "On Next");
                        updateRecyclerUI(topStoriesItems.getResults());
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d("Business Tag", "On Error" + Log.getStackTraceString(e).toUpperCase());
                        mHttpRequest.internetDisable(mProgressBar, getString(R.string.no_internet), getContext());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Business Tag", "On Complete !");
                    }
                });
    }

    // This method handle the Http rest request takes a model object as parameter and display a result
    private void executeSearchArticleHttpRequest() {
        this.mHttpRequest.updateUIWhenStartingHTTPRequest(mProgressBar);
        // Get the array content to provide query to the http request
        String[] mDataValues = new Intent().getStringArrayExtra(SEARCH_ARTICLE_VALUES);

        // mDataValues[0] == query, mDataValues[1] == new_desk, mDataValues[2] == begin_date, mDataValues[3] == endDate
        this.mDisposable = NewYorkTimeStream.streamFetchSearchArticle(mDataValues[0],mDataValues[1],mDataValues[2],mDataValues[3])
                .subscribeWith(new DisposableObserver<SearchArticle>() {

                    @Override
                    public void onNext(SearchArticle article) {
                        Log.d("Search Article", "On Next");
                        updateRecyclerUI(article.getResponse().getDocs());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Search Article", "On Error " + Log.getStackTraceString(e));
                        mHttpRequest.internetDisable(mProgressBar, getString(R.string.no_internet), getContext());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Search Article", "On Complete !");
                    }
                });
    }

    // Dispose subscription
    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
    }


    // ------------------
    //  UPDATE UI
    // ------------------

    // This method executes the http request When the screen is swipe
    private void configureSwipeRefreshLayout() {
        this.mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                assert getArguments() != null;
                int position = getArguments().getInt(KEY_POSITION);
                switch (position){
                    case TOP_STORIES_POSITION :
                        executeTopStoriesHttpRequest();
                        break;
                    case MOST_POPULAR_POSITION :
                        executeMostPopularHttpRequest();
                        break;
                    case BUSINESS_POSITION :
                        executeBusinessHttpRequest();
                        break;
                }
                //executeSearchArticleHttpRequest();
            }
        });
    }


    // This methods Update the UI from the recycler view with the adapter
    private void updateRecyclerUI(List<? extends ArticleItem> articleItems) {
        this.mHttpRequest.updateUIWhenStopingHTTPRequest(mRefreshLayout, mProgressBar);
        this.mTopStoriesArray.clear();
        this.mTopStoriesArray.addAll(articleItems);
        this.mAdapter.notifyDataSetChanged();
    }
}




