package com.example.project_who_yak;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_who_yak.databinding.ActivityUserpageBinding;

import java.util.ArrayList;

public class fragment_wrtboard extends Fragment {

    private View view;
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;
    private Spinner spinner1;
    private static final String[] genre = new String[]{"자유", "질문", "공지", "정보"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wrtboard, container, false);

        Button btnadd = (Button) view.findViewById(R.id.blt_brd);
        Button btncnl = (Button) view.findViewById(R.id.blt_rmb);
        Button btnfil = (Button) view.findViewById(R.id.fl_plus);

        spinner1 = (Spinner) view.findViewById(R.id.spn_main);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,genre);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btncnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),activity_bltboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        btnfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mintent = new Intent(Intent.ACTION_PICK);
                mintent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(mintent,GET_GALLERY_IMAGE);
                //갤러리 열기
//                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
//                startActivity(mIntent);
            }
        });
        return view;
    }
}