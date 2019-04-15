package com.bootdo.goodsManager.controller;


import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.goodsManager.domain.GmGoodsInfoDO;
import com.bootdo.goodsManager.domain.GmGoodsUserDO;
import com.bootdo.goodsManager.service.GmGoodsInfoService;
import com.bootdo.goodsManager.service.GmGoodsUserService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Autowired
    private GmGoodsUserService goodsUserService;
    @Autowired
    private GmGoodsInfoService goodsInfoService;
    @Autowired
    private UserService userService;

    // 上级奖励金额
    private static final Double REWARD_A = 80.0;
    // 上上级奖励金额
    private static final Double REWARD_B = 50.0;

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
    @RequestMapping("/wddd")
    public String toWddd(){
        return "wjfh/wddd";
    }
    @RequestMapping("/wdkc")
    public String toWdkc(Model model){
        // 获取当前用户
        UserDO user = ShiroUtils.getUser();
        if(user==null){
            ShiroUtils.logout();
        }
        UserDO parent = userService.getOutRole(user.getParentId());
        Map<String,Object> query = new HashMap<>();
        query.put("userId",user.getUserId());
        List<GmGoodsUserDO> goodsUserList = goodsUserService.list(query);
        Map<String,Integer> num = new HashMap<>();
        for (GmGoodsUserDO goodsUser:goodsUserList) {
            GmGoodsInfoDO goodsInfo = goodsInfoService.get(Integer.parseInt(goodsUser.getType()));
            String goodsName = goodsInfo.getGoodsName();
            goodsUser.setOther(goodsName);
            goodsUser.setRemark(parent.getName());
            if(num.containsKey(goodsName)){
                Integer goodsNum = num.get(goodsName);
                goodsNum++;
                num.replace(goodsName,goodsNum);
            }else{
                num.put(goodsName,1);
            }

        }

        model.addAttribute("total",goodsUserList.size());
        model.addAttribute("num",num);
        model.addAttribute("list",goodsUserList);
        return "wjfh/wdkc";
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
    // 我的奖励
    @RequestMapping("/myAward")
    public String toMyAward(){
        return "wjfh/myAward";
    }
    // 我的业绩
    @RequestMapping("/myPer")
    public String toMyPer(){
        return "wjfh/myPer";
    }



}
