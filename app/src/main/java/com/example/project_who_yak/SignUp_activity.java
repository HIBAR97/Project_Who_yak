package com.example.project_who_yak;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp_activity extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String userCID;
    private AlertDialog dialog;
    private boolean validate = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText idText = (EditText) findViewById(R.id.Edittext_Signup_Id);
        EditText passwordText = (EditText) findViewById(R.id.Edittext_Signup_Password);
        EditText nameText = (EditText) findViewById(R.id.Edittext_Signup_UserName);
        EditText emailText = (EditText) findViewById(R.id.Edittext_Signup_Email);
        EditText cidText = (EditText) findViewById(R.id.Edittext_Signup_CUser);





        //btn
        Button btnLogin_ID;
        Button btncheck;

        btnLogin_ID = (Button)findViewById(R.id.Login_Button_ID);
        btncheck = (Button)findViewById(R.id.Btn_Id_check);



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
                String userName = nameText.getText().toString();
                String userEmail = emailText.getText().toString();
                String userCID = cidText.getText().toString();
                if(!validate)
                {
                    Toast.makeText(getApplicationContext(),"먼저 중복 체크를 해주세요.",Toast.LENGTH_SHORT).show();
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
                SignUp_Request singUp_request = new SignUp_Request(userID, userPassword, userName, userEmail, userCID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUp_activity.this);
                queue.add(singUp_request);


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