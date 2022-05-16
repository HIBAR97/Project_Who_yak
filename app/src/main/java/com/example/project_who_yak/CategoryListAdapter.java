package com.example.project_who_yak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CategoryListAdapter extends BaseAdapter {
    private Context context;
    LayoutInflater mLayoutInflater ;
    private List<Category> CategoryList;

    public CategoryListAdapter(Context context, List<Category> category) {
        this.context = context;
        this.CategoryList = category;
    }

    @Override
    public int getCount() {
        return CategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return CategoryList.get(i);
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

        noticeText.setText(CategoryList.get(i).getNotice());
        nameText.setText(CategoryList.get(i).getName());
        dateText.setText(CategoryList.get(i).getDate());

        v.setTag(CategoryList.get(i).getNotice());
        return v;
    }

}
