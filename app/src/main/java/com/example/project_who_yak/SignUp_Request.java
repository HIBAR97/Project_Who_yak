package com.example.project_who_yak;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUp_Request extends StringRequest {
    final static private String URL ="http://whoyak.dothome.co.kr/UserRegister.php";
    private Map<String, String> parametrs;

    public SignUp_Request(String userID, String userPassword, String userName, String userEmail, String userCID,
                          String userGender,String userYear, String userMonth, String userDay, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parametrs = new HashMap<>();
        parametrs.put("userID",userID);
        parametrs.put("userPassword",userPassword);
        parametrs.put("userName",userName);
        parametrs.put("userEmail",userEmail);
        parametrs.put("userCID",userCID);
        parametrs.put("userGender",userGender);
        parametrs.put("userYear",userYear);
        parametrs.put("userMonth",userMonth);
        parametrs.put("userDay",userDay);
    }

    @Override
    public Map<String,String> getParams(){
        return parametrs;
    }
}
