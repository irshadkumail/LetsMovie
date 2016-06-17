package com.sodo.kumail.letsmovie.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sodo.kumail.letsmovie.DetailActivity;
import com.sodo.kumail.letsmovie.R;
import com.sodo.kumail.letsmovie.info.Movie;
import com.sodo.kumail.letsmovie.info.RequestQueueSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kumail on 5/11/2016.
 */
public class FragmentC extends Fragment implements CardViewHolder.OnMyItemClicked {

    RecyclerView recyclerView;
    MyRecyclerAdapter myRecyclerAdapter;
    ArrayList<Movie> responseList;


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {

        View view = layoutInflater.inflate(R.layout.fragment_c, parent, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_c);
        setLayoutManager();
        getJson();

        return view;
    }

    public void setLayoutManager() {
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL)
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void getJson() {
        RequestQueue requestQueue = RequestQueueSingleton.getInstance().getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://api.themoviedb.org/3/movie/popular?api_key=ce011fa6eca448775cf42ad614c86548", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                responseList = parseJson(jsonObject);
                myRecyclerAdapter = new MyRecyclerAdapter(getContext(), responseList);
                recyclerView.setAdapter(myRecyclerAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public ArrayList<Movie> parseJson(JSONObject jsonObject) {
        ArrayList<Movie> responseList = new ArrayList<Movie>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject current = (JSONObject) jsonArray.get(i);

                String name = current.getString("title");
                String release_date = current.getString("release_date");
                String language = current.getString("original_language");
                String overview = current.getString("overview");
                int ratings = current.getInt("vote_average");
                String poster_path = current.getString("poster_path");

                Movie movie = new Movie(name, release_date, overview, language, ratings, poster_path);
                responseList.add(movie);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseList;
    }

    @Override
    public void onItemClicked(int pos, View view) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        Movie movie = responseList.get(pos);
        bundle.putParcelable("MOVIE", movie);
        intent.putExtra("BUNDLE", bundle);
        startActivity(intent);

    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<CardViewHolder> {
        Context context;
        ArrayList<Movie> arrayList;
        LayoutInflater layoutInflater;

        public MyRecyclerAdapter(Context context, ArrayList<Movie> arraylist) {
            this.context = context;
            this.arrayList = arraylist;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public CardViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
            View view = layoutInflater.inflate(R.layout.card_view, parent, false);
            CardViewHolder cardViewHolder = new CardViewHolder(view);
            cardViewHolder.setOnMyItemClicked(FragmentC.this);

            return cardViewHolder;
        }

        public void onBindViewHolder(final CardViewHolder cardViewHolder, int p) {
            cardViewHolder.movieTitle.setText(arrayList.get(p).title_name);
            cardViewHolder.releaseDate.setText(arrayList.get(p).release_date);
            cardViewHolder.ratingBar.setProgress(arrayList.get(p).rating);
            ImageLoader imageLoader = RequestQueueSingleton.getInstance().getImageLoader();

            imageLoader.get(arrayList.get(p).poster_url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    cardViewHolder.imageView.setImageBitmap(imageContainer.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

        }

        public int getItemCount() {
            return arrayList.size();

        }
    }

}
