package com.bootdo.goodsManager.dao;

import com.bootdo.goodsManager.domain.GmOrderDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 发货订单表
 * @author xyy
 * @email 1992lcg@163.com
 * @date 2019-04-04 16:01:15
 */
@Mapper
public interface GmOrderDao {

	GmOrderDO get(Long id);
	
	List<GmOrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmOrderDO gmOrder);
	
	int update(GmOrderDO gmOrder);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
