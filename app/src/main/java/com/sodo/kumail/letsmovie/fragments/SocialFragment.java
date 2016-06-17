package com.sodo.kumail.letsmovie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sodo.kumail.letsmovie.MainActivity;
import com.sodo.kumail.letsmovie.R;

import org.json.JSONObject;

/**
 * Created by kumail on 5/11/2016.
 */
public class SocialFragment extends Fragment {

    LoginButton loginButton;
    CallbackManager callbackManager;


    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        Log.d("Irshad","aagaya");
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager=CallbackManager.Factory.create();
    }
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup parent,Bundle bundle)
    {
        Log.d("Irshad","aagaya idhar b");
        View view=layoutInflater.inflate(R.layout.social_fragment,parent,false);
        loginButton= (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

        return view;
    }
    public void start(AccessToken accessToken)
    {

        GraphRequest request= GraphRequest.newMeRequest(accessToken,new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {


            }
        });
    }
    public void onPause()
    {
        super.onPause();
        ((ActionBarActivity)getActivity()).getSupportActionBar().show();

    }
}
