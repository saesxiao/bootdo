package com.bootdo.goodsManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @RequestMapping("/jinhuo")
    public String toJinhuo(){
        return "wjfh/jinhuo";
    }
    @RequestMapping("/ddgl")
    public String toDdgl(){
        return "wjfh/dldd";
    }
    @RequestMapping("/fahuo")
    public String toFahuo(String goodsCode, Model model){
        model.addAttribute("goodsCode",goodsCode);
        return "wjfh/fahuo";
    }
    @RequestMapping("/retail")
    public String toRetail(){
        return "wjfh/retail";
    }
    // 获取我的订单
    @RequestMapping("/myOrder")
    public String toMyOrder(){
        return "wjfh/myOrder";
    }
    // 查看库存
    @RequestMapping("/myStock")
    public String toMyStock(){
        return "wjfh/myStock";
    }
    // 我的业绩 奖励
    @RequestMapping("/myAward")
    public String toMyAward(){
        return "wjfh/myAward";
    }


}
