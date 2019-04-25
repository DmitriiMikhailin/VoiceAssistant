package com.example.voiceassistant;

import android.support.v4.util.Consumer;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Aphorism {
    public static class ApiResult {
        @SerializedName("quoteText")
        public String quoteText;
    }

    public interface AphorismService {
        @GET("/api/1.0/?method=getQuote")
        Call<ApiResult> getResult(@Query("format") String format, @Query("lang") String lang);
    }

    public static void get(final Consumer<String> callback) {
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://api.forismatic.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<ApiResult> call = retrofit
                .create(AphorismService.class)
                .getResult("json", "ru");

        call.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResult = response.body();

                String result = apiResult.quoteText;

                callback.accept(result);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

            }
        });
    }
}
