package com.bootdo.goodsManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.goodsManager.dao.GmProfitDao;
import com.bootdo.goodsManager.domain.GmProfitDO;
import com.bootdo.goodsManager.service.GmProfitService;



@Service
public class GmProfitServiceImpl implements GmProfitService {
	@Autowired
	private GmProfitDao gmProfitDao;
	
	@Override
	public GmProfitDO get(Long id){
		return gmProfitDao.get(id);
	}

	@Override
	public GmProfitDO getByUserId(Long userId) {
		return gmProfitDao.getByUserId(userId);
	}

	@Override
	public List<GmProfitDO> list(Map<String, Object> map){
		return gmProfitDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return gmProfitDao.count(map);
	}
	
	@Override
	public int save(GmProfitDO gmProfit){
		return gmProfitDao.save(gmProfit);
	}
	
	@Override
	public int update(GmProfitDO gmProfit){
		return gmProfitDao.update(gmProfit);
	}
	
	@Override
	public int remove(Long id){
		return gmProfitDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return gmProfitDao.batchRemove(ids);
	}
	
}
