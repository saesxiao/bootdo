package com.bootdo.goodsManager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.common.utils.*;
import com.bootdo.goodsManager.dao.GmGoodsUserDao;
import com.bootdo.goodsManager.domain.GmGoodsUserDO;
import com.bootdo.goodsManager.service.GmGoodsUserService;
import com.bootdo.system.domain.UserDO;
import io.swagger.models.auth.In;
import org.apache.catalina.User;
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

import com.bootdo.goodsManager.domain.GmGoodsInfoDO;
import com.bootdo.goodsManager.service.GmGoodsInfoService;

/**
 * 商品信息表
 * 
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-02 15:44:34
 */
 
@Controller
@RequestMapping("/goodsManager/gmGoodsInfo")
public class GmGoodsInfoController {
	@Autowired
	private GmGoodsInfoService gmGoodsInfoService;
	@Autowired
	private GmGoodsUserService goodsUserService;
	@Autowired
	private GmGoodsInfoService goodsInfoService;
	
	@GetMapping()
	@RequiresPermissions("goodsManager:gmGoodsInfo:gmGoodsInfo")
	String GmGoodsInfo(){
	    return "goodsManager/gmGoodsInfo/gmGoodsInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("goodsManager:gmGoodsInfo:gmGoodsInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		UserDO user = ShiroUtils.getUser();
		if(user==null){
			ShiroUtils.logout();
		}
		Long userId = user.getUserId();
        Query query = new Query(params);
		List<GmGoodsInfoDO> gmGoodsInfoList = gmGoodsInfoService.list(query);
		for (int i = 0;i< gmGoodsInfoList.size();i++) {
			GmGoodsInfoDO goodsInfoDO = gmGoodsInfoList.get(i);
			Map<String,Object> param = new HashMap<>();
			param.put("status","0");
			param.put("userId",userId);
			param.put("type",goodsInfoDO.getId()+"");
			Integer num = goodsUserService.count(param);
			goodsInfoDO.setRemark(num+"");
			goodsInfoService.update(goodsInfoDO);
			gmGoodsInfoList.set(i,goodsInfoDO);

		}
		int total = gmGoodsInfoService.count(query);
		PageUtils pageUtils = new PageUtils(gmGoodsInfoList, total);
		return pageUtils;
	}

	@ResponseBody
	@GetMapping("/getList")
	public Object getList(){
		//查询列表数据
		Map<String,Object> query = new HashMap<>();
		query.put("statu",1);
		List<GmGoodsInfoDO> gmGoodsInfoList = gmGoodsInfoService.list(query);

		return gmGoodsInfoList;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("goodsManager:gmGoodsInfo:add")
	String add(){
	    return "goodsManager/gmGoodsInfo/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("goodsManager:gmGoodsInfo:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		GmGoodsInfoDO gmGoodsInfo = gmGoodsInfoService.get(id);
		model.addAttribute("gmGoodsInfo", gmGoodsInfo);
	    return "goodsManager/gmGoodsInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("goodsManager:gmGoodsInfo:add")
	public R save( GmGoodsInfoDO gmGoodsInfo){
		try {
			UserDO user = ShiroUtils.getUser();
			if(gmGoodsInfoService.save(gmGoodsInfo)>0){
				String remark = gmGoodsInfo.getRemark();
				if(remark.matches("\\d+")){
					Integer size = Integer.parseInt(remark);
					for (int i = 0; i < size; i++) {
						GmGoodsUserDO goodsUser = new GmGoodsUserDO();
						goodsUser.setUserId(user.getUserId());
						goodsUser.setStatus("0");
						goodsUser.setType(gmGoodsInfo.getId()+"");
						goodsUser.setInTime(DateUtil.getDateTime());
						goodsUser.setBatch(1);
						goodsUser.setGoodsCode("-");
						goodsUserService.save(goodsUser);
					}
				}

				return R.ok();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("goodsManager:gmGoodsInfo:edit")
	public R update( GmGoodsInfoDO gmGoodsInfo){
		try {
			UserDO user = ShiroUtils.getUser();
			Map<String,Object> query = new HashMap<>();
			query.put("userId",user.getUserId());
			query.put("status","0");
			query.put("type",gmGoodsInfo.getId()+"");
			List<GmGoodsUserDO> goodsUserList = goodsUserService.list(query);
			Integer have = goodsUserList.size();
			String remark = gmGoodsInfo.getRemark();
			if(remark.matches("\\d+")){
				Integer size = Integer.parseInt(remark);
				Integer sub = size-have;
				if(sub>=0){
					for (int i = 0; i < sub; i++) {
						GmGoodsUserDO goodsUser = new GmGoodsUserDO();
						goodsUser.setUserId(user.getUserId());
						goodsUser.setStatus("0");
						goodsUser.setType(gmGoodsInfo.getId()+"");
						goodsUser.setInTime(DateUtil.getDateTime());
						goodsUser.setBatch(1);
						goodsUser.setGoodsCode("-");
						goodsUserService.save(goodsUser);
					}
				}else{
					return R.error("库存不能减小");
				}
			}

			gmGoodsInfoService.update(gmGoodsInfo);
		}catch (Exception e){
			e.printStackTrace();
		}
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmGoodsInfo:remove")
	public R remove( Integer id){
		if(gmGoodsInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmGoodsInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		gmGoodsInfoService.batchRemove(ids);
		return R.ok();
	}
	
}
