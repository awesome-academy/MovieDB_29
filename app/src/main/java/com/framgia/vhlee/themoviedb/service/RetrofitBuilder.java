package com.framgia.vhlee.themoviedb.service;

import com.framgia.vhlee.themoviedb.BuildConfig;
import com.framgia.vhlee.themoviedb.utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static final String API_KEY = "api_key";
    private static final int READ_TIMEOUT = 5000;
    private static final int WRITE_TIMEOUT = 5000;
    private static final int CONNECT_TIMEOUT = 5000;
    private static Retrofit sRetrofit;

    public static Retrofit initClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl httpUrl = request
                        .url()
                        .newBuilder()
                        .addQueryParameter(API_KEY, BuildConfig.API_KEY)
                        .build();
                Request.Builder requestBuilder = request
                        .newBuilder()
                        .url(httpUrl);
                return chain.proceed(requestBuilder.build());
            }
        });
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(httpClientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
