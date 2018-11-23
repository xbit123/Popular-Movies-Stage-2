package com.udproj.popularmovies.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udproj.popularmovies.R;
import com.udproj.popularmovies.model.Movie;
import com.udproj.popularmovies.utils.NetworkUtils;

import java.util.List;

public class MainMoviesAdapter extends RecyclerView.Adapter<MainMoviesAdapter.MoviesAdapterViewHolder> {
    private List<Movie> mMovies;

    private final MoviesAdapterOnClickHandler mClickHandler;

    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MainMoviesAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mPosterImageView;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            mPosterImageView = view.findViewById(R.id.iv_poster_rv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder moviesAdapterViewHolder, int position) {
        String moviePosterUrl = NetworkUtils.buildImageUrl(mMovies.get(position).getPoster_path()).toString();
        ImageView posterIv = moviesAdapterViewHolder.mPosterImageView;
        Picasso.get().load(moviePosterUrl)/*.resize(mImageWidth, 0)*/.into(posterIv);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        } else {
            return mMovies.size();
        }
    }

    public void setMoviesData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}
