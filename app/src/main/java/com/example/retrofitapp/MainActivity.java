package com.example.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retrofitapp.MovieModels.Genre;
import com.example.retrofitapp.MovieModels.MovieModel;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original";
    public static final String API_KEY = "5954db931e9993ddb65982259ca72648";
    @BindView(R.id.movieGenreTv)
    TextView movieGenreTv;
    @BindView(R.id.movieNameTv)
    TextView movieNameTv;
    @BindView(R.id.movieIv)
    ImageView movieIv;
    @BindView(R.id.movieOverviewTv)
    TextView movieOverviewTv;
    @BindView(R.id.loading)
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife
        ButterKnife.bind(this);

        String review_id = "5d975abdae26be001abe129b";
        int movie_id = 278;

        HashMap<String, String> queries = new HashMap<>();
        queries.put("api_key", API_KEY);
        queries.put("language", "en-US");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ReviewModel> review = apiInterface.getReview(review_id, API_KEY);

        review.enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
//                helloTv.setText(response.body().getContent());
            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {

            }
        });

        Call<MovieModel> movieCall = apiInterface.getMovie(movie_id, queries);
        movieCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                loading.setVisibility(View.GONE);
                assert response.body() != null;
                String img = response.body().getBackdropPath();
                if (img != null) {
                    Glide.with(MainActivity.this)
                            .load(IMAGE_BASE_URL + img).into(movieIv);
                }

                Log.d("MyApp", IMAGE_BASE_URL + img);

                movieNameTv.setText(response.body().getTitle());

                List<Genre> genre = response.body().getGenres();
                movieGenreTv.setText(genre.get(0).getName());
                movieOverviewTv.setText(response.body().getOverview());
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });

    }
}
