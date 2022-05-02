package com.example.project_who_yak;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Userpage_profile_Request extends StringRequest {
    final static private String URL ="http://whoyak.dothome.co.kr/Userpage_profile_Request.php";
    private Map<String, String> parametrs;

    public Userpage_profile_Request(String ID,String Pw, String fPw, String Name, String Phone, String Burth, String BurthY, String BurthM, String BurthD, String Sex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parametrs = new HashMap<>();
        parametrs.put("userID",ID);
        parametrs.put("userPassword",Pw);
        parametrs.put("userFPassword",fPw);
        parametrs.put("userName",Name);
        //parametrs.put("userPhone",Phone);
        //parametrs.put("userBurth",Burth);
        parametrs.put("userBurthY",BurthY);
        parametrs.put("userBurthM",BurthM);
        parametrs.put("userBurthD",BurthD);
        parametrs.put("userSex",Sex);
    }

    @Override
    public Map<String,String> getParams(){return parametrs;}
}
