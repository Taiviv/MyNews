package com.chartier.virginie.mynews.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.chartier.virginie.mynews.R;
import com.chartier.virginie.mynews.model.ArticleItem;
import com.chartier.virginie.mynews.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Virginie Chartier alias Taiviv on 08/10/2018.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.item_layout) RelativeLayout mLayout;
    @BindView(R.id.item_title) TextView mTextView;
    @BindView(R.id.item_image) ImageView mImageView;
    @BindView(R.id.item_date) TextView mDateView;
    @BindView(R.id.item_summary) TextView mSummaryView;
    DateUtils mFormater = new DateUtils();
    //Because an error du to Glide it 'is necessary to concat a base url
    //String NytUri = "https://www.nytimes.com/";



    public ItemViewHolder(View itemView, final ArticleAdapter.OnItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        //Here we give the possibility to click on item (itemView)
        //We can choose to click on a specific element like an image view instead
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }


    public void updateWithArticles(ArticleItem result, RequestManager glide) {

        this.mTextView.setText(result.getSection());
        this.mDateView.setText(mFormater.getItemArticleFormatedDate((result.getPublishedDate())));
        this.mSummaryView.setText(result.getTitle());
        if (result.getUrlImage() != null){
            glide.load(result.getUrlImage()).into(mImageView);
        }
    }

   /*public void updateWithSearchArticle(Doc searchArticle, RequestManager glide) {

        this.mTextView.setText(searchArticle.getNewDesk());
        if (searchArticle.getPublishedDate()!= null){
            this.mDateView.setText(mFormater.getItemArticleFormatedDate(searchArticle.getPubDate()));}
        this.mSummaryView.setText(searchArticle.getSnippet());
        if((searchArticle.getMultimedia() != null) && (!searchArticle.getMultimedia().isEmpty()))
            glide.load(NytUri+searchArticle.getMultimedia().get(0).getUrl()).into(this.mImageView);
    }*/

    @Override
    public void onClick(View v) {

    }
}
