package com.bootdo.goodsManager.controller;


import com.bootdo.common.annotation.Log;
import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.*;
import com.bootdo.goodsManager.domain.GmGoodsInfoDO;
import com.bootdo.goodsManager.domain.GmGoodsUserDO;
import com.bootdo.goodsManager.domain.GmProfitDO;
import com.bootdo.goodsManager.service.GmGoodsInfoService;
import com.bootdo.goodsManager.service.GmGoodsUserService;
import com.bootdo.goodsManager.service.GmProfitService;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.DeptService;
import com.bootdo.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class GmIndexController{

    @Autowired
    private GmGoodsUserService goodsUserService;
    @Autowired
    private GmGoodsInfoService goodsInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    DeptService deptService;
    @Autowired
    GmProfitService profitService;

    // 上级奖励金额
    private static final Double REWARD_A = 80.0;
    // 上上级奖励金额
    private static final Double REWARD_B = 50.0;

    // 首页
    @RequestMapping("/index")
    public String toIndex(){
        return "wjfh/index";
    }
    // 注册
    @RequestMapping("/resg")
    public String toResg(){
        return "wjfh/resg";
    }
    // 登录
    @RequestMapping("/login")
    public String toLogin(){
        return "wjfh/login";
    }
    // 进货
    @RequestMapping("/jinhuo")
    public String toJinhuo(){
        return "wjfh/jinhuo";
    }
    // 下级订单
    @RequestMapping("/ddgl")
    public String toDdgl(){
        return "wjfh/dldd";
    }
    // 我的订单
    @RequestMapping("/wddd")
    public String toWddd(){
        return "wjfh/wddd";
    }
    // 下级列表
    @RequestMapping("/xiaji")
    public String toXiaji(){
        return "wjfh/xiaji";
    }
    // 我的库存
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
    // 发货
    @RequestMapping("/fahuo")
    public String toFahuo(String goodsCode, Model model){
        model.addAttribute("goodsCode",goodsCode);
        return "wjfh/fahuo";
    }
    // 零售
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
    // 邀请
    @RequestMapping("/yaoqing")
    public String toYq(){
        return "wjfh/yaoqing";
    }
    // 升级
    @RequestMapping("/shengji")
    public String toSj(){
        return "wjfh/shengji";
    }

    @Log("保存用户")
    @PostMapping("/register")
    @ResponseBody
    R register(UserDO user) {
        System.out.println(user);
        try{
            String level = "";
            UserDO parent = userService.getByInvite(user.getInvite());
            if (parent == null) {
                return R.error("邀请码错误");
            }
            user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
            DeptDO dept = deptService.get(parent.getDeptId());
            Map<String, Object> query = new HashMap<>();
            query.put("parentId", dept.getDeptId());
            List<DeptDO> childrenList = deptService.list(query);
            if (childrenList != null && childrenList.size() > 0) { // 如果有下级 注册时候为下级
                DeptDO children = childrenList.get(0);
                user.setDeptId(children.getDeptId());
                user.setDeptName(children.getName());
                user.setCreateTime(DateUtil.getDateTime());
                user.setStatus(1);
                level = children.getDeptId()+"";
            } else { //如果没有下级 注册为经销商
                user.setDeptId(dept.getDeptId());
                user.setDeptName(dept.getName());
                level = dept.getDeptId()+"";
            }

            if (userService.save(user) > 0) {
                user.setParentId(parent.getUserId());
                user.setCreateTime(DateUtil.getDateTime());
                user.setInvite(RandomCode.toSerialCode(user.getUserId()));
                userService.update(user);
                GmProfitDO profit = new GmProfitDO();
                profit.setUserId(user.getUserId());
                profit.setParentId(parent.getUserId());
                profit.setLevel(level);
                profitService.save(profit);
                return R.ok();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return R.error();
    }


}
