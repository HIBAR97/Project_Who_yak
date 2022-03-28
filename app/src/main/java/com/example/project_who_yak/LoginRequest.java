package com.example.project_who_yak;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL ="http://whoyak.dothome.co.kr/UserLogin.php";
    private Map<String, String> parametrs;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parametrs = new HashMap<>();
        parametrs.put("userID",userID);
        parametrs.put("userPassword",userPassword);

    }

    @Override
    public Map<String,String> getParams(){
        return parametrs;
    }
}
