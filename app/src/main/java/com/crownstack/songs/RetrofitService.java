package com.crownstack.songs;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amitkumartiwari on 2/9/2018.
 */

public class RetrofitService {

    private final static long DEFAULT_CONNECTION_TIME_OUT = 60;
    private final static long DEFAULT_READ_TIME_OUT = 60;
    private final static long DEFAULT_WRITE_TIME_OUT = 60;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/")
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.connectTimeout(DEFAULT_CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG && !httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
