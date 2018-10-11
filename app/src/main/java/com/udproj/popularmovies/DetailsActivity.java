package com.udproj.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView mTvTitleYear;
    private TextView mTvVotes;
    private TextView mTvOverview;
    private ImageView mIvPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Receiving a Movie object
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("Movie");

        //Populating field with data from the Movie object
        mTvTitleYear = findViewById(R.id.tv_title_release_date);
        mTvTitleYear.setText(getString(R.string.details_title_year, movie.getTitle(), movie.getRelease_year()));

        mTvVotes = findViewById(R.id.tv_votes);
        mTvVotes.setText(getString(R.string.details_votes, movie.getVote_average(), movie.getVote_count()));

        mTvOverview = findViewById(R.id.tv_overview);
        mTvOverview.setText(movie.getOverview());

        //Loading poster image
        mIvPoster = findViewById(R.id.iv_poster_details);
        String moviePosterUrl = NetworkUtils.buildImageUrl(movie.getPoster_path()).toString();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Picasso.get().load(moviePosterUrl).resize(displayMetrics.widthPixels * 37 / 100, 0).into(mIvPoster);
    }
}
