package com.bootdo.goodsManager.service;

import com.bootdo.goodsManager.domain.GmOrderDO;

import java.util.List;
import java.util.Map;

/**
 * 发货订单表
 * 
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-04 16:01:15
 */
public interface GmOrderService {
	
	GmOrderDO get(Long id);
	
	List<GmOrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmOrderDO gmOrder);
	
	int update(GmOrderDO gmOrder);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
