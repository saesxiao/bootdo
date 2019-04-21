package com.bootdo.goodsManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.HttpUtil;
import com.bootdo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller()
@RequestMapping("/wechat")
public class WechatApiUtils {

    //获取相关的参数,在application.properties文件中
    @Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.appSecret}")
    private String appSecret;
    @Value("${wechat.url.accessToken}")
    private String accessTokenUrl;
    @Value("${wechat.url.apiTicket}")
    private String apiTicketUrl;

    //微信参数
    private String accessToken;
    private String jsApiTicket;
    //获取参数的时刻
    private Long getTiketTime = 0L;
    private Long getTokenTime = 0L;
    //参数的有效时间,单位是秒(s)
    private Long tokenExpireTime = 0L;
    private Long ticketExpireTime = 0L;

    //获取微信参数
    @RequestMapping("/wechatParam")
    @ResponseBody
    public Map<String, String> getWechatParam(String url) {
        try {
            //当前时间
            long now = System.currentTimeMillis();

            //判断accessToken是否已经存在或者token是否过期
            if (StringUtils.isBlank(accessToken) || (now - getTokenTime > tokenExpireTime * 1000)) {
                JSONObject tokenInfo = getAccessToken();
                if (tokenInfo != null) {
                    accessToken = tokenInfo.getString("access_token");
                    tokenExpireTime = tokenInfo.getLongValue("expires_in");
                    //获取token的时间
                    getTokenTime = System.currentTimeMillis();
                } else {
                }

            }

            //判断jsApiTicket是否已经存在或者是否过期
            if (StringUtils.isBlank(jsApiTicket) || (now - getTiketTime > ticketExpireTime * 1000)) {
                JSONObject ticketInfo = getJsApiTicket();
                if (ticketInfo != null) {
                    System.out.println("ticketInfo====>" + ticketInfo.toJSONString());
                    jsApiTicket = ticketInfo.getString("ticket");
                    ticketExpireTime = ticketInfo.getLongValue("expires_in");
                    getTiketTime = System.currentTimeMillis();
                } else {
                }
            }

            //生成微信权限验证的参数
            Map<String, String> wechatParam = makeWXTicket(jsApiTicket, url);
            return wechatParam;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取accessToken
    private JSONObject getAccessToken() {
        //String accessTokenUrl = https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        String requestUrl = accessTokenUrl.replace("APPID", appId).replace("APPSECRET", appSecret);
        JSONObject result = JSONObject.parseObject(HttpUtil.get(requestUrl));
        return result;
    }

    //获取ticket
    private JSONObject getJsApiTicket() {
        //String apiTicketUrl = https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
        System.out.println(apiTicketUrl);
        System.out.println(accessToken);
        String requestUrl = apiTicketUrl.replace("ACCESS_TOKEN", accessToken);
        System.out.println(requestUrl);
        JSONObject result = JSONObject.parseObject(HttpUtil.get(requestUrl));
        return result;
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
        try {
            MessageDigest crypt = null;
            try {
                crypt = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsApiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", appId);

        return ret;
    }

    //字节数组转换为十六进制字符串
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
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

