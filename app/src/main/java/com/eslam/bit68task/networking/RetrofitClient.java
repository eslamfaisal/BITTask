package com.eslam.bit68task.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "https://5bcce576cf2e850013874767.mockapi.io/";
    public static Dispatcher dispatcher = new Dispatcher();
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.dispatcher(dispatcher);
        httpClientBuilder.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClientBuilder.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {

        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public static Dispatcher getDispatcher() {
        return dispatcher;
    }

    public API getApi() {
        return retrofit.create(API.class);
    }
}
