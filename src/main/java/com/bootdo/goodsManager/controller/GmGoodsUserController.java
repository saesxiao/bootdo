package com.bootdo.goodsManager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.goodsManager.domain.GmGoodsInfoDO;
import com.bootdo.goodsManager.service.GmGoodsInfoService;
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

import com.bootdo.goodsManager.domain.GmGoodsUserDO;
import com.bootdo.goodsManager.service.GmGoodsUserService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 商品对应用户表
 * 
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-02 15:44:34
 */
 
@Controller
@RequestMapping("/goodsManager/gmGoodsUser")
public class GmGoodsUserController {
	@Autowired
	private GmGoodsUserService gmGoodsUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private GmGoodsInfoService gmGoodsInfoService;
	
	@GetMapping()
	@RequiresPermissions("goodsManager:gmGoodsUser:gmGoodsUser")
	String GmGoodsUser(){
	    return "goodsManager/gmGoodsUser/gmGoodsUser";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("goodsManager:gmGoodsUser:gmGoodsUser")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
//		List<GmGoodsUserDO> gmGoodsUserList = gmGoodsUserService.list(query);
		List<HashMap<String,Object>> gmGoodsUserList = gmGoodsUserService.listUser(query);
		int total = gmGoodsUserService.count(query);
		PageUtils pageUtils = new PageUtils(gmGoodsUserList, total);
		return pageUtils;
	}


	
	@GetMapping("/add")
	@RequiresPermissions("goodsManager:gmGoodsUser:add")
	String add(){
	    return "goodsManager/gmGoodsUser/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("goodsManager:gmGoodsUser:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		GmGoodsUserDO gmGoodsUser = gmGoodsUserService.get(id);
		model.addAttribute("gmGoodsUser", gmGoodsUser);
	    return "goodsManager/gmGoodsUser/edit";
	}

	@GetMapping("/getOrder")
	String getOrder(String goodsCode,Model model){
		Map<String,Object> query = new HashMap<>();
		query.put("goodsCode",goodsCode);
		List<HashMap<String,Object>> list = gmGoodsUserService.listUser(query);

		Map<String,Object> res = new HashMap<>();
		res.put("goodsName",list.get(0).get("goods_name"));
		res.put("goodsCode",list.get(0).get("goods_code"));
		List<Map<String,Object>> infoList = new ArrayList<>();
		for (HashMap<String,Object> item:list) {
			Map<String,Object> info = new HashMap<>();
			try{
				UserDO user = userService.get((Long) item.get("parent_id"));
				info.put("parentName",user.getName());
			}catch (Exception e){
				info.put("parentName"," - ");
			}
			info.put("name",item.get("name"));

			info.put("inTime",item.get("in_time"));
			info.put("outTime",item.get("out_time"));
			infoList.add(info);
		}
		res.put("info",infoList);

		model.addAttribute("orderList",res);
		return "goodsManager/gmGoodsUser/order";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("goodsManager:gmGoodsUser:add")
	public R save( GmGoodsUserDO gmGoodsUser){
		if(gmGoodsUserService.save(gmGoodsUser)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("goodsManager:gmGoodsUser:edit")
	public R update( GmGoodsUserDO gmGoodsUser){
		gmGoodsUserService.update(gmGoodsUser);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmGoodsUser:remove")
	public R remove( Integer id){
		if(gmGoodsUserService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmGoodsUser:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		gmGoodsUserService.batchRemove(ids);
		return R.ok();
	}
	
}
