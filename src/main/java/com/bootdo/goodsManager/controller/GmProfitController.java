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

import com.bootdo.goodsManager.domain.GmProfitDO;
import com.bootdo.goodsManager.service.GmProfitService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 分润表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-09 15:48:52
 */
 
@Controller
@RequestMapping("/goodsManager/gmProfit")
public class GmProfitController {
	@Autowired
	private GmProfitService gmProfitService;
	
	@GetMapping()
	@RequiresPermissions("goodsManager:gmProfit:gmProfit")
	String GmProfit(){
	    return "goodsManager/gmProfit/gmProfit";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("goodsManager:gmProfit:gmProfit")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<GmProfitDO> gmProfitList = gmProfitService.list(query);
		int total = gmProfitService.count(query);
		PageUtils pageUtils = new PageUtils(gmProfitList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("goodsManager:gmProfit:add")
	String add(){
	    return "goodsManager/gmProfit/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("goodsManager:gmProfit:edit")
	String edit(@PathVariable("id") Long id,Model model){
		GmProfitDO gmProfit = gmProfitService.get(id);
		model.addAttribute("gmProfit", gmProfit);
	    return "goodsManager/gmProfit/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("goodsManager:gmProfit:add")
	public R save( GmProfitDO gmProfit){
		if(gmProfitService.save(gmProfit)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("goodsManager:gmProfit:edit")
	public R update( GmProfitDO gmProfit){
		gmProfitService.update(gmProfit);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmProfit:remove")
	public R remove( Long id){
		if(gmProfitService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmProfit:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		gmProfitService.batchRemove(ids);
		return R.ok();
	}
	
}
