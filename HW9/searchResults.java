package com.example.newsapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.ui.home.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class searchResults extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipe;
    ListAdapter listAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void styling() {
        getSupportActionBar().setBackgroundDrawable( new ColorDrawable( Color.WHITE ) );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setHomeButtonEnabled( true );
        getSupportActionBar().setElevation( 0 );

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        styling();
        swipe = (SwipeRefreshLayout) findViewById( R.id.swiperefresh_items );
        swipe.setOnRefreshListener( this );
        jsonParser();
    }
    public void jsonParser() {
        RequestQueue volleyRequest = Volley.newRequestQueue( getApplicationContext() );
        Intent intent = getIntent();
        final String keyword = intent.getExtras().getString("query");
        String url = "https://hw9nodejs.wm.r.appspot.com/api/guardiansearch?articlesearch="+keyword;

        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println( "inResponse" );
                findViewById( R.id.progress_home ).setVisibility(View.GONE );
                findViewById( R.id.text_progress ).setVisibility(View.GONE );
                if(swipe.isRefreshing()){
                    swipe.setRefreshing( false );
                }


                ArrayList<String> titlelist = new ArrayList();
                ArrayList<String> sectionlist = new ArrayList();
                ArrayList<String> timelist = new ArrayList();
                ArrayList<String> imagelist = new ArrayList();
                ArrayList<String> urllist = new ArrayList();
                ArrayList<String> id = new ArrayList();
                ArrayList<String> description = new ArrayList();

                try {
                    if(response.getJSONObject("response").getJSONArray("results").length()==0){
                        findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
                        return;
                    }

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
//                try {
//                    JSONArray jsonarraydescr = response.getJSONObject("response").getJSONObject( "content" ).getJSONObject( "blocks" ).getJSONArray( "body" );
//                    for (int i = 0; i < jsonarraydescr.length(); i++) {
//                        JSONObject singledesc = jsonarraydescr.getJSONObject( i );
//                        description.add(singledesc.getString("bodyHtml"));
//                        System.out.println("description:   "+description);
//                    } }catch (JSONException e) {
//                    e.printStackTrace();
//                }
                getSupportActionBar().setTitle("Search Results for "+keyword);
                RecyclerView recyclerView = (RecyclerView) findViewById( R.id.listRecyclerView );
                System.out.println( "Before checking title" );
                listAdapter = new ListAdapter();
                listAdapter.setValues( titlelist, sectionlist, timelist, imagelist, urllist,id, searchResults.this);
                recyclerView.setAdapter( listAdapter );
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(searchResults.this );
                recyclerView.setLayoutManager( layoutManager );


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        volleyRequest.add( request );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listAdapter!=null){
            listAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onRefresh() {
        jsonParser();
    }
}
