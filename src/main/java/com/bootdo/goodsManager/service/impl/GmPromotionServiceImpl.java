package com.bootdo.goodsManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.goodsManager.dao.GmPromotionDao;
import com.bootdo.goodsManager.domain.GmPromotionDO;
import com.bootdo.goodsManager.service.GmPromotionService;



@Service
public class GmPromotionServiceImpl implements GmPromotionService {
	@Autowired
	private GmPromotionDao gmPromotionDao;
	
	@Override
	public GmPromotionDO get(Long id){
		return gmPromotionDao.get(id);
	}
	
	@Override
	public List<GmPromotionDO> list(Map<String, Object> map){
		return gmPromotionDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return gmPromotionDao.count(map);
	}
	
	@Override
	public int save(GmPromotionDO gmPromotion){
		return gmPromotionDao.save(gmPromotion);
	}
	
	@Override
	public int update(GmPromotionDO gmPromotion){
		return gmPromotionDao.update(gmPromotion);
	}
	
	@Override
	public int remove(Long id){
		return gmPromotionDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return gmPromotionDao.batchRemove(ids);
	}
	
}
