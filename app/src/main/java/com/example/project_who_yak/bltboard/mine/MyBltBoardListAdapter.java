package com.example.project_who_yak.bltboard.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project_who_yak.R;

import java.util.List;

public class MyBltBoardListAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater mLayoutInflater ;
    private List<MyBltBoard> myBltBoardList;

    public MyBltBoardListAdapter(Context context, List<MyBltBoard> myBltBoardList) {
        this.context = context;
        this.myBltBoardList = myBltBoardList;
    }

    @Override
    public int getCount() {
        return myBltBoardList.size();
    }

    @Override
    public Object getItem(int i) {
        return myBltBoardList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.board, null);
        //View v = mLayoutInflater.inflate(R.layout.notice, null);
        TextView titleText = (TextView) v.findViewById(R.id.noticeText);
        TextView writerText = (TextView) v.findViewById(R.id.nameText);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);
        TextView popularText = (TextView) v.findViewById(R.id.popularText);

        titleText.setText(myBltBoardList.get(i).getTitle());
        writerText.setText(myBltBoardList.get(i).getWriter());
        dateText.setText(myBltBoardList.get(i).getDate());
        popularText.setText(myBltBoardList.get(i).getPopularity());

        v.setTag(myBltBoardList.get(i).getTitle());
        return v;
    }

}