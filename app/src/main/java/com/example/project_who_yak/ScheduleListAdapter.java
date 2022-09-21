package com.example.project_who_yak;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class ScheduleListAdapter extends BaseAdapter {
    private Context context;
    private List<Schedule> schedulelist;

    public ScheduleListAdapter(Context context, List<Schedule> schedulelist) {
        this.context = context;
        this.schedulelist = schedulelist;
    }

    @Override
    public int getCount() {
        return schedulelist.size();
    }

    @Override
    public Object getItem(int i) {
        return schedulelist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v =View.inflate(context,R.layout.schedulelist,null);
        TextView schedule = (TextView) v.findViewById(R.id.scheduleList);
        TextView schedule_date = (TextView) v.findViewById(R.id.scheduleList_date);



        schedule.setText(schedulelist.get(i).getSchedule());
        schedule_date.setText(schedulelist.get(i).getDate());
        v.setTag(schedulelist.get(i).getSchedule());

        return v;
    }



}
