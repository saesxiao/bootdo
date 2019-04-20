package com.bootdo.goodsManager.service;

import com.bootdo.goodsManager.domain.GmOrderDetailDO;

import java.util.List;
import java.util.Map;

/**
 * 订单明细表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-20 13:48:30
 */
public interface GmOrderDetailService {
	
	GmOrderDetailDO get(Long id);
	
	List<GmOrderDetailDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmOrderDetailDO gmOrderDetail);
	
	int update(GmOrderDetailDO gmOrderDetail);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
