package com.example.project_who_yak;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Userpage_profile extends Fragment {

    private TextView fraguserid;
    private View view;
    @Nullable
    //Connect xml file using container
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_userpage_profile, container, false);

        //아이디값을 가져오기위한 bundle
        view = inflater.inflate(R.layout.fragment_userpage_profile, container, false);
        //Bundle bundle = getArguments();
        //String userID = bundle.getString("userID");
       // fraguserid = view.findViewById(R.id.frag_userid);
       // fraguserid.setText(userID);
        return view;

    }
}