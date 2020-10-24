package com.example.newsapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        styling();
        final BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_bookmark)
                .build();
        BottomNavigationView nav_home = (BottomNavigationView) findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
     
        final AutoCompleteTextView searchAutoComplete = searchView.findViewById(searchView.getResources().getIdentifier("android:id/search_src_text", null,null));
        
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                menuItem.collapseActionView();
                return false;
            }
        });

       
            public boolean onMenuItemActionCollapse(MenuItem item) {
                invalidateOptionsMenu();
                
                return true;
            }
        });
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, searchResults.class );
                intent.putExtra( "query", query );
                startActivity(intent);
                return false;
            }

searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchAutoComplete.setText(parent.getItemAtPosition(position).toString());
            }
        });

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }

            private void bingsearch(String newText) {
             volleyRequest = Volley.newRequestQueue( getApplicationContext() );
                String url = "https://api.cognitive.microsoft.com/bing/v7.0/Suggestions/?mkt=en-IN&subscription-Key=1eb208768ea44369b22b84db66e5479b&q="+newText;
                System.out.println( url + " url of article" );
              
                            autoCompleteAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
             
                volleyRequest.add( request );

            }
        });
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void styling() {
        getSupportActionBar().setBackgroundDrawable( new ColorDrawable( Color.WHITE ) );
        getSupportActionBar().setElevation( 0 );

    }


}
