package com.example.project_who_yak;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Userpage_profile_Request extends StringRequest {
    final static private String URL ="http://whoyak.dothome.co.kr/Userpage_profile_Request.php";
    private Map<String, String> parametrs;

    public Userpage_profile_Request(String Pw, String fPw, String Name, String Phone, String Burth, String Sex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parametrs = new HashMap<>();
        parametrs.put("userPassword",Pw);
        parametrs.put("userRPassword",fPw);
        parametrs.put("useraName",Name);
        parametrs.put("userPhoen",Phone);
        parametrs.put("userBurth",Burth);
        parametrs.put("userSex",Sex);
    }

    @Override
    public Map<String,String> getParams(){return parametrs;}
}
