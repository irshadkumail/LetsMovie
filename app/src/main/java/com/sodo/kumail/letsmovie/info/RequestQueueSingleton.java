package com.sodo.kumail.letsmovie.info;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.sodo.kumail.letsmovie.MyApplication;

import java.lang.ref.ReferenceQueue;

/**
 * Created by kumail on 5/11/2016.
 */
public class RequestQueueSingleton {

    RequestQueue requestQueue;
    ImageLoader imageLoader;

    public RequestQueueSingleton() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(MyApplication.getInstance().getApplicationContext());
        }
        if (imageLoader == null) {
            imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                LruCache<String, Bitmap> cache = new LruCache<>(20);

                @Override
                public Bitmap getBitmap(String s) {
                    return cache.get(s);
                }

                @Override
                public void putBitmap(String s, Bitmap bitmap) {
                    cache.put(s, bitmap);

                }
            });

        }
    }
    public static RequestQueueSingleton getInstance()
    {
        return new RequestQueueSingleton();
    }


    public RequestQueue getRequestQueue()
    {
        return requestQueue;
    }
    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }


}
