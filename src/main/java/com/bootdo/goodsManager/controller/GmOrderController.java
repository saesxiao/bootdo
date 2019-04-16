package com.bootdo.goodsManager.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.*;
import com.bootdo.goodsManager.domain.*;
import com.bootdo.goodsManager.service.*;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 发货订单表
 * 
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-04 16:01:15 分润
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
	@Autowired
	private GmProfitService profitService;
	@Autowired
	private GmProfitDetailService profitDetailService;

	// 上级奖励金额
	private static final Double REWARD_A = 80.0;
	 // 上上级奖励金额
	private static final Double REWARD_B = 50.0;

	/**
	 * 下单
	 * @param jsonStr
	 * @return
	 */
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
			// 商品订单 1号:五件 2号:三件
			Map<String,Object> map = JSONObject.parseObject(goodsInfo.toJSONString(),Map.class);
			String address= param.getString("address");
			String imgUrl = param.getString("imgUrl");
			String time = DateUtil.getDateTime();
			String orderCode = "XD"+OrderTool.getOrderNo(5);
			// 循环商品订单
			for (String key : map.keySet()) {
				Integer goodsId = Integer.parseInt(key);
				GmGoodsInfoDO goods = goodsInfoService.get(goodsId);
				String tempNum = String.valueOf(map.get(key));
				if(tempNum.equals("")){
					tempNum = "0";
				}
				Integer goodsNum  = Integer.parseInt(tempNum);
				// 如果此商品有且 需求数量大于零
				if(goods!=null&&goodsNum>0){
					Map<String,Object> query = new HashMap<>();
					query.put("userId",parentId); // 根据上级id 查询到上级的库存
					query.put("status",0);
					query.put("type",goods.getId());
					List<GmGoodsUserDO> list =goodsUserService.list(query);
					// 如果上级库存大于需求数量
					if(list.size()>=goodsNum){
						// 创建一个订单 上级登录后 可根据parentId 查询订单 进行发货
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
//						for (int i = 0; i < goodsNum ; i++) {
//							GmGoodsUserDO goodsUser = list.get(i);
//							goodsUser.setStatus("1");
//							goodsUserService.update(goodsUser);
//						}
					}else{
						return R.error("上级库存不足");
					}
				}
			}
			return R.ok("下单成功");

		}catch (Exception e){
			e.printStackTrace();
			return R.error();
		}
	}

	/**
	 * 获取发货订单列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSendOrder")
	public Object getSnedOrder(){
		Map<String,Map<String,Object>> res = new LinkedHashMap<>();
		try{
			UserDO user = ShiroUtils.getUser();
			if(user==null){
				ShiroUtils.logout();
			}
			Long userId = user.getUserId();
			Map<String,Object> query = new HashMap<>();
			query.put("parentId",userId);
			List<GmOrderDO> orderList = gmOrderService.list(query);
//			query = new HashMap<>();
//			query.put("userId",userId);
//			orderList.addAll(gmOrderService.list(query));
			for (GmOrderDO order:orderList) {
				GmGoodsInfoDO goodsInfo = goodsInfoService.get(order.getGoodsId());
				order.setGoodsName(goodsInfo.getGoodsName());
				String niceName = userService.getById(order.getUserId()).getName();
				String orderCode = order.getType();
				if(res.containsKey(orderCode)){
					Map<String,Object> map= res.get(orderCode);
					List<GmOrderDO> tempList = (List<GmOrderDO>) map.get("data");
					tempList.add(order);
					map.replace("data",tempList);
					res.replace(orderCode,map);
				}else{
					List<GmOrderDO> tempList = new ArrayList<>();
					tempList.add(order);
					Map<String,Object> map= new HashMap<>();
					map.put("data",tempList);
					map.put("type",order.getOrderStatus());
					map.put("name",niceName);
					res.put(orderCode,map);
				}
			}
			for (String orderCode:res.keySet()) {
				Map<String,Object> child = res.get(orderCode);
				List<GmOrderDO> list = (List<GmOrderDO>) child.get("data");
				Double money = 0.0;
				for (GmOrderDO order:list) {
					GmGoodsInfoDO goodsInfo = goodsInfoService.get(order.getGoodsId());
					money += order.getGoodsNum()*goodsInfo.getGoodsPrice();
				}
				child.put("money",money);
				res.replace(orderCode,child);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 获取我的订单列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMyOrder")
	public Object getMyOrder(){
		Map<String,Map<String,Object>> res = new LinkedHashMap<>();
		try{
			UserDO user = ShiroUtils.getUser();
			if(user==null){
				ShiroUtils.logout();
			}
			Long userId = user.getUserId();
			Map<String,Object> query = new HashMap<>();
			query.put("userId",userId);
			List<GmOrderDO> orderList = gmOrderService.list(query);
			for (GmOrderDO order:orderList) {
				GmGoodsInfoDO goodsInfo = goodsInfoService.get(order.getGoodsId());
				order.setGoodsName(goodsInfo.getGoodsName());
				String niceName = userService.getById(order.getUserId()).getName();
				String orderCode = order.getType();
				if(res.containsKey(orderCode)){
					Map<String,Object> map= res.get(orderCode);
					List<GmOrderDO> tempList = (List<GmOrderDO>) map.get("data");
					tempList.add(order);
					map.replace("data",tempList);
					res.replace(orderCode,map);
				}else{
					List<GmOrderDO> tempList = new ArrayList<>();
					tempList.add(order);
					Map<String,Object> map= new HashMap<>();
					map.put("data",tempList);
					map.put("type",order.getOrderStatus());
					map.put("name",niceName);
					res.put(orderCode,map);
				}
			}
			for (String orderCode:res.keySet()) {
				Map<String,Object> child = res.get(orderCode);
				List<GmOrderDO> list = (List<GmOrderDO>) child.get("data");
				Double money = 0.0;
				for (GmOrderDO order:list) {
					GmGoodsInfoDO goodsInfo = goodsInfoService.get(order.getGoodsId());
					money += order.getGoodsNum()*goodsInfo.getGoodsPrice();
				}
				query = new HashMap<>();
				query.put("remark",orderCode);
				List<GmProfitDetailDO> detailDOS = profitDetailService.list(query);
				Double profit = 0.0;
				for (GmProfitDetailDO detail:detailDOS) {
					profit += detail.getAmount();
				}
				child.put("profit",profit);
				child.put("money",money);
				res.replace(orderCode,child);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 获取单个订单
	 * @param goodsCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrderOne")
	public Object getOrderOne(String goodsCode){
		List<Map<String,Object>> res = new ArrayList<>();
		Map<String,Object> result = new HashMap<>();
		try{
			UserDO user = ShiroUtils.getUser();
			if(user==null){
				ShiroUtils.logout();
			}
			Long userId = user.getUserId();
			Map<String,Object> query = new HashMap<>();
			query.put("parentId",userId);
			query.put("type",goodsCode);
			List<GmOrderDO> orderList = gmOrderService.list(query);
			UserDO child = null;
			String imgUrl = "";
			if(orderList!=null&&orderList.size()>0){
				for (GmOrderDO order:orderList) {
					Map<String,Object> data  = new HashMap<>();
					GmGoodsInfoDO goodsInfo = goodsInfoService.get(order.getGoodsId());
					data.put("goodsName",goodsInfo.getGoodsName());
					data.put("goodsNum",order.getGoodsNum());
					data.put("goodsId",order.getGoodsId());
					res.add(data);
					child = userService.getOutRole(order.getUserId());
					imgUrl = order.getOther();
				}

				result.put("orderCode",goodsCode);
				result.put("img",imgUrl);
				result.put("data",res);
				if(child!=null){
					result.put("receiptName",child.getName());
					result.put("receiptPhone",child.getMobile());
					result.put("receiptProvince",child.getProvince());
					result.put("receiptCity",child.getCity());
					result.put("receiptDistrict",child.getDistrict());
					result.put("receiptAddress",child.getLiveAddress());

				}
			}


		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 处理订单
	 * @param orderCode
	 * @param ids
	 * @param postCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendOrder")
	public R sendOrder(String orderCode, String ids, String postCode){

		UserDO user = ShiroUtils.getUser();
		if(user==null){
			ShiroUtils.logout();
		}
		UserDO child = null;
		Boolean isAdmin = false;
		Boolean isHave = true;
		Boolean flag = true;
		try {
			// 分隔二维码
			String[]goodsCodes = ids.split(",");

			// 先判断数量是否相同
			Integer codeSize  = goodsCodes.length;
			Integer orderSize = 0;

			// 查找下级用户申请的订单以便发货
			Map<String,Object> query = new HashMap<>();
			query.put("parentId",user.getUserId());
			query.put("type",orderCode);
			List<GmOrderDO> orderList= gmOrderService.list(query);
			Map<Integer,Integer> validSize = new HashMap<>();
			for (GmOrderDO orderDO:orderList) {
				Integer goodsId = orderDO.getGoodsId();
				Integer goodsNum = orderDO.getGoodsNum();
				orderSize += goodsNum;
				validSize.put(goodsId,goodsNum);

			}
			if(codeSize!=orderSize){
				return R.error("与请求发货数量不相符!");
			}


			// 获取分润信息
			GmProfitDO userProfit = profitService.getByUserId(user.getUserId());
			// 查找上级用户
			UserDO parent = userService.getOutRole(user.getParentId());
			// 上级分润信息
			GmProfitDO parentProfit = null;
			// 当前用户等级
			Long type = user.getDeptId();
			//联合创始人发货时奖励自己和上级 总经销商只奖励自己
			if(type==2){
				if(parent!=null){
					parentProfit = profitService.getByUserId(parent.getUserId());
				}
			}
			// 标记是否是平台管理员
			if(type==1){
				isAdmin = true;
			}

			// 如果无订单
			if(orderList==null||orderList.size()<1){
				return R.error("订单无效");
			}
			// 循环遍历订单 根据订单 拿到所有订单需要的商品goodsUser
			List<GmGoodsUserDO> goodsUserList = new ArrayList<>();
			for (int j = 0; j < orderList.size(); j++) {
				// 获取订单
				GmOrderDO order = orderList.get(j);
				child = userService.getOutRole(order.getUserId());
				if (order.getOrderStatus() != 1) {
					return R.error("已处理或失效订单");
				}

				query = new HashMap<>();
				query.put("userId", user.getUserId());
				query.put("status", "0");
				query.put("type", order.getGoodsId());
				goodsUserList.addAll(goodsUserService.list(query));
			}
			// 处理完的goodsUserList
			goodsUserList = hasCode(goodsUserList,orderList,goodsCodes);


			if(goodsUserList.size()!=codeSize){
				return R.error("无效二维码!");
			}
			Map<Integer,Integer> validSizeRes = new HashMap<>();
			for (GmGoodsUserDO goodsUser:goodsUserList) {
				Integer goodsId = Integer.parseInt(goodsUser.getType());
				if(validSizeRes.containsKey(goodsId)){
					validSizeRes.replace(goodsId,validSizeRes.get(goodsId)+1);
				}else{
					validSizeRes.put(goodsId,1);
				}
			}
			for (Integer num:validSizeRes.keySet()) {
				if(validSizeRes.get(num)!=validSize.get(num)){
					return R.error("二维码与订单不符!请核对");
				}
			}

			for (GmGoodsUserDO goodsUser:goodsUserList) {
				// 先将这个库存改变状态
				goodsUser.setStatus("1");
				goodsUser.setOutTime(DateUtil.getDateTime());
				if(goodsUserService.update(goodsUser)<0){
					flag = false;
				}
				// 然后给 下级 新建一个库存
				goodsUser.setUserId(child.getUserId());
				goodsUser.setId(null);
				goodsUser.setStatus("0");
				goodsUser.setInTime(DateUtil.getDateTime());
				goodsUser.setOutTime(null);
				if(goodsUserService.save(goodsUser)<0){
					flag = false;
				}

				// 分润开始 fuck me...
				if(flag){
					if(type==2&&userProfit!=null&&parentProfit!=null){ //联合创始人
						GmProfitDetailDO profitDetail = new GmProfitDetailDO();
						// 给下级发货 自身直接奖励80
						profitDetail.setAmount(REWARD_A);
						profitDetail.setCreateTime(DateUtil.getDateTime());
						profitDetail.setStatus(2);
						// 谁奖励的 这里平台奖励的 也就是admin
						profitDetail.setParentId(parent.getUserId());
						profitDetail.setProfitId(userProfit.getId());
						profitDetail.setRemark(orderCode);
						profitDetailService.save(profitDetail);
						//  推荐人奖励 50
						profitDetail.setAmount(REWARD_B);
						profitDetail.setProfitId(parent.getUserId());
						profitDetailService.save(profitDetail);
					}else if(type==3&&userProfit!=null){ //若果是经销商 发给下级或 直接给自己分润
						// 给下级发货 自身直接奖励80
						GmProfitDetailDO profitDetail = new GmProfitDetailDO();
						profitDetail.setAmount(REWARD_A);
						profitDetail.setCreateTime(DateUtil.getDateTime());
						profitDetail.setStatus(2);
						profitDetail.setParentId(parent.getUserId());
						profitDetail.setProfitId(userProfit.getId());
						profitDetail.setRemark(orderCode);
						profitDetailService.save(profitDetail);
					}
				}
				for (GmOrderDO order:orderList) {
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

	/**
	 * 查找订单 有二维码就添加已有的 没有就添加新的
	 * @param gmGoodsUserList
	 * @param orderList
	 * @param codes
	 * @return
	 */
	private List<GmGoodsUserDO> hasCode(List<GmGoodsUserDO> gmGoodsUserList,List<GmOrderDO> orderList,String[]codes){

		Long start = System.currentTimeMillis();
		// 返回结果接
		List<GmGoodsUserDO> res = new ArrayList<>();
		//  goodsId     goodsNum
		LinkedHashMap<Integer,LinkedHashMap<Integer,List<GmGoodsUserDO>>> orderMap = new LinkedHashMap<>();

		try {
			for (GmOrderDO order:orderList) {
				LinkedHashMap<Integer,List<GmGoodsUserDO>> tempMap = new LinkedHashMap<>();
				List<GmGoodsUserDO> tempList = new ArrayList<>();
				tempMap.put(order.getGoodsNum(),tempList);
				orderMap.put(order.getGoodsId(),tempMap);
			}

			// 先根据二维码查找
			for (int i = 0; i < codes.length; i++) {
				String code = codes[i];
				for (int j = 0; j < gmGoodsUserList.size(); j++) {
					GmGoodsUserDO goodsUser = gmGoodsUserList.get(j);
					String goodsCode = goodsUser.getGoodsCode();
					if(StringUtils.isNotBlank(goodsCode)){
						if(goodsCode.equals(code)){
							Integer goodsId = Integer.parseInt(goodsUser.getType());
							LinkedHashMap<Integer,List<GmGoodsUserDO>> tempMap = orderMap.get(goodsId);
							Integer goodsNum = null;
							List<GmGoodsUserDO> tempList = null;
							for (Integer key:tempMap.keySet()) {
								goodsNum = key;
								tempList = tempMap.get(goodsNum);
							}
							tempList.add(goodsUser);
							tempMap.replace(goodsNum,tempList);
							orderMap.replace(goodsId,tempMap);
							gmGoodsUserList.remove(j);
						}
					}
				}
			}
			// 再遍历orderMap 如果上面没有添加到二维码 就添加新的goodsUser
			for (Integer goodsId:orderMap.keySet()) {
				LinkedHashMap<Integer,List<GmGoodsUserDO>> tempMap = orderMap.get(goodsId);
				Integer goodsNum = null;
				List<GmGoodsUserDO> tempList = null;
				for (Integer key:tempMap.keySet()) {
					goodsNum = key;
					tempList = tempMap.get(key);
				}
				Integer size = tempList.size();
				if(goodsNum>size){
					for (int i = 0; i < goodsNum-size; i++) {
						loop:for (int j = 0;j<gmGoodsUserList.size();j++) {
							GmGoodsUserDO goodsUser = gmGoodsUserList.get(j);
							if(goodsUser.getType().equals(String.valueOf(goodsId))&&StringUtils.isBlank(goodsUser.getGoodsCode())){
								tempList.add(goodsUser);
								tempMap.replace(goodsNum,tempList);
								orderMap.replace(goodsId,tempMap);
								gmGoodsUserList.remove(j);
								break loop;
							}
						}
					}
				}
			}

			// 再遍历一次 取出list
			for (Integer goodsId:orderMap.keySet()) {
				LinkedHashMap<Integer,List<GmGoodsUserDO>> tempMap = orderMap.get(goodsId);
				for (Integer goodsNum:tempMap.keySet()) {
					res.addAll(tempMap.get(goodsNum));
				}
			}
//			for (GmGoodsUserDO goodsUserDO:res) {
//				System.out.println(goodsUserDO);
//			}

			// 再再遍历一遍 取出已经有二维码的 妈的数据库设计不好 能写死你
			Map<Integer,String> query = new HashMap<>();
			for (int i = 0; i < codes.length; i++) {
				String code = codes[i];
				for (int j = 0; j < res.size(); j++) {
					GmGoodsUserDO goodsUser = res.get(j);
					String goodsCode = goodsUser.getGoodsCode();
					if(StringUtils.isNotBlank(goodsCode)){
						if(goodsUser.getGoodsCode().equals(code)){
							query.put(j,code);
						}
					}
				}
			}

			for (Integer index:query.keySet()) {
				String goodsCode = query.get(index);
				for (int i = 0; i < codes.length; i++) {
					if(goodsCode.equals(codes[i])){
						codes[i] = codes[codes.length-1];
						codes = Arrays.copyOf(codes,codes.length-1);
					}
				}
			}
//			System.out.println(Arrays.toString(codes));
			for (int i=0;i<res.size();i++) {
				GmGoodsUserDO goodsUser = res.get(i);
				if(StringUtils.isBlank(goodsUser.getGoodsCode())){
					Integer index = new Random().nextInt(codes.length);
					goodsUser.setGoodsCode(codes[index]);
					codes[index] = codes[codes.length-1];
					codes = Arrays.copyOf(codes,codes.length-1);
					res.set(i,goodsUser);
				}
			}
//			System.out.println(Arrays.toString(codes));
//			for (GmGoodsUserDO goodsUserDO:res) {
//				System.out.println(goodsUserDO);
//			}


		}catch (Exception e){
			e.printStackTrace();
		}

		return res;
	}




}
