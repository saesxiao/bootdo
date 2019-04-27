package com.bootdo.goodsManager.service;

import com.bootdo.goodsManager.domain.GmUserStatusDO;

import java.util.List;
import java.util.Map;

/**
 * 记录用户状态表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-27 09:11:10
 */
public interface GmUserStatusService {
	
	GmUserStatusDO get(Long id);
	
	List<GmUserStatusDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmUserStatusDO gmUserStatus);
	
	int update(GmUserStatusDO gmUserStatus);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
