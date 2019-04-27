package com.bootdo.goodsManager.controller;

import java.util.HashMap;
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

import com.bootdo.goodsManager.domain.GmUserStatusDO;
import com.bootdo.goodsManager.service.GmUserStatusService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 记录用户状态表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-27 09:11:10
 */
 
@Controller
@RequestMapping("/goodsManager/gmUserStatus")
public class GmUserStatusController {
	@Autowired
	private GmUserStatusService gmUserStatusService;

	
	@ResponseBody
	@RequestMapping("/get")
	public Object getUserStatus(String userId){
		Long thisUserId = null;
		try {
			thisUserId = Long.parseLong(userId);
		}catch (Exception e){
			R.error("用户id错误");

		}
		if(thisUserId!=null){
			Map<String,Object> query = new HashMap<>();
			query.put("userId",thisUserId);
			List<GmUserStatusDO> userStatusList = gmUserStatusService.list(query);
			if(userStatusList!=null){
				GmUserStatusDO status = userStatusList.get(0);
				return status;
			}
		}
		return R.error("暂无用户状态");
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Object updateStatus(String userId,String oldStatus,String newStatus){
		Long thisUserId = null;
		GmUserStatusDO userStatus = null;
		try {
			thisUserId = Long.parseLong(userId);
			Map<String,Object> query = new HashMap<>();
			query.put("userId",thisUserId);
			List<GmUserStatusDO> userStatusList = gmUserStatusService.list(query);
			if(userStatusList!=null){
				userStatus = userStatusList.get(0);
			}
		}catch (Exception e){
			R.error("用户id错误");
		}
		try {
			if(userStatus!=null){
				if(oldStatus.equals(userStatus.getPromotionStatus())){
					userStatus.setPromotionStatus(newStatus);
					if(gmUserStatusService.update(userStatus)>0){
						return R.ok("用户状态更改成功");
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return R.error("用户状态更改失败");
	}
	
}
