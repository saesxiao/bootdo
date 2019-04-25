package com.bootdo.goodsManager.dao;

import com.bootdo.goodsManager.domain.GmPromotionDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户晋升表
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-25 08:55:54
 */
@Mapper
public interface GmPromotionDao {

	GmPromotionDO get(Long id);
	
	List<GmPromotionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmPromotionDO gmPromotion);
	
	int update(GmPromotionDO gmPromotion);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
