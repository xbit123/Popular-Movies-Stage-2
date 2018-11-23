package com.udproj.popularmovies.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udproj.popularmovies.R;
import com.udproj.popularmovies.model.Trailer;
import com.udproj.popularmovies.utils.NetworkUtils;

import java.util.List;

public class DetailsTrailersAdapter extends RecyclerView.Adapter<DetailsTrailersAdapter.TrailersAdapterViewHolder> {
    private List<Trailer> mTrailers;

    private final TrailersAdapterOnClickHandler mClickHandler;

    public interface TrailersAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    public DetailsTrailersAdapter(TrailersAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class TrailersAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mTrailerThumbnailImageView;
        private final TextView mTrailerTitleTextView;

        public TrailersAdapterViewHolder(View view) {
            super(view);
            mTrailerThumbnailImageView = view.findViewById(R.id.iv_trailer_thumbnail_rv);
            mTrailerTitleTextView = view.findViewById(R.id.tv_trailer_title_rv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = mTrailers.get(adapterPosition);
            mClickHandler.onClick(trailer);
        }
    }

    @Override
    public TrailersAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new TrailersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailersAdapterViewHolder trailersAdapterViewHolder, int position) {
        ImageView trailerIv = trailersAdapterViewHolder.mTrailerThumbnailImageView;
        TextView trailerTv = trailersAdapterViewHolder.mTrailerTitleTextView;

        Trailer trailer = mTrailers.get(position);

        String trailerThumbnailUrl = NetworkUtils.buildTrailerThumbnailUrl(trailer.getYoutube_id()).toString();
        Picasso.get().load(trailerThumbnailUrl)/*.resize(200, 0)*/.into(trailerIv);

        trailerTv.setText(trailer.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mTrailers == null) {
            return 0;
        } else {
            return mTrailers.size();
        }
    }

    public void setTrailersData(List<Trailer> trailers) {
        mTrailers = trailers;
        notifyDataSetChanged();
    }
}