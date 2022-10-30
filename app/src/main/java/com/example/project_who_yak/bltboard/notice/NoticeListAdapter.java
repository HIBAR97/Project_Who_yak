package com.example.project_who_yak.bltboard.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project_who_yak.R;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater mLayoutInflater ;
    private List<Notice> noticeList;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
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
        TextView popularText = (TextView) v.findViewById(R.id.popularText);

        noticeText.setText(noticeList.get(i).getTitle());
        nameText.setText(noticeList.get(i).getWriter());
        dateText.setText(noticeList.get(i).getDate());
        popularText.setText(noticeList.get(i).getPopularity());

        v.setTag(noticeList.get(i).getTitle());
        return v;
    }

}