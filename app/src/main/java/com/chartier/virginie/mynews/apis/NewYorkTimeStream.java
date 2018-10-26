package com.chartier.virginie.mynews.apis;

import com.chartier.virginie.mynews.model.MostPopular;
import com.chartier.virginie.mynews.model.SearchArticle;
import com.chartier.virginie.mynews.model.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Virginie Chartier alias Taiviv on 19/09/2018.
 */
public class NewYorkTimeStream {

    // ------------------------------
    //  Reactive X
    // ------------------------------

    // Create observable for TopStories
    public static Observable<TopStories> streamFetchTopStories(String section) {
        NewYorkTimeService newYorkTimeService1 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return newYorkTimeService1.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }


    // Create observable for MostPopular
    public static Observable<MostPopular> streamFetchMostPopular() {
        NewYorkTimeService newYorkTimeService2 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return newYorkTimeService2.getMostPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Create observable for searchArticle
    public static Observable<SearchArticle> streamFetchSearchArticle(String query, String news_desk, String begin_date, String end_date) {
        NewYorkTimeService newYorkTimeService3 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return newYorkTimeService3.getSearchArticle(query, news_desk, begin_date, end_date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
