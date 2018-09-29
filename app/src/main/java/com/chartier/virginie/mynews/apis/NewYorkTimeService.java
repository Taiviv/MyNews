package com.chartier.virginie.mynews.apis;


import com.chartier.virginie.mynews.model.TopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by Virginie Chartier alias Taiviv on 19/09/2018.
 */
public interface NewYorkTimeService {
    String ApiKey = "3f27c361e7a148be9d2bf9beb0b4e885";
    String BaseUrl = "http://api.nytimes.com/svc/";


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    // Top Stories API
    @GET("topstories/v2/{section}.json?api-key=" + ApiKey)
    Observable<TopStories> getTopStories(@Path("section") String section);


}


