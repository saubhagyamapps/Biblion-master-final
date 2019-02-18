package app.biblion.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static app.biblion.util.Constant.mBaseUrl;


public class ApiClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit==null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            retrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}