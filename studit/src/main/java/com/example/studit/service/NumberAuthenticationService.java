package com.example.studit.service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NumberAuthenticationService {

    @Value("${coolsms.api-key}")
    private String apiKey;

    @Value("${coolsms.api-secret}")
    private String apiSecret;

    @Value("${coolsms.from-number}")
    private String fromNumber;

    public void sendMessage(String toNumber, String randomNumber){
         Message coolsms = new Message(apiKey, apiSecret);

        //4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", toNumber);
        params.put("from", fromNumber);
        params.put("type", "SMS");
        params.put("text", "[StudIT] 인증번호 " + "[" + randomNumber + "]" + "를 입력하세요.");
        params.put("app_version", "StudIT 1.0"); //appliciation name and version

        try{
            JSONObject object = coolsms.send(params);
            System.out.println(object.toJSONString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}
