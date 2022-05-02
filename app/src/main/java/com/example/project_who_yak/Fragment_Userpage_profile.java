package com.example.project_who_yak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Fragment_Userpage_profile extends Fragment {

    private String ID;
    private String Pw;
    private String rPw;
    private String fPw;
    private String Name;
    private String Phone;
    private String Burth;
    private String BurthY;
    private String BurthM;
    private String BurthD;
    private String Sex;
    private String Male;
    private String Female;
    private TextView fraguserid;

    private AlertDialog dialog;
    private View view;

    @Nullable
    //Connect xml file using container
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_userpage_profile, container, false);

        //아이디값을 가져오기위한 bundle
        view = inflater.inflate(R.layout.fragment_userpage_profile, container, false);
        //선언
        TextView tv_ID = view.findViewById(R.id.frag_userid);
        EditText et_Pw = view.findViewById(R.id.frag_Edittext_Password);
        EditText et_fPw = view.findViewById(R.id.frag_Edittext_fixPassword);
        EditText et_rfPw = view.findViewById(R.id.frag_Edittext_reFixPassword);
        EditText et_Name = view.findViewById(R.id.frag_Edittext_Name);
        EditText et_Phone = view.findViewById(R.id.frag_Edittext_Phone);
        DatePicker dp_burth = view.findViewById(R.id.frag_DatePick);
        RadioButton rb_male = view.findViewById(R.id.radiobutton_male);
        RadioButton rb_female = view.findViewById(R.id.radiobutton_female);
        Button btn_Fix = view.findViewById(R.id.btnFix);


//        Bundle bundle = getArguments();
//        String userID = bundle.getString("userID");
//        tv_ID.setText(userID);

        btn_Fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선언
                //ID = tv_ID.getText().toString();
                ID = "test3";
                Pw = et_Pw.getText().toString();
                fPw = et_fPw.getText().toString();
                rPw = et_rfPw.getText().toString();
                Name = et_Name.getText().toString();
                Phone = et_Phone.getText().toString();
                BurthY = String.valueOf(dp_burth.getYear());
                BurthM = String.valueOf(dp_burth.getMonth() + 1);
                BurthD = String.valueOf(dp_burth.getDayOfMonth());

//                int day= dp_burth.getDayOfMonth();
//                int month = dp_burth.getMonth() + 1;
//                int year = dp_burth.getYear();
//                Burth = Integer.toString(year)+ "." + Integer.toString(month) + "." + Integer.toString(day);

                switch (view.getId()){
                    case R.id.radiobutton_male:
                        //Sex = rb_male.getText().toString();
                        Sex = "남성";
                    case R.id.radiobutton_female:
                        //Sex = rb_female.getText().toString();
                        Sex = "여성";
                }



                //누락된 변경사항이 있을 경우
                if(Pw.equals("") || rPw.equals("") || fPw.equals("") || Name.equals("") || Phone.equals("") || Burth.equals("") || Sex.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("입력되지 않은 변수가 있습니다.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                //변경할 비밀번호가 같을 경우
                if(!Pw.equals(fPw)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("변경할 비밀번호가 현재 비밀번호와 같습니다.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                //비밀번호 재입력이 같지 않을 경우
                if(!fPw.equals(rPw)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("변경할 비밀번호가 같지 않습니다.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                //서버에서 중복 검사 할 필요 없을듯....?

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success){
                                    Toast.makeText(getActivity().getApplicationContext(),"정보 수정에 완료하였습니다.",Toast.LENGTH_SHORT).show();
                                    //finish();
                                }else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    dialog =  builder.setMessage("수정하는데 실패했습니다.")
                                            .setNegativeButton("확인",null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getActivity().getApplicationContext(),"Json에러",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                    }
                };


                Userpage_profile_Request Profile_Request = new Userpage_profile_Request(ID, Pw, fPw, Name, Phone, Burth, BurthY, BurthM, BurthD, Sex, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(Profile_Request);

                //수정이 완료되었을시 토스 메시지
                Toast.makeText(getContext(), "수정 완료", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onStop(){
        super.onStop();
        if(dialog!= null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}