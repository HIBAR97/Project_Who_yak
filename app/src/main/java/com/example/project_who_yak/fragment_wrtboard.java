package com.example.project_who_yak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment_wrtboard extends Fragment {

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wrtboard, container, false);

//        Button btnadd = view.findViewById(R.id.blt_brd);
//        Button btncnl = view.findViewById(R.id.blt_rmb);
//        Button btnfil = view.findViewById(R.id.fl_plus);



    }
}