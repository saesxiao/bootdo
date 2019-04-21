package com.bootdo.goodsManager.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.*;
import com.bootdo.goodsManager.domain.*;
import com.bootdo.goodsManager.service.*;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.DeptService;
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
	@Autowired
	private GmOrderDetailService orderDetailService;
	@Autowired
	private DeptService deptService;

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
		// 创建订单
		GmOrderDO orderDO = new GmOrderDO();
		try {
			Long userId = user.getUserId();
			Long parentId = user.getParentId();
			JSONObject param = JSON.parseObject(jsonStr);
			JSONObject goodsInfo = param.getJSONObject("goods");
			// 商品订单
			Map<String,Object> map = JSONObject.parseObject(goodsInfo.toJSONString(),Map.class);
			String address= param.getString("address");
			String imgUrl = param.getString("imgUrl");
			String time = DateUtil.getDateTime();
			String orderCode = "XD"+OrderTool.getOrderNo(5);


			orderDO.setUserId(userId);
			orderDO.setParentId(parentId);
			orderDO.setOrderStatus(1);
			orderDO.setOrderTime(time);
			orderDO.setRemark(address);
			orderDO.setOther(imgUrl);
			orderDO.setOrderCode(orderCode);
			gmOrderService.save(orderDO);

			Double amount = 0.0;
			// 循环商品订单
			for (String key : map.keySet()) {
				Integer goodsId = Integer.parseInt(key);
				GmGoodsInfoDO goods = goodsInfoService.get(goodsId);
				String tempNum = String.valueOf(map.get(key));
				if(StringUtils.isNotBlank(tempNum)){
					Integer goodsNum  = Integer.parseInt(tempNum);
					GmOrderDetailDO orderDetail = new GmOrderDetailDO();
					orderDetail.setGoodsId(goods.getId());
					orderDetail.setGoodsNum(Integer.parseInt(tempNum));
					orderDetail.setOrderId(orderDO.getId());
					orderDetailService.save(orderDetail);
					Integer level = Math.toIntExact(user.getDeptId());
					String levelPrice = goods.getGoodsPrice().split(",")[level-2];
					amount += Double.parseDouble(levelPrice)*goodsNum;
				}


//				Integer goodsNum  = Integer.parseInt(tempNum);
//				// 如果此商品有且 需求数量大于零
//				if(goods!=null&&goodsNum>0){
//					Map<String,Object> query = new HashMap<>();
//					query.put("userId",parentId); // 根据上级id 查询到上级的库存
//					query.put("status",0);
//					query.put("type",goods.getId());
//					List<GmGoodsUserDO> list =goodsUserService.list(query);
//					// 如果上级库存大于需求数量
//					if(list.size()>=goodsNum){
//						GmOrderDetailDO orderDetail = new GmOrderDetailDO();
//						orderDetail.setGoodsId(goods.getId());
//						orderDetail.setGoodsNum(goodsNum);
//						orderDetail.setOrderId(orderDO.getId());
//						orderDetailService.save(orderDetail);
//						Integer level = Math.toIntExact(user.getDeptId());
//						String levelPrice = goods.getGoodsPrice().split(",")[level-2];
//						amount += Double.parseDouble(levelPrice)*goodsNum;
//					}else{
//						//通时删除订单
//						gmOrderService.remove(orderDO.getId());
//						return R.error("上级库存不足");
//					}
//				}
			}
			orderDO.setAmount(amount);
			gmOrderService.update(orderDO);
			return R.ok("下单成功");

		}catch (Exception e){
			e.printStackTrace();
			gmOrderService.remove(orderDO.getId());
			return R.error();
		}
	}

	/**
	 * 获取发货订单列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSendOrder")
	public Object getSnedOrder(Boolean isMine){
		Map<String,Map<String,Object>> res = new LinkedHashMap<>();
		try{
			UserDO user = ShiroUtils.getUser();
			if(user==null){
				ShiroUtils.logout();
			}
			String isParent = null;
			Long userId = user.getUserId();
			String niceName = null;
			if(isMine!=null&&isMine==true){ //获取自己订单
				isParent="userId";
				niceName = user.getName();
			}else{//获取下级订单
				isParent="parentId";
			}
			Map<String,Object> query = new HashMap<>();
			query.put(isParent,userId);
			List<GmOrderDO> orderList = gmOrderService.list(query);
			for (GmOrderDO order :orderList) {
				// 获取订单编号 金额
				String orderCode = order.getOrderCode();
				query = new HashMap<>();
				query.put("orderId",order.getId());
				List<GmOrderDetailDO> orderDetailList = orderDetailService.list(query);
				Map<String,Object> tempList = new HashMap<>();
				tempList.put("data",orderDetailList);
				tempList.put("status",order.getOrderStatus());
				if(isMine==null){
					niceName = userService.getOutRole(order.getUserId()).getName();
				}
				tempList.put("name",niceName);
				tempList.put("amount",order.getAmount());
				tempList.put("createTime",order.getOrderTime());
				tempList.put("orderId",order.getId());
				res.put(orderCode,tempList);
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
		try{
			return getSnedOrder(true);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取单个订单
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrderOne")
	public Object getOrderOne(String orderId){
		Map<String,Object> res = new HashMap<>();
		GmOrderDO order = null;
		try {
			order = gmOrderService.get(Long.parseLong(orderId));
			if(order==null){
				return R.error("订单号错误");
			}
		}catch (Exception e){
			e.printStackTrace();
			return R.error("订单号错误");
		}
		try{
			UserDO user = userService.getOutRole(order.getUserId());
			DeptDO dept = deptService.get(user.getDeptId());

			if(order!=null){
				Map<String,Object> query = new HashMap<>();
				query.put("orderId",order.getId());
				List<GmOrderDetailDO> detailList = orderDetailService.list(query);
				res.put("data",detailList);
				res.put("orderCode",order.getOrderCode());
				res.put("img",order.getOther());
				res.put("data",detailList);
				res.put("address",order.getRemark());
				res.put("name",user.getName());
				res.put("deptName",dept.getName());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 处理订单
	 * @param orderId
	 * @param ids
	 * @param postCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendOrder")
	public R sendOrder(String orderId, String ids, String postCode){

		UserDO user = ShiroUtils.getUser();
		if(user==null){
			ShiroUtils.logout();
		}
		if(StringUtils.isBlank(orderId)||StringUtils.isBlank(ids)){
			return R.error("参数错误");
		}
		// 储存二维码信息
		Map<String,List<String>> goodsCodes = null;

		try {
			// 解析二维码信息
			goodsCodes = JSONObject.parseObject(ids,Map.class);
		}catch (Exception e){
			e.printStackTrace();
			return R.error("二维码参数错误");
		}

		Boolean flag = true;
		try {


			GmOrderDO order = gmOrderService.get(Long.parseLong(orderId));
			if(order==null){
				return R.error("订单错误");
			}
			if(order.getOrderStatus()!=1){
				return R.error("已处理或失效订单");
			}
			// 获取订单详情
			Map<String,Object> query = new HashMap<>();
			query.put("orderId",order.getId());
			List<GmOrderDetailDO> orderDetailList = orderDetailService.list(query);

			// 获取要处理的订单
			Map<String,Object> res = getGoodsUserList(user,orderDetailList,goodsCodes);
			Integer resCode = (Integer) res.get("resCode");
			if(resCode!=200){
				return R.error((String)res.get("data"));
			}
			List<GmGoodsUserDO> goodsUserList = (List<GmGoodsUserDO>)res.get("data");

			// 获取本人分润信息
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

			for (GmGoodsUserDO goodsUser:goodsUserList) {
				// 先将这个库存改变状态
				goodsUser.setStatus("1");
				goodsUser.setOutTime(DateUtil.getDateTime());
				if(goodsUserService.update(goodsUser)<0){
					flag = false;
				}
				// 然后给 下级 新建一个库存
				goodsUser.setUserId(order.getUserId());
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
						profitDetail.setRemark(order.getOrderCode());
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
						profitDetail.setRemark(order.getOrderCode());
						profitDetailService.save(profitDetail);
					}
				}
				order.setOrderStatus(2);
				order.setEndTime(DateUtil.getDateTime());
				if(postCode!=null){
					order.setType(postCode);
				}
				gmOrderService.update(order);
			}
			return R.ok();
		}catch (Exception e){
			e.printStackTrace();
		}
		return R.error();
	}

	/**
	 * 查找订单 有二维码就添加已有的 没有就添加新的
	 * @param user			当前用户
	 * @param detailList	订单详情
	 * @param codes			二维码数组
	 * @return
	 */
	private Map<String,Object> getGoodsUserList(UserDO user ,List<GmOrderDetailDO> detailList,Map<String,List<String>> codes) {
		// 数据库查找Query
		Map<String, Object> query = new HashMap<>();

		// 最终返回结果
		Map<String,Object> res = new HashMap<>();

		// 临时储存库存 goodsId  库存列表
		Map<Integer, List<GmGoodsUserDO>> tempMap = new LinkedHashMap<>();

		//统计订单数量Map  goodsId goodsNum
		Map<Integer, Integer> validSize = new HashMap<>();

		Boolean flag = true;

		try {

			// 遍历订单明细 给临时库存 和订单数量统计Map建立数据
			for (GmOrderDetailDO detail : detailList) {

				// 给临时库存建立值
				List<GmGoodsUserDO> temp = new ArrayList<>();
				tempMap.put(detail.getGoodsId(), temp);

				// 给统计订单数量Map建立值
				validSize.put(detail.getGoodsId(), detail.getGoodsNum());
			}

			// 如果是平台发货 则添加没有二维码的平台库存并赋予二维码
			if(user.getDeptId()==1){
				loop:for (Integer goodsId : tempMap.keySet()) {

					// 获取 统计订单数量Map 的改商品订单数量
					Integer num = validSize.get(goodsId);

					// 判断临时库存是否够数
					List<GmGoodsUserDO> tempList = tempMap.get(goodsId);

					// 获取此商品id 的库存
					query = new HashMap<>();
					query.put("userId", user.getUserId());
					query.put("status", "0");
					query.put("type", goodsId);
					query.put("goodsCode", "-");

					List<GmGoodsUserDO> goodsUserListOutCode = goodsUserService.list(query);

					// 判断库存是否足够
					if(goodsUserListOutCode.size()<num){
						res.put("resCode",401);
						res.put("data","库存不足");
						flag = false;
						break loop;
					}

					// 获取前台扫码的此商品二维码
					List<String> qrCodeList = codes.get(String.valueOf(goodsId));
					if(qrCodeList!=null&&qrCodeList.size()==num){
						for (int i = 0; i < num; i++) {
							GmGoodsUserDO goodsUser = goodsUserListOutCode.get(i);
							goodsUser.setGoodsCode(qrCodeList.get(i));
							tempList.add(goodsUser);
						}
					}else {
						res.put("resCode",403);
						res.put("data","商品id错误或二维码数量错误");
						flag = false;
						break loop;
					}

					tempMap.replace(goodsId, tempList);
				}
			}else{
				// 如果不是平台发货 则必须要正确的二维码
				// 遍历二维码数组 查找有二维码的库存 并添加到临时库存
				loop: for (String goodsId:codes.keySet()) {
					List<String> qrCodeList = codes.get(String.valueOf(goodsId));
					if(qrCodeList!=null){
						for (String qrCode:qrCodeList) {
							query = new HashMap<>();
							query.put("userId", user.getUserId());
							query.put("status", "0");
							query.put("goodsCode", qrCode);
							List<GmGoodsUserDO> temp = goodsUserService.list(query);

							if (temp != null) {
								GmGoodsUserDO goodsUser = temp.get(0);
								Integer goodsUserId = Integer.parseInt(goodsUser.getType());
								List<GmGoodsUserDO> tempList = tempMap.get(goodsUserId);
								tempList.add(goodsUser);
								tempMap.replace(goodsUserId, tempList);
							}else{
								res.put("resCode",402);
								res.put("data","二维码错误,请仔细核对");
								flag = false;
								break loop;
							}
						}
					}else{
						res.put("resCode",403);
						res.put("data","商品id错误或二维码数量错误");
						flag = false;
						break loop;
					}

				}
			}

			if (!flag){
				return res;
			}


			List<GmGoodsUserDO> goodsUserList = new ArrayList<>();
			// 最后遍历临时库存 取出要处理的库存
			for (Integer goodsId:tempMap.keySet()) {
				goodsUserList.addAll(tempMap.get(goodsId));
			}
			res.put("resCode",200);
			res.put("data",goodsUserList);
			return res;
		}catch (Exception e){
			e.printStackTrace();
			res.put("resCode",500);
			res.put("data","服务器繁忙,请稍候再试");
			return res;
		}
	}

}
