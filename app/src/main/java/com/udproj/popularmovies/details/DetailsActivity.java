package com.udproj.popularmovies.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udproj.popularmovies.R;
import com.udproj.popularmovies.model.Movie;
import com.udproj.popularmovies.model.Review;
import com.udproj.popularmovies.model.Trailer;
import com.udproj.popularmovies.utils.Constants;
import com.udproj.popularmovies.utils.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsTrailersAdapter.TrailersAdapterOnClickHandler {

    @BindView(R.id.tv_title_release_date) TextView mTvTitleYear;
    @BindView(R.id.tv_votes) TextView mTvVotes;
    @BindView(R.id.tv_overview) TextView mTvOverview;
    @BindView(R.id.iv_poster_details) ImageView mIvPoster;
    @BindView(R.id.iv_star_off) ImageView mIvStarOff;
    @BindView(R.id.iv_star_on) ImageView mIvStarOn;
    @BindView(R.id.tv_reviews) TextView mTvReviews;
    @BindView(R.id.rv_trailers) RecyclerView mRvTrailers;

    private DetailsViewModel mDetailsViewModel;
    private DetailsTrailersAdapter mDetailsTrailersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvTrailers.setLayoutManager(layoutManager);
        mRvTrailers.setHasFixedSize(true);

        mDetailsTrailersAdapter = new DetailsTrailersAdapter(this);
        mRvTrailers.setAdapter(mDetailsTrailersAdapter);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("Movie");
        Boolean favorite = intent.getBooleanExtra(Constants.FAVORITE, false);

        DetailsViewModelFactory factory = new DetailsViewModelFactory(getApplication(), movie);
        mDetailsViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel.class);

        fillBaseMovieInfo(movie);
        setupFavoriteBtn(favorite);
        setupReviews();
        setupTrailers();
    }

    @Override
    public void onClick(Trailer trailer) {
        Uri uri = NetworkUtils.buildTrailerUri(trailer.getYoutube_id());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void setupTrailers() {
        mDetailsViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                mDetailsTrailersAdapter.setTrailersData(trailers);
            }
        });
    }

    private void setupReviews() {
        mDetailsViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < reviews.size(); i++) {
                    if (i != 0) {
                        sb.append("\n\n*****\n\n");
                    }
                    sb.append(reviews.get(i).getAuthor());
                    sb.append("\n");
                    sb.append(reviews.get(i).getContent());
                }
                mTvReviews.setText(sb.toString());
            }
        });
    }

    private void setupFavoriteBtn(Boolean status) {
        updateFavoriteBtn(status);

        mIvStarOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetailsViewModel.markUnfavorite();
                updateFavoriteBtn(false);
            }
        });

        mIvStarOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetailsViewModel.markFavorite();
                updateFavoriteBtn(true);
            }
        });
    }

    private void updateFavoriteBtn(Boolean status) {
        if (status) {
            mIvStarOff.setVisibility(View.INVISIBLE);
            mIvStarOn.setVisibility(View.VISIBLE);
        } else {
            mIvStarOff.setVisibility(View.VISIBLE);
            mIvStarOn.setVisibility(View.INVISIBLE);
        }
    }

    private void fillBaseMovieInfo(Movie movie) {
        setTitle(movie.getTitle());
        mTvTitleYear.setText(getString(R.string.details_title_year, movie.getTitle(), movie.getRelease_year()));
        mTvVotes.setText(getString(R.string.details_votes, movie.getVote_average(), movie.getVote_count()));
        mTvOverview.setText(movie.getOverview());
        String moviePosterUrl = NetworkUtils.buildImageUrl(movie.getPoster_path()).toString();
        Picasso.get().load(moviePosterUrl).into(mIvPoster);
    }
}
