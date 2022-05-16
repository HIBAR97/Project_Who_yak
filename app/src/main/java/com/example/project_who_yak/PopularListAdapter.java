package com.example.project_who_yak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PopularListAdapter extends BaseAdapter {
    private Context context;
    LayoutInflater mLayoutInflater ;
    private List<Popular> PopularList;

    public PopularListAdapter(Context context, List<Popular> popularList) {
        this.context = context;
        this.PopularList = popularList;
    }

    @Override
    public int getCount() {
        return PopularList.size();
    }

    @Override
    public Object getItem(int i) {
        return PopularList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.notice, null);
        //View v = mLayoutInflater.inflate(R.layout.notice, null);
        TextView noticeText = (TextView) v.findViewById(R.id.noticeText);
        TextView nameText = (TextView) v.findViewById(R.id.nameText);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);

        noticeText.setText(PopularList.get(i).getNotice());
        nameText.setText(PopularList.get(i).getName());
        dateText.setText(PopularList.get(i).getDate());

        v.setTag(PopularList.get(i).getNotice());
        return v;
    }

}
