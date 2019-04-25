package com.bootdo.goodsManager.controller;

import java.util.List;
import java.util.Map;

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

import com.bootdo.goodsManager.domain.GmPromotionDO;
import com.bootdo.goodsManager.service.GmPromotionService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 用户晋升表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-25 08:55:54
 */
 
//@Controller
//@RequestMapping("/goodsManager/gmPromotion")
public class GmPromotionController {
//	@Autowired
//	private GmPromotionService gmPromotionService;
//
//	@GetMapping()
//	@RequiresPermissions("goodsManager:gmPromotion:gmPromotion")
//	String GmPromotion(){
//	    return "goodsManager/gmPromotion/gmPromotion";
//	}
//
//	@ResponseBody
//	@GetMapping("/list")
//	@RequiresPermissions("goodsManager:gmPromotion:gmPromotion")
//	public PageUtils list(@RequestParam Map<String, Object> params){
//		//查询列表数据
//        Query query = new Query(params);
//		List<GmPromotionDO> gmPromotionList = gmPromotionService.list(query);
//		int total = gmPromotionService.count(query);
//		PageUtils pageUtils = new PageUtils(gmPromotionList, total);
//		return pageUtils;
//	}
//
//	@GetMapping("/add")
//	@RequiresPermissions("goodsManager:gmPromotion:add")
//	String add(){
//	    return "goodsManager/gmPromotion/add";
//	}
//
//	@GetMapping("/edit/{id}")
//	@RequiresPermissions("goodsManager:gmPromotion:edit")
//	String edit(@PathVariable("id") Long id,Model model){
//		GmPromotionDO gmPromotion = gmPromotionService.get(id);
//		model.addAttribute("gmPromotion", gmPromotion);
//	    return "goodsManager/gmPromotion/edit";
//	}
//
//	/**
//	 * 保存
//	 */
//	@ResponseBody
//	@PostMapping("/save")
//	@RequiresPermissions("goodsManager:gmPromotion:add")
//	public R save( GmPromotionDO gmPromotion){
//		if(gmPromotionService.save(gmPromotion)>0){
//			return R.ok();
//		}
//		return R.error();
//	}
//	/**
//	 * 修改
//	 */
//	@ResponseBody
//	@RequestMapping("/update")
//	@RequiresPermissions("goodsManager:gmPromotion:edit")
//	public R update( GmPromotionDO gmPromotion){
//		gmPromotionService.update(gmPromotion);
//		return R.ok();
//	}
//
//	/**
//	 * 删除
//	 */
//	@PostMapping( "/remove")
//	@ResponseBody
//	@RequiresPermissions("goodsManager:gmPromotion:remove")
//	public R remove( Long id){
//		if(gmPromotionService.remove(id)>0){
//		return R.ok();
//		}
//		return R.error();
//	}
//
//	/**
//	 * 删除
//	 */
//	@PostMapping( "/batchRemove")
//	@ResponseBody
//	@RequiresPermissions("goodsManager:gmPromotion:batchRemove")
//	public R remove(@RequestParam("ids[]") Long[] ids){
//		gmPromotionService.batchRemove(ids);
//		return R.ok();
//	}
	
}
