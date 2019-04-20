package com.bootdo.goodsManager.service;

import com.bootdo.goodsManager.domain.GmOrderDO;

import java.util.List;
import java.util.Map;

/**
 * 发货订单表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-20 14:04:15
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
