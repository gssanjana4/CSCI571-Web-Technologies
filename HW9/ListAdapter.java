package com.example.newsapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.ArticleDetails;
import com.example.newsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter {

    private ArrayList<String> sectionlist;
    private ArrayList<String> titlelist;
    private ArrayList<String> imagelist;
    private ArrayList<String> timelist;
    private ArrayList<String> urllist;
    private ArrayList<String> idlist;

    private Context context;
    private int pos ;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.list_items, parent, false );
        return new ListViewHolder( view );
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView( position );
    }

    @Override
    public int getItemCount() {
        return titlelist.size();
    }

    public void setValues(ArrayList<String> title, ArrayList<String> section, ArrayList<String> time, ArrayList<String> image, ArrayList<String> url, ArrayList<String> id, Context context) {
        System.out.println( "Checking setValues function call" );
        this.sectionlist = section;
        this.titlelist = title;
        this.timelist = time;
        this.imagelist = image;
        this.urllist = url;
        this.idlist = id;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, ExampleDialog.exampledialogListener {
        private TextView mItemText;
        private ImageView mItemImage;
        private TextView mItemTime;
        private TextView mItemSection;
        private ImageView mitemimagebookmark;



        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ListViewHolder(View itemView){
            super(itemView);



//            Intent intent = ((Activity)itemView.getContext()).getIntent();
//            System.out.println( intent.getExtras() );
            itemView.findViewById( R.id.singleview).setClipToOutline( true );
            mItemText = (TextView) itemView.findViewById( R.id.itemText );
            mItemImage = (ImageView) itemView.findViewById( R.id.itemImage );
            mItemSection = (TextView) itemView.findViewById( R.id.sectionText );
            mItemTime = (TextView) itemView.findViewById( R.id.timeText );
            mitemimagebookmark = (ImageView) itemView.findViewById( R.id.bookmark );

            final SharedPreferences pref = itemView.getContext().getSharedPreferences( "mypref", Context.MODE_MULTI_PROCESS );

            mitemimagebookmark.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   pos = getAdapterPosition();

                    SharedPreferences.Editor edit = pref.edit();
                    System.out.println( urllist.get(pos) );
                    if(!pref.contains(urllist.get( pos ))) {
                        JSONObject jsonObj = new JSONObject();
                        try {
                            jsonObj.put( "imageurl", imagelist.get(pos) );
                            jsonObj.put( "title", titlelist.get(pos) );
                            jsonObj.put( "section", sectionlist.get(pos) );
                            jsonObj.put( "time", timelist.get(pos) );
                            jsonObj.put( "id", idlist.get(pos) );
                            jsonObj.put( "url", urllist.get(pos) );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        edit.putString( urllist.get(pos), jsonObj.toString());
//                        edit.putInt( urllist.get(pos), urllist.size() );
                        System.out.println(urllist.size()+"size of bookmark items");
                        edit.commit();
                        mitemimagebookmark.setImageResource( R.drawable.ic_turned_in_24px );
                        Toast.makeText( v.getContext(), "\'"+titlelist.get( pos )+"\' was added to Bookmarks", Toast.LENGTH_SHORT ).show();
                    }else {
                        edit.remove( urllist.get(pos));
                        edit.commit();
                        mitemimagebookmark.setImageResource( R.drawable.ic_turned_in_not_24px );
                        Toast.makeText( v.getContext(), "\'"+titlelist.get( pos )+"\' was removed to Bookmarks", Toast.LENGTH_SHORT ).show();
                    }
                }
            });
           itemView.setOnClickListener( this );
           itemView.setOnLongClickListener( this );
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bindView(int position){
//            System.out.println("Checking title" + imagelist);
            pos=position;
            mItemText.setText( titlelist.get( position ) );
            mItemSection.setText( sectionlist.get(position));
            ZoneId zoneid = ZoneId.of( "America/Los_Angeles" );
            ZonedDateTime zonedDateTime1 = ZonedDateTime.now(zoneid);
            ZonedDateTime zonedDateTime = (ZonedDateTime.parse( timelist.get(position) ));
            Duration d = Duration.between( zonedDateTime , zonedDateTime1 );
            System.out.println(d);
            long s = d.getSeconds();
            if(s<60){
                mItemTime.setText( String.valueOf(d.getSeconds())+"s ago");
            }
            else if(s>=60 && s<3600){
                mItemTime.setText(String.valueOf(s/60)+"m ago");
            }
            else if(s>=3600 && s<216000){
                mItemTime.setText(String.valueOf(s/3600)+"h ago");
            }
            else{
                mItemTime.setText(String.valueOf(s/216000)+"d ago");
            }
//            mItemImage.setImageResource(OurData.picturePath[position]);
            Glide.with(itemView.getContext()).load( imagelist.get( position ) ).into( mItemImage );
            final SharedPreferences pref = itemView.getContext().getSharedPreferences( "mypref", Context.MODE_MULTI_PROCESS );

            if(pref.contains(urllist.get( pos )))
            {
                mitemimagebookmark.setImageResource( R.drawable.ic_turned_in_24px );
            }
            else{
                mitemimagebookmark.setImageResource( R.drawable.ic_turned_in_not_24px );
            }
        }
         @Override
        public void onClick(View v) {
              Intent intent = new Intent(context, ArticleDetails.class );
             intent.putExtra( "id", idlist.get(getAdapterPosition()) );
             context.startActivity(intent);
        }

//            Toast.makeText( v.getContext(), "Why did you do that? That hurts!!!", Toast.LENGTH_LONG ).show();
       public void alertDialog() {
            ExampleDialog exampleDialog = new ExampleDialog();
            exampleDialog.setValues(titlelist, imagelist, getAdapterPosition(), urllist, sectionlist,timelist,idlist);
            exampleDialog.setListener( ListViewHolder.this);
            exampleDialog.show( ((AppCompatActivity)context).getSupportFragmentManager(),"example dialog" );
//            AlertDialog.Builder dialog=new AlertDialog.Builder( itemView.getContext());
//            dialog.setMessage("Please Select any option");
//            dialog.setTitle("Dialog Box");
//            dialog.setPositiveButton("YES",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog,
//                                            int which) {
//                            Toast.makeText(itemView.getContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
//                        }
//                    });
//            dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(itemView.getContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
//                }
//            });
//            AlertDialog alertDialog=exampleDialog.OnCreateDialog();
//            alertDialog.show();
        }

        @Override
        public boolean onLongClick(View v) {
            alertDialog();
            return false;
        }
        @Override
        public void bookmarkhomeupdate() {
            final SharedPreferences pref = itemView.getContext().getSharedPreferences( "mypref", Context.MODE_MULTI_PROCESS );
            if(pref.contains(urllist.get( getAdapterPosition() )))
            {
                mitemimagebookmark.setImageResource( R.drawable.ic_turned_in_24px );
            }
            else{
                mitemimagebookmark.setImageResource( R.drawable.ic_turned_in_not_24px );
            }
        }
    }
}