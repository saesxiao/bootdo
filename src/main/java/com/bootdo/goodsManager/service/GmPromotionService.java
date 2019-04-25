package com.bootdo.goodsManager.service;

import com.bootdo.goodsManager.domain.GmPromotionDO;

import java.util.List;
import java.util.Map;

/**
 * 用户晋升表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-25 08:55:54
 */
public interface GmPromotionService {
	
	GmPromotionDO get(Long id);
	
	List<GmPromotionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmPromotionDO gmPromotion);
	
	int update(GmPromotionDO gmPromotion);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
