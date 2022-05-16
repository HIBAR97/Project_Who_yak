package com.example.project_who_yak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RememberListAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater mLayoutInflater ;
    private List<Remember> remembersList;

    public RememberListAdapter(Context context, List<Remember> remembersList) {
        this.context = context;
        this.remembersList = remembersList;
    }

    @Override
    public int getCount() {
        return remembersList.size();
    }

    @Override
    public Object getItem(int i) {
        return remembersList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.remember, null);
        //View v = mLayoutInflater.inflate(R.layout.notice, null);
        TextView titleTRmb = (TextView) v.findViewById(R.id.titleTRmb);
        TextView nameRmb = (TextView) v.findViewById(R.id.nameRmb);
        TextView dateRmb = (TextView) v.findViewById(R.id.dateRmb);

        titleTRmb.setText(remembersList.get(i).getTitle());
        nameRmb.setText(remembersList.get(i).getName());
        dateRmb.setText(remembersList.get(i).getDate());

        v.setTag(remembersList.get(i).getTitle());
        return v;
    }

}