package com.example.project_who_yak;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DrugListAdapter extends BaseAdapter {
    private Context context;
    private List<Drug> druglist;

    public DrugListAdapter(Context context, List<Drug> druglist) {
        this.context = context;
        this.druglist = druglist;
    }

    @Override
    public int getCount() {
        return druglist.size();
    }

    @Override
    public Object getItem(int i) {
        return druglist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v =View.inflate(context,R.layout.druglist,null);
        TextView drugName = (TextView) v.findViewById(R.id.druglist_name);

        drugName.setText(druglist.get(i).getDrugname());

        v.setTag(druglist.get(i).getDrugname());
        return v;
    }



}
