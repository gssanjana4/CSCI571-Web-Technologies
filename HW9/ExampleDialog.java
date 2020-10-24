package com.example.newsapp.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExampleDialog extends AppCompatDialogFragment {
    private ArrayList<String> titledialog;
    private ArrayList<String> imagedialog;
    private ArrayList<String> url;
    private ArrayList<String> sectiondialog;
    private ArrayList<String> timedialog;
    private ArrayList<String> iddialog;
    private int pos;
    private exampledialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate( R.layout.dialoglayout, null );
        ImageView i = view.findViewById( R.id.itemImage );//and set image to image view
        final TextView text = view.findViewById( R.id.itemText );
        Glide.with( view.getContext() ).load( imagedialog.get( pos ) ).into( i );
        text.setText( titledialog.get( pos ) );
        final ImageView imagebookmark = (ImageView) view.findViewById( R.id.bookmarkdialog );

        ImageView imagetwitter = (ImageView) view.findViewById( R.id.twitter );
        imagetwitter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String textTwitter = "Check out this Link: "+url.get( pos );
                String url = "https://twitter.com/intent/tweet?text="+textTwitter+"&hashtags=CSCI571NewsSearch";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData( Uri.parse(url));
                startActivity(i);
            }
        } );

        final SharedPreferences pref = getContext().getSharedPreferences( "mypref", Context.MODE_MULTI_PROCESS );
        if (pref.contains( url.get( pos ) )) {
            imagebookmark.setImageResource( R.drawable.ic_turned_in_24px );
        } else {
            imagebookmark.setImageResource( R.drawable.ic_turned_in_not_24px );
        }
        imagebookmark.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println( url.get( pos ) );
                SharedPreferences.Editor edit = pref.edit();
                if (!pref.contains( url.get( pos ) )) {
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put( "imageurl", imagedialog.get(pos) );
                        jsonObj.put( "title", titledialog.get(pos) );
                        jsonObj.put( "section", sectiondialog.get(pos) );
                        jsonObj.put( "time", timedialog.get(pos) );
                        jsonObj.put( "id", iddialog.get(pos) );
                        jsonObj.put( "url", url.get(pos) );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    edit.putString( url.get(pos), jsonObj.toString());
                    edit.commit();
                    imagebookmark.setImageResource( R.drawable.ic_turned_in_24px );
                    Toast.makeText( v.getContext(), "\'" + titledialog.get( pos ) + "\' was added to Bookmarks", Toast.LENGTH_SHORT ).show();
                } else {
                    edit.remove( url.get( pos ) );
                    edit.commit();
                    imagebookmark.setImageResource( R.drawable.ic_turned_in_not_24px );
                    Toast.makeText( v.getContext(), "\'" + titledialog.get( pos ) + "\' was removed to Bookmarks", Toast.LENGTH_SHORT ).show();
                }
                listener.bookmarkhomeupdate();

            }
        } );


//            builder.setView(view).setTitle( this.getString("Sanjana" ) ).setNeutralButton( "cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            } );
        builder.setView( view );
        return builder.create();
    }

    public void setValues(ArrayList<String> title, ArrayList<String> image, int position, ArrayList<String> url,ArrayList<String> section,ArrayList<String> time,ArrayList<String> id) {
        this.titledialog = title;
        this.imagedialog = image;
        this.pos = position;
        this.url = url;
        this.sectiondialog = section;
        this.timedialog = time;
        this.iddialog = id;
    }
 public void setListener(exampledialogListener interfacelistener){
        listener = interfacelistener;
 }
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach( context );
//        try {
//            listener = (exampledialogListener) context;
//        } catch (ClassCastException e) {
//            e.printStackTrace();
//        }
//    }

    public interface exampledialogListener {
        void bookmarkhomeupdate();

    }
}
