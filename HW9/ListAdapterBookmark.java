package com.example.newsapp.ui.Bookmark;

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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListAdapterBookmark extends RecyclerView.Adapter {

    private ArrayList<String> sectionlist;
    private ArrayList<String> titlelist;
    private ArrayList<String> imagelist;
    private ArrayList<String> timelist;
    private ArrayList<String> urllist;
    private ArrayList<String> id;
    private Context context;
    private int pos;
    interfaceForNoResults listener;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.bookmark_grid_item, parent, false );

        return new ListViewHolder( view );
    }

    ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView( position );
    }

    @Override
    public int getItemCount() {
        return titlelist.size();
    }

    public void setValues(ArrayList<String> title, ArrayList<String> section, ArrayList<String> time, ArrayList<String> image, ArrayList<String> id, ArrayList<String> url, Context context) {
        System.out.println( "Checking setValues function call" );
        this.sectionlist = section;
        this.titlelist = title;
        this.timelist = time;
        this.imagelist = image;
        this.urllist = url;
        this.context = context;
        this.id = id;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, ExampleDialogBookmark.exampledialogListener {
        private TextView mItemText;
        private ImageView mItemImage;
        private TextView mItemTime;
        private TextView mItemSection;
        private ImageView mitemimagebookmark;


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ListViewHolder(View itemView) {
            super( itemView );
//            Intent intent = ((Activity)itemView.getContext()).getIntent();
//            System.out.println( intent.getExtras() );
            itemView.findViewById( R.id.singleview ).setClipToOutline( true );
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
                    System.out.println( urllist.get(pos));
                    if (pref.contains( urllist.get( pos ) )) {
                        edit.remove( urllist.get( pos ) );
                        System.out.println( urllist.size() + "size of bookmark items" );
                        edit.commit();
                        mitemimagebookmark.setImageResource( R.drawable.ic_turned_in_not_24px );
                        Toast.makeText( v.getContext(), "\'" + titlelist.get( pos ) + "\' was removed from Bookmarks", Toast.LENGTH_SHORT ).show();
                    }
                    titlelist.remove( getAdapterPosition() );
                    imagelist.remove( getAdapterPosition() );
                    sectionlist.remove( getAdapterPosition() );
                    urllist.remove( getAdapterPosition() );
                    timelist.remove( getAdapterPosition() );
                    id.remove( getAdapterPosition() );
                    notifyItemRemoved( getAdapterPosition() );
                    notifyItemRangeChanged( getAdapterPosition(),getItemCount() );
                    if(getItemCount()==0){
                        listener.hide();
                    }
                }
            } );
            itemView.setOnClickListener( this );
            itemView.setOnLongClickListener( this );
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bindView(int position) {
            System.out.println( "Checking title" + imagelist );
            pos = position;
            mItemText.setText( titlelist.get( position ) );
            mItemSection.setText( sectionlist.get( position ) );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            ZoneId zoneid = ZoneId.of( "America/Los_Angeles" );
            LocalDateTime localArticleTime = (LocalDateTime.parse(timelist.get(position),formatter));
            ZonedDateTime zonedDateTime1 = ZonedDateTime.ofInstant(localArticleTime, ZoneOffset.UTC, zoneid);
            ZonedDateTime zonedDateTime = (ZonedDateTime.parse( timelist.get( position ) ));
            Duration d = Duration.between( zonedDateTime, zonedDateTime1 );
            System.out.println(d);
            String bookmarkedmonth = String.valueOf( zonedDateTime1.getMonth() );
            int bookmarkeddate = Integer.parseInt( String.valueOf( zonedDateTime1.getDayOfMonth() ) );
            long s = d.getSeconds();
            if (bookmarkeddate < 10) {
                mItemTime.setText( "0" + bookmarkeddate + " " + bookmarkedmonth.toUpperCase().substring( 0, 1 ) + bookmarkedmonth.toLowerCase().substring( 1, 3 ) );
            } else {
                mItemTime.setText( bookmarkeddate + " " + bookmarkedmonth.toUpperCase().substring( 0, 1 ) + bookmarkedmonth.toLowerCase().substring( 1, 3 ) );
            }
//            mItemImage.setImageResource(OurData.picturePath[position]);
            Glide.with( itemView.getContext() ).load( imagelist.get( position ) ).into( mItemImage );
            SharedPreferences pref = itemView.getContext().getSharedPreferences( "mypref", Context.MODE_MULTI_PROCESS );
            if (pref.contains( urllist.get( position ) )) {
                mitemimagebookmark.setImageResource( R.drawable.ic_turned_in_24px );
            } else {
                mitemimagebookmark.setImageResource( R.drawable.ic_turned_in_not_24px );
            }
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ArticleDetails.class );
            intent.putExtra( "id", id.get(getAdapterPosition()) );
            context.startActivity(intent);
        }
        ExampleDialogBookmark exampleDialog;

        public void alertDialog() {
            exampleDialog = new ExampleDialogBookmark();
            exampleDialog.setValues( titlelist, imagelist, getAdapterPosition(), urllist );
            exampleDialog.setListener( ListViewHolder.this );
            exampleDialog.show( ((AppCompatActivity) context).getSupportFragmentManager(), "example dialog" );
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
//
        @Override
        public void bookmarkhomeupdate() {
            titlelist.remove( getAdapterPosition() );
            imagelist.remove( getAdapterPosition() );
            sectionlist.remove( getAdapterPosition() );
            urllist.remove( getAdapterPosition() );
            timelist.remove( getAdapterPosition() );
            id.remove( getAdapterPosition() );
            exampleDialog.dismiss();
            notifyItemRemoved( getAdapterPosition() );
            notifyItemRangeChanged( getAdapterPosition(),getItemCount() );
            if(getItemCount()==0){
                listener.hide();
            }
        }
    }
    public void setListener(interfaceForNoResults lsr){
        listener = lsr;
    }


    public interface interfaceForNoResults{
        void hide();
    }
}

