package com.example.project_who_yak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Drug_precaution extends Fragment {
    private TextView edt_drugprecaution;
    private View view;
    @Nullable
    //Connect xml file using container
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drug_precaution, container, false);
        edt_drugprecaution = view.findViewById(R.id.Frag_precaution_text);
        if (this.getArguments() != null){
            String drugPrecaution = getArguments().getString("drugPrecaution");
            edt_drugprecaution.setText(drugPrecaution);
        }
        return view;
    }
}
