package com.example.newsapp.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipe;
    RequestQueue request_weather;
    ListAdapter listAdapter;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback = new LocationCallback(){
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onLocationResult(LocationResult locationResult) {
            currentweather();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        request_weather.cancelAll("weatherRequest");
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onResume() {
        super.onResume();
        if(listAdapter!=null){
            listAdapter.notifyDataSetChanged();
        }
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION )== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED )
            currentweather();

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home, container, false );
        System.out.println("Checking");
        view.findViewById(R.id.weatherlayout).setClipToOutline(true);
        swipe = (SwipeRefreshLayout) view.findViewById( R.id.swiperefresh_items );
        swipe.setOnRefreshListener( this );
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        currentweather();
        //new getResponse().execute();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("In resukt");
        if (requestCode == 44) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                currentweather();
            }
            else {
                jsonParser();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void currentweather(){
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION )== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED ){
            LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
            if(locationManager.isLocationEnabled()){

                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                 
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                        else{
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            
                            city = addressList.get(0).getLocality();
                            state = addressList.get(0).getAdminArea();
//                            Toast.makeText(getContext(), city+" "+state, Toast.LENGTH_LONG).show();
                            request_weather = Volley.newRequestQueue( getActivity().getApplicationContext() );
                            String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=a25dd333717154c06eccd2c5b25bb2da";

                            JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    jsonParser();

                                 
                                    try {
                                        citytext.setText(city);
                                        statetext.setText(state);
                                        temptext.setText(String.valueOf(Math.round(Float.parseFloat(response.getJSONObject("main").getString("temp"))))+" \u2103" );
                                        sunnycloudyetxt.setText(response.getJSONArray("weather").getJSONObject(0).getString("main"));
                                        if(sunnycloudyetxt.getText().equals("Clear")){
                                            weatherimage.setImageResource(R.drawable.clear_weather);
                                        }
                                        else if(sunnycloudyetxt.getText().equals("Snowy"))
                                            weatherimage.setImageResource(R.drawable.snowy_weather);

                                        else if(sunnycloudyetxt.getText().equals("Clouds"))
                                            weatherimage.setImageResource(R.drawable.cloudy_weather);
                                        else if(sunnycloudyetxt.getText().equals("Rain") || sunnycloudyetxt.getText().equals("Drizzle"))
                                            weatherimage.setImageResource(R.drawable.rainy_weather);
                                        else if(sunnycloudyetxt.getText().equals("Thunderstorm"))
                                            weatherimage.setImageResource(R.drawable.thunder_weather);
                                        else
                                            weatherimage.setImageResource(R.drawable.sunny_weather);

                                    } catch (JSONException | NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 

                        }
                    }
                });

            }else{
                Toast.makeText(getContext(), "Turn on Location", Toast.LENGTH_LONG).show();
            }

        }else{
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


    public void jsonParser() {
        RequestQueue volleyRequest = Volley.newRequestQueue( getActivity().getApplicationContext() );
        String url = "https://hw9nodejs.wm.r.appspot.com/api/guardian";

        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println( "inResponse" );
                getActivity().findViewById( R.id.progress_home ).setVisibility(View.GONE );
                getActivity().findViewById( R.id.text_progress ).setVisibility(View.GONE );

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
                            imagelist.add( singleResult.getJSONObject( "fields" ).getString( "thumbnail" ) );
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

                RecyclerView recyclerView = (RecyclerView) getActivity().findViewById( R.id.listRecyclerView );
                System.out.println( "Before checking title" );
                listAdapter = new ListAdapter();
                listAdapter.setValues( titlelist, sectionlist, timelist, imagelist, urllist,id, getContext() );
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
        volleyRequest.add( request );
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onRefresh() {
        currentweather();
    }
}


