package com.softmed.ccm_facility.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.softmed.ccm_facility.utils.constants.BASE_URL;

/**
 * Created by issy on 11/27/17.
 */

public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Gson gson = new GsonBuilder()
            .setLenient() //Without this it returns an error from the server that it requires to set lenient in order to read the json
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password, String hfuuid) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken, hfuuid);
        }

        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken, String hfuuid) {
        if (!TextUtils.isEmpty(authToken)) {

            if (!TextUtils.isEmpty(hfuuid)){
                AuthenticationInterceptor interceptor =
                        new AuthenticationInterceptor(authToken, hfuuid);

                if (!httpClient.interceptors().contains(interceptor)) {
                    httpClient.addInterceptor(interceptor);

                    builder.client(httpClient
                            .readTimeout(600, TimeUnit.SECONDS)
                            .connectTimeout(600, TimeUnit.SECONDS)
                            .build());
                    //retrofit = builder.build();
                }
            }else {
                LoginInterceptor loginInterceptor =
                        new LoginInterceptor(authToken);

                if (!httpClient.interceptors().contains(loginInterceptor)) {
                    httpClient.addInterceptor(loginInterceptor);

                    builder.client(httpClient
                            .readTimeout(120, TimeUnit.SECONDS)
                            .connectTimeout(120, TimeUnit.SECONDS)
                            .build());
                    //retrofit = builder.build();
                }
            }

        }

        //return retrofit.create(serviceClass);
        return builder.build().create(serviceClass);
    }

}
