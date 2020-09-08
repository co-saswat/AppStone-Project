package api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClient {

    public static Retrofit retrofit = null;
    public static Retrofit getClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);         //Used to get the details in logcat


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)        // ping server to timeout if not connected in 1 minute
                .readTimeout(30,TimeUnit.SECONDS)           // ping server to timeout if not get writeable content in 30 seconds
                .writeTimeout(30,TimeUnit.SECONDS)          // ping server to timeout if not get readable content in 30 seconds
                .addInterceptor(loggingInterceptor).build();        // to display the log interceptor in logcat

        retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(ScalarsConverterFactory.create()).client(client).build();
        return retrofit;

    }
}

