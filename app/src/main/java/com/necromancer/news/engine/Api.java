package com.necromancer.news.engine;

import com.necromancer.news.domain.NewsBean;

import rx.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HP-PC on 2018/4/13.
 */

public interface Api {
    @GET("toutiao/index")
    Observable<NewsBean> getNews(@Query("key")String key, @Query("type")String type);
}
