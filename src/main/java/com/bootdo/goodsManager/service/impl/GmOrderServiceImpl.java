package com.bootdo.goodsManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.goodsManager.dao.GmOrderDao;
import com.bootdo.goodsManager.domain.GmOrderDO;
import com.bootdo.goodsManager.service.GmOrderService;



@Service
public class GmOrderServiceImpl implements GmOrderService {
	@Autowired
	private GmOrderDao gmOrderDao;
	
	@Override
	public GmOrderDO get(Long id){
		return gmOrderDao.get(id);
	}
	
	@Override
	public List<GmOrderDO> list(Map<String, Object> map){
		return gmOrderDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return gmOrderDao.count(map);
	}
	
	@Override
	public int save(GmOrderDO gmOrder){
		return gmOrderDao.save(gmOrder);
	}
	
	@Override
	public int update(GmOrderDO gmOrder){
		return gmOrderDao.update(gmOrder);
	}
	
	@Override
	public int remove(Long id){
		return gmOrderDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return gmOrderDao.batchRemove(ids);
	}
	
}
