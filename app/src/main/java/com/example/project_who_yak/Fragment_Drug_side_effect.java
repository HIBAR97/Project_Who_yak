package com.example.project_who_yak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Drug_side_effect extends Fragment {
    private EditText edt_drugsideeffect;
    private View view;
    @Nullable
    //Connect xml file using container
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drug_side_effect, container, false);
        edt_drugsideeffect = view.findViewById(R.id.Frag_effect_text);
        if (this.getArguments() != null){
            String drugSideEffect = getArguments().getString("drugSideEffect");
            edt_drugsideeffect.setText(drugSideEffect);
        }
        return view;
    }
}
