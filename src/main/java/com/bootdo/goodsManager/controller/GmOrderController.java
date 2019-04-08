package com.bootdo.goodsManager.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.*;
import com.bootdo.goodsManager.dao.GmGoodsUserDao;
import com.bootdo.goodsManager.domain.GmGoodsInfoDO;
import com.bootdo.goodsManager.domain.GmGoodsUserDO;
import com.bootdo.goodsManager.service.GmGoodsInfoService;
import com.bootdo.goodsManager.service.GmGoodsUserService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.goodsManager.domain.GmOrderDO;
import com.bootdo.goodsManager.service.GmOrderService;

/**
 * 发货订单表
 * 
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-04 16:01:15
 */
 
@Controller
@RequestMapping("/goodsManager/gmOrder")
public class GmOrderController {
	@Autowired
	private GmOrderService gmOrderService;
	@Autowired
	private GmGoodsInfoService goodsInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private GmGoodsUserService goodsUserService;


	@ResponseBody
	@RequestMapping("/saveOrder")
	public R order(@RequestParam String jsonStr){
		UserDO user = ShiroUtils.getUser();
		if(user==null){
			ShiroUtils.logout();
		}
		UserDO parent = userService.getById(user.getParentId());
		if(parent==null){
			return R.error(403,"无上级");
		}
		try {
			Long userId = user.getUserId();
			Long parentId = user.getUserId();
			JSONObject param = JSON.parseObject(jsonStr);
			JSONObject goodsInfo = param.getJSONObject("goods");
			String address= param.getString("address");
			String imgUrl = param.getString("imgUrl");
			String time = DateUtil.getDateTime();
			String orderCode = "XD"+OrderTool.getOrderNo(5);
			goodsInfo.entrySet().stream().forEach(temp->{
				Integer goodsId = Integer.parseInt(temp.getKey());
				GmGoodsInfoDO goods = goodsInfoService.get(goodsId);
				Integer goodsNum  = (Integer) (temp.getValue());
				if(goods!=null&&goodsNum>0){
					Map<String,Object> query = new HashMap<>();
					query.put("userId",parentId);
					query.put("status",0);
					query.put("type",goods.getId());
					List<GmGoodsUserDO> list =goodsUserService.list(query);
					if(list.size()>goodsNum){
						GmOrderDO orderDO = new GmOrderDO();
						orderDO.setUserId(userId);
						orderDO.setParentId(parentId);
						orderDO.setGoodsId(goods.getId());
						orderDO.setOrderStatus(1);
						orderDO.setGoodsNum(goodsNum);
						orderDO.setOrderTime(time);
						orderDO.setRemark(address);
						orderDO.setOther(imgUrl);
						orderDO.setType(orderCode);
						gmOrderService.save(orderDO);
					}
				}
			});
			return R.ok();

		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
	}

	@ResponseBody
	@RequestMapping("/getOrder")
	public Object getOrder(){
		UserDO user = ShiroUtils.getUser();
		if(user==null){
			ShiroUtils.logout();
		}
		Map<String,List<GmOrderDO>> res = new LinkedHashMap<>();
		Long parentId = user.getUserId();
		Map<String,Object> query = new HashMap<>();
		query.put("parentId",parentId);
		List<GmOrderDO> orderList = gmOrderService.list(query);
		for (GmOrderDO order:orderList) {
			String orderCode = order.getType();
			if(res.containsKey(orderCode)){
				List<GmOrderDO> tempList = res.get(orderCode);
				tempList.add(order);
				res.replace(orderCode,tempList);
			}else{
				List<GmOrderDO> tempList = new ArrayList<>();
				tempList.add(order);
				res.put(orderCode,tempList);
			}
		}
		return res;
	}
}
