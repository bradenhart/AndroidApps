package com.bradenhart.navigation;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by bradenhart on 17/12/14.
 */
public class TimetableFragment extends Fragment {

    private RelativeLayout monRL, tueRL, wedRL, thuRL, friRL;
    private int dayVal;
    private String[] titles;
    private String mTitle;

    // Default constructor
    public TimetableFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_timetable, container, false);

        Bundle args = getArguments();
        if (args != null && args.containsKey("entries")) {
            ArrayList<TimetableEntry> newEntries1 = args.getParcelableArrayList("entries");
            if (newEntries1 != null && newEntries1.size() > 0) {
                for (int i = 0; i < newEntries1.size(); i++) {
                    dayVal = newEntries1.get(i).getDay();
                    addViewToTimetable(getDayLayout(dayVal, v), newEntries1.get(i));
                }
            }
        }

        return v;
    }

    private RelativeLayout getDayLayout(int dayVal, View v) {
        RelativeLayout dayLayout;

        switch (dayVal) {
            case 1:
                dayLayout = (RelativeLayout) v.findViewById(R.id.mondayRelativeLayout);
                break;
            case 2:
                dayLayout = (RelativeLayout) v.findViewById(R.id.tuesdayRelativeLayout);
                break;
            case 3:
                 dayLayout = (RelativeLayout) v.findViewById(R.id.wednesdayRelativeLayout);
                break;
            case 4:
                 dayLayout = (RelativeLayout) v.findViewById(R.id.thursdayRelativeLayout);
                break;
            case 5:
                dayLayout = (RelativeLayout) v.findViewById(R.id.fridayRelativeLayout);
                break;
            default:
                dayLayout = null;
                break;
        }

        return dayLayout;
    }

    private int getPixelsFromDP(int dipValue) {
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
        return px;
    }

    private int getPixelsFromSP(int spValue) {
        Resources r = getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, r.getDisplayMetrics());
        return px;
    }

    // ADDING THE ENTRIES TO THE TIMETABLE
    public void addViewToTimetable(RelativeLayout rl, TimetableEntry entry) {
        final TextView newView = new TextView(getActivity());
        int screenDensityFactor = (int) getResources().getDisplayMetrics().density;
        int height = getPixelsFromDP(entry.getDuration());
        int start = getPixelsFromDP(entry.getStartTime());
        String entryText = entry.getFullString();

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, height-2);
        lp.setMargins(0, start, 2, 0);

        newView.setLayoutParams(lp);
        newView.setText(entryText);
        newView.setTextSize((float) getPixelsFromSP(7));
        newView.setGravity(Gravity.CENTER);
        newView.setBackgroundColor(getResources().getColor(R.color.newView_color));

        newView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                newView.setBackgroundResource(R.drawable.longclick_border);
                Toast.makeText(getActivity(), "Long click worked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        rl.addView(newView);
    }

    @Override
    public void onResume() {
        super.onResume();
        titles = getResources().getStringArray(R.array.nav_drawer_items);
        mTitle = titles[0];
        // Set title
        getActivity().getActionBar()
                .setTitle(mTitle);
    }

}
