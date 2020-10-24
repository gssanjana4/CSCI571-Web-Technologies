package com.example.newsapp;

import android.content.Context;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> mlistData;

    public AutoCompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mlistData = new ArrayList<>();
    }

    public void setData(List<String> list) {
        mlistData.clear();
        mlistData.addAll(list);
    }
    @Override
    public int getCount() {
        if(mlistData.size()<=5)
            return mlistData.size();
        else  return 5;
    }
    @Nullable
    @Override
    public String getItem(int position) {
        return mlistData.get(position);
    }
    /**
     * Used to Return the full object directly from adapter.
     *
     * @param position
     * @return
     */
    public String getObject(int position) {
        return mlistData.get(position);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        Filter dataFilter = new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = mlistData;
                    filterResults.count = mlistData.size();
                }
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;
    }

}
