package com.bootdo.goodsManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 发货订单表
 *
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-04 16:01:15 分润
 */

@Controller
@RequestMapping("/goodsManager/page")
public class GmIndexController {

    @RequestMapping("/index")
    public String toIndex(){
        return "wjfh/index";
    }
    @RequestMapping("/resg")
    public String toResg(){
        return "wjfh/resg";
    }
    @RequestMapping("/login")
    public String toLogin(){
        return "wjfh/login";
    }


}
