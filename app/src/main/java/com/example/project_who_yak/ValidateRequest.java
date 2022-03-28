package com.example.project_who_yak;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    final static private String URL ="http://whoyak.dothome.co.kr/UserValidate.php";
    private Map<String, String> parametrs;

    public ValidateRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parametrs = new HashMap<>();
        parametrs.put("userID",userID);

    }

    @Override
    public Map<String,String> getParams(){
        return parametrs;
    }
}
