package com.bootdo.goodsManager.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.*;
import com.bootdo.goodsManager.domain.*;
import com.bootdo.goodsManager.service.*;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
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

	@ResponseBody
	@RequestMapping("/getOrder")
	public Object getOrder(){
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
			query = new HashMap<>();
			query.put("userId",userId);
			orderList.addAll(gmOrderService.list(query));

			for (GmOrderDO order:orderList) {
				GmGoodsInfoDO goodsInfo = goodsInfoService.get(order.getGoodsId());
				order.setGoodsName(goodsInfo.getGoodsName());
				String type = "";
				String niceName = userService.getById(order.getUserId()).getName();
				if(order.getUserId()==userId&&order.getParentId()!=userId&&order.getOrderStatus()==1){
					type = "1";
				}else if(order.getParentId()==userId&&order.getUserId()!=userId&&order.getOrderStatus()!=2){
					type = "2";
				}else if(order.getParentId()==userId&&order.getUserId()!=userId&&order.getOrderStatus()==2){
					type = "3";
				}
				Double money = goodsInfo.getGoodsPrice()*order.getGoodsNum();
				String orderCode = order.getType(); //+"-"+type+"-"+userDO.getName()+"-"+money
				if(res.containsKey(orderCode)){
					Map<String,Object> map= res.get(orderCode);
					List<GmOrderDO> tempList = (List<GmOrderDO>) map.get("data");
					tempList.add(order);
					map.replace("data",tempList);
					map.put("type",type);
					map.put("name",niceName);
					map.put("money",money);
					res.replace(orderCode,map);
				}else{
					List<GmOrderDO> tempList = new ArrayList<>();
					tempList.add(order);
					Map<String,Object> map= new HashMap<>();
					map.put("data",tempList);
					map.put("type",type);
					map.put("name",niceName);
					map.put("money",money);
					res.put(orderCode,map);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}

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
			if(orderList!=null&&orderList.size()>0){
				for (GmOrderDO order:orderList) {
					Map<String,Object> data  = new HashMap<>();
					GmGoodsInfoDO goodsInfo = goodsInfoService.get(order.getGoodsId());
					data.put("goodsName",goodsInfo.getGoodsName());
					data.put("goodsNum",order.getGoodsNum());
					data.put("goodsId",order.getGoodsId());
					res.add(data);
					child = userService.getOutRole(order.getUserId());
				}

				result.put("orderCode",goodsCode);
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

	@ResponseBody
	@RequestMapping("/sendOrder")
	public R sendOrder(String orderCode,String ids,String postCode){

		UserDO user = ShiroUtils.getUser();
		if(user==null){
			ShiroUtils.logout();
		}
		Boolean isAdmin = false;
		Boolean isHave = true;
		try {

			//获取分润
			GmProfitDO userProfit = profitService.getByUserId(user.getUserId());
			UserDO parent = userService.getOutRole(user.getParentId());
			GmProfitDO parentProfit = null;
			Long type = user.getDeptId();
			if(type==2){ //联合创始人发货时奖励自己 和上级 总经销商只奖励自己
				if(parent!=null){
					parentProfit = profitService.getByUserId(parent.getUserId());
				}
			}
			if(type==1){ // 标记平台发货
				isAdmin = true;
			}


			Map<String,Object> query = new HashMap<>();
			// 本账户登录 找到parentId为 本用户id的 订单
			query.put("parentId",user.getUserId());
			query.put("type",orderCode);
			List<GmOrderDO> orderList= gmOrderService.list(query);
			// 如果无订单
			if(orderList==null||orderList.size()<1){
				return R.error("订单无效");
			}
			for (int j = 0; j < orderList.size(); j++) {
				// 获取订单
				GmOrderDO order = orderList.get(j);
				if(order.getOrderStatus()!=1){
					return R.error("已处理或失效订单");
				}
				// 通过当前账户id 和 订单的商品id 找到当前用户的库存
				query = new HashMap<>();
				query.put("userId",user.getUserId());
				query.put("status","0");
				query.put("type",order.getGoodsId());
				List<GmGoodsUserDO> goodsUserList =goodsUserService.list(query);

				// 解析传来的二维码
				String[]goodsCodes = ids.split(",");
				Integer size = goodsCodes.length;
				if(size>goodsUserList.size()){
					return R.error("库存不足");
				}

				// 根据二维码的数量 遍历库存 给库存添加二维码
				broken:for (int i = 0; i < size; i++) {

					// 如果是平台管理者 则跟新平台库存
					if(isAdmin){
						GmGoodsInfoDO goodsInfoDO = goodsInfoService.get(order.getGoodsId());
						if(goodsInfoDO!=null){
							Integer remark = Integer.parseInt(goodsInfoDO.getRemark());
							if(remark>0){
								remark -= 1;
								goodsInfoDO .setRemark(remark+"");
								goodsInfoService.update(goodsInfoDO);
							}else {
								goodsInfoDO.setStatu(2);
								goodsInfoService.update(goodsInfoDO);
								isHave = false;
								break broken;
							}
						}


					}

					String goodsCode = goodsCodes[i];
					Boolean flag = true;
					//先根据二维码查询
					Map<String,Object> queryGoodsUser = new HashMap<>();
					queryGoodsUser.put("goodsCode",goodsCode);
					queryGoodsUser.put("userId",user.getUserId());
					queryGoodsUser.put("status","0");
					List<GmGoodsUserDO> goodsUserDOList = goodsUserService.list(queryGoodsUser);
					GmGoodsUserDO goodsUserDO = null;

					if(goodsUserDOList!=null&&goodsUserDOList.size()>0){
						goodsUserDO = goodsUserDOList.get(0);
						// 先将该库存 改变状态为已发
						goodsUserDO.setStatus("1");
						goodsUserDO.setOutTime(DateUtil.getDateTime());
						if(goodsUserService.update(goodsUserDO)<0){
							flag = false;
						}
						// 然后给 下级 新建一个库存
						goodsUserDO.setUserId(order.getUserId());
						goodsUserDO.setId(null);
						goodsUserDO.setStatus("0");
						goodsUserDO.setInTime(DateUtil.getDateTime());
						goodsUserDO.setOutTime(null);
						if(goodsUserService.save(goodsUserDO)<0){
							flag = false;
						}
					}else { // 如果查不到二维码 则下发新的
						Boolean isNoCode = false;
						loop:for (GmGoodsUserDO temp:goodsUserList) {
							if(temp.getGoodsCode()==null||temp.getGoodsCode().equals("")){
								isNoCode = true;
								goodsUserDO = temp;
								break loop;
							}
						}
						if(!isNoCode){
							return R.error("二维码错误");
						}
						// 先将该库存设置一个二维码 并改变状态为已发
						goodsUserDO.setStatus("1");
						goodsUserDO.setGoodsCode(goodsCode);
						goodsUserDO.setOutTime(DateUtil.getDateTime());
						if(goodsUserService.update(goodsUserDO)<0){
							flag = false;
						}
						// 然后给 下级 新建一个库存 并设置二维码
						goodsUserDO.setUserId(order.getUserId());
						goodsUserDO.setId(null);
						goodsUserDO.setStatus("0");
						goodsUserDO.setInTime(DateUtil.getDateTime());
						goodsUserDO.setOutTime(null);
						if(goodsUserService.save(goodsUserDO)<0){
							flag = false;
						}
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
							profitDetail.setRemark(order.getType());
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
							profitDetail.setRemark(order.getType());
							profitDetailService.save(profitDetail);
						}
					}


				}

				if(!isHave){
					return R.error("库存不足,请前往平台上货");
				}
				order.setOrderStatus(2);
				order.setEndTime(DateUtil.getDateTime());
				order.setRemark(postCode);
				gmOrderService.update(order);
			}
			return R.ok();
		}catch (Exception e){
			e.printStackTrace();
		}
		return R.error();
	}



}
