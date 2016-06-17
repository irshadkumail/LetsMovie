package com.sodo.kumail.letsmovie.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class FragmentA extends Fragment implements CardViewHolder.OnMyItemClicked {

    RecyclerView recyclerView;
    MyRecyclerAdapter adapter;
    ArrayList<Movie> responseList;


    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);


    }
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup parent,Bundle bundle)
    {

        View view= layoutInflater.inflate(R.layout.fragment_a,parent,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.fragment_a_recycler);
        setLayoutManager();
        getJson();
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        return view;

    }
    public void setLayoutManager()
    {
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
        {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)== Configuration.SCREENLAYOUT_SIZE_NORMAL)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)== Configuration.SCREENLAYOUT_SIZE_SMALL)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

    }
    public void getJson()
    {
        RequestQueue requestQueue= RequestQueueSingleton.getInstance().getRequestQueue();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,"http://api.themoviedb.org/3/movie/now_playing?api_key=ce011fa6eca448775cf42ad614c86548",null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                responseList=parseJson(jsonObject);
                adapter=new MyRecyclerAdapter(getContext(),responseList);
                recyclerView.setAdapter(adapter);


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    public ArrayList<Movie> parseJson(JSONObject jsonObject)
    {
        ArrayList<Movie> responseList=new ArrayList<>();

        try {
            JSONArray jsonArray=jsonObject.getJSONArray("results");

            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject myjsonObject= (JSONObject) jsonArray.get(i);
                String title=myjsonObject.getString("title");
                String overview=myjsonObject.getString("overview");
                String release_date=myjsonObject.getString("release_date");
                int rating=myjsonObject.getInt("vote_average");
                String language=myjsonObject.getString("original_language");
                String poster_url=myjsonObject.getString("poster_path");
                Log.d("Irshad",poster_url);

                Movie movie= new Movie(title,release_date,overview,language,rating,poster_url);

                responseList.add(movie);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return responseList;
    }

    @Override
    public void onItemClicked(int pos, View view) {
        Movie movie=responseList.get(pos);
        Bundle bundle= new Bundle();
        bundle.putParcelable("MOVIE",movie);

        Intent intent=new Intent(getActivity(),DetailActivity.class);
        intent.putExtra("BUNDLE",bundle);
        startActivity(intent);

    }


    class MyRecyclerAdapter extends RecyclerView.Adapter<CardViewHolder>
    {
        Context context;
        ArrayList<Movie> arrayList;
        LayoutInflater layoutInflater;
        Bitmap image;
        String url;

        public MyRecyclerAdapter(Context context,ArrayList<Movie> arrayList)
        {
            this.context=context;
            this.arrayList=arrayList;
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public CardViewHolder onCreateViewHolder(ViewGroup parent,int p)
        {
           View view= layoutInflater.inflate(R.layout.card_view,parent,false);

            CardViewHolder cardViewHolder=new CardViewHolder(view);
            cardViewHolder.setOnMyItemClicked(FragmentA.this);

            return cardViewHolder;

        }
        public void onBindViewHolder(final CardViewHolder viewHolder,int p)
        {
            viewHolder.movieTitle.setText(arrayList.get(p).title_name);
            viewHolder.releaseDate.setText(arrayList.get(p).release_date);
            viewHolder.ratingBar.setProgress(arrayList.get(p).rating);
            ImageLoader imageLoader=RequestQueueSingleton.getInstance().getImageLoader();
            url=arrayList.get(p).poster_url;
            Log.d("kul",url);
            imageLoader.get(url, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                            image=imageContainer.getBitmap();

                            viewHolder.imageView.setImageBitmap(image);
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("kul",volleyError.toString());

                        }
                    });


        }

        public int getItemCount()
        {

            return arrayList.size();
        }
    }

}
