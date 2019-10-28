package com.example.retrofitapp;

import com.example.retrofitapp.MovieModels.MovieModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("review/{review_id}")
    Call<ReviewModel> getReview(@Path("review_id") String review_id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}")
    Call<MovieModel> getMovie(@Path("movie_id") int movie_id, @QueryMap Map<String,String> queries);
}
