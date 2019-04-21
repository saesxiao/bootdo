package com.bootdo.common.wechat;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WechatUtils {
    private static final String APP_ID = "wxcff90894f5b7a499";
    private static final String APP_SECRET = "2b0fbcedf77c0519b7d927a31425bc18";
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    private static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    private static String token = "20_0Svdy8ba34TWOJxSy7oppc07JGBYQUn7Np7G5u_QjQEjAPkyyl6ktfaLfypf6RfXXpCkVOcrE0K8qbd96AzetQRNtFePp-R3fBKXrDtiuuoJkuVBSdcOqWvQjskXVObAIALJC";
    private static String ticket = "HoagFKDcsGMVCIY2vOjf9mguAvqtCyRCAWQ022gtZQDeXprYhHI7tN7oIolsC5SpoWwM7LZkJsJlXdbYmPs4tg";


    // 获取token
    public static JSONObject getToken(){
        String res =  HttpUtil.get(TOKEN_URL+"&appid="+APP_ID+"&secret="+APP_SECRET);
        return JSONObject.parseObject(res);
    }

    //获取accessToken
    private JSONObject getAccessToken(){
        String requestUrl = TOKEN_URL+"&appid="+APP_ID+"&secret="+APP_SECRET;
        System.out.println(requestUrl);
        String res =  HttpUtil.get(requestUrl);
        return JSONObject.parseObject(res);
    }

    // 获取jsapi_ticket
    public static JSONObject getJsApiTicket(){

        String response =  HttpUtil.get(TICKET_URL.replace("ACCESS_TOKEN",token));
        JSONObject res =  JSONObject.parseObject(response);
        if(0!=res.getInteger("errcode")){
            token = getToken().getString("access_token");
            response =  HttpUtil.get(TICKET_URL.replace("ACCESS_TOKEN",token));
            res = JSONObject.parseObject(response);
        }
        return res;
    }

    //生成微信权限验证的参数
    public Map<String, String> makeWXTicket(String jsApiTicket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            System.out.println(signature);
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println("WeChatController.makeWXTicket=====Start");
            e.printStackTrace();
            System.out.println("WeChatController.makeWXTicket=====End");
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println("WeChatController.makeWXTicket=====Start");
            e.printStackTrace();
            System.out.println("WeChatController.makeWXTicket=====End");
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsApiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", APP_ID);

        return ret;
    }
    //字节数组转换为十六进制字符串
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    //生成随机字符串
    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }
    //生成时间戳
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }



}
//{"access_token":"","expires_in":7200}
//{"errcode":40001,"errmsg":"invalid credential, access_token is invalid or not latest hint: [8rQ8BA04188994!]"}
//{"errcode":0,"ticket":"HoagFKDcsGMVCIY2vOjf9mguAvqtCyRCAWQ022gtZQDeXprYhHI7tN7oIolsC5SpoWwM7LZkJsJlXdbYmPs4tg","errmsg":"ok","expires_in":7200}
