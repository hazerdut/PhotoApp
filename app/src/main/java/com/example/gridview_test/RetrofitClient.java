package com.example.gridview_test;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String URL = "https://networkimage.herokuapp.com/";
    private static Retrofit instance = new Retrofit.Builder()
            .baseUrl(URL)
            .client((new OkHttpClient.Builder()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static APISevice getApi(){
        return instance.create(APISevice.class);
    }
}
