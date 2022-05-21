package com.example.project_who_yak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Category3_Latest_Adapter extends BaseAdapter {
        private Context context;
        LayoutInflater mLayoutInflater ;
        private List<Category3_Latest> Category3_Latest;

        public Category3_Latest_Adapter(Context context, ArrayList<Category3_Latest> Category3_Latest_List) {
                this.context = context;
                this.Category3_Latest = Category3_Latest_List;
        }

        @Override
        public int getCount() {
                return Category3_Latest.size();
        }

        @Override
        public Object getItem(int i) {return Category3_Latest.get(i);}

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

                noticeText.setText(Category3_Latest.get(i).getNotice());
                nameText.setText(Category3_Latest.get(i).getName());
                dateText.setText(Category3_Latest.get(i).getDate());
                dateText.setText(Category3_Latest.get(i).getRate());
                dateText.setText(Category3_Latest.get(i).getCategory());

                v.setTag(Category3_Latest.get(i).getNotice());
                return v;
        }
        }
