package com.example.newsapp.ui.Bookmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class BookmarkFragment extends Fragment implements ListAdapterBookmark.interfaceForNoResults{

    private ArrayList<String> titlelist = new ArrayList<>( );
    private ArrayList<String> sectionlist = new ArrayList<>( );
    private ArrayList<String> timelist = new ArrayList<>( );
    private ArrayList<String> imagelist = new ArrayList<>( );
    private ArrayList<String> urllist = new ArrayList<>( );
    private ArrayList<String> idlist = new ArrayList<>( );
    View view;
    ListAdapterBookmark listAdapter;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_bookmark, container, false );

        jsonParser();


        return view;
    }

    private void jsonParser() {
        final SharedPreferences pref = view.getContext().getSharedPreferences( "mypref", Context.MODE_MULTI_PROCESS );
        Map<String,?> keys = pref.getAll();
        recyclerView = (RecyclerView) view.findViewById( R.id.listRecyclerViewBookmark);

        view.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        for(Map.Entry<String,?> entry : keys.entrySet()){
            view.findViewById(R.id.empty_view).setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            try {
                JSONObject obj = new JSONObject( entry.getValue().toString() );
                titlelist.add( obj.getString( "title"));
                imagelist.add(obj.getString( "imageurl" ));
                sectionlist.add(obj.getString( "section" ));
                timelist.add(obj.getString( "time" ));
                idlist.add(obj.getString( "id" ));
                urllist.add(obj.getString( "url"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
        }
        //pref.getString("imageurl", "stringvalue");
//        for(int i=0;i<)
//        if(pref.contains( "imageurl"))
//            System.out.println("true imageurl");
//
//        System.out.println(pref.getString( String.valueOf( urllist ), "urlcheck" )+"Checking url");


        System.out.println( "Before checking title in Bookmark");
        listAdapter = new ListAdapterBookmark();
        listAdapter.setListener(BookmarkFragment.this);
        listAdapter.setValues( titlelist, sectionlist, timelist, imagelist,idlist, urllist, getContext() );
        recyclerView.setAdapter( listAdapter );
        GridLayoutManager layoutManager = new GridLayoutManager( getContext(),2 );
        recyclerView.setLayoutManager( layoutManager );

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("resume="+listAdapter);
        if(!listAdapter.equals(null)){
            titlelist = new ArrayList<>( );
            sectionlist = new ArrayList<>( );
            timelist = new ArrayList<>( );
            imagelist = new ArrayList<>( );
            urllist = new ArrayList<>( );
            idlist = new ArrayList<>( );
            jsonParser();
        }
    }

    @Override
    public void hide() {
        recyclerView.setVisibility(View.GONE);
        getActivity().findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
    }
}
