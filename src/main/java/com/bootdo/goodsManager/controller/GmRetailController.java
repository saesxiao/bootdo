package com.bootdo.goodsManager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.goodsManager.domain.*;
import com.bootdo.goodsManager.service.*;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.DeptService;
import com.bootdo.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 零售表
 *
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-04 16:01:15
 */
@Controller
@RequestMapping("/goodsManager/retail")
public class GmRetailController {

    @Autowired
    private GmGoodsUserService goodsUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private GmGoodsInfoService goodsInfoService;
    @Autowired
    private GmProfitDetailService profitDetailService;
    @Autowired
    private GmProfitService profitService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private GmOrderService orderService;

    /**
     * 零售
     * @return
     */
    @RequestMapping("/toRetail")
    @ResponseBody
    public R toRetail(String ids){

        // 拿到当前用户
        UserDO user = ShiroUtils.getUser();
        if(user==null){
            ShiroUtils.logout();
        }
        Long userId = user.getUserId();
        try{
            String[]idsList = ids.split(",");
            for (String goodsCode:idsList) {
                Map<String,Object> query = new HashMap<>();
                query.put("status","0");
                query.put("userId",userId);
                query.put("goodsCode",goodsCode);
                List<GmGoodsUserDO> goodsUserList = goodsUserService.list(query);
                if(goodsUserList==null||goodsUserList.size()<1){
                    return R.error("订单编号错误或已售");
                }
                GmGoodsUserDO goodsUser = goodsUserList.get(0);
                goodsUser.setStatus("3");
                goodsUserService.update(goodsUser);
            }
            return R.ok("零售成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.error("订单格式错误");
        }
    }

    /**
     * 获取 下级列表
     *
     */
    @ResponseBody
    @RequestMapping("/subordinate")
    public Object getSubordinate(){
        // 获取当前用户
        UserDO user = ShiroUtils.getUser();
        if(user==null){
            ShiroUtils.logout();
        }
        Long userId = user.getUserId();
        // 返回结果集
        JSONArray res = new JSONArray();

        try {
            // 根据userId 作为parentId 获取下级列表
            Map<String,Object> query = new HashMap<>();
            query.put("parentId",userId);
            List<UserDO> userList = userService.getList(query);
            for (UserDO subordinate:userList) {
                Long subId = subordinate.getUserId();
                JSONObject info = new JSONObject();
                info.put("userName",subordinate.getName());
                info.put("deptId",subordinate.getDeptId());
                DeptDO dept = deptService.get(subordinate.getDeptId());
                info.put("deptName",dept.getName());
                info.put("createTime",subordinate.getCreateTime());

                // 添加商品信息
                JSONObject goodsInfo = new JSONObject();

                List<GmGoodsInfoDO> goodsList = goodsInfoService.list(new HashMap<>());
                for (GmGoodsInfoDO goods:goodsList) {
                    JSONObject infoTemp = new JSONObject();
                    infoTemp.put("goodsId",goods.getId());
                    Map<String,Object> queryGoodsUser = new HashMap<>();
                    queryGoodsUser.put("userId",subId);
                    queryGoodsUser.put("type",goods.getId()+"");
                    List<GmGoodsUserDO> goodsUserList = goodsUserService.list(queryGoodsUser);
                    infoTemp.put("total",goodsUserList.size());
                    queryGoodsUser.put("status","0");
                    goodsUserList = goodsUserService.list(queryGoodsUser);
                    infoTemp.put("stock",goodsUserList.size());
                    queryGoodsUser.replace("status","2");
                    goodsUserList = goodsUserService.list(queryGoodsUser);
                    infoTemp.put("sail",goodsUserList.size());
                    goodsInfo.put(goods.getGoodsName(),infoTemp);
                }
                info.put("orderInfo",goodsInfo);

                //添加销售金额
                Map<String,Object> querySail = new HashMap<>();
                querySail.put("userId",subId);
                querySail.put("status","2");
                List<GmGoodsUserDO> sailList = goodsUserService.list(querySail);
                Double sailAmount = 0.0;
                for (GmGoodsUserDO goodsUser:sailList) {
                    Integer goodsId = Integer.parseInt(goodsUser.getType());
                    GmGoodsInfoDO goods = goodsInfoService.get(goodsId);
                    sailAmount += goods.getGoodsPrice();
                }
                info.put("amount",sailAmount);


                // 添加奖励信息
                GmProfitDO profit = profitService.getByUserId(subId);
                Map<String,Object> queryProfit = new HashMap<>();
                queryProfit.put("profitId",profit.getId());
                List<GmProfitDetailDO> detailList = profitDetailService.list(queryProfit);
                Double profitAmount = 0.0;
                for (GmProfitDetailDO detail:detailList) {
                    profitAmount += detail.getAmount();
                }
                info.put("profit",profitAmount);

                res.add(info);
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }

        return res;

    }

    /**
     * 获取 当前用户数据列表
     *
     */
    @ResponseBody
    @RequestMapping("/mine")
    public Object getMine(){
        // 获取当前用户
        UserDO user = ShiroUtils.getUser();
        if(user==null){
            ShiroUtils.logout();
            return R.error("请登录");
        }
        Long userId = user.getUserId();
        // 返回结果集
        JSONObject res = new JSONObject();

        try {
            // 根据userId 作为parentId 获取下级列表
            Map<String,Object> query = new HashMap<>();
            query.put("parentId",userId);
            List<UserDO> userList = userService.getList(query);
            res.put("subordinate",userList.size());
            query = new HashMap<>();
            query.put("userId",userId);
            query.put("status","0");
            List<GmGoodsUserDO> goodsUserList = goodsUserService.list(query);
            res.put("stock",goodsUserList.size());
            GmProfitDO profit = profitService.getByUserId(userId);
            query = new HashMap<>();
            query.put("profitId",profit.getId());
            List<GmProfitDetailDO> detailList = profitDetailService.list(query);
            Double profitAmount = 0.0;
            for (GmProfitDetailDO detail:detailList) {
                profitAmount += detail.getAmount();
            }
            res.put("profit",profitAmount);
            query = new HashMap<>();
            query.put("parentId",userId);
            List<GmOrderDO> orderList = orderService.list(query);
            res.put("order",orderList.size());


        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }

        return res;

    }

    @RequestMapping("/wechat")
    @ResponseBody
    public Object getUserWechat(){
        // 获取当前用户
        UserDO user = ShiroUtils.getUser();
        if(user==null){
            ShiroUtils.logout();
        }
        return R.ok(user.getWechat());
    }


}
