package com.sodo.kumail.letsmovie.info;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by kumail on 5/11/2016.
 */
public class Movie implements Parcelable {

    public String title_name;
    public String release_date;
    public int rating;
    public String language;
    public String overview;
    public String poster_url;

    public Movie(Parcel parcel)
    {
        this.title_name=parcel.readString();
        this.release_date=parcel.readString();
        this.language=parcel.readString();
        this.overview=parcel.readString();
        this.rating=parcel.readInt();
        this.poster_url=parcel.readString();

    }

    public Movie(String title_name,String release_date,String overview,String language,int rating,String poster_url)
    {
        this.title_name=title_name;
        this.release_date=release_date;
        this.overview=overview;
        this.language=language;
        this.rating=rating;
        this.poster_url="http://image.tmdb.org/t/p/w154" + poster_url;
        Log.d("Irshad","Here"+this.poster_url);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title_name);
        dest.writeString(release_date);
        dest.writeString(language);
        dest.writeString(overview);
        dest.writeInt(rating);
        dest.writeString(poster_url);



    }

    public static final Creator CREATOR= new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
