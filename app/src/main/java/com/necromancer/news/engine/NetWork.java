package com.necromancer.news.engine;

import com.necromancer.news.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP-PC on 2018/4/12.
 */

public class NetWork {
    private static Retrofit sRetrofit;

    /**
     * 返回Retrofit
     */
    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            OkHttpClient httpClient;
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
            httpClient = builder.build();
            //创建Retrofit构建器
            sRetrofit = new Retrofit.Builder()
                    .baseUrl("http://v.juhe.cn/")
                    //返回的数据通过Gson解析
                    .addConverterFactory(GsonConverterFactory.create())
                    //使用RxJava模式
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return sRetrofit;
    }
    public static Api createApi(){
        return NetWork.getRetrofit().create(Api.class);
    }
}
