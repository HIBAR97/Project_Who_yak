package com.example.project_who_yak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SignUp_activity extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private String userrePassword;
    private String userName;
    private String userEmail;
    private String userCID;
    private String userDate;
    private String userGender2;
    private AlertDialog dialog;
    private boolean validate = false;
    public static String yy,mm,dd;
    public static boolean check=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText idText = (EditText) findViewById(R.id.Edittext_Signup_Id);
        EditText passwordText = (EditText) findViewById(R.id.Edittext_Signup_Password);
        EditText repasswordText = (EditText) findViewById(R.id.Edittext_Signup_rePassword);
        EditText nameText = (EditText) findViewById(R.id.Edittext_Signup_UserName);
        EditText emailText = (EditText) findViewById(R.id.Edittext_Signup_Email);
        EditText cidText = (EditText) findViewById(R.id.Edittext_Signup_CUser);
        DatePicker datePicker = (DatePicker) findViewById(R.id.DatePicker_Sing_Up);
        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.Sign_Up_radiogroup_sex);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        userGender2 = ((RadioButton) findViewById(genderGroupID)).getText().toString();

        //btn
        Button btnLogin_ID;
        Button btncheck;

        btnLogin_ID = (Button)findViewById(R.id.Login_Button_ID);
        btncheck = (Button)findViewById(R.id.Btn_Id_check);



        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                userGender2 = genderButton.getText().toString();
            }
        });
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                yy = Integer.toString(year);
                mm = Integer.toString(monthOfYear);
                dd = Integer.toString(dayOfMonth);
                check = true;
            }
        });



        //Check Button Listener
        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID= idText.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_activity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_activity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디 입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled((false));
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.white));
                                btncheck.setBackgroundColor(getResources().getColor(R.color.white));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_activity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디 입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUp_activity.this);
                queue.add(validateRequest);
            }
        });



        //Login Button Listener
        btnLogin_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userrePassword = repasswordText.getText().toString();
                String userName = nameText.getText().toString();
                String userEmail = emailText.getText().toString();
                String userCID = cidText.getText().toString();
                String userYear = yy;
                String userMonth = mm;
                String userDay = dd;
                String userGender = userGender2;
                if(!validate)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_activity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                if(userID.equals("") || userPassword.equals("") || userName.equals("")|| userEmail.equals("")|| userCID.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_activity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(!userrePassword.equals(userPassword))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_activity.this);
                    dialog = builder.setMessage("비밀번호 와 비밀번호 확인이 다릅니다.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(),"회원가입에 성공했습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_activity.this);
                                dialog = builder.setMessage("회원가입에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"json에러",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                };
                SignUp_Request singUp_request = new SignUp_Request(userID, userPassword, userName, userEmail, userCID, userGender,
                        userYear, userMonth, userDay, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUp_activity.this);
                queue.add(singUp_request);

                // 메인화면 띄우기
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onStop() {
        super.onStop();
        if(dialog!= null)
        {
            dialog.dismiss();
            dialog = null;
        }

    }
}