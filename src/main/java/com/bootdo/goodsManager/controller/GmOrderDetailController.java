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

import com.bootdo.goodsManager.domain.GmOrderDetailDO;
import com.bootdo.goodsManager.service.GmOrderDetailService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 订单明细表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-20 13:48:30
 */
 
@Controller
@RequestMapping("/goodsManager/gmOrderDetail")
public class GmOrderDetailController {
	@Autowired
	private GmOrderDetailService gmOrderDetailService;
	
	@GetMapping()
	@RequiresPermissions("goodsManager:gmOrderDetail:gmOrderDetail")
	String GmOrderDetail(){
	    return "goodsManager/gmOrderDetail/gmOrderDetail";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("goodsManager:gmOrderDetail:gmOrderDetail")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<GmOrderDetailDO> gmOrderDetailList = gmOrderDetailService.list(query);
		int total = gmOrderDetailService.count(query);
		PageUtils pageUtils = new PageUtils(gmOrderDetailList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("goodsManager:gmOrderDetail:add")
	String add(){
	    return "goodsManager/gmOrderDetail/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("goodsManager:gmOrderDetail:edit")
	String edit(@PathVariable("id") Long id,Model model){
		GmOrderDetailDO gmOrderDetail = gmOrderDetailService.get(id);
		model.addAttribute("gmOrderDetail", gmOrderDetail);
	    return "goodsManager/gmOrderDetail/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("goodsManager:gmOrderDetail:add")
	public R save( GmOrderDetailDO gmOrderDetail){
		if(gmOrderDetailService.save(gmOrderDetail)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("goodsManager:gmOrderDetail:edit")
	public R update( GmOrderDetailDO gmOrderDetail){
		gmOrderDetailService.update(gmOrderDetail);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmOrderDetail:remove")
	public R remove( Long id){
		if(gmOrderDetailService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmOrderDetail:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		gmOrderDetailService.batchRemove(ids);
		return R.ok();
	}
	
}
