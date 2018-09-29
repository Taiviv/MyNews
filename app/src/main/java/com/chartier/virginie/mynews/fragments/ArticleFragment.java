package com.chartier.virginie.mynews.fragments;

import android.os.Bundle;
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
import com.chartier.virginie.mynews.model.TopStories;
import com.chartier.virginie.mynews.utils.Helper;
import com.chartier.virginie.mynews.utils.HttpRequest;
import com.chartier.virginie.mynews.view.RecyclerViewArticle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.chartier.virginie.mynews.view.PageAdapter.topStoriesSection;


public class ArticleFragment extends Fragment {


    // FOR DATA

    // Create keys for our Bundle
    private static final String KEY_POSITION = "position";
    public static final int TOP_STORIES_POSITION = 0;
    public static final int MOST_POPULAR_POSITION = 1;
    public static final int BUSINESS_POSITION = 2;
    public List<TopStories.Resultum> mTopStoriesArray = new ArrayList<>();
    private Disposable mDisposable;
    private RecyclerViewArticle mAdapter;
    private Helper mHelper = new Helper();
    private HttpRequest mHttpRequest = new HttpRequest();


    //FOR DESIGN

    @BindView(R.id.fragment_article_textview) TextView titleTextView;
    @BindView(R.id.recyclerview_layout) RecyclerView mRecyclerView;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this,view);
        //We get the index position of the viewPager
        int position = getArguments().getInt(KEY_POSITION);
        if (position == TOP_STORIES_POSITION){
            titleTextView.setText("Top Stories");
        }
        if (position == MOST_POPULAR_POSITION){
            titleTextView.setText("Most Popular");
        }
        if (position == BUSINESS_POSITION){
            titleTextView.setText("Business");
        }

        //A progress bar is loaded and setted
        this.mHttpRequest.progressBarHandler(mProgressBar, getContext());
        //Call the recyclerView builder method
        this.configureRecyclerView();
        this.executeTopStoriesHttpRequest();

        return view;
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

    //This method configure the recyclerView set up
    private void configureRecyclerView() {
        //Calling the adapter
        this.mAdapter = new RecyclerViewArticle(mTopStoriesArray, Glide.with(this));
        //Set them with natives methods
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //When user click on an item a new activity is launched to display a webView
        this.displayWebView();
    }


    //This method used to open a web view directly in the app, not by default application
    private void displayWebView() {
        this.mAdapter.setOnItemClickListener(new RecyclerViewArticle.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mHelper.openActivityAsBrowser(mTopStoriesArray.get(position).getUrl(), getContext(), WebViewActivity.class);
            }
        });
    }


    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    //This method handle the Http rest request  takes a model object as parameter and display a result
    public void executeTopStoriesHttpRequest() {
        this.mHttpRequest.updateUIWhenStartingHTTPRequest(mProgressBar);

        this.mDisposable = NewYorkTimeStream.streamFetchTopStories(topStoriesSection[0])
                .subscribeWith(new DisposableObserver<TopStories>() {

                    @Override
                    public void onNext(TopStories topStoriesItems) {
                        Log.d("TopStories Tag", "On Next");
                        upDateTopStoriesUI(topStoriesItems);
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


    //Dispose subscription
    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
    }


    // ------------------
    //  UPDATE UI
    // ------------------


    //This method Update the UI from the recycler view with the adapter
    private void upDateTopStoriesUI(TopStories topStories) {
        this.mTopStoriesArray.clear();
        this.mTopStoriesArray.addAll(topStories.getResults());
        this.mAdapter.notifyDataSetChanged();
    }
}












