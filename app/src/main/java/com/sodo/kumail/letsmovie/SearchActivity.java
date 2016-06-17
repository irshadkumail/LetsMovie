package com.sodo.kumail.letsmovie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by kumail on 5/30/2016.
 */
public class SearchActivity extends Activity {

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search);
        if(getIntent().getAction()== Intent.ACTION_SEARCH)
            Toast.makeText(this,"Hogaya Search",Toast.LENGTH_LONG).show();

    }
}
