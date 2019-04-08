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
import org.apache.ibatis.annotations.Param;
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
	public R order(@Param("jsonStr") String jsonStr){
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
			Long parentId = user.getParentId();
			JSONObject param = JSON.parseObject(jsonStr);
			JSONObject goodsInfo = param.getJSONObject("goods");
			Map<String,Object> map = JSONObject.parseObject(goodsInfo.toJSONString(),Map.class);
			String address= param.getString("address");
			String imgUrl = param.getString("imgUrl");
			String time = DateUtil.getDateTime();
			String orderCode = "XD"+OrderTool.getOrderNo(5);
			for (String key : map.keySet()) {
				Integer goodsId = Integer.parseInt(key);
				GmGoodsInfoDO goods = goodsInfoService.get(goodsId);
				Integer goodsNum  = (Integer) (map.get(key));
				if(goods!=null&&goodsNum>0){
					Map<String,Object> query = new HashMap<>();
					query.put("userId",parentId);
					query.put("status",0);
					query.put("type",goods.getId());
					List<GmGoodsUserDO> list =goodsUserService.list(query);
					if(list.size()>=goodsNum){
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
						for (int i = 0; i < goodsNum ; i++) {
							GmGoodsUserDO goodsUser = list.get(i);
							goodsUser.setStatus("1");
							goodsUserService.update(goodsUser);
						}
					}else{
						return R.error("上级库存不足");
					}
				}
			}

			goodsInfo.entrySet().stream().forEach(temp->{
				System.out.println(temp.getKey()+"  :  "+temp.getValue());
			});
			return R.ok("下单成功");

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
		Long userId = user.getUserId();
		Map<String,Object> query = new HashMap<>();
		query.put("userId",userId);
		List<GmOrderDO> orderList = gmOrderService.list(query);
		for (GmOrderDO order:orderList) {
			GmGoodsInfoDO goodsInfo = goodsInfoService.get(order.getGoodsId());
			order.setGoodsName(goodsInfo.getGoodsName());
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

	@ResponseBody
	@RequestMapping("/sendOrder")
	public R sendOrder(String orderCode){
		UserDO user = ShiroUtils.getUser();
		if(user==null){
			ShiroUtils.logout();
		}
		Map<String,Object> query = new HashMap<>();
		query.put("userId",user.getUserId());
		query.put("type",orderCode);
		List<GmOrderDO> orderList = gmOrderService.list(query);
		try {
			for (GmOrderDO order:orderList) {
				if(order.getUserId()==user.getUserId()){
					order.setOrderStatus(2);
					gmOrderService.update(order);
				}
			}
			return R.ok();
		}catch (Exception e){
			e.printStackTrace();
		}
		return R.error();
	}
}
