package com.example.project_who_yak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Category1_Popular_Adapter extends BaseAdapter {
    private Context context;
    LayoutInflater mLayoutInflater ;
    private List<Category1_Popular> Category1_Popular;

    public Category1_Popular_Adapter(Context context, ArrayList<Category1_Popular> Category1_Popular_List) {
        this.context = context;
        this.Category1_Popular = Category1_Popular_List;
    }

    @Override
    public int getCount() {
        return Category1_Popular.size();
    }

    @Override
    public Object getItem(int i) {return Category1_Popular.get(i);}

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.notice, null);
        TextView noticeText = (TextView) v.findViewById(R.id.noticeText);
        TextView nameText = (TextView) v.findViewById(R.id.nameText);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);

        noticeText.setText(Category1_Popular.get(i).getNotice());
        nameText.setText(Category1_Popular.get(i).getName());
        dateText.setText(Category1_Popular.get(i).getDate());
        dateText.setText(Category1_Popular.get(i).getRate());
        dateText.setText(Category1_Popular.get(i).getCategory());

        v.setTag(Category1_Popular.get(i).getNotice());
        return v;
    }
}
