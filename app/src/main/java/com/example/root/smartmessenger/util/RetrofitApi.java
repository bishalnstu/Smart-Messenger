package com.example.root.smartmessenger.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ROOT on 2/3/2018.
 */

public class RetrofitApi {

    //address of your remote server. Here I used localhost
    private static Retrofit retrofit = null;

    private RetrofitApi() {} // So that nobody can create an object with constructor


    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static synchronized Retrofit getClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://imbishal.me")
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
