package com.sodo.kumail.letsmovie.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sodo.kumail.letsmovie.R;

/**
 * Created by kumail on 5/11/2016.
 */
public class NavFrag extends Fragment implements AdapterView.OnItemClickListener {

    ListView listView;
    MyListAdapter myListAdapter;
    OnSocialClickedListener onSocialClickedListener;


    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        myListAdapter=new MyListAdapter(getActivity(),new String[]{"Home","About","Settings","Facebook SignIn"},new int[]{R.drawable.home,R.drawable.about,R.drawable.settings,R.drawable.facebook});

    }
    public void setOnSocialClickedListener(OnSocialClickedListener onSocialClickedListener)
    {
        this.onSocialClickedListener=onSocialClickedListener;
    }
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle bundle)
    {
        View view=inflater.inflate(R.layout.nav_fragment,parent,false);
        listView= (ListView) view.findViewById(R.id.nav_list_view);
        listView.setAdapter(myListAdapter);
        listView.setOnItemClickListener(this);


        return view;
    }
    public void onItemClick(AdapterView adapterView,View view,int p,long id)
    {
        Toast.makeText(getContext(),"Clicked-->"+p,Toast.LENGTH_LONG).show();
        if (p==3)
        {
            onSocialClickedListener.onSocialClick();
        }

    }



    class MyListAdapter extends BaseAdapter
    {
        Context context;
        String[] array;
        int[] images;
        MyListAdapter(Context context,String[] array,int[] images)
        {
            this.array=array;
            this.context=context;
            this.images=images;

        }

        public long getItemId(int pos)
        {
            return pos;
        }
        public Object getItem(int pos)
        {
            return array[pos];
        }
        public int getCount()
        {
            return array.length;
        }

        public View getView(int p,View convertView,ViewGroup parent)
        {
            View row=convertView;
            if(row==null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row=layoutInflater.inflate(R.layout.nav_list_row,parent,false);

                ImageView row_image= (ImageView) row.findViewById(R.id.nav_row_image);
                TextView row_text=(TextView)row.findViewById(R.id.nav_row_text);
                row_image.setImageDrawable(context.getDrawable(images[p]));
                row_text.setText(array[p]);


            }
            return row;
        }

    }
    public interface OnSocialClickedListener
    {
        public void onSocialClick();
    }

}
