package com.sodo.kumail.letsmovie;

import android.app.SearchManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sodo.kumail.letsmovie.fragments.FragmentA;
import com.sodo.kumail.letsmovie.fragments.FragmentB;
import com.sodo.kumail.letsmovie.fragments.FragmentC;
import com.sodo.kumail.letsmovie.fragments.NavFrag;
import com.sodo.kumail.letsmovie.fragments.SocialFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener, ActionBar.TabListener,NavFrag.OnSocialClickedListener {

    ViewPager viewPager;
    ActionBar actionBar;
    NavFrag fragment;
    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    LinearLayout linearLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);

        setViewPager();
        setActionBar();
        setActionBarDrawerToggle();




        linearLayout=(LinearLayout)findViewById(R.id.linear_layout);
        fragmentManager=getSupportFragmentManager();
        fragment= (NavFrag) fragmentManager.findFragmentById(R.id.fragment_navigation);
        fragment.setOnSocialClickedListener(this);
        //fragment=findViewById(R.id.fragment_navigation);

    }
    public void setActionBarDrawerToggle()
    {
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.drawable.menu,R.string.drawer_open,R.string.drawer_close){


            @Override
             public void onDrawerOpened(View view)
            {
                super.onDrawerOpened(view);

            }
            @Override
        public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);

            }

        };
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);



    }
    public void setViewPager()
    {
        viewPager= (ViewPager) findViewById(R.id.main_pager);
        viewPager.setOnPageChangeListener(this);
        viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));

    }
    public void setActionBar()
    {
        actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1=actionBar.newTab();
        tab1.setTabListener(this);
        tab1.setText("NOW PLAYING");

        ActionBar.Tab tab2=actionBar.newTab();
        tab2.setTabListener(this);
        tab2.setText("UPCOMING");

        ActionBar.Tab tab3=actionBar.newTab();
        tab3.setTabListener(this);
        tab3.setText("POPULAR");

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
    }
    public void onTabSelected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction)
    {
        viewPager.setCurrentItem(tab.getPosition());

    }
    public void onTabReselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction)
    {

    }
    public void onTabUnselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction)
    {

    }

    public void onPageSelected(int position)
    {

        actionBar.setSelectedNavigationItem(position);
    }
    public void onPageScrolled(int pos,float toffSet,int pixels)
    {

    }
    public void onPageScrollStateChanged(int state)
    {

    }
    @Override
    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        actionBarDrawerToggle.onConfigurationChanged(configuration);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager= (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView= (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSocialClick() {


        getSupportActionBar().hide();
        drawerLayout.closeDrawers();
        Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show();
        SocialFragment socialFragment=new SocialFragment();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.drawer_layout, socialFragment);
        fragmentTransaction.addToBackStack("Hello");
        fragmentTransaction.commit();

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }


    public class MyPageAdapter extends FragmentStatePagerAdapter
    {
        public MyPageAdapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
        }

        public Fragment getItem(int pos)
        {
            Fragment fragment=null;

            if(pos==0)
            {
                fragment=new FragmentA();
            }
            if(pos==1)
            {
                fragment=new FragmentB();
            }
            if (pos==2)
            {
                fragment=new FragmentC();
            }
            return fragment;

        }
        public int getCount()
        {
            return 3;
        }



    }

}
