package com.chartier.virginie.mynews.apis;


import com.chartier.virginie.mynews.model.MostPopular;
//import com.chartier.virginie.mynews.model.MostPopularResult;
//import com.chartier.virginie.mynews.model.MostPopularResultDeserializer;
import com.chartier.virginie.mynews.model.SearchArticle;
import com.chartier.virginie.mynews.model.TopStories;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Virginie Chartier alias Taiviv on 19/09/2018.
 */
public interface NewYorkTimeService {
    String ApiKey = "q7ArKCzXuhO735DpAAQtjkksJFKp8HYL";
    String BaseUrl = "http://api.nytimes.com/svc/";
    String SearchArticleFl = "fl=web_url,snippet,pub_date,news_desk,multimedia,document_type,type_of_material";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl)
            // .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(MostPopularResult.class,new MostPopularResultDeserializer()).create()))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    // Top Stories API
    @GET("topstories/v2/{section}.json?api-key=" + ApiKey)
    Observable<TopStories> getTopStories(@Path("section") String section);

    // Most Popular API
    @GET("mostpopular/v2/mostemailed/all-sections/1.json?api-key=" + ApiKey)
    Observable<MostPopular> getMostPopular();

    // Search Activity API
    @GET("search/v2/articlesearch.json?" + SearchArticleFl + "&sort=newest&page=3&api-key=" + ApiKey)
    Observable<SearchArticle> getSearchArticle(@Query("q") String query,
                                               @Query("fq") String news_desk,
                                               @Query("begin_date") String begin_date,
                                               @Query("end_date") String end_date);

    // Notification Activity API
    @GET("search/v2/articlesearch.json?" + SearchArticleFl + "&sort=newest&api-key=" + ApiKey)
    Observable<SearchArticle> getNotification(@Query("q") String query,
                                              @Query("fq") String news_desk);
}


