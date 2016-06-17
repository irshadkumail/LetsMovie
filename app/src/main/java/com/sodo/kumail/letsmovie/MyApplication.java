package com.sodo.kumail.letsmovie;

import android.app.Application;

/**
 * Created by kumail on 5/11/2016.
 */
public class MyApplication extends Application {

    static MyApplication myApplication;

    public void onCreate()
    {
        super.onCreate();
        myApplication=this;
    }
    public static MyApplication getInstance()
    {
        return myApplication;
    }
}
