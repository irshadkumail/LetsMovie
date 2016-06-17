package com.sodo.kumail.letsmovie.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sodo.kumail.letsmovie.R;

import org.w3c.dom.Text;

/**
 * Created by kumail on 5/11/2016.
 */
public class CardViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView movieTitle;
    TextView releaseDate;
    RatingBar ratingBar;
    OnMyItemClicked onMyItemClicked;

    public CardViewHolder(final View view)
    {
        super(view);

       imageView= (ImageView) view.findViewById(R.id.card_poster_image);
        movieTitle=(TextView)view.findViewById(R.id.card_movie_title);
        releaseDate=(TextView)view.findViewById(R.id.card_release_date);
        ratingBar=(RatingBar)view.findViewById(R.id.card_rating_bar);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyItemClicked.onItemClicked(getPosition(),view);

            }
        });

    }
    public void setOnMyItemClicked(OnMyItemClicked onMyItemClicked)
    {
        this.onMyItemClicked=onMyItemClicked;
    }

    public interface OnMyItemClicked{
        public void onItemClicked(int pos,View view);
    }
}
