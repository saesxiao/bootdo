package com.bootdo.goodsManager.controller;


import com.bootdo.common.annotation.Log;
import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.*;
import com.bootdo.goodsManager.domain.*;
import com.bootdo.goodsManager.service.*;
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
    @Autowired
    private GmOrderService gmOrderService;
    @Autowired
    private GmOrderDetailService orderDetailService;
    @Autowired
    private GmPromotionService promotionService;

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
    // 晋升规则
    @RequestMapping("/guize")
    public String toGz(){
        return "wjfh/guize";
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
            goodsUser.setRemark(parent!=null?parent.getName():"-");
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

    @Log("保存用户")
    @PostMapping("/register")
    @ResponseBody
    R register(UserDO user) {
        try{
            UserDO parent = userService.getByInvite(user.getInvite());
            if (parent == null) {
                return R.error("邀请码错误");
            }
            user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
            DeptDO dept = deptService.get(5L);

            user.setDeptId(dept.getDeptId());
            user.setDeptName(dept.getName());
            user.setCreateTime(DateUtil.getDateTime());
            user.setStatus(1);
            user.setParentId(parent.getUserId());
            if (userService.save(user) > 0) {
                user.setInvite(RandomCode.toSerialCode(user.getUserId()));
                if(userService.update(user)>0){
                    GmProfitDO profit = new GmProfitDO();
                    profit.setUserId(user.getUserId());
                    profit.setParentId(parent.getUserId());
                    profit.setLevel(dept.getDeptId()+"");
                    profitService.save(profit);
                    return R.ok();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error();
    }


    /**
     *  获取当前用户订单列表 自动晋升逻辑
     */
    @ResponseBody
    @RequestMapping("/promotion")
    public R promotion(String userId ,String deptId,String newDeptId){
        if(StringUtils.isBlank(deptId)||StringUtils.isBlank(newDeptId)){
            return R.error("参数错误!");
        }
        Long oldUserDept = null;
        Long newUserDept = null;
        Long thisUserId = null;
        try{
            oldUserDept = Long.parseLong(deptId);
            newUserDept = Long.parseLong(newDeptId);
            thisUserId = Long.parseLong(userId);
        }catch (Exception e){
            return R.error("参数错误!");
        }
        try {
            UserDO user = userService.getOutRole(thisUserId);
            if(user!=null&&user.getDeptId()==oldUserDept){
                DeptDO deptDO = deptService.get(newUserDept);
                if(deptDO!=null){
                    user.setDeptId(newUserDept);
                    if(userService.update(user)>0){
                        return R.ok("晋升成功!");
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error("晋升系统正忙");
        }
        return R.ok("暂无晋升信息");
    }

    /**
     *  获取当前用户订单列表 自动晋升逻辑
     */
    @ResponseBody
    @RequestMapping("/autoPromotion")
    public R autoPromotion(){
        try {

            UserDO user = ShiroUtils.getUser();
            // 判断用户等级 1 平台管理者 2联合创始人 3总经销商 4经销商 5VIP客户
            Long userLevel = user.getDeptId();
            // 获取当前日期
            Integer today = Integer.parseInt(DateUtil.getDay());
            Integer month = Integer.parseInt(DateUtil.getMonth());
            Integer year = Integer.parseInt(DateUtil.getYear());


            // 如果是VIP用户 则根据每次订单数量 时时判断是否晋升 其他用户则每月一号结算
            if(userLevel==5){
                // 获取当前用户已完成订单
                Map<String,Object> query = new HashMap<>();
                query.put("userId",user.getUserId());
                query.put("orderStatus",2);
                List<GmOrderDO> orderList = gmOrderService.list(query);

                //拿到最后一个订单的数量
                GmOrderDO lastOrder = orderList.get(0);
                query = new HashMap<>();
                query.put("orderId",lastOrder.getId());
                List<GmOrderDetailDO> lastOrderDetailList = orderDetailService.list(query);
                Integer lastOrderNum = 0;
                for (GmOrderDetailDO detail:lastOrderDetailList) {
                    lastOrderNum += detail.getGoodsNum();
                }
                // 如果是VIP用户 一次性拿够12盒 晋升为经销商
                if(lastOrderNum>=12){
                    user.setDeptId(4L);
                    userService.update(user);
                    return R.ok("由于您的进货量达到平台要求,系统已自动将您的登记变更为\"经销商\"");
                }
            }else if(today==1){ //判断是否为本月一号

                // 总经销商和经销商晋升
                if(userLevel==2||userLevel==3||userLevel==4){

                    //如果是联合创始人 筛选时间为一个季度 如果是经销商或总经销商 筛选时间为前两个月
                    Integer pastMonth = userLevel==2?3:2;

                    // 获取本月一号 和两个月前的一号
                    Integer pastTwoMonth = month-pastMonth>0?month-pastMonth:12+(month-pastMonth);
                    Integer pastYear = month-pastMonth>0?year:year-1;
                    String monthStart = pastYear + "-" + (pastTwoMonth<10?"0"+pastTwoMonth:pastTwoMonth) + "-" + "01 00:00:00";
                    String monthEnd = DateUtil.getFirstDayOfMonth()+" 00:00:00";

                    //获取用户时间范围内的有效订单
                    Map<String,Object> query = new HashMap<>();
                    query.put("userId",user.getUserId());
                    query.put("orderStatus",2);
                    query.put("monthStart",monthStart);
                    query.put("monthEnd",monthEnd);
                    List<GmOrderDO> orderList = gmOrderService.list(query);

                    //循环订单列表 获取两个月内的订单总数
                    Integer orderNum = 0;
                    for (GmOrderDO order:orderList) {
                        query = new HashMap<>();
                        query.put("orderId",order.getId());
                        List<GmOrderDetailDO> detailList = orderDetailService.list(query);
                        for (GmOrderDetailDO detail:detailList) {
                            orderNum += detail.getGoodsNum();
                        }
                    }

                    // 如果是经销商 累积拿够36 晋升为总经销
                    if(userLevel==4){
                        if(orderNum>=36){
                            user.setDeptId(3L);
                            userService.update(user);
                            return R.ok("由于您的进货量达到平台要求,系统已自动将您的登记变更为\"总经销商\"");
                        }
                    }
                    // 如果是总经销商 累积120 晋升为联合创始人
                    if(userLevel==3){
                        if(orderNum>=120){
                            user.setDeptId(2L);
                            userService.update(user);
                            return R.ok("由于您的进货量达到平台要求,系统已自动将您的登记变更为\"联合创始人\"");
                        }
                    }
                    // 如果是联合创始人
                    if(userLevel==2){
                        if(orderNum<240){
                            // TODO: 2019/4/20 联合创始人不够考核怎么办
//							return R.ok("由于您的进货量达到平台要求,系统已自动将您的登记变更为\"联合创始人\"");
                        }
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            R.error("晋升系统正忙");
        }
        return R.ok("暂无晋升信息");
    }

    @RequestMapping("/deptList")
    @ResponseBody
    public Object getDeptList(){
        List<DeptDO> deptList = deptService.list(new HashMap<>());
        return deptList;
    }


}
