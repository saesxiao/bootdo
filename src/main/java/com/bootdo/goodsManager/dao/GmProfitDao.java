package com.bootdo.goodsManager.dao;

import com.bootdo.goodsManager.domain.GmProfitDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 分润表
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-09 15:48:52
 */
@Mapper
public interface GmProfitDao {

	GmProfitDO get(Long id);

	GmProfitDO getByUserId(Long userId);
	
	List<GmProfitDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmProfitDO gmProfit);
	
	int update(GmProfitDO gmProfit);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
