package com.sodo.kumail.letsmovie;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sodo.kumail.letsmovie.info.Movie;
import com.sodo.kumail.letsmovie.info.RequestQueueSingleton;

/**
 * Created by kumail on 5/16/2016.
 */
public class DetailActivity extends ActionBarActivity{


    TextView movieTitle,releaseDate,language,overview;
    RatingBar ratingBar;
    ImageView imageView;
    Movie movie;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.deatil_activity);

        Bundle mybundle=getIntent().getBundleExtra("BUNDLE");
        movie=mybundle.getParcelable("MOVIE");
        init();
        initData();





    }
    public void init()
    {
        movieTitle= (TextView) findViewById(R.id.detail_movie_title);
        releaseDate= (TextView) findViewById(R.id.detail_release_date);
        language=(TextView)findViewById(R.id.detail_language);
        imageView= (ImageView) findViewById(R.id.detail_image);
        ratingBar= (RatingBar) findViewById(R.id.detail_rating_bar);
        overview= (TextView) findViewById(R.id.detail_overview);
    }
    public void initData()
    {
        movieTitle.setText(movie.title_name);
        releaseDate.setText(movie.release_date);
        language.setText(getLanguage(getLanguage(movie.language)));
        ratingBar.setProgress(movie.rating);
        overview.setText(movie.overview);
        ImageLoader imageLoader= RequestQueueSingleton.getInstance().getImageLoader();
        imageLoader.get(movie.poster_url,new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                imageView.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });


    }
    public String getLanguage(String str)
    {
        if(str.equalsIgnoreCase("en"))
        {

            return "Language-English";
        }

        return str;
    }
}
