package com.bootdo.goodsManager.service;

import com.bootdo.goodsManager.domain.GmProfitDetailDO;

import java.util.List;
import java.util.Map;

/**
 * 分润明细表
 * 
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-09 15:48:52
 */
public interface GmProfitDetailService {
	
	GmProfitDetailDO get(Long id);
	
	List<GmProfitDetailDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(GmProfitDetailDO gmProfitDetail);
	
	int update(GmProfitDetailDO gmProfitDetail);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
