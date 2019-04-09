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

import com.bootdo.goodsManager.domain.GmProfitDetailDO;
import com.bootdo.goodsManager.service.GmProfitDetailService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 分润明细表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-09 15:48:52
 */
 
@Controller
@RequestMapping("/goodsManager/gmProfitDetail")
public class GmProfitDetailController {
	@Autowired
	private GmProfitDetailService gmProfitDetailService;
	
	@GetMapping()
	@RequiresPermissions("goodsManager:gmProfitDetail:gmProfitDetail")
	String GmProfitDetail(){
	    return "goodsManager/gmProfitDetail/gmProfitDetail";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("goodsManager:gmProfitDetail:gmProfitDetail")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<GmProfitDetailDO> gmProfitDetailList = gmProfitDetailService.list(query);
		int total = gmProfitDetailService.count(query);
		PageUtils pageUtils = new PageUtils(gmProfitDetailList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("goodsManager:gmProfitDetail:add")
	String add(){
	    return "goodsManager/gmProfitDetail/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("goodsManager:gmProfitDetail:edit")
	String edit(@PathVariable("id") Long id,Model model){
		GmProfitDetailDO gmProfitDetail = gmProfitDetailService.get(id);
		model.addAttribute("gmProfitDetail", gmProfitDetail);
	    return "goodsManager/gmProfitDetail/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("goodsManager:gmProfitDetail:add")
	public R save( GmProfitDetailDO gmProfitDetail){
		if(gmProfitDetailService.save(gmProfitDetail)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("goodsManager:gmProfitDetail:edit")
	public R update( GmProfitDetailDO gmProfitDetail){
		gmProfitDetailService.update(gmProfitDetail);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmProfitDetail:remove")
	public R remove( Long id){
		if(gmProfitDetailService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("goodsManager:gmProfitDetail:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		gmProfitDetailService.batchRemove(ids);
		return R.ok();
	}
	
}
