package com.udproj.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "movie")
public class Movie implements Parcelable{

    private int vote_count;
    @PrimaryKey
    private int id;
    private double vote_average;
    private String title;
    private double popularity;
    private String poster_path;
    private String overview;
    private int release_year;

    public Movie(int vote_count, int id, double vote_average, String title, double popularity, String poster_path, String overview, int release_year) {
        this.vote_count = vote_count;
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_year = release_year;
    }

    public int getId() {
        return id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public double getPopularity() {
        return popularity;
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

    public int getRelease_year() {
        return release_year;
    }

    public Movie (Parcel in) {
        this.vote_count = in.readInt();
        this.id = in.readInt();
        this.vote_average = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.release_year = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.vote_count);
        parcel.writeInt(this.id);
        parcel.writeDouble(this.vote_average);
        parcel.writeString(this.title);
        parcel.writeDouble(this.popularity);
        parcel.writeString(this.poster_path);
        parcel.writeString(this.overview);
        parcel.writeInt(this.release_year);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
