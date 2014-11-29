package com.example.bradenhart.myunitimetable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by bradenhart on 18/11/14.
 */
public class TTEntryListAdapter extends ArrayAdapter<TimetableEntry>{

    public Context mContext;

    public TTEntryListAdapter(Context context, int resource) {
        super(context, resource);
        context = mContext;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(10, 10));
        } else {
            textView = (TextView) convertView;
        }


        return textView;
    }

}




















