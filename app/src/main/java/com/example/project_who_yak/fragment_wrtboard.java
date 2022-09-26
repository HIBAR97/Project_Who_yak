package com.example.project_who_yak;

import static android.os.SystemClock.sleep;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.project_who_yak.databinding.ActivityUserpageBinding;

import org.json.JSONObject;

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
        EditText wrtTitle = (EditText) view.findViewById(R.id.wrt_title);
        EditText wrtContent = (EditText) view.findViewById(R.id.wrt_content);

        spinner1 = (Spinner) view.findViewById(R.id.spn_main);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,genre);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                String title = wrtTitle.getText().toString(); //적혀있는 제목 추출
                String content = wrtContent.getText().toString();//적혀있는 내용 추출
                if(title.equals("") || content.equals("")) { //아무것도 안적으면 toast메세지 출력
                    Toast.makeText(getActivity().getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() { //--여기부터
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getActivity().getApplicationContext(), "게시글을 등록했습니다.", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "게시글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), "json에러", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    };
                    BoardAddRequest boardAddRequest = new BoardAddRequest(title, content, responseListener); //-이부분이 Board에추가하는부분 나머지부분은 연결부분
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext()); //이부분은 ACTIVITY와 FRAGMENT부분이 다름 SCHEDULEACTIVITY
                    //와 FRAGEMNT_WRTBOARD를 비교해보면 알수 있음
                    queue.add(boardAddRequest); //--여기까지가 입력하기
                }
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