package apps.softmed.com.hfreferal.utils;

import android.text.TextUtils;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by issy on 11/27/17.
 */

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://45.56.90.103:8080/opensrp/";
//    public static final String API_BASE_URL = "http://192.`168.2.8:8080/opensrp/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

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

        return createService(serviceClass, null, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken, String hfuuid) {
        if (!TextUtils.isEmpty(authToken)) {

            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken, hfuuid);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

}
