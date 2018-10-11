package com.udproj.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

//Movie class to store movie objects. Implements Parcelable interface so it can be passed in intent.
public class Movie implements Parcelable {
    private int vote_count;
    private double vote_average;
    private String title;
    private String poster_path;
    private String overview;
    private int release_year;

    public Movie(int vote_count, double vote_average, String title, String poster_path, String overview, int release_year) {
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_year = release_year;
    }

    public int getVote_count() {
        return vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public int getRelease_year() { return release_year; }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(vote_count);
        dest.writeDouble(vote_average);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeInt(release_year);
    }

    public Movie(Parcel parcel) {
        vote_count = parcel.readInt();
        vote_average = parcel.readDouble();
        title = parcel.readString();
        poster_path = parcel.readString();
        overview = parcel.readString();
        release_year = parcel.readInt();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[0];
        }
    };

    public int describeContents() {
        return 0;
    }
}
