package com.example.newsapp.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    LineChart lineChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        lineChart = (LineChart) root.findViewById( R.id.trends_graph );
        trendingraph("coronavirus");

        EditText text1 = (EditText) root.findViewById( R.id.search_keyword );
        text1.setOnEditorActionListener( new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((actionId == EditorInfo.IME_ACTION_SEND)||(event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
                    trendingraph( v.getText().toString() );
                    return true;
                }
                return false;
            }
        } );
        return root;
    }

    private void trendingraph(final String keyword) {
        RequestQueue volleyRequest = Volley.newRequestQueue( getActivity().getApplicationContext() );
        String url = "https://hw9nodejs.wm.r.appspot.com/trends?trends="+keyword;

        final JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(JSONObject response) {
            try{
                ArrayList<Entry> searchword = new ArrayList();
                JSONArray jsonArray = response.getJSONObject( "default" ).getJSONArray( "timelineData" );
                for(int i=0;i<jsonArray.length();i++){
                    searchword.add(new Entry( i, jsonArray.getJSONObject( i ).getJSONArray( "value" ).getInt( 0 )));
                }
                LineDataSet lineDataSet;
                if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
                    lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
                    lineDataSet.setValues(searchword);
                    lineDataSet.setLabel("Trending Chart for "+keyword);
                    lineChart.getData().notifyDataChanged();
                    lineChart.notifyDataSetChanged();
                    lineChart.invalidate();
                } else {
                    System.out.println("Chart Pol");
                    lineDataSet = new LineDataSet(searchword, "Trending Chart for "+keyword);
                    lineDataSet.setDrawIcons(false);
                    lineDataSet.disableDashedLine();
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
                    lineDataSet.setColor(getActivity().getColor(R.color.colorPrimary));
                    lineDataSet.setCircleColor(getActivity().getColor(R.color.colorPrimary));
                    lineDataSet.setLineWidth(1f);
                    lineDataSet.setCircleRadius(3f);
                    lineDataSet.setDrawCircleHole(false);
                    lineDataSet.setValueTextSize(9f);
                    lineDataSet.setDrawFilled(false);
                    lineDataSet.setFormLineWidth(1f);
                    lineDataSet.setDrawHighlightIndicators(false);
                    lineChart.getAxisLeft().setDrawGridLines(false);
                    lineChart.getXAxis().setDrawGridLines(false);
                    lineChart.getAxisRight().setDrawGridLines(false);
                    lineChart.getAxisLeft().setDrawAxisLine(false);
                    lineChart.getAxisRight().setAxisLineWidth(1f);
                    lineChart.getXAxis().setAxisLineWidth(1f);
                    lineChart.getLegend().setTextSize(16f);

                    //set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                    lineDataSet.setFormSize(15.f);
                    // set1.setFillColor(Color.DKGRAY);
//            if (Utils.getSDKInt() >= 18) {
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
//                set1.setFillDrawable(drawable);
//            } else {
//
//            }
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(lineDataSet);
                    LineData data = new LineData(dataSets);
                    lineChart.setData(data);
                    lineChart.invalidate();
                }
            }catch(Exception e){
                e.printStackTrace();
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
}
