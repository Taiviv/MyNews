package com.chartier.virginie.mynews.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.chartier.virginie.mynews.R;
import com.chartier.virginie.mynews.model.ArticleItem;


import java.util.List;

/**
 * Created by Virginie Chartier alias Taiviv on 12/09/2018.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    // FOR DATA

    private List<ArticleItem> mTopStoriesResult;
    //private List<ArticleItem> mMostPopularResult;
    private OnItemClickListener mListener;
    private RequestManager mGlide;


    public ArticleAdapter(List<ArticleItem> topStoriesResult,/*List<ArticleItem>mostPopularResult,*/RequestManager glide) {
        mTopStoriesResult = topStoriesResult;
        //mMostPopularResult = mostPopularResult;
        mGlide = glide;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        return new ItemViewHolder(view, mListener);
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.updateWithTopStories(this.mTopStoriesResult.get(position), mGlide);
        //holder.updateWithMostPopular(this.mMostPopularResult.get(position), mGlide);
    }


    @Override
    public int getItemCount() {

        return mTopStoriesResult.size();
        //return mMostPopularResult.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //This interface provide onItemClick method which allow to click on something in the activities class
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

