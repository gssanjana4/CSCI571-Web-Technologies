package com.example.newsapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.R;
import com.example.newsapp.ui.home.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TabFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{

        int position;
        private TextView textView;
        ListAdapter listAdapter;
        SwipeRefreshLayout swipe;
    RequestQueue volleyRequest;
    String urls[] = {"urlworldguardian","urlbusinessguardian", "urlpoliticsguardian", "urltechguardian", "urlsportsguardian","urlscienceguardian"};
    @Override
    public void onResume() {
        super.onResume();
        if(listAdapter!=null){
            listAdapter.notifyDataSetChanged();
        }

   }
        public static Fragment getInstance(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("pos", position);
            TabFragment tabFragment = new TabFragment();
            tabFragment.setArguments(bundle);
            return tabFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            position = getArguments().getInt("pos");
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate( R.layout.tab_layout, container, false);
            jsonParser();
            swipe = (SwipeRefreshLayout) view.findViewById( R.id.swiperefresh_items );
            swipe.setOnRefreshListener( this );
            return view;
        }
   public void jsonParser() {
        volleyRequest = Volley.newRequestQueue( getActivity().getApplicationContext() );
        String url;
        switch (position){
            case 0: url = "https://hw9nodejs.wm.r.appspot.com/api/urlworldguardian";break;
            case 1: url="https://hw9nodejs.wm.r.appspot.com/api/urlbusinessguardian";break;
            case 2: url="https://hw9nodejs.wm.r.appspot.com/api/urlpoliticsguardian";break;
            case 3: url="https://hw9nodejs.wm.r.appspot.com/api/urlsportsguardian";break;
            case 4: url="https://hw9nodejs.wm.r.appspot.com/api/urltechguardian";break;
            case 5: url="https://hw9nodejs.wm.r.appspot.com/api/urlscienceguardian";break;
            default: url="";break;
   }
       JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println( "inResponse" );
                getView().findViewById( R.id.progress_home ).setVisibility(View.GONE );
                getView().findViewById( R.id.text_progress ).setVisibility(View.GONE );

                ArrayList<String> titlelist = new ArrayList();
                ArrayList<String> sectionlist = new ArrayList();
                ArrayList<String> timelist = new ArrayList();
                ArrayList<String> imagelist = new ArrayList();
                ArrayList<String> urllist = new ArrayList();
                ArrayList<String> id = new ArrayList();
                ArrayList<String> description = new ArrayList();

                try {
                    JSONArray jsonArray = response.getJSONObject( "response" ).getJSONArray( "results" );
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject singleResult = jsonArray.getJSONObject( i );
                        titlelist.add( singleResult.getString( "webTitle" ) );
                        sectionlist.add( singleResult.getString( "sectionName" ) );
                        timelist.add( singleResult.getString( "webPublicationDate" ) );
                        id.add( singleResult.getString( "id" ) );

                        try {
                            JSONArray jsonArray1 =singleResult.getJSONObject( "blocks" ).getJSONObject( "main" ).getJSONArray( "elements" ).getJSONObject( 0 ).getJSONArray( "assets" );
                            imagelist.add(jsonArray1.getJSONObject( jsonArray1.length() - 1 ).getString( "file" ));
                        } catch (JSONException e) {
                            imagelist.add( "https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png" );
                        }
                        //System.out.println( "aimge"+singleResult.getJSONObject( "fields" ).getString("thumbnail"  ) );
                        urllist.add( singleResult.getString( "webUrl" ) );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RecyclerView recyclerView = (RecyclerView) getView().findViewById( R.id.listRecyclerView );
                System.out.println( "Before checking title" );
                 listAdapter = new ListAdapter();
                listAdapter.setValues( titlelist, sectionlist, timelist, imagelist, urllist,id, getContext());
                recyclerView.setAdapter( listAdapter );
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
                recyclerView.setLayoutManager( layoutManager );
                if(swipe.isRefreshing()){
                    swipe.setRefreshing( false );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setTag( "tabheadlines" );
        volleyRequest.add( request );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (volleyRequest != null) {
            volleyRequest.cancelAll( "tabheadlines" );
        }
    }

    @Override
    public void onRefresh() {
        jsonParser();

    }
}

