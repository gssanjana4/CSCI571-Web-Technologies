package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleDetails extends AppCompatActivity {
    String titledescription = new String();
    String sectiondescription = new String();
  


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
           
                startActivity( i );
                return true;
            case R.id.menu_bookmark:
                SharedPreferences.Editor edit = pref.edit();
                if (!pref.contains( urldescription )) {
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put( "imageurl", imagedescription );
                        jsonObj.put( "title", titledescription );
                        jsonObj.put( "section", sectiondescription );
                      
                    }

                    edit.putString( urldescription, jsonObj.toString() );
//                        edit.putInt( urllist.get(pos), urllist.size() );
//                    System.out.println(urldescription+"size of bookmark items");
                    edit.commit();
                    menu.getItem( 0 ).setIcon( R.drawable.ic_turned_in_24px );
                    Toast.makeText( getApplicationContext(), "\'" + titledescription + "\' was added to Bookmarks", Toast.LENGTH_SHORT ).show();
                } else {
                    edit.remove( urldescription );
                    edit.commit();
                    menu.getItem( 0 ).setIcon( R.drawable.ic_turned_in_not_24px );
                    Toast.makeText( getApplicationContext(), "\'" + titledescription + "\' was removed to Bookmarks", Toast.LENGTH_SHORT ).show();
                }
                return true;


            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void styling() {
        findViewById( R.id.border_desc ).setClipToOutline( true );
        getSupportActionBar().setBackgroundDrawable( new ColorDrawable( Color.WHITE ) );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setHomeButtonEnabled( true );
        getSupportActionBar().setElevation( 0 );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate( R.menu.menu_description, menu );
        return true;
//        return super.onCreateOptionsMenu( menu );
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        pref = getSharedPreferences( "mypref", Context.MODE_MULTI_PROCESS );
page");
//            textView.setText( intent.getStringExtra("title"));
////            imageView.setImageResource( Integer.parseInt( intent.getStringExtra( "image" ) ) );

//            System.out.println( "Checking details" +title);
    }

    private void jsonParser() {
        RequestQueue volleyRequest = Volley.newRequestQueue( this );
        String url = "https://hw9nodejs.wm.r.appspot.com/api/details?article=" + id;
        System.out.println( url + " url of article" );
        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
             ring( "webTitle" );
                    System.out.println( titledescription + " : title description " );
                    sectiondescription = jsonObject.getString( "sectionName" );
                    datedescription = jsonObject.getString( "webPublicationDate" );
                    urldescription = jsonObject.getString( "webUrl" );
                    ((TextView) findViewById( R.id.fullarticle )).setMovementMethod( LinkMovementMethod.getInstance() );
                    ((TextView) findViewById( R.id.fullarticle )).setText( Html.fromHtml( "<a href=\"" + urldescription + "\">View Full Article</a>" ) );


                    JSONArray jsonArraydesc = jsonObject.getJSONObject( "blocks" ).getJSONArray( "body" );
                    for (int i = 0; i < jsonArraydesc.length(); i++) {
                        description += jsonArraydesc.getJSONObject( i ).getString( "bodyHtml" );
                    }
//                        ZonedDateTime zonedDateTime = (ZonedDateTime.parse( timelist.get(position) ));
                    ZoneId zone = ZoneId.of("America/Los_Angeles");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

                    LocalDateTime localArticleTime = (LocalDateTime.parse(datedescription,formatter));
                    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(localArticleTime, ZoneOffset.UTC, zone);

                    TextView article_description = findViewById( R.id.articledescription );
                    textView.setText( titledescription );
                    Glide.with( ArticleDetails.this ).load( imagedescription ).into( imageView );
                    sectionview.setText( sectiondescription );
                    dateview.setText( DateTimeFormatter.ofPattern( "dd MMM yyyy" ).format( zonedDateTime ) );
                    article_description.setText( Html.fromHtml( description ) );
                    //System.out.println( "aimge"+singleResult.getJSONObject( "fields" ).getString("thumbnail"  ) );

                    getSupportActionBar().setTitle( titledescription );
                    menu.getItem( 1 ).setIcon( R.drawable.bluetwitter );
                    if (pref.contains( urldescription )) {
                        menu.getItem( 0 ).setIcon( R.drawable.ic_turned_in_24px );
                    } else {
                        menu.getItem( 0 ).setIcon( R.drawable.ic_turned_in_not_24px );

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
      

    }

}

