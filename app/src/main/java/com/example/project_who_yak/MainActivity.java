package com.example.project_who_yak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private String userID="test1";
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //스크린 위에 accessibility가 있는지 확인
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        boolean isScreenReaderEnabled = accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();


        Button btnSignUp;
        Button btnSignIn;
        Button btnVoinceACC;


        final EditText idText = (EditText) findViewById(R.id.Edittext_Id);
        final EditText passwordText = (EditText) findViewById(R.id.Edittext_Password);
        btnSignUp = (Button)findViewById(R.id.Sigin_Button_ID);
        btnSignIn = (Button)findViewById(R.id.Login_Button_ID);
        btnVoinceACC = (Button)findViewById(R.id.Voice_acc_Button_ID);

        //Sigin Button Listener
        btnSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp_activity.class);
                startActivity(intent);
            }
        });


        //Login_Button Listener
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String userID = jsonResponse.getString("userID");
                                Toast.makeText(getApplicationContext(),"로그인에 성공했습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.putExtra("userID",userID);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                dialog = builder.setMessage("로그인에 실패했습니다.")
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
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });




        btnVoinceACC.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //Acc이 켜있을경우
                if (isScreenReaderEnabled) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);

                    //꺼져있을 경우
                } else {
//                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//                    startActivity(intent);
                    //startActivityForResult(intent, 0);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("userID",userID);
                    startActivity(intent);
                }
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