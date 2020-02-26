package com.project.simoneconigliaro.movies.models;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("genre")
    @Expose
    private String genre;

    @SerializedName("poster")
    @Expose
    private String poster;

    @ColumnInfo(name = "timestamp")
    private int timestamp;


    public Movie(int id, String title, String year, String genre, String poster) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.poster = poster;
    }

    @Ignore
    public Movie(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.genre = movie.getGenre();
        this.poster = movie.getPoster();
    }

    @Ignore
    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        Movie movie = (Movie) obj;
        return movie.getId() == getId() && movie.getTitle().contains(getTitle());
    }
}
